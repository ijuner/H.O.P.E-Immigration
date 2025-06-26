package ca.conestoga.project.product.controller.api;

import ca.conestoga.project.annotation.Authen;
import ca.conestoga.project.common.*;
import ca.conestoga.project.dao.program.ProgramDao;
import ca.conestoga.project.dao.program.UserReportDao;
import ca.conestoga.project.entity.policy.Policy;
import ca.conestoga.project.entity.policy.PolicyNews;
import ca.conestoga.project.entity.policy.ProgramInvitation;
import ca.conestoga.project.product.bo.policy.*;
import ca.conestoga.project.product.bo.user.*;
import ca.conestoga.project.product.controller.HopeController;
import ca.conestoga.project.service.ClientService;
import ca.conestoga.project.service.PolicyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * controller for api
 */
@RestController
@RequestMapping("/api/")
public class ApiController extends HopeController {

    // date formatter to format date to string
    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private final ProgramDao programDao;
    private final UserReportDao userReportDao;

    /**
     * get policy list
     *
     * @param  page & lang & size
     * @return policy list
     */
    //@RequestMapping("policy/news/list")
    //public RespBody<?> getPolicyNewsList(@RequestBody(required = false) RPolicyNewsList r) {
    @GetMapping("policy/news/list")
    public RespBody<?> getPolicyNewsList(@RequestParam(required = false, defaultValue = "1")int page,
                                         @RequestParam(required = false, defaultValue = "en")String lang,
                                         @RequestParam(required = false, defaultValue = "0")int size) {
        if (size == 0) {
            size = Const.DEFAULT_ADMIN_PAGE_SIZE;
        }
        if ("en-us".equalsIgnoreCase(lang)) {
            lang = LanguageEnum.EN.getValue();
        } else if ("zh-cn".equalsIgnoreCase(lang)) {
            lang = LanguageEnum.CH.getValue();
        }
        final String language = lang;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "lastUpdateDate");
        Page<Policy> list = policyService.getPolicyList(pageable);
        ApiPolicyNewsList apiPolicyNewsList = new ApiPolicyNewsList();
        apiPolicyNewsList.setLang(lang);
        apiPolicyNewsList.setPage(page);
        apiPolicyNewsList.setPageSize(size);
        apiPolicyNewsList.setTotalPage(list.getTotalPages());
        List<ApiPolicyNewsList.PolicyNews> policyNewsList = list.toList().stream().map(policy -> {
            ApiPolicyNewsList.PolicyNews a = new ApiPolicyNewsList.PolicyNews();
            PolicyNews policyNews = policyService.findPolicyNewsByPolicyAndLang(policy, language);
            a.setId(policyNews.getId());
            a.setTitle(policyNews.getTitle());
            String date = Integer.toString(policy.getLastUpdateDate());
            a.setUpdateTime(date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6));
            return a;
        }).collect(Collectors.toList());
        apiPolicyNewsList.setList(policyNewsList);
        return RespBody.isOk().data(apiPolicyNewsList);
    }

    /**
     * get policy content by id
     *
     * @param id policy id
     * @return policy content of the id
     */
    @RequestMapping("policy/news/content/{id}")
    public RespBody<?> getPolicyNewsById(@PathVariable int id) {
        PolicyNews policyNews = policyService.getPolicyNewsById(id);
        if (policyNews == null) {
            return RespBody.isFail().msg(ErrorEnum.DATA_NOT_FOUND);
        }
        return RespBody.isOk().data(policyNews);
    }

    /**
     *  simple login implementation
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public RespBody<?> login(@RequestBody ApiUserEntity userEntity) {
        return clientService.login(userEntity);
    }

    /**
     *  simple logout implementation
     * @return
     */
    @Authen
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public RespBody<?> logout() {
        // user must have authenticated or else they will respond with 401
        String token = getLoginToken();
        return clientService.logout(token);
    }

    /**
     * register new user
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public RespBody<?> Register(@RequestBody ApiUserEntity userEntity) {
        return clientService.register(userEntity);
    }

    @GetMapping(value = "program/conditions")
    public RespBody<?> getProgramsConditions() {
        // response all conditions
        return policyService.getProgramsConditions();
    }
    /**
     * get program list
     *
     * @param request
     * @return program list
     */
    @GetMapping("programs")
    public RespBody<?> getProgramList(@RequestBody(required = false) ApiProgramsRequest request) {
        return policyService.getProgramsList(request);
    }

    @GetMapping("program/{programId}")
    public RespBody<?> getProgram(@PathVariable int programId) {
        return policyService.getprogramById(programId);
//        ApiProgramsResponse response = new ApiProgramsResponse().setName("Canada Express Entry")
//                .setContent("<h2 id=\"section-5\">Scoring factors</h2><p>Once you register an expression of interest under this stream you will be assigned points based on the following factors.</p>" +
//                        "<p>If you are invited to apply, you will be required to submit specific documents to support each scoring factor for which you received points. Scoring factors are not the same as stream criteria. You must meet all criteria for this stream and provide the mandatory documents with your application. Refer to the <a href=\"/document/oinp-document-checklists/employer-job-offer-foreign-worker-stream-applicant-checklist\">document checklist</a> for the Employer Job Offer: Foreign Worker stream.</p>" +
//                        "<h4>Employment / labour market factors</h4>" +
//                        "<div class=\"row\"><div class=\"columns small-12 medium-4\"><h5>Job offer: <abbr title=\"National Occupation Classification\">NOC</abbr> TEER category</h5><ul><li><abbr title=\"National Occupation Classification\">NOC</abbr> TEER 0 or 1 – 10 Points</li><li><abbr title=\"National Occupation Classification\">NOC</abbr> TEER 2 or 3 – 8 Points</li><li><abbr title=\"National Occupation Classification\">NOC</abbr> TEER 4 – 0 Points</li><li><abbr title=\"National Occupation Classification\">NOC</abbr> TEER 5 – 0 Points</li></ul></div><div class=\"columns small-12 medium-4\"><h5>Job offer:<br><abbr title=\"National Occupation Classification\">NOC</abbr> broad occupational category</h5><ul><li>Occupational Category 0, 2, 3 – 10 points</li><li>Occupational Category 7 – 7 points</li><li>Occupational Category 1, 9 – 5 points</li><li>Occupational Category 4, 8 – 4 points</li><li>Occupational Category 5, 6 – 3 points</li></ul></div><div class=\"columns small-12 medium-4\"><h5>Job offer: wage</h5><ul><li>$40 per hour or higher – 10&nbsp;points</li><li>$35 to $39.99 per hour – 8&nbsp;points</li><li>$30 to $34.99 per hour – 7&nbsp;points</li><li>$25 to $29.99 per hour – 6&nbsp;points</li><li>$20 to $24.99 per hour – 5&nbsp;points</li><li>Less than $20 per hour – 0&nbsp;points</li></ul></div></div>" +
//                        "<div class=\"row\"><div class=\"columns small-12 medium-4\"><h5>Work permit status</h5><ul><li>With valid work permit – 10 points</li><li>Without valid work permit – 0 points</li></ul><p>The work permit must confer legal status.</p></div><div class=\"columns small-12 medium-4\"><h5>Job tenure with job offer employer</h5><ul><li>6 months or more working in job offer position&nbsp;– 3 points</li><li>Less than 6 months working in job offer position or not currently working in position&nbsp;– 0 points</li></ul><p>The work in the job offer position must have occurred within Ontario.</p></div><div class=\"columns small-12 medium-4\"><h5>Canadian work experience: earnings history</h5><p>Based on a Notice of Assessment issued by the Canada Revenue Agency in the last five years.</p><ul><li>$40k or more earnings in a year – 3&nbsp;points</li><li>Less than $40k earnings in a year – 0&nbsp;points</li></ul></div></div>" +
//                        "<h4>Language</h4>" +
//                        "<div class=\"small-up-1 medium-up-2\"><div class=\"column\"><h5>Official language ability</h5><ul><li>CLB 9 or higher – 10 points</li><li>CLB 8 – 6 points</li><li>CLB 7 – 4 points</li><li>CLB 6 or lower – 0 points</li></ul></div><div class=\"column\"><h5>Knowledge of official languages</h5><ul><li>2 Official Languages – 10 points</li><li>1 Official Language – 5 points</li></ul></div></div>" +
//                        "<h4>Regionalization</h4>" +
//                        "<div class=\"row\"><div class=\"columns small-12 medium-6\"><h5>Regional immigration: location of job offer</h5><ul><li>Northern Ontario – 10 points</li><li>Other areas outside <abbr title=\"Greater Toronto Area\">GTA</abbr> (except Northern Ontario) – 8 points</li><li>Inside <abbr title=\"Greater Toronto Area\">GTA</abbr> (except Toronto) – 3 points</li><li>Toronto – 0 points</li></ul></div></div>" +
//                        "<p>For Regionalization scoring factors, the regions are defined as follows:</p>" +
//                        "<ul><li>Northern Ontario, includes the following Census Divisions:<ul><li>Muskoka</li><li>Haliburton</li><li>Nipissing</li><li>Parry Sound</li><li>Manitoulin</li><li>Sudbury</li><li>Greater Sudbury/Grand Sudbury</li><li>Timiskaming</li><li>Cochrane</li><li>Algoma</li><li>Thunder Bay</li><li>Rainy River</li><li>Kenora</li></ul></li><li>Other areas outside of Toronto and the Greater Toronto Area (<abbr>GTA</abbr>), except Northern Ontario, includes all Ontario regions except those listed as part of Toronto, the Greater Toronto Area and Northern Ontario</li><li>Greater Toronto Area (except Toronto), includes the regional municipalities of Durham, Halton, Peel and York</li><li>Toronto, includes the City of Toronto</li></ul>");
//        return RespBody.isOk().data(response);
    }

    @GetMapping("report/my")
    public RespBody<?> myScore() {
        List<ApiMyReport> myReports = new ArrayList<ApiMyReport>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 查询所有项目
        programDao.findAll().forEach(programs -> {// 循环项目
            ApiMyReport report = new ApiMyReport();
            // 根据项目id 查询最新报告
            userReportDao.findByProgramId(programs.getId()).ifPresentOrElse(r-> {
                report.setId(r.getId());
                report.setProgramId(r.getProgram().getId());
                report.setProgramName(r.getProgramName());
                report.setScore(r.getScore());
                report.setLastInviteScore(r.getInvitationScore());
                // build chart
                // get last invitation
                List<ProgramInvitation> invitationList = programs.getInvitations().stream().sorted(Comparator.comparing(ProgramInvitation::getInvitedDate)).collect(Collectors.toList());
                ApiReportCharData reportCharData = new ApiReportCharData();
                String[] xLabel;
                int[] data;
                int[] iData;
                if(invitationList.isEmpty()) {
                  data = new int[1];
                  iData = new int[1];
                  xLabel = new String[1];
                  // 没有邀请记录时，增加一个个人得分的点
                    xLabel[0] = "My Score";
                    data[0] = r.getInvitationScore();
                }else{
                    xLabel = new String[invitationList.size()];
                    data = new int[invitationList.size()];
                    iData = new int[invitationList.size()];
                    AtomicInteger i = new AtomicInteger();
                    invitationList.forEach(invit -> {
                        xLabel[i.get()] = dateFormat.format(invit.getInvitedDate());
                        iData[i.get()] = invit.getMinimumScore();
                        data[i.get()] = r.getScore();
                        i.getAndIncrement();
                    });
                }

                reportCharData.setLabels(xLabel);
                reportCharData.setDatasets(List.of(new ApiChartDataset().setLabel("Last Invasion").setBackgroundColor("#f87979").setData(iData),
                        new ApiChartDataset().setLabel("Your score").setBackgroundColor("black").setData(data)));
                report.setChartSets(reportCharData);
            }, ()-> {
                report.setProgramId(programs.getId());
                report.setProgramName(programs.getName());
                report.setLastInviteScore(programs.getInvitations().stream().sorted(Comparator.comparing(ProgramInvitation::getInvitedDate)).collect(Collectors.toList()).stream().findFirst().orElse(new ProgramInvitation()).getMinimumScore());
                report.setChartSets(null);
            });
            myReports.add(report);
        });
        return RespBody.isOk().data(myReports);
    }

    @GetMapping("report/{reportId}")
    public RespBody<?> report(@PathVariable int reportId) {
        return clientService.findReportById(reportId);
    }
    /**
     * get dictionary
     * @return
     */
    @GetMapping("/common/dictionary")
    public RespBody<?> getDictionary(@RequestParam String key) {
        // if multiple keys exist
        String[] keys = key.split(",");
        Map dictionary = new HashMap();
        for (String s : keys) {
            dictionary.put(s, Context.dictionary(s));
        }
        return RespBody.isOk().data(dictionary);
    }



    /**
     * This part for update client profile information to database one by one
     * @param apiEntity from front end
     * @return
     */
    @RequestMapping(value = "UpdateClientBasic", method = RequestMethod.POST)
    public RespBody<?> UpdateClientBasic(@RequestBody ApiClientBasicEntity apiEntity) {

        return clientService.UpdateClientBasic(apiEntity, getLoginUser().getClientId());
    }
    @RequestMapping(value = "UpdateEducationExp", method = RequestMethod.POST)
    public RespBody<?> UpdateEducationExp(@RequestBody ApiEducationExperienceEntity apiEntity) {

        return clientService.UpdateEducationExp(apiEntity, getLoginUser().getClientId());
    }

    @PostMapping(value = "deleteEducationExp/{id}")
    public RespBody<?> deleteEducationExp(@PathVariable int id) {

        return clientService.EducationExp(id, getLoginUser().getClientId());
    }


    @RequestMapping(value = "UpdateExtraInfo", method = RequestMethod.POST)
    public RespBody<?> UpdateExtraInfo(@RequestBody ApiExtraInfoEntity apiEntity) {

        return clientService.UpdateExtraInfo(apiEntity, getLoginUser().getClientId());
    }
    @RequestMapping(value = "UpdateLanguageAbility", method = RequestMethod.POST)
    public RespBody<?> UpdateLanguageAbility(@RequestBody ApiLanguageAbilityEntity apiEntity) {

        return clientService.UpdateLanguageAbility(apiEntity, getLoginUser().getClientId());
    }

    @PostMapping(value = "deleteLanguageAbility/{id}")
    public RespBody<?> deleteLanguageAbility(@PathVariable int id) {

        return clientService.deleteLanguageAbility(id, getLoginUser().getClientId());
    }
    @RequestMapping(value = "UpdateSpouseInfo", method = RequestMethod.POST)
    public RespBody<?> UpdateSpouseInfo(@RequestBody ApiSpouseInfoEntity apiEntity) {

        return clientService.UpdateSpouseInfo(apiEntity,getLoginUser().getClientId());
    }
    @RequestMapping(value = "UpdateWorkExp", method = RequestMethod.POST)
    public RespBody<?> UpdateWorkExp(@RequestBody ApiWorkExperienceEntity apiEntity) {

        return clientService.UpdateWorkExp(apiEntity,getLoginUser().getClientId());
    }

    @RequestMapping(value = "deleteWorkExp/{id}", method = RequestMethod.POST)
    public RespBody<?> UpdateWorkExp(@PathVariable int id) {

        return clientService.deleteWorkExp(id, getLoginUser().getClientId());
    }

    @RequestMapping(value = "assessment/{programId}", method = RequestMethod.POST)
    public RespBody<?> assessment(@PathVariable int programId) {
        return clientService.assessment(programId, getLoginUser());
    }

    /**
     * generate document
     * @return
     */
    @GetMapping("/document/generate")
    public RespBody<?> createDocument(@RequestParam String type) {
        return clientService.createDocument(getLoginUser(), type);
    }

    /**
     * get document
     * @return
     */
    @GetMapping("/document/get")
    public RespBody<?> getDocument(@RequestParam String type) {
        return clientService.getDocument(getLoginUser(), type);
    }

    private final PolicyService policyService;
    private final ClientService clientService;

    /**
     * constructor for object injection
     *
     * @param policyService Service for actions of policy
     */
    public ApiController(PolicyService policyService, ClientService clientService, ProgramDao programDao, UserReportDao userReportDao) {
        this.policyService = policyService;
        this.clientService = clientService;
        this.programDao = programDao;
        this.userReportDao = userReportDao;
    }
}
