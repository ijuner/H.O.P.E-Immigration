package ca.conestoga.project.entity.client;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * entity for spouse information
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "client_spouse_info")
public class SpouseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSpouseInfo;

    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false)
    private Client client;

    @Column(name = "educationLevel", nullable = true)
    private String educationLevel;

    @Column(name = "program", nullable = true)
    private String program;

    @Column(name = "officialLanguage", nullable = true)
    private String officialLanguage;

    @Column(name = "codeNOC", nullable = true)
    private String codeNOC;
}
