package com.external.plugins.exceptions;

import com.appsmith.external.exceptions.AppsmithErrorAction;
import com.appsmith.external.exceptions.pluginExceptions.BasePluginError;
import com.appsmith.external.models.ErrorType;
import lombok.Getter;

import java.text.MessageFormat;

@Getter
public enum MongoPluginError implements BasePluginError {
    UNSUPPORTED_OPERATION(
            500,
            "PE-MNG-4005",
            "{0}",
            AppsmithErrorAction.LOG_EXTERNALLY,
            "Unsupported operation",
            ErrorType.INTERNAL_ERROR,
            "{1}",
            "{2}"
    ),
    QUERY_EXECUTION_FAILED(
            500,
            "PE-MNG-5000",
            "{0}",
            AppsmithErrorAction.LOG_EXTERNALLY,
            "Query execution error",
            ErrorType.INTERNAL_ERROR,
            "{1}",
            "{2}"
    ),
    FORM_TO_NATIVE_TRANSLATION_ERROR(
            500,
            "PE-MNG-5001",
            "Plugin failed to convert formData into native query",
            AppsmithErrorAction.LOG_EXTERNALLY,
            "Query configuration error",
            ErrorType.INTERNAL_ERROR,
            "{0}",
            "{1}"
    ),
    ;

    private final Integer httpErrorCode;
    private final String appErrorCode;
    private final String message;
    private final String title;
    private final AppsmithErrorAction errorAction;
    private final ErrorType errorType;

    private final String downstreamErrorMessage;

    private final String downstreamErrorCode;

    MongoPluginError(Integer httpErrorCode, String appErrorCode, String message, AppsmithErrorAction errorAction,
                        String title, ErrorType errorType, String downstreamErrorMessage, String downstreamErrorCode) {
        this.httpErrorCode = httpErrorCode;
        this.appErrorCode = appErrorCode;
        this.errorType = errorType;
        this.errorAction = errorAction;
        this.message = message;
        this.title = title;
        this.downstreamErrorMessage = downstreamErrorMessage;
        this.downstreamErrorCode = downstreamErrorCode;
    }

    public String getMessage(Object... args) {
        return new MessageFormat(this.message).format(args);
    }

    public String getErrorType() { return this.errorType.toString(); }

    public String getDownstreamErrorMessage(Object... args) {
        return replacePlaceholderWithValue(this.downstreamErrorMessage, args);
    }

    public String getDownstreamErrorCode(Object... args) {
        return replacePlaceholderWithValue(this.downstreamErrorCode, args);
    }
}