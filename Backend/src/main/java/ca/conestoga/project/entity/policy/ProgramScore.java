package ca.conestoga.project.entity.policy;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * entity for Programs Score
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "program_score")
public class ProgramScore {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "p_id", nullable = false)
  private Programs program;

  @Column(name = "target_object", nullable = false)
  private String targetObject;

  @Column(name = "target_field", nullable = false)
  private String targetField;

  /*
  condition validation name
   */
  @Column(name = "condition_method", nullable = false)
  private String conditionMethod;

  /*
  condition method parameter 1, must set
   */
  @Column(name = "condition_value", nullable = false)
  private String conditionValue1;

  @Column(name = "condition_value2", nullable = true)
  private String conditionValue2;

  @Column(name = "condition_value3", nullable = true)
  private String conditionValue3;

  @Column(name = "condition_value4", nullable = true)
  private String conditionValue4;

  @Column(name = "condition_value5", nullable = true)
  private String conditionValue5;

  /*
0 closed, 1 active
 */
  @Column(nullable = false)
  private short status;

  private String content;

  private int score;
}
