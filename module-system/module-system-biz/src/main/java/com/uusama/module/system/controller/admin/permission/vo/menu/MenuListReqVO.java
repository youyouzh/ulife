package com.uusama.module.system.controller.admin.permission.vo.menu;

import com.uusama.framework.web.enums.CommonState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 菜单列表 Request VO")
@Data
public class MenuListReqVO {

    @Schema(description = "菜单名称,模糊匹配", example = "uusama")
    private String name;

    @Schema(description = "展示状态,参见 CommonState 枚举类", example = "1")
    private CommonState state;

}
