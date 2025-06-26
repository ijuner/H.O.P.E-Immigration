package ca.conestoga.project.dao.policy;

import ca.conestoga.project.entity.policy.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access object for policy
 */
public interface PolicyDao extends JpaRepository<Policy, Integer> {
    Page<Policy> findAllByStrategyAndStatus(int strategy, int status, Pageable pageable);
}
