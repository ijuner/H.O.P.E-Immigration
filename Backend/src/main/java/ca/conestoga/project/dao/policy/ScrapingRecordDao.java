package ca.conestoga.project.dao.policy;

import ca.conestoga.project.entity.policy.ScrapingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access object for scraping record
 */
public interface ScrapingRecordDao extends JpaRepository<ScrapingRecord, Integer> {
    Page<ScrapingRecord> findAll(Pageable pageable);
}