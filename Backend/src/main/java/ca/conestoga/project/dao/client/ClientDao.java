package ca.conestoga.project.dao.client;

import ca.conestoga.project.entity.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access object for Client
 */
public interface ClientDao extends JpaRepository<Client, Integer> {

    Client findByUserName(String name);

    Client findByEmail(String email);

    Client findByUserNameOrEmail(String key1, String key2);
}
