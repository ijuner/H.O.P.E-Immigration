package ca.conestoga.project.product.bo.policy;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApiProgramConditions {
  private String code;

  private String name;

}
