package ca.conestoga.project.product.controller.api;

import ca.conestoga.project.common.DocumentTypeEnum;
import ca.conestoga.project.common.RespBody;
import ca.conestoga.project.entity.client.ClientDocument;
import ca.conestoga.project.entity.policy.PolicyNews;
import ca.conestoga.project.product.bo.policy.ApiPolicyNewsList;
import ca.conestoga.project.product.bo.policy.ApiProgramsRequest;
import ca.conestoga.project.product.bo.user.ApiLoginUser;
import ca.conestoga.project.product.bo.user.ApiUserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class of ApiController
 */
@SpringBootTest
class ApiControllerTest {

    @Autowired
    ApiController apiController;

    @Test
    void getPolicyNewsList() {
        // Arrange
        int page = 1;
        String lang = "en";
        int size = 10;

        // Act
        RespBody<?> response = apiController.getPolicyNewsList(page, lang, size);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), response.getCode());
        ApiPolicyNewsList apiPolicyNewsList = (ApiPolicyNewsList) response.getData();
        assertNotNull(apiPolicyNewsList);
        assertEquals(size, apiPolicyNewsList.getList().size());
    }

    @Test
    void getPolicyNewsById() {
        // Arrange
        int id = 1;

        // Act
        RespBody<?> response = apiController.getPolicyNewsById(id);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), response.getCode());
        PolicyNews policyNews = (PolicyNews) response.getData();
        assertNotNull(policyNews);
        assertEquals(id, policyNews.getId());
    }

    @Test
    void getProgramsConditions() {
        // Arrange

        // Act
        RespBody<?> response = apiController.getProgramsConditions();

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), response.getCode());
    }

    @Test
    void getProgramList() {
        // Arrange
        ApiProgramsRequest request = null;

        // Act
        RespBody<?> respBody = apiController.getProgramList(request);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), respBody.getCode());
    }

    @Test
    void getProgram() {
        // Arrange
        int id = 1;

        // Act
        RespBody<?> respBody = apiController.getProgram(id);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), respBody.getCode());
    }

    @Test
    void myScore() {
        // Arrange

        // Act
        RespBody<?> respBody = apiController.myScore();

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), respBody.getCode());
    }

    @Test
    void getDictionary() {
        // Arrange
        String key = "1";

        // Act
        RespBody<?> respBody = apiController.getDictionary(key);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), respBody.getCode());
    }
}