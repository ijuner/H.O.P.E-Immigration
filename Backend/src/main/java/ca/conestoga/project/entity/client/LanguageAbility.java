package ca.conestoga.project.entity.client;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * entity for languageAbility
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "client_language_ability")
public class LanguageAbility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLanguageAbility;

    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false)
    private Client client;

    @Column(name = "English", nullable = true)
    private String English;

    @Column(name = "French", nullable = true)
    private String French;

    @Column(name = "obtainedTerm", nullable = true)
    private String obtainedTerm;
}
