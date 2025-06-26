package ca.conestoga.project.product.bo.policy;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApiChartDataset {
  private String label;

  private String backgroundColor;

  private int[] data;
}
