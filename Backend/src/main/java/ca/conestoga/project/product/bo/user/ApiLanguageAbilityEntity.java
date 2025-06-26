package ca.conestoga.project.product.bo.user;

import ca.conestoga.project.entity.client.LanguageAbility;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ApiLanguageAbilityEntity {
    private int idLanguageAbility;



    private int clientId;


    private String english;


    private String french;


    private String obtainedTerm;

    public ApiLanguageAbilityEntity(LanguageAbility languageAbility) {
        if(!ObjectUtils.isEmpty(languageAbility)) {
            this.idLanguageAbility = languageAbility.getIdLanguageAbility();
            this.english = languageAbility.getEnglish();
            this.french = languageAbility.getFrench();
            this.obtainedTerm = languageAbility.getObtainedTerm();
            this.clientId = languageAbility.getClient().getId();
        }
    }
}
