package ca.conestoga.project.entity.policy;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * entity for Programs invitation
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "program_invitation")
public class ProgramInvitation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "p_id", nullable = false)
  private Programs program;

  @Column(name = "invited_count", nullable = false)
  private int invitedCount;

  @Column(name = "minimum_score", nullable = false)
  private int minimumScore;

  @Column(name = "invited_no")
  private Integer invitedNo;

  private String content;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "invited_date")
  private Date invitedDate;
}
