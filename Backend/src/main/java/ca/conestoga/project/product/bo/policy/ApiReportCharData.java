package ca.conestoga.project.product.bo.policy;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ApiReportCharData {
  private String[] labels;

  private List<ApiChartDataset> datasets;
}
