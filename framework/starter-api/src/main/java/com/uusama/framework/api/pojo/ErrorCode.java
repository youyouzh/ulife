package com.uusama.framework.api.pojo;

import com.uusama.framework.api.constants.GlobalErrorCodeConstants;
import com.uusama.framework.api.constants.ServiceErrorCodeRange;
import lombok.Data;

/**
 * 错误码对象
 * <p>
 * 全局错误码，占用 [0, 999], 参见 {@link GlobalErrorCodeConstants}
 * 业务异常错误码，占用 [1 000 000 000, +∞)，参见 {@link ServiceErrorCodeRange}
 * <p>
 * TODO 错误码设计成对象的原因，为未来的 i18 国际化做准备
 *
 * @author uusama
 */
@Data
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误提示
     */
    private String message;

    public ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorCode withMessage(String message) {
        this.message = message;
        return this;
    }
}
