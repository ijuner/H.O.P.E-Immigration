package ca.conestoga.project.product.bo.user;

import ca.conestoga.project.entity.client.WorkExperience;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ApiWorkExperienceEntity {
    private int idWorkExperience;



    private int clientId;


    private String jobTitle;


    private String English;


    private String codeNOC;


    private String amtWage;


    private String txtDuties;


    private String jobType;


    private String workplaceLocation;


    private String dateStart;

    public ApiWorkExperienceEntity(WorkExperience workExperience) {
        if(!ObjectUtils.isEmpty(workExperience)) {
            this.idWorkExperience = workExperience.getIdWorkExperience();
            this.jobTitle = workExperience.getJobTitle();
            this.English = workExperience.getEnglish();
            this.codeNOC = workExperience.getCodeNOC();
            this.amtWage = workExperience.getAmtWage();
            this.txtDuties = workExperience.getTxtDuties();
            this.jobType = workExperience.getJobType();
            this.workplaceLocation = workExperience.getWorkplaceLocation();
            this.dateStart = workExperience.getDateStart();
        }
    }
}
