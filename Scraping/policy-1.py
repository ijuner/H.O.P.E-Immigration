import logging
import requests
from bs4 import BeautifulSoup
from DatabaseManager import DatabaseManager
from datetime import datetime

logging.basicConfig(level=logging.DEBUG, format='%(asctime)s - %(levelname)s - %(message)s')

def get_update_time_from_canada(url):
  response = requests.get(url)
  if response.status_code == 200:
    soup = BeautifulSoup(response.text, 'html.parser')
    timeArray = soup.find_all('time')
    formatted_date = timeArray[0].get_text().replace("-", "")
    return int(formatted_date)
  else:
    print(f"Failed to retrieve webpage: Status code {response.status_code}")

def get_links_from_canada(url):
  response = requests.get(url)
  if response.status_code == 200:
    soup = BeautifulSoup(response.text, 'html.parser')
    #print(soup.prettify())
    ultag = soup.find_all('ul')
    return ultag[3].find_all('li')
  else:
    print(f"Failed to retrieve webpage: Status code {response.status_code}")

def main():
  db_manager = DatabaseManager('172.105.15.207', 'team3', 'Desire2Learn#2024', 'project')

  # select
  result = db_manager.select_data('policy_main', ['url', 'lastUpdateDate'], 'strategy=1')
  for urlObj in result:
    url = urlObj['url']
    savedlastUpdateDate = urlObj['lastUpdateDate']
    logging.info('start to fetch from %s', url)
    logging.info('last update date in db is %s', savedlastUpdateDate);
    pageLastUpdateTime = get_update_time_from_canada(url)
    logging.info('last update date in page is %s', pageLastUpdateTime)
    if pageLastUpdateTime > savedlastUpdateDate:
      logging.info('last update date in page is newer than last update date in database')
      logging.info('start to update')
      litags = get_links_from_canada(url)
      policys = db_manager.select_data('policy_main', ['url'])
      policy_set = set(dic[next(iter(dic))] for dic in policys)
      for litag in litags:
        href = 'https://www.canada.ca/' + litag.a.get('href')
        title = litag.a.get_text()
        status = 1
        if title.endswith("Archived"):
          status = 0
        if href not in policy_set:
          db_manager.insert_data('policy_main', {
            'url':  href, 
            'title': title, 
            'lastUpdateDate': 0, 
            'status': status,
            'strategy': 2,
            'createTime': datetime.now(), 
            'updateTime': datetime.now()
          })
          logging.info('insert url %s', href)
        else:
          logging.info('url %s already existed, skip', href)
        db_manager.update_data('policy_main', {'lastUpdateDate':pageLastUpdateTime}, 'strategy=1')
        logging.info('last update date in db updated to %s', pageLastUpdateTime)
      else:
        logging.info('last update date in page is equal to last update date in database')
    logging.info('policy scrapping finished')
    logging.info('===================================================================================================')

    # close connection
    db_manager.close()

if __name__ == '__main__':
    main()
      
  
  
  
 #'https://www.ontario.ca/page/oinp-employer-job-offer-foreign-worker-stream')
 #'https://www.canada.ca/en/immigration-refugees-citizenship/services/immigrate-canada/provincial-nominees.html')