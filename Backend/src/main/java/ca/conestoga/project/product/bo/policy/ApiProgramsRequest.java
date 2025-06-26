package ca.conestoga.project.product.bo.policy;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * api request Form Object
 */
@Data
@Accessors(chain = true)
public class ApiProgramsRequest {
    // country code
    private String country = "CA";    // default is "CA"

    // organization code
    private String code;

    private String type;
}
