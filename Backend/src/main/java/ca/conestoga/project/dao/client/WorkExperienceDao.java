package ca.conestoga.project.dao.client;

import ca.conestoga.project.entity.client.Client;
import ca.conestoga.project.entity.client.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Data access object for WorkExperience
 */

public interface WorkExperienceDao extends JpaRepository<WorkExperience, Integer> {

    List<WorkExperience> findListByClient(Client client);
}
