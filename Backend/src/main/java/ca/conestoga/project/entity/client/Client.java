package ca.conestoga.project.entity.client;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * business object of User information
 */
@Data
@Accessors(chain = true)
@Getter
@Entity
@Table(name = "client_main")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    private String userName;
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
    private String password;

    @Column(name = "is_vip")
    private Character isVIP;
    private String language;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date dateCreated;

    @OneToMany(mappedBy = "client")
    private List<UserReport> reports = new ArrayList<>();;

    //structure client with other information relationship
    @OneToOne (mappedBy = "client")
    private ClientBasicInfo clientBasicInfo;

    @OneToMany(mappedBy = "client")
    private List<EducationExperience> educationExperiences = new ArrayList<>();;

    @OneToOne(mappedBy = "client")
    private ExtraInfo extraInfo;

    @OneToMany(mappedBy = "client")
    private List<LanguageAbility> languageAbilities = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<SpouseInfo> spouseInfos = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<WorkExperience> workExperiences = new ArrayList<>();;


}
