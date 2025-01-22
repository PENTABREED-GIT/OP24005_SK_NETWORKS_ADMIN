package com.skn.admin.config.api.apidto;

import com.skn.admin.config.api.constant.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class APIDataResponse<T>  extends APIErrorResponse{

    private final T data;

    private APIDataResponse(T data) {
        super(true, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }

    private APIDataResponse(T data, String message) {
        super(true, ErrorCode.OK.getCode(), message);
        this.data = data;
    }

    public static <T> APIDataResponse<T> of (T data) {
        return new APIDataResponse<>(data);
    }

    public static <T> APIDataResponse<T> of (T data, String message) {
        return new APIDataResponse<>(data, message);
    }

    public static <T> APIDataResponse<T> empty () {
        return new APIDataResponse<>(null);
    }
}