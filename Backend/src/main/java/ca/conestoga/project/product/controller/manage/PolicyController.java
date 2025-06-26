package ca.conestoga.project.product.controller.manage;

import ca.conestoga.project.common.Const;
import ca.conestoga.project.entity.policy.Policy;
import ca.conestoga.project.entity.policy.PolicyNews;
import ca.conestoga.project.service.PolicyService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * controller of policy page
 */
@Controller
@RequestMapping("/manage/policy/")
public class PolicyController {

    /**
     * query policy of a page
     *
     * @param page page info
     * @return policy list of the page
     */
    @GetMapping("list/{page}")
    public ModelAndView list(@PathVariable Optional<Integer> page) {
        int p = 1;
        if (page.isPresent()) {
            p = page.get();
        }
        Pageable pageable = PageRequest.of(p - 1, Const.DEFAULT_ADMIN_PAGE_SIZE,
                Sort.Direction.DESC, "lastUpdateDate");
        return new ModelAndView("policy/list", "list", policyService.getPolicyList(pageable));
    }

    /**
     * query policy of a page
     *
     * @param page page info
     * @return policy list of the page
     */
    @GetMapping("record/{page}")
    public ModelAndView getRecordList(@PathVariable Optional<Integer> page) {
        int p = 1;
        if (page.isPresent()) {
            p = page.get();
        }
        Pageable pageable = PageRequest.of(p - 1, Const.DEFAULT_ADMIN_PAGE_SIZE,
                Sort.Direction.DESC, "id");
        return new ModelAndView("policy/record", "list", policyService.getRecordList(pageable));
    }

    /**
     * show content of a policy
     *
     * @param id policy's id
     * @return the content of the policy
     */
    @GetMapping("list/content/{id}")
    public ModelAndView getPolicyContent(@PathVariable Integer id) {
        return getPolicyContentByLang(id, PolicyNews.LauguageEnum.EN.getName());
    }

    /**
     * show content of a policy
     *
     * @param id policy's id
     * @return the content of the policy
     */
    @GetMapping("list/content/{id}/{lang}")
    public ModelAndView getPolicyContentByLang(@PathVariable Integer id, @PathVariable String lang) {
        Map<String, Object> model = new HashMap<>();
        Policy policy = policyService.getPolicyById(id);
        model.put("policy", policy);
        model.put("langs", PolicyNews.LauguageEnum.values());
        model.put("content", policyService.findPolicyNewsByPolicyAndLang(policy, lang));
        model.put("lang", lang);
        model.put("id", id);
        return new ModelAndView("policy/content", model);
    }

    /**
     * constructor for object injection
     *
     * @param policyService Service for actions of policy
     */
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    private final PolicyService policyService;
}
