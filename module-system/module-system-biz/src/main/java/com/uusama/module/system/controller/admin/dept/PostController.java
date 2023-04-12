package com.uusama.module.system.controller.admin.dept;

import com.uusama.framework.mybatis.pojo.PageResult;
import com.uusama.framework.recorder.annotations.OperateLog;
import com.uusama.framework.tool.util.ExcelUtils;
import com.uusama.framework.web.enums.CommonState;
import com.uusama.framework.web.pojo.CommonResult;
import com.uusama.module.system.controller.admin.dept.vo.post.PostCreateReqVO;
import com.uusama.module.system.controller.admin.dept.vo.post.PostExcelVO;
import com.uusama.module.system.controller.admin.dept.vo.post.PostExportReqVO;
import com.uusama.module.system.controller.admin.dept.vo.post.PostPageReqVO;
import com.uusama.module.system.controller.admin.dept.vo.post.PostRespVO;
import com.uusama.module.system.controller.admin.dept.vo.post.PostSimpleRespVO;
import com.uusama.module.system.controller.admin.dept.vo.post.PostUpdateReqVO;
import com.uusama.module.system.convert.dept.PostConvert;
import com.uusama.module.system.entity.dept.PostDO;
import com.uusama.module.system.service.dept.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.uusama.framework.recorder.enums.OperateTypeEnum.EXPORT;
import static com.uusama.framework.web.pojo.CommonResult.success;

@Tag(name = "管理后台 - 岗位")
@RestController
@RequestMapping("/system/post")
@Validated
public class PostController {

    @Resource
    private PostService postService;

    @PostMapping("/create")
    @Operation(summary = "创建岗位")
    @PreAuthorize("@ss.hasPermission('system:post:create')")
    public CommonResult<Long> createPost(@Valid @RequestBody PostCreateReqVO reqVO) {
        Long postId = postService.createPost(reqVO);
        return success(postId);
    }

    @PutMapping("/update")
    @Operation(summary = "修改岗位")
    @PreAuthorize("@ss.hasPermission('system:post:update')")
    public CommonResult<Boolean> updatePost(@Valid @RequestBody PostUpdateReqVO reqVO) {
        postService.updatePost(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除岗位")
    @PreAuthorize("@ss.hasPermission('system:post:delete')")
    public CommonResult<Boolean> deletePost(@RequestParam("id") Long id) {
        postService.deletePost(id);
        return success(true);
    }

    @GetMapping(value = "/get")
    @Operation(summary = "获得岗位信息")
    @Parameter(name = "id", description = "岗位编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public CommonResult<PostRespVO> getPost(@RequestParam("id") Long id) {
        return success(PostConvert.INSTANCE.convert(postService.getPost(id)));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取岗位精简信息列表", description = "只包含被开启的岗位，主要用于前端的下拉选项")
    public CommonResult<List<PostSimpleRespVO>> getSimplePostList() {
        // 获得岗位列表，只要开启状态的
        List<PostDO> list = postService.getPostList(null, Collections.singleton(CommonState.ENABLE));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(PostDO::getSort));
        return success(PostConvert.INSTANCE.convertList02(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得岗位分页列表")
    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public CommonResult<PageResult<PostRespVO>> getPostPage(@Validated PostPageReqVO reqVO) {
        return success(PostConvert.INSTANCE.convertPage(postService.getPostPage(reqVO)));
    }

    @GetMapping("/export")
    @Operation(summary = "岗位管理")
    @PreAuthorize("@ss.hasPermission('system:post:export')")
    @OperateLog(type = EXPORT)
    public void export(HttpServletResponse response, @Validated PostExportReqVO reqVO) throws IOException {
        List<PostDO> posts = postService.getPostList(reqVO);
        List<PostExcelVO> data = PostConvert.INSTANCE.convertList03(posts);
        // 输出
        ExcelUtils.write(response, "岗位数据.xls", "岗位列表", PostExcelVO.class, data);
    }

}