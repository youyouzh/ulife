package com.uusama.module.system.controller.admin.dict.vo.type;

import com.uusama.framework.web.enums.CommonState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.uusama.common.util.DateTimeUtil.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 字典类型分页列表 Request VO")
@Data
public class DictTypeExportReqVO {

    @Schema(description = "字典类型名称,模糊匹配", example = "uusama")
    private String name;

    @Schema(description = "字典类型,模糊匹配", example = "sys_common_sex")
    private String type;

    @Schema(description = "展示状态,参见 CommonState 枚举类", example = "1")
    private CommonState state;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}
