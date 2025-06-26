package ca.conestoga.project.dao.client;

import ca.conestoga.project.entity.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import ca.conestoga.project.entity.client.ExtraInfo;

/**
 * Data access object for ExtraInfo
 */

public interface ExtraInfoDao extends JpaRepository<ExtraInfo, Integer> {

    ExtraInfo findByClient(Client client);
}
