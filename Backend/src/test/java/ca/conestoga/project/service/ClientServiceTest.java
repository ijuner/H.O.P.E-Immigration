package ca.conestoga.project.service;

import ca.conestoga.project.common.DocumentTypeEnum;
import ca.conestoga.project.common.RespBody;
import ca.conestoga.project.entity.client.ClientDocument;
import ca.conestoga.project.product.bo.user.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class of ClientService
 */
@SpringBootTest
class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Transactional
    @Rollback
    @Test
    void updateClientBasic() {
        // Arrange
        ApiClientBasicEntity apiClientBasicEntity = new ApiClientBasicEntity().setAge("77")
                .setImmigrationStatus("y").setMarriageStatus("y");

        // Act
        //RespBody<?> result = clientService.UpdateClientBasic(apiClientBasicEntity, 1);

        // Assert
        //assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), result.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void updateEducationExp() {
        // Arrange
        ApiEducationExperienceEntity apiEducationExperienceEntity = new ApiEducationExperienceEntity()
                .setClientId(111).setIdEducationExperience(1).setEducationLevel("123").setAddress("ABC");

        // Act
        RespBody<?> result = clientService.UpdateEducationExp(apiEducationExperienceEntity, 1);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), result.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void updateExtraInfo() {
        // Arrange
        ApiExtraInfoEntity apiExtraInfoEntity = new ApiExtraInfoEntity().setIdExtraInfo(123).setClientId(111)
                .setBilingual_flg("YES").setPNP_flg("YES").setRelatives_flg("YES");

        // Act
        RespBody<?> result = clientService.UpdateExtraInfo(apiExtraInfoEntity, 1);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), result.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void updateLanguageAbility() {
        // Arrange
        ApiLanguageAbilityEntity apiLanguageAbilityEntity = new ApiLanguageAbilityEntity().setClientId(111);

        // Act
        RespBody<?> result = clientService.UpdateLanguageAbility(apiLanguageAbilityEntity, 1);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), result.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void updateSpouseInfo() {
        // Arrange
        ApiSpouseInfoEntity apiSpouseInfoEntity = new ApiSpouseInfoEntity().setClientId(111);

        // Act
        RespBody<?> result = clientService.UpdateSpouseInfo(apiSpouseInfoEntity, 1);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), result.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void updateWorkExp() {
        // Arrange
        ApiWorkExperienceEntity apiWorkExperienceEntity = new ApiWorkExperienceEntity().setClientId(111);

        // Act
        RespBody<?> result = clientService.UpdateWorkExp(apiWorkExperienceEntity, 1);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), result.getCode());
    }

    @Test
    void getDocument() {
        // Arrange
        ApiLoginUser apiLoginUser = new ApiLoginUser().setClientId(3);
        String type = DocumentTypeEnum.STUDY_PLAN.getValue();

        // Act
        RespBody<?> result = clientService.getDocument(apiLoginUser, type);

        // Assert
        assertEquals(RespBody.CodeEnum.SUCCESS.getCode(), result.getCode());
        //ClientDocument clientDocument = (ClientDocument)result.getData();
        //assertNotNull(clientDocument);
    }
}