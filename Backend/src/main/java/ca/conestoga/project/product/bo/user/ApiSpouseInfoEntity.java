package ca.conestoga.project.product.bo.user;

import ca.conestoga.project.entity.client.SpouseInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ApiSpouseInfoEntity {
    private int idSpouseInfo;



    private int clientId;

    private String educationLevel;


    private String program;


    private String officialLanguage;


    private String codeNOC;

    public ApiSpouseInfoEntity(SpouseInfo spouseInfo) {
        if(!ObjectUtils.isEmpty(spouseInfo)) {
            this.idSpouseInfo = spouseInfo.getIdSpouseInfo();
            this.educationLevel = spouseInfo.getEducationLevel();
            this.program = spouseInfo.getProgram();
            this.officialLanguage = spouseInfo.getOfficialLanguage();
            this.codeNOC = spouseInfo.getCodeNOC();
            this.clientId = spouseInfo.getClient().getId();
        }
    }
}
