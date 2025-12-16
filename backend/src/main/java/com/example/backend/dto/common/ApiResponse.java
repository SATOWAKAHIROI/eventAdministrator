package com.example.backend.dto.common;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ErrorInfo error;

    public ApiResponse(){

    }

    public ApiResponse(boolean success, T data, ErrorInfo error){
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> error(String code, String message){
        return new ApiResponse<>(false, null, new ErrorInfo(code, message));
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorInfo getError() {
        return error;
    }

    public void setError(ErrorInfo error) {
        this.error = error;
    }

    public static class ErrorInfo{
        private String code;
        private String message;
        private Object details;

        public ErrorInfo(){
        }

        public ErrorInfo(String code, String message){
            this.code = code;
            this.message = message;
        }

        public ErrorInfo(String code, String message, Object details){
            this.code = code;
            this.message = message;
            this.details = details;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getDetails() {
            return details;
        }

        public void setDetails(Object details) {
            this.details = details;
        }
    }
}
