package com.uusama.module.system.controller.admin.logger.vo.loginlog;

import com.alibaba.excel.annotation.ExcelProperty;
import com.uusama.framework.tool.annotation.DictFormat;
import com.uusama.framework.tool.convert.DictConvert;
import com.uusama.module.system.constant.DictTypeConstants;
import com.uusama.module.system.logger.LoginLogTypeEnum;
import com.uusama.module.system.logger.LoginResultEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志 Excel 导出响应 VO
 */
@Data
public class LoginLogExcelVO {

    @ExcelProperty("日志主键")
    private Long id;

    @ExcelProperty("用户账号")
    private String username;

    @ExcelProperty(value = "日志类型", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.LOGIN_TYPE)
    private LoginLogTypeEnum logType;

    @ExcelProperty(value = "登录结果", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.LOGIN_RESULT)
    private LoginResultEnum result;

    @ExcelProperty("登录 IP")
    private String userIp;

    @ExcelProperty("浏览器 UA")
    private String userAgent;

    @ExcelProperty("登录时间")
    private LocalDateTime createTime;

}
