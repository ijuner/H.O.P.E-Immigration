package ca.conestoga.project.product.bo.policy;

import ca.conestoga.project.entity.client.UserReport;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
@Accessors(chain = true)
public class ApiReportScore {
  private Long id;

  private String targetObject;

  private String targetField;

  private String content;

  private int score;
}
