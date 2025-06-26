import logging
import requests
import re
from bs4 import BeautifulSoup
from DatabaseManager import DatabaseManager
from datetime import datetime

logging.basicConfig(level=logging.DEBUG, format='%(asctime)s - %(levelname)s - %(message)s')

def get_update_time_from_canada(url):
  try:
    response = requests.get(url) # get response from url
    response.raise_for_status()  # This will raise an HTTPError if the HTTP request returned an unsuccessful status code
    soup = BeautifulSoup(response.text, 'html.parser') # parse html content
    timeArray = soup.find_all('time') # get time section from html content
    # if there is no time section, return Node
    if not timeArray:
      logging.ERROR("cannot find time section in html %s", soup.prettify())
      return None

    formatted_date = timeArray[0].get_text().replace("-", "") # get date value from data section
    return int(formatted_date) # turn the value to integer and return
  except Exception as e:
    logging.error(f"Failed to retrieve webpage: {e}")
    return None

def get_content_from_canada(url):
  try:
    response = requests.get(url) # get response from url
    response.raise_for_status()  # This will raise an HTTPError if the HTTP request returned an unsuccessful status code
    soup = BeautifulSoup(response.text, 'html.parser')  # parse html content
    h1tag = soup.find_all('h1') # find all h1 tags
    if not h1tag:
      logging.ERROR("cannot find main section in html %s", soup.prettify())
      return None
    mainSection = soup.find_all('main') # find main section
    if not mainSection:
      logging.ERROR("cannot find main section in html %s", soup.prettify())
      return None
    content = mainSection[0].find_all("div") # find all divs in main
    if not content:
      logging.ERROR("there's no div in main section %s", mainSection[0].prettify())
      return None
    return {'title': h1tag[0].get_text(), 'content': str(content)}
  except Exception as e:
    logging.error(f"Failed to retrieve webpage: {e}")
    return None

def translate(text, lang):
  from google.cloud import translate_v2 as translate
  translate_client = translate.Client()
  if isinstance(text, bytes):
    text = text.decode("utf-8")
  result = translate_client.translate(text, target_language=lang)
  return result["translatedText"]

def createOrUpdate(db_manager, id, lang, title, content, updateTime):
  condition = 'policy_id=' + str(id) + ' and lang="' + lang + '"'
  result = db_manager.select_data('policy_news', ['id'], condition)
  logging.info("fetch policy news with policy id = %s and lang = %s ", id, lang)
  if result:
    newsId = result[0]['id']
    logging.info("policy news exists with id = %d, start to update", newsId)
    db_manager.update_data('policy_news', {
      'title':title,
      'content': content,
      'updateTime': updateTime
      }, 'id=' + str(newsId))
  else:
    logging.info("policy news doesn't exist, start to insert")
    db_manager.insert_data('policy_news', {
      'policy_id': id,
      'lang' : lang,
      'title': title,
      'content': content,
      'createTime': datetime.now(),
      'updateTime': updateTime
    })


def main():
  # obtain db link
  db_manager = DatabaseManager('172.105.15.207', 'team3', 'Desire2Learn#2024', 'project')
  logging.info("start to scrap strategy 2")
  # select url which strategy value 2
  result = db_manager.select_data('policy_main', ['id', 'url', 'lastUpdateDate'], 'strategy=2 and status=1')
  logging.info("there is " + str(len(result)) + ' policies')
  # iterate results
  for urlObj in result:
    policyId = urlObj['id']
    url = urlObj['url']
    savedlastUpdateDate = urlObj['lastUpdateDate']
    logging.info('last update date in db is %s', savedlastUpdateDate);
    logging.info('start to fetch from %s', url)
    pageLastUpdateTime = get_update_time_from_canada(url)
    logging.info('last update date in page is %s', pageLastUpdateTime)
    if pageLastUpdateTime is None:
      logging.info('fetch last update date error')
      db_manager.insert_data('policy_scraping_record', {
        'policy_id': policyId,
        'action': 'error',
        'createTime': datetime.now(), 
        'updateTime': datetime.now(),
        'status': 0
      })
    elif pageLastUpdateTime > savedlastUpdateDate:
      logging.info('last update date in page is newer than last update date in database')
      logging.info('start to update')
      contentPart = get_content_from_canada(url)
      if contentPart is None:
        logging.info('fetch last update date error')
        db_manager.insert_data('policy_scraping_record', {
          'policy_id': policyId,
          'action': 'error',
          'createTime': datetime.now(), 
          'updateTime': datetime.now(),
          'status': 0
        })
      else:
        title = contentPart['title']
        if title.startswith('\n'):
          title = title[1:]
        content = contentPart['content']
        pattern = re.compile(r'\sclass="[^"]*"')
        content = pattern.sub("", content)
        createOrUpdate(db_manager, policyId, "en", title, content, pageLastUpdateTime)
        lang = 'zh-CN'
        title_chs = translate(title, lang)
        content_chs = translate(content, lang)
        createOrUpdate(db_manager, policyId, lang, title_chs, content_chs, pageLastUpdateTime)
        db_manager.update_data('policy_main', {
          'lastUpdateDate':pageLastUpdateTime, 
          'updateTime': datetime.now()
        }, 'id=' + str(policyId))
        db_manager.insert_data('policy_scraping_record', {
          'policy_id': policyId,
          'action': 'Updated',
          'createTime': datetime.now(), 
          'updateTime': datetime.now(),
          'status': 1
        })
        logging.info('last update date in db updated to %s', pageLastUpdateTime)
    else:
      db_manager.insert_data('policy_scraping_record', {
        'policy_id': policyId,
        'action': 'No change',
        'createTime': datetime.now(), 
        'updateTime': datetime.now(),
        'status': 1
      })
      logging.info('last update date in page is equal to last update date in database')
  logging.info('policy scrapping finished')
  logging.info('===================================================================================================')
  # close connection
  db_manager.close()

# 
if __name__ == '__main__':
    main()