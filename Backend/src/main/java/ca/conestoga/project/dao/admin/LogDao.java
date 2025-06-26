package ca.conestoga.project.dao.admin;

import ca.conestoga.project.entity.admin.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access object for log
 */
public interface LogDao extends JpaRepository<Log, Integer> {
    Page<Log> findAllByType(int type, Pageable pageable);
}
