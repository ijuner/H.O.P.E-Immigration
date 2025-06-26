package ca.conestoga.project.product.bo.policy;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * api request Form Object
 */
@Data
@Accessors(chain = true)
public class ApiProgramsResponse {
    private int id;
    // country code
    private String country = "CA";    // default is "CA"

    /*
the name of the program
 */
    private String name;

    /*
    the description of the program
     */
    private String content;
}
