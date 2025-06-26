package ca.conestoga.project.service;

import ca.conestoga.project.common.RespBody;
import ca.conestoga.project.dao.admin.AdminDao;
import ca.conestoga.project.entity.admin.Admin;
import ca.conestoga.project.entity.admin.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for actions of admin
 */
@Service
public class AdminService {

    /**
     * find admin by id
     *
     * @param id id of admin
     * @return admin object，<code>null</code> if not found
     */
    public Admin getAdminById(Integer id) {
        return adminDao.findById(id).orElse(null);
    }

    /**
     * find admin by username
     *
     * @param username username of admin
     * @return admin object，<code>null</code> if not found
     */
    public Admin findByUsername(String username) {
        return adminDao.findByUsername(username);
    }

    /**
     * query all admins
     *
     * @return all admins list
     */
    public List<Admin> listAllAdmin() {
        return adminDao.findAllByOrderById();
    }

    /**
     * create a admin account
     *
     * @param username username
     * @param realname real name
     */
    public RespBody<?> saveAdmin(String username, String realname) {
        int count = adminDao.countByUsername(username);
        logger.info("check admin with username = {} count = {}", username, count);
        if (count > 0) {
            logger.info("username {} is not unique while saving", username);
            return RespBody.isFail().msg("user name is already taken");
        }
        logger.info("check passed, start to save");
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setRealname(realname);
        admin.setPassword(passwordEncoder.encode(getDefaultPassword(admin)));
        admin.setStatus(Admin.StatusEnum.ON.getValue());
        adminDao.save(admin);
        logger.info("admin {} is saved", admin);
        logService.recordAdminLog(Log.ActionEnum.ADD_ADMIN, "", admin);
        return RespBody.isOk().data(admin);
    }

    /**
     * get default password
     *
     * @param admin admin to edit password
     * @return the password
     */
    private String getDefaultPassword(Admin admin) {
        return admin.getUsername().substring(0, Math.min(admin.getUsername().length(), 4)) + "#1234";
    }

    /**
     * edit admin info
     *
     * @param admin    admin account to be edited
     * @param username username
     * @param realname real name
     */
    public RespBody<?> updateAdmin(Admin admin, String username, String realname) {
        int count = adminDao.countByUsernameAndIdNot(username, admin.getId());
        logger.info("check admin with new username = {} count = {}", username, count);
        if (count > 0) {
            logger.info("username {} is not unique while updating", username);
            return RespBody.isFail().msg("username is already taken");
        }
        logger.info("check passed, start to update");
        String oldAdmin = admin.toString();
        admin.setUsername(username);
        admin.setRealname(realname);
        adminDao.save(admin);
        logger.info("admin {} is updated", admin);
        logService.recordAdminLog(Log.ActionEnum.EDIT_ADMIN, oldAdmin, admin);
        return RespBody.isOk().data(admin);
    }

    /**
     * set admin account to active
     *
     * @param admin admin need to be enabled
     * @return set result
     */
    public RespBody<?> enableAdmin(Admin admin) {
        String oldStatus = Admin.StatusEnum.findStatusByValue(admin.getStatus()).getName();
        admin.setStatus(Admin.StatusEnum.ON.getValue());
        adminDao.save(admin);
        logger.info("admin {} is enabled", admin);
        logService.recordAdminLog(Log.ActionEnum.ADD_ADMIN, oldStatus, Admin.StatusEnum.ON.getName());
        return RespBody.isOk();
    }

    /**
     * set admin account to disable
     *
     * @param admin admin need to be disabled
     * @return set result
     */
    public RespBody<?> disableAdmin(Admin admin) {
        String oldStatus = Admin.StatusEnum.findStatusByValue(admin.getStatus()).getName();
        admin.setStatus(Admin.StatusEnum.OFF.getValue());
        adminDao.save(admin);
        logger.info("admin {} is disabled", admin);
        logService.recordAdminLog(Log.ActionEnum.ADD_ADMIN, oldStatus, Admin.StatusEnum.OFF.getName());
        return RespBody.isOk();
    }

    /**
     * edit password
     *
     * @param admin       admin account
     * @param oldPassword original password
     * @param newPassword new password
     * @return edit result
     */
    public RespBody<?> editPassword(Admin admin, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, admin.getPassword())) {
            logger.info("old password does not match");
            return RespBody.isFail().msg("old password does not match");
        }
        admin.setPassword(passwordEncoder.encode(newPassword));
        adminDao.save(admin);
        logger.info("edit finished");
        logService.recordAdminLog(Log.ActionEnum.EDIT_PASSWORD, admin, admin);
        return RespBody.isOk();
    }

    /**
     * reset password
     *
     * @param admin admin account
     * @return reset result
     */
    public RespBody<?> resetPassword(Admin admin) {
        admin.setPassword(passwordEncoder.encode(getDefaultPassword(admin)));
        adminDao.save(admin);
        logger.info("reset finished");
        logService.recordAdminLog(Log.ActionEnum.RESET_PASSWORD, admin, admin);
        return RespBody.isOk();
    }

    /**
     * check if a admin account is using default password
     *
     * @param admin admin account
     * @return <code>true</code> if using default password，else <code>false</code>
     */
    public boolean checkDefaultPassword(Admin admin) {
        return passwordEncoder.matches(getDefaultPassword(admin), admin.getPassword());
    }

    /**
     * constructor for object injection
     *
     * @param adminDao         dao of admin
     * @param logService       dao of log
     * @param passwordEncoder  password encoder
     */
    public AdminService(AdminDao adminDao,
                        LogService logService,
                        PasswordEncoder passwordEncoder) {
        this.adminDao = adminDao;
        this.logService = logService;
        this.passwordEncoder = passwordEncoder;
    }

    private final AdminDao adminDao;
    private final LogService logService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LogManager.getLogger("serviceLogger");
}
