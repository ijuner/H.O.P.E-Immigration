package ca.conestoga.project.entity.client;

import ca.conestoga.project.entity.policy.Programs;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

/**
 * entity for user reporting
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "client_report")
public class UserReport {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /*
   * user id
   */
  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @ManyToOne
  @JoinColumn(name = "program_id", nullable = false)
  private Programs program;

  @Column(name = "program_name", nullable = false)
  private String programName;

  @Column(nullable = false)
  private int score;

  @Column(name = "inviteation_score", nullable = false)
  private int invitationScore;

  @Column(nullable = false)
  private String content;

  @OneToMany(mappedBy = "report")
  private List<UserReportScore> reportScores;
}
