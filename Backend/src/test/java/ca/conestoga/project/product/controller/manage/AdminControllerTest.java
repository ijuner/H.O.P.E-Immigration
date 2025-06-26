package ca.conestoga.project.product.controller.manage;

import ca.conestoga.project.common.RespBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class of AdminController
 */
@SpringBootTest
class AdminControllerTest {

    @Autowired
    private AdminController adminController;

    @Test
    void resetPassword() {
        // Act
        String modelAndView = adminController.resetPassword();

        // Assert
        assertEquals("admin/edit_password", modelAndView);
    }

    @Test
    void list() {
        // Act
        ModelAndView modelAndView = adminController.list();

        // Assert
        assertEquals("admin/admin_list", modelAndView.getViewName());
    }

    @Test
    void addAdmin() {
        // Act
        String modelAndView = adminController.addAdmin();

        // Assert
        assertEquals("admin/add_admin", modelAndView);
    }

    @Test
    void editAdmin() {
        // Arrange
        int adminId = 1;

        // Act
        ModelAndView modelAndView = adminController.editAdmin(adminId);

        // Assert
        assertEquals("admin/edit_admin", modelAndView.getViewName());
    }

    @Transactional
    @Rollback
    @Test
    void enableAdmin() {
        // Arrange
        int adminId = 1;

        // Act
        RespBody<?> result = adminController.enableAdmin(adminId);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), result.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void disableAdmin() {
        // Arrange
        int adminId = 1;

        // Act
        RespBody<?> result = adminController.disableAdmin(adminId);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), result.getCode());
    }

    @Test
    void listAllLog() {
        // Act
        ModelAndView modelAndView = adminController.list();

        // Assert
        assertEquals("admin/admin_list", modelAndView.getViewName());
    }


    @Test
    void listAdminLog() {
        // Act
        ModelAndView modelAndView = adminController.list();

        // Assert
        assertEquals("admin/admin_list", modelAndView.getViewName());
    }

    @Test
    void listApiLog() {
        // Act
        ModelAndView modelAndView = adminController.list();

        // Assert
        assertEquals("admin/admin_list", modelAndView.getViewName());
    }
}