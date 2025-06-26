package ca.conestoga.project.entity.client;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * entity for extra information
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "client_extra_info")
public class ExtraInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idExtraInfo;

    @OneToOne
    @JoinColumn(name = "clientId", nullable = false)
    private Client client;

    @Column(name = "relatives_flg", nullable = true)
    private String relatives_flg;

    @Column(name = "PNP_flg", nullable = true)
    private String PNP_flg;

    @Column(name = "offer_flg", nullable = true)
    private String offer_flg;

    @Column(name = "bilingual_flg", nullable = true)
    private String bilingual_flg;
}
