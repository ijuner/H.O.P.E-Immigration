package ca.conestoga.project.dao.client;

import ca.conestoga.project.entity.client.Client;
import ca.conestoga.project.entity.client.EducationExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Data access object for EducationExperience
 */
public interface EducationExperienceDao extends JpaRepository<EducationExperience, Integer> {

    List<EducationExperience> findListByClient(Client client);
}
