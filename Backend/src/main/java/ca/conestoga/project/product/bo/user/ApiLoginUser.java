package ca.conestoga.project.product.bo.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ApiLoginUser {
  private int clientId;
  private String name;
  private String email;
  private String phoneNumber;
  private Character isVIP;

  private ApiClientBasicEntity clientBasicInfo;

  private List<ApiEducationExperienceEntity> educationExperiences;

  private ApiExtraInfoEntity extraInfo;

  private List<ApiLanguageAbilityEntity> languageAbilities;

  private ApiSpouseInfoEntity spouseInfo;

  private List<ApiWorkExperienceEntity> workExperiences;
}
