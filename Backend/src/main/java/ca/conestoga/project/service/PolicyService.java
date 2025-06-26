package ca.conestoga.project.service;

import ca.conestoga.project.common.ErrorEnum;
import ca.conestoga.project.common.RespBody;
import ca.conestoga.project.dao.policy.PolicyDao;
import ca.conestoga.project.dao.policy.PolicyNewsDao;
import ca.conestoga.project.dao.policy.ScrapingRecordDao;
import ca.conestoga.project.dao.program.ProgramDao;
import ca.conestoga.project.entity.policy.Policy;
import ca.conestoga.project.entity.policy.PolicyNews;
import ca.conestoga.project.entity.policy.Programs;
import ca.conestoga.project.entity.policy.ScrapingRecord;
import ca.conestoga.project.product.bo.policy.ApiProgramsRequest;
import ca.conestoga.project.product.bo.policy.ApiProgramConditions;
import ca.conestoga.project.product.bo.policy.ApiProgramsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for actions of policy
 */
@Service
public class PolicyService {

    private final ProgramDao programDao;

    /**
     * find policy by id
     *
     * @param id policy id
     * @return policy object
     */
    public Policy getPolicyById(Integer id) {
        return policyDao.findById(id).orElse(null);
    }

    /**
     * create a policy
     * @param title policy title
     * @param url policy url
     * @return created result
     */
    public RespBody<?> createPolicy(String title, String url) {
        Policy policy = new Policy();
        policy.setTitle(title);
        policy.setUrl(url);
        policy.setStatus(Policy.StatusEnum.NORMAL.getValue());
        policyDao.save(policy);
        return RespBody.isOk();
    }

    /**
     * delete policy by id
     * @param id the id of policy needs to be deleted
     * @return delete result
     */
    public RespBody<?> deletePolicy(int id) {
        Policy policy = policyDao.findById(id).orElse(null);
        if (policy == null) {
            return RespBody.isFail().msg(ErrorEnum.DATA_NOT_FOUND);
        }
        policy.setStatus(Policy.StatusEnum.DELETE.getValue());
        policyDao.save(policy);
        return RespBody.isOk();
    }

    /**
     * get policy list by page
     * @param pageable current page
     * @return the policy news list of the page
     */
    public Page<Policy> getPolicyList(Pageable pageable) {
        return policyDao.findAllByStrategyAndStatus(2, 1, pageable);
    }

    public PolicyNews findPolicyNewsByPolicyAndLang(Policy policy, String lang) {
        return policyNewsDao.findPolicyNewsByPolicyAndLang(policy, lang);
    }

    /**
     * get policy by id
     * @param id the id to fetch policy
     * @return policy object of the id
     */
    public PolicyNews getPolicyNewsById(int id) {
        return policyNewsDao.findById(id).orElse(null);
    }


    public Page<ScrapingRecord> getRecordList(Pageable pageable) {
        return scrapingRecordDao.findAll(pageable);
    }

    /**
     * return all conditions for the programs
     * @return
     */
    public RespBody<?> getProgramsConditions() {
        // that condition has two levels of conditions
        // the first level is nation or province the second level is the type of that nation/province supported

        // todo need real data , but haven't finished , now set temporary data
        Map<String, List<ApiProgramConditions>> con = new ConcurrentHashMap<>();
        List<Programs> programs = programDao.findAll();
        programs.forEach(program -> {
                List<ApiProgramConditions> conditions = con.get(program.getProvince());
                if(conditions == null) {
                    conditions = new ArrayList<>();
                }
                conditions.add(new ApiProgramConditions().setCode(program.getName()).setName(program.getName()));
                con.put(program.getProvince(), conditions);
        });

//        // ee mock
//        con.put("CA", List.of(new ApiProgramConditions().setCode("EE").setName("Express Entry")));
//        // on pnp
//        con.put("ON", List.of(new ApiProgramConditions().setCode("PNP").setName("Ontario PNP")));
        return RespBody.isOk().data(con);
    }

    public RespBody<?> getProgramsList(ApiProgramsRequest request) {
        if (ObjectUtils.isEmpty(request)) {

            return RespBody.isOk().data(programDao.findAll().stream().map(pro -> new ApiProgramsResponse().setId(pro.getId()).setContent(pro.getName()).setCountry(pro.getNation()).setName(pro.getName())));
        }
        List<Programs> programs = programDao.findByProvinceAndName(request.getCode(), request.getType());
        return RespBody.isOk().data(programs.stream().map(pro -> new ApiProgramsResponse().setId(pro.getId()).setContent(pro.getName()).setCountry(pro.getNation()).setName(pro.getName())));
    }
    /**
     * constructor for object injection
     * @param policyDao     dao of policy
     * @param policyNewsDao dao of policy news
     */
    public PolicyService(PolicyDao policyDao, PolicyNewsDao policyNewsDao,
                         PasswordEncoder passwordEncoder, ScrapingRecordDao scrapingRecordDao, ProgramDao programDao) {
        this.policyDao = policyDao;
        this.policyNewsDao = policyNewsDao;
        this.passwordEncoder = passwordEncoder;
        this.scrapingRecordDao = scrapingRecordDao;
        this.programDao = programDao;
    }

    private final PolicyDao policyDao;
    private final PolicyNewsDao policyNewsDao;
    private final ScrapingRecordDao scrapingRecordDao;
    private final PasswordEncoder passwordEncoder;

    public RespBody<?> getprogramById(int programId) {
        Programs program = programDao.findById(programId).orElseThrow(() -> new IllegalStateException("No program found"));

        return RespBody.isOk().data(new ApiProgramsResponse().setId(programId).setContent(program.getContent()).setName(program.getName()).setCountry(program.getNation()));
    }
}
