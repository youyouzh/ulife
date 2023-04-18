package com.uusama.module.system.controller.admin.user.vo.user;

import com.uusama.framework.web.enums.CommonState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 用户更新状态 Request VO")
@Data
public class UserUpdateStatusReqVO {

    @Schema(description = "用户编号", required = true, example = "1024")
    @NotNull(message = "角色编号不能为空")
    private Long id;

    @Schema(description = "状态,见 CommonState 枚举", required = true, example = "1")
    @NotNull(message = "状态不能为空")
    private CommonState state;

}
