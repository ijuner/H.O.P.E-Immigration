package ca.conestoga.project.dao.client;

import ca.conestoga.project.entity.client.Client;
import ca.conestoga.project.entity.client.SpouseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access object for SpouseInfo
 */

public interface SpouseInfoDao extends JpaRepository<SpouseInfo, Integer> {

    SpouseInfo findByClient(Client client);
}
