package com.uusama.module.system.controller.admin.dept.vo.dept;

import com.uusama.framework.web.enums.CommonState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 部门信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptRespVO extends DeptBaseVO {

    @Schema(description = "部门编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "状态,参见 CommonState 枚举类", required = true, example = "1")
    private CommonState state;

    @Schema(description = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

}
