package ca.conestoga.project.dao.program;

import ca.conestoga.project.entity.client.UserReport;
import ca.conestoga.project.entity.policy.Programs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Data access object for UserReport
 */
public interface UserReportDao extends JpaRepository<UserReport, Integer> {

  Optional<UserReport> findByProgramId(int id);
}
