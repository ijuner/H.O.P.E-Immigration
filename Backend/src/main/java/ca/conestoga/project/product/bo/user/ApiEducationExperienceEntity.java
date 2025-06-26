package ca.conestoga.project.product.bo.user;

import ca.conestoga.project.entity.client.EducationExperience;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ApiEducationExperienceEntity {
    private int idEducationExperience;



    private int clientId;


    private String educationLevel;



    private String program;


    private String address;

    public ApiEducationExperienceEntity(EducationExperience education) {
        if(!ObjectUtils.isEmpty(education)) {
            this.idEducationExperience = education.getIdEducationExperience();
            this.educationLevel = education.getEducationLevel();
            this.program = education.getProgram();
            this.address = education.getAddress();
            this.clientId = education.getClient().getId();
        }
    }
}
