package ca.conestoga.project.dao.program;

import ca.conestoga.project.entity.client.UserReport;
import ca.conestoga.project.entity.client.UserReportScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Data access object for UserReport Score
 */
public interface UserReportScoreDao extends JpaRepository<UserReportScore, Integer> {

  List<UserReportScore> findByReportId(int reportId);
}
