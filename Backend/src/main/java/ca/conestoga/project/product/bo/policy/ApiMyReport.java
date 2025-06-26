package ca.conestoga.project.product.bo.policy;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class ApiMyReport {
  private int id;

  private int programId;

  private String programName;

  private int score;

  private String content;

  private int lastInviteScore;

  private ApiReportCharData chartSets;

  private List<ApiReportScore> reportScores = new ArrayList<ApiReportScore>();
}
