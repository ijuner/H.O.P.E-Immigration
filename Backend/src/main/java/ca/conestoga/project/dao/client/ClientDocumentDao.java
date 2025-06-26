package ca.conestoga.project.dao.client;

import ca.conestoga.project.entity.client.Client;
import ca.conestoga.project.entity.client.ClientDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Data access object for Client Document
 */
public interface ClientDocumentDao extends JpaRepository<ClientDocument, Integer> {
    Optional<ClientDocument> findTopByClientAndTypeOrderByVersionDesc(Client client, String type);
    List<ClientDocument> findListByClientAndTypeOrderByVersionDesc(Client client, String type);
}
