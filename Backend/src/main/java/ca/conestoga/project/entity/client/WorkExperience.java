package ca.conestoga.project.entity.client;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * entity for WorkExperience
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "client_work_experience")
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idWorkExperience;

    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false)
    private Client client;

    @Column(name = "jobTitle", nullable = true)
    private String jobTitle;

    @Column(name = "English", nullable = true)
    private String English;

    @Column(name = "codeNOC", nullable = true)
    private String codeNOC;

    @Column(name = "amtWage", nullable = true)
    private String amtWage;

    @Column(name = "txtDuties", nullable = true)
    private String txtDuties;

    @Column(name = "jobType", nullable = true)
    private String jobType;

    @Column(name = "workplaceLocation", nullable = true)
    private String workplaceLocation;

    @Column(name = "dateStart", nullable = true)
    private String dateStart;
}
