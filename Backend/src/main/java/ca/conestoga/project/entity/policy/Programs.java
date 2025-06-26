package ca.conestoga.project.entity.policy;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * entity for Programs
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "program_main")
public class Programs {
  /*
  primary key of program
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /*
  the name of the program
   */
  private String name;

  /*
  the description of the program
   */
  private String content;

  /*
  what nation the program were active on
   */
  private String nation;

  /*
  what province the program was active on
   */
  private String province;

  /*
  the start date of the program
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "start_time")
  private Date startTime;

  /*
  the end date of the program
  */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "end_time")
  private Date endTime;

  /*
  0 closed, 1 active
   */
  private short status;

  /**
   * List of program scores associated with this program.
   */
  @OneToMany(mappedBy = "program")
  private List<ProgramScore> scores;

  @OneToMany(mappedBy = "program")
  private List<ProgramInvitation> invitations;
}
