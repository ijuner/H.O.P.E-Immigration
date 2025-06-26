package ca.conestoga.project.common;

/**
 * All the Errors
 */
public enum ErrorEnum {
    UNKNOWN_ERROR("unknown error"),
    DATA_NOT_FOUND("data not found"),
    LOGIN_STATUS_ERROR("login status error"),
    ADMIN_ACCOUNT_NOT_FOUND("admin account not found"),
    PROFILE_NOT_COMPLETED("profile not completed"),
    USER_NOT_FOUND("user not found"),
    DATE_FORMAT_ERROR("data format error"),
    DATA_ERROR("data error"),
    ORIGINAL_PASSWORD_ERROR("original password error"),
    NAME_CONFLICT("name conflict"),
    FILE_SIZE_EXCEED("file size exceed"),
    EMAIL_EXISTS("email exist");

    ErrorEnum(String message) {
        this.message = message;
    }

    private final String message;

    public String getMessage() {
        return this.message;
    }
}