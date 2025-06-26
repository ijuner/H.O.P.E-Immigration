package ca.conestoga.project.common;

import ca.conestoga.project.entity.admin.Admin;

import java.util.Arrays;

/**
 * a enumeration for support languages
 */
public enum LanguageEnum {

    EN("en"),
    CH("zh-CN");

    private final String value;

    LanguageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public static LanguageEnum findStatusByValue(String value) {
        if (value == null) {
            return LanguageEnum.EN;
        }
        return Arrays.stream(LanguageEnum.values()).filter(s -> value.equalsIgnoreCase(s.value)).findFirst().orElse(LanguageEnum.EN);
    }

}
