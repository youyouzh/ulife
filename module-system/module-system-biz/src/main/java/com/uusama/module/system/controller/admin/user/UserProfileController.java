package com.uusama.module.system.controller.admin.user;

import com.uusama.common.util.CollUtil;
import com.uusama.framework.api.pojo.CommonResult;
import com.uusama.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import com.uusama.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import com.uusama.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import com.uusama.module.system.convert.user.UserConvert;
import com.uusama.module.system.entity.dept.DeptDO;
import com.uusama.module.system.entity.dept.PostDO;
import com.uusama.module.system.entity.permission.RoleDO;
import com.uusama.module.system.entity.user.AdminUserDO;
import com.uusama.module.system.service.dept.DeptService;
import com.uusama.module.system.service.dept.PostService;
import com.uusama.module.system.service.permission.PermissionService;
import com.uusama.module.system.service.permission.RoleService;
import com.uusama.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.uusama.framework.security.util.SecurityAuthUtils.getLoginUserId;
import static com.uusama.framework.web.exception.ServiceExceptionUtil.exception;
import static com.uusama.module.system.constant.ErrorCodeConstants.FILE_IS_EMPTY;

@Tag(name = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/system/user/profile")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserProfileController {
    private final AdminUserService userService;
    private final DeptService deptService;
    private final PostService postService;
    private final PermissionService permissionService;
    private final RoleService roleService;

    @GetMapping("/get")
    @Operation(summary = "获得登录用户信息")
    public CommonResult<UserProfileRespVO> profile() {
        // 获得用户基本信息
        AdminUserDO user = userService.getUser(getLoginUserId());
        UserProfileRespVO resp = UserConvert.INSTANCE.convert03(user);
        // 获得用户角色
        List<RoleDO> userRoles = roleService.getRoleListFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
        resp.setRoles(UserConvert.INSTANCE.convertList(userRoles));
        // 获得部门信息
        if (user.getDeptId() != null) {
            DeptDO dept = deptService.getDept(user.getDeptId());
            resp.setDept(UserConvert.INSTANCE.convert02(dept));
        }
        // 获得岗位信息
        if (CollUtil.isNotEmpty(user.getPostIds())) {
            List<PostDO> posts = postService.getPostList(user.getPostIds());
            resp.setPosts(UserConvert.INSTANCE.convertList02(posts));
        }
        return CommonResult.success(resp);
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户个人信息")
    public CommonResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return CommonResult.success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "修改用户个人密码")
    public CommonResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return CommonResult.success(true);
    }

    @RequestMapping(value = "/update-avatar", method = {RequestMethod.POST, RequestMethod.PUT}) // 解决 uni-app 不支持 Put 上传文件的问题
    @Operation(summary = "上传用户个人头像")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw exception(FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
        return CommonResult.success(avatar);
    }

}
