package ca.conestoga.project.entity.client;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * entity for EducationExperience
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "client_education_experience")
public class EducationExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEducationExperience;

    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false)
    private Client client;

    @Column(name = "educationLevel", nullable = true)
    private String educationLevel;


    @Column(name = "program", nullable = true)
    private String program;

    @Column(name = "address", nullable = true)
    private String address;
}
