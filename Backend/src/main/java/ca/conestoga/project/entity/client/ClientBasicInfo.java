package ca.conestoga.project.entity.client;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * entity for client basic information
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "client_basic_info")
public class ClientBasicInfo {
    @Id
    @Column(name = "id")
    private int idClientBasic;

    @Column(name = "age", nullable = true)
    private String age;

    @Column(name = "marriageStatus", nullable = true)
    private String marriageStatus;

    @Column(name = "immigrationStatus", nullable = true)
    private String immigrationStatus;

    @OneToOne
    @JoinColumn(name = "ClientId", nullable = true)
    private Client client;
}
