package com.brliu.utils;


import com.brliu.enums.ResponseStateEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {

    private int state;
    private String message;
    private Object data;

    private ResponseMessage(ResponseStateEnum state, Object data) {
        this.state = state.getState();
        this.data = data;
    }

    private ResponseMessage(ResponseStateEnum state) {
        this.state = state.getState();
    }

    private ResponseMessage(ResponseStateEnum state, String message) {
        this.state = state.getState();
        this.message = message;
    }

    public static ResponseMessage success(Object data) {

        return new ResponseMessage(ResponseStateEnum.SUCCESS, data);
    }

    public static ResponseMessage success(String message) {

        return new ResponseMessage(ResponseStateEnum.SUCCESS, message);
    }

    public static ResponseMessage error(String message) {

        return new ResponseMessage(ResponseStateEnum.SERVER_ERROR, message);
    }

    public static ResponseMessage success() {
        return new ResponseMessage(ResponseStateEnum.SUCCESS);
    }

    public static ResponseMessage paramError(String message) {

        return new ResponseMessage(ResponseStateEnum.PARAM_ERROR, message);
    }

    public static ResponseMessage of(ResponseStateEnum state, String message) {

        return new ResponseMessage(state, message);
    }
}
