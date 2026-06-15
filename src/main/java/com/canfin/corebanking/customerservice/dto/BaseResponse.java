package com.canfin.corebanking.customerservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class BaseResponse<T> {

    private T data;
    private String errorCode="0";
    private String errorMessage;
    private String successMessage;
    private String successCode;

    public BaseResponse(T data,  String successCode,String successMessage) {
        this.data = data;
        this.successCode = successCode;
        this.successMessage = successMessage;
        this.errorCode = "0";
        this.errorMessage = null;
    }

    public BaseResponse() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }
}
