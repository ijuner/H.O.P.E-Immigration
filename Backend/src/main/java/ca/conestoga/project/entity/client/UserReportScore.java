package ca.conestoga.project.entity.client;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * entity for user report score
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "client_report_score")
public class UserReportScore {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "report_id", nullable = false)
  private UserReport report;

  @Column(name = "target_object", nullable = false)
  private String targetObject;

  @Column(name = "target_field", nullable = false)
  private String targetField;

  private String content;

  @Column(nullable = false)
  private int score;
}
