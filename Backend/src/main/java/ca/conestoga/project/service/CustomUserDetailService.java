package ca.conestoga.project.service;

import ca.conestoga.project.dao.admin.AdminDao;
import ca.conestoga.project.entity.admin.Admin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * user service for security
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("start to load admin user with name = {}", username);
        // load user from database
        Admin admin = adminDao.findByUsername(username);
        logger.info("admin user with name = {} is {}", username, admin);
        // check if the user exists
        if (admin == null) {
            logger.info("admin is null, return exception");
            throw new UsernameNotFoundException("username not found");
        }
        if (admin.getStatus() != Admin.StatusEnum.ON.getValue()) {
            logger.info("admin status is {}, return exception", admin.getStatus());
            throw new UsernameNotFoundException("username not found");
        }
        logger.info("start to load privileges from roles of this admin user");
        // return user with empty privilege list
        return new User(admin.getUsername(), admin.getPassword(), new ArrayList<>());
    }

    /**
     * constructor for object injection
     *
     * @param adminDao         dao of admin
     */
    public CustomUserDetailService(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    private final AdminDao adminDao;
    private final Logger logger = LogManager.getLogger("serviceLogger");
}
