package ca.conestoga.project.product.bo.user;

import ca.conestoga.project.entity.client.ExtraInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ApiExtraInfoEntity {
    private int idExtraInfo;



    private int clientId;


    private String relatives_flg;


    private String PNP_flg;


    private String offer_flg;


    private String bilingual_flg;

    public ApiExtraInfoEntity(ExtraInfo extraInfo) {
        if(!ObjectUtils.isEmpty(extraInfo)) {
            this.idExtraInfo = extraInfo.getIdExtraInfo();
            this.relatives_flg = extraInfo.getRelatives_flg();
            this.PNP_flg = extraInfo.getPNP_flg();
            this.offer_flg = extraInfo.getOffer_flg();
            this.bilingual_flg = extraInfo.getBilingual_flg();
        }
    }
}
