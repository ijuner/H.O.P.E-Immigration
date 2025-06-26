package ca.conestoga.project.product.controller.manage;

import ca.conestoga.project.common.LanguageEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class of PolicyController
 */
@SpringBootTest
class PolicyControllerTest {

    @Autowired
    private PolicyController policyController;

    @Test
    void list() {
        // Arrange
        Optional<Integer> optional = Optional.of(1);

        // Act
        ModelAndView modelAndView = policyController.list(optional);

        // Assert
        assertEquals("policy/list", modelAndView.getViewName());
        assertNotEquals(Collections.emptyList(), modelAndView.getModel().get("list"));
    }

    @Test
    void getRecordList() {
        // Arrange
        Optional<Integer> optional = Optional.of(1);

        // Act
        ModelAndView modelAndView = policyController.getRecordList(optional);

        // Assert

        assertEquals("policy/record", modelAndView.getViewName());
        assertNotEquals(Collections.emptyList(), modelAndView.getModel().get("list"));
    }

    @Test
    void getPolicyContent() {
        // Arrange
        int policyId = 1;

        // Act
        ModelAndView modelAndView = policyController.getPolicyContent(policyId);

        // Assert

        assertEquals("policy/content", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("policy"));
    }

    @Test
    void getPolicyContentByLang() {
        // Arrange
        int policyId = 1;

        // Act
        ModelAndView modelAndView = policyController.getPolicyContentByLang(policyId, LanguageEnum.CH.getValue());

        // Assert
        assertEquals("policy/content", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("policy"));
    }
}