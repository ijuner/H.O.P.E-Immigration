package ca.conestoga.project.dao.client;

import ca.conestoga.project.entity.client.Client;
import ca.conestoga.project.entity.client.ExtraInfo;
import ca.conestoga.project.entity.client.LanguageAbility;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access object for LanguageAbility
 */

public interface LanguageAbilityDao extends JpaRepository<LanguageAbility, Integer> {

    LanguageAbility findByClient(Client client);
}
