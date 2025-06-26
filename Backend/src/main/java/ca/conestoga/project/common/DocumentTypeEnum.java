package ca.conestoga.project.common;

import java.util.Arrays;

/**
 * a enumeration for document type
 */
public enum DocumentTypeEnum {

    STUDY_PLAN("sp");

    private final String value;

    DocumentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public static DocumentTypeEnum findStatusByValue(String value) {
        if (value == null) {
            return DocumentTypeEnum.STUDY_PLAN;
        }
        return Arrays.stream(DocumentTypeEnum.values()).filter(s -> value.equalsIgnoreCase(s.value)).findFirst().orElse(DocumentTypeEnum.STUDY_PLAN);
    }

}
