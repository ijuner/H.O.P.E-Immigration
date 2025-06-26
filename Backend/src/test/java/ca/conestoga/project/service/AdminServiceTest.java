package ca.conestoga.project.service;

import ca.conestoga.project.common.RespBody;
import ca.conestoga.project.entity.admin.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test class of AdminService
 */
@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    void getAdminById() {
        // Arrange
        int adminId = 1;

        // Act
        Admin admin = adminService.getAdminById(adminId);

        // Assert
        assertEquals(adminId, admin.getId());
    }

    @Test
    void findByUsername() {
        // Arrange
        String username = "admin";

        // Act
        Admin admin = adminService.findByUsername(username);

        // Assert
        assertEquals(username, admin.getUsername());
    }

    @Test
    void listAllAdmin() {
        // Act
        List<Admin> admins = adminService.listAllAdmin();

        // Assert
        assertEquals(1, admins.size());
    }

    @Transactional
    @Rollback
    @Test
    void saveAdmin() {
        // Arrange
        String username = "test_username";
        String password = "test_password";

        // Act
        RespBody<?> respBody = adminService.saveAdmin(username, password);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), respBody.getCode());
        Admin admin = (Admin) respBody.getData();
        assertEquals(admin.getUsername(), username);

        /* insert a admin with same name */
        // Act
        RespBody<?> respBody2 = adminService.saveAdmin(username, password);
        // Assert
        assertEquals(RespBody.CodeEnum.ERROR.getCode(), respBody2.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void updateAdmin() {
        // Arrange
        String username = "new_name";
        Admin admin = adminService.getAdminById(1);

        // Act
        RespBody<?> respBody = adminService.updateAdmin(admin, username, "test_real_name");

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), respBody.getCode());
        assertEquals(username, admin.getUsername());
    }

    @Transactional
    @Rollback
    @Test
    void enableAdmin() {
        // Arrange
        Admin admin = adminService.getAdminById(1);

        // Act
        RespBody<?> respBody = adminService.enableAdmin(admin);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), respBody.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void disableAdmin() {
        // Arrange
        Admin admin = adminService.getAdminById(1);

        // Act
        RespBody<?> respBody = adminService.disableAdmin(admin);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), respBody.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void editPassword() {
        // Arrange
        Admin admin = adminService.getAdminById(1);
        String wrongPassword = "admin#1234";
        String rightPassword = "admi#123456";
        String newPassword = "123456";

        // Act
        RespBody<?> respBody = adminService.editPassword(admin, wrongPassword, newPassword);
        RespBody<?> respBody2 = adminService.editPassword(admin, rightPassword, newPassword);

        // Assert
        assertEquals(RespBody.CodeEnum.ERROR.getCode(), respBody.getCode());
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), respBody2.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void resetPassword() {
        // Arrange
        Admin admin = adminService.getAdminById(1);

        // Act
        RespBody<?> respBody = adminService.resetPassword(admin);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), respBody.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void checkDefaultPassword() {
        // Arrange
        int adminId = 1;

        // Act
        Admin admin = adminService.getAdminById(adminId);

        // Assert
        assertFalse(adminService.checkDefaultPassword(admin));
    }
}