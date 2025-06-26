package ca.conestoga.project.dao.program;

import ca.conestoga.project.entity.client.Client;
import ca.conestoga.project.entity.policy.Programs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Data access object for Program
 */
public interface ProgramDao extends JpaRepository<Programs, Integer> {

  List<Programs> findByProvinceAndName(String code, String type);
}
