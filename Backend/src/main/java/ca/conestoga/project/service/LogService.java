package ca.conestoga.project.service;

import ca.conestoga.project.dao.admin.LogDao;
import ca.conestoga.project.entity.admin.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Service for actions of log
 */
@Service
public class LogService {

    /**
     * create admin operation log
     * @param operation operation enumeration
     * @param oldContent old content
     * @param newContent new content
     */
    public void recordAdminLog(Log.ActionEnum operation, Object oldContent, Object newContent) {
        recordLog(operation, oldContent, newContent, Log.TypeEnum.ADMIN);
    }

    /**
     * create api operation log
     * @param operation operation enumeration
     * @param oldContent old content
     * @param newContent new content
     */
    public void recordApiLog(Log.ActionEnum operation, Object oldContent, Object newContent) {
        recordLog(operation, oldContent, newContent, Log.TypeEnum.API);
    }

    /**
     * save record log
     *
     * @param operation operation enumeration
     * @param oldContent old content
     * @param newContent new content
     * @param type operation type
     */
    private void recordLog(Log.ActionEnum operation, Object oldContent, Object newContent, Log.TypeEnum type) {
        logger.info("start to record log");
        Log log = new Log();
        log.setAccount(getCurrentUsername());
        log.setOperation(operation.getName());
        log.setOldContent(oldContent.toString());
        log.setNewContent(newContent.toString());
        log.setType(type.getValue());
        logDao.save(log);
        logger.info("log (id={})is saved", log.getId());
    }

    /**
     * get account logged in current environment
     *
     * @return account username
     */
    private String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "NO USER";
        }
        if (authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return authentication.getPrincipal().toString();
    }

    /**
     * query log of a page
     *
     * @param pageable page info
     * @return operation logs list of the page
     */
    public Page<Log> listAllLog(Pageable pageable) {
        return logDao.findAll(pageable);
    }

    /**
     * query log of a page by type
     *
     * @param type operation type
     * @param pageable page info
     * @return operation logs list of the page for the type
     */
    public Page<Log> listLogByType(int type, Pageable pageable) {
        return logDao.findAllByType(type, pageable);
    }

    /**
     * constructor for object injection
     *
     * @param logDao dao of log
     */
    public LogService(LogDao logDao) {
        this.logDao = logDao;
    }

    private final LogDao logDao;
    private final Logger logger = LogManager.getLogger("serviceLogger");
}
