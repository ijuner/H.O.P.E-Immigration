package ca.conestoga.project.service;

import ca.conestoga.project.common.RespBody;
import ca.conestoga.project.entity.policy.Policy;
import ca.conestoga.project.entity.policy.PolicyNews;
import ca.conestoga.project.entity.policy.ScrapingRecord;
import ca.conestoga.project.product.bo.policy.ApiProgramsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class of PolicyService
 */
@SpringBootTest
class PolicyServiceTest {

    @Autowired
    private PolicyService policyService;

    @Test
    void getPolicyById() {
        // Arrange
        int policyId = 1;
        int policyId2 = -1;

        // Act
        Policy policy = policyService.getPolicyById(policyId);
        Policy policy2 = policyService.getPolicyById(policyId2);

        // Assert
        assertNotNull(policy);
        assertEquals(policyId, policy.getId());
        assertNull(policy2);
    }

    @Transactional
    @Rollback
    @Test
    void createPolicy() {
        // Arrange
        String title = "test_name";
        String url = "test_url";

        // Act
        RespBody<?> result = policyService.createPolicy(title, url);

        // Assert
        assertEquals(result.getCode(), RespBody.CodeEnum.SUCCESS.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void deletePolicy() {
        // Arrange
        int policyId = 1;
        int policyId2 = -1;

        // Act
        RespBody<?> result = policyService.deletePolicy(policyId);
        RespBody<?> result2 = policyService.deletePolicy(policyId2);

        // Assert
        assertEquals(result.getCode(), RespBody.CodeEnum.SUCCESS.getCode());
        assertEquals(result2.getCode(), RespBody.CodeEnum.ERROR.getCode());
    }

    @Test
    void getPolicyList() {
        // Arrange
        Pageable pageable = PageRequest.of(1, 3, Sort.Direction.DESC, "id");

        // Act
        Page<Policy> result = policyService.getPolicyList(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getContent().size());
    }

    @Test
    void findPolicyNewsByPolicyAndLang() {
        // Arrange
        Policy nullPolicy = null;
        int policyId = 2;

        // Act
        PolicyNews nullPolicyNews = policyService.findPolicyNewsByPolicyAndLang(nullPolicy, "en");
        Policy policy = policyService.getPolicyById(policyId);
        PolicyNews policyNews = policyService.findPolicyNewsByPolicyAndLang(policy, "en");
        PolicyNews noNews = policyService.findPolicyNewsByPolicyAndLang(policy, "no");

        // Assert
        assertNull(nullPolicyNews);
        assertNotNull(policy);
        assertEquals(policyNews.getPolicy().getId(), policyId);
        assertNull(noNews);
    }

    @Test
    void getPolicyNewsById() {
        // Arrange
        int policyNewsId = 1;

        // Act
        PolicyNews policyNews = policyService.getPolicyNewsById(policyNewsId);

        // Assert
        assertNotNull(policyNews);
        assertEquals(policyNews.getId(), policyNewsId);
    }

    @Test
    void getRecordList() {
        // Arrange
        Pageable pageable = PageRequest.of(1, 3, Sort.Direction.DESC, "id");

        // Act
        Page<ScrapingRecord> list = policyService.getRecordList(pageable);

        // Assert
        assertNotNull(list);
        assertEquals(3, list.getContent().size());
    }

    @Test
    void getProgramsConditions() {
        // Act
        RespBody<?> result = policyService.getProgramsConditions();

        // Assert
        assertEquals(result.getCode(), RespBody.CodeEnum.SUCCESS.getCode());
    }

    @Test
    void getProgramsList() {
        // Arrange
        ApiProgramsRequest request = null;

        // Act
        RespBody<?> result = policyService.getProgramsList(request);

        // Assert
        assertEquals(result.getCode(), RespBody.CodeEnum.SUCCESS.getCode());
    }
}