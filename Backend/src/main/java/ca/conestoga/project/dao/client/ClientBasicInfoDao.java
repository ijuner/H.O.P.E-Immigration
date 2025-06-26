package ca.conestoga.project.dao.client;

import ca.conestoga.project.entity.client.Client;
import ca.conestoga.project.entity.client.ClientBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access object for ClientBasicInfo
 */
public interface ClientBasicInfoDao extends JpaRepository<ClientBasicInfo, Integer> {

    ClientBasicInfo findByClient(Client client);
}
