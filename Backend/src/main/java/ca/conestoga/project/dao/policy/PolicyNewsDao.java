package ca.conestoga.project.dao.policy;

import ca.conestoga.project.entity.policy.Policy;
import ca.conestoga.project.entity.policy.PolicyNews;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access object for policy news
 */
public interface PolicyNewsDao extends JpaRepository<PolicyNews, Integer> {

    PolicyNews findPolicyNewsByPolicyAndLang(Policy policy, String lang);
}
