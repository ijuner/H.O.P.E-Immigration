package ca.conestoga.project.dao.admin;

import ca.conestoga.project.entity.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Data access object for admin
 */
public interface AdminDao extends JpaRepository<Admin, Integer> {
    List<Admin> findAllByOrderById();

    Admin findByUsername(String username);

    int countByUsername(String username);

    int countByUsernameAndIdNot(String username, int id);
}
