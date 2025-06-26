package ca.conestoga.project.product.bo.user;

import ca.conestoga.project.entity.client.ClientBasicInfo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ApiClientBasicEntity {

    private int idClientBasic;

    private int clientId;

    private String age;


    private String marriageStatus;


    private String immigrationStatus;

    public ApiClientBasicEntity(ClientBasicInfo basicInfo) {
        if(!ObjectUtils.isEmpty(basicInfo)){
            this.idClientBasic = basicInfo.getIdClientBasic();
            this.age = basicInfo.getAge();
            this.marriageStatus = basicInfo.getMarriageStatus();
            this.immigrationStatus = basicInfo.getImmigrationStatus();
        }
    }
}
