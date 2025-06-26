package ca.conestoga.project.dao.program;

import ca.conestoga.project.entity.policy.ProgramScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Data access object for Program score
 */
public interface ProgramScoreDao extends JpaRepository<ProgramScore, Integer> {

  List<ProgramScore> findByProgramId(int id);
}
