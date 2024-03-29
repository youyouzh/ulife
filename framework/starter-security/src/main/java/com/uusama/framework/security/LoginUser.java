package com.uusama.framework.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uusama.framework.web.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录用户信息
 *
 * @author uusama
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class LoginUser {

    /** 用户编号 */
    private Long id;
    /** 用户类型 */
    private UserTypeEnum userType;
    /** 租户编号 */
    private Long tenantId;
    /** 授权范围 */
    private List<String> scopes;

    // ========== 上下文 ==========
    /**
     * 上下文字段，不进行持久化
     *
     * 1. 用于基于 LoginUser 维度的临时缓存
     */
    @JsonIgnore
    private Map<String, Object> context;

    public void setContext(String key, Object value) {
        if (context == null) {
            context = new HashMap<>();
        }
        context.put(key, value);
    }
}
