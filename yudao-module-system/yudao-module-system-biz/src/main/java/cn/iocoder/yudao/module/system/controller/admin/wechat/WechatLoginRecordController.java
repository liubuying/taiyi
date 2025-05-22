package cn.iocoder.yudao.module.system.controller.admin.wechat;

import cn.iocoder.yudao.module.system.controller.admin.wechat.vo.WechatLoginRecordPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.wechat.vo.WechatLoginRecordRespVO;
import cn.iocoder.yudao.module.system.controller.admin.wechat.vo.WechatLoginRecordSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.wx.WechatLoginRecordDO;
import cn.iocoder.yudao.module.system.service.wx.WechatLoginRecordService;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;


@Tag(name = "管理后台 - 微信用户登录记录")
@RestController
@RequestMapping("/taiyi/wechat-login-record")
@Validated
public class WechatLoginRecordController {

    @Resource
    private WechatLoginRecordService wechatLoginRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建微信用户登录记录")
    @PreAuthorize("@ss.hasPermission('taiyi:wechat-login-record:create')")
    public CommonResult<Long> createWechatLoginRecord(@Valid @RequestBody WechatLoginRecordSaveReqVO createReqVO) {
        return success(wechatLoginRecordService.createWechatLoginRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新微信用户登录记录")
    @PreAuthorize("@ss.hasPermission('taiyi:wechat-login-record:update')")
    public CommonResult<Boolean> updateWechatLoginRecord(@Valid @RequestBody WechatLoginRecordSaveReqVO updateReqVO) {
        wechatLoginRecordService.updateWechatLoginRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除微信用户登录记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('taiyi:wechat-login-record:delete')")
    public CommonResult<Boolean> deleteWechatLoginRecord(@RequestParam("id") Long id) {
        wechatLoginRecordService.deleteWechatLoginRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得微信用户登录记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('taiyi:wechat-login-record:query')")
    public CommonResult<WechatLoginRecordRespVO> getWechatLoginRecord(@RequestParam("id") Long id) {
        WechatLoginRecordDO wechatLoginRecord = wechatLoginRecordService.getWechatLoginRecord(id);
        return success(BeanUtils.toBean(wechatLoginRecord, WechatLoginRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得微信用户登录记录分页")
    @PreAuthorize("@ss.hasPermission('taiyi:wechat-login-record:query')")
    public CommonResult<PageResult<WechatLoginRecordRespVO>> getWechatLoginRecordPage(@Valid WechatLoginRecordPageReqVO pageReqVO) {
        PageResult<WechatLoginRecordDO> pageResult = wechatLoginRecordService.getWechatLoginRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, WechatLoginRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出微信用户登录记录 Excel")
    @PreAuthorize("@ss.hasPermission('taiyi:wechat-login-record:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportWechatLoginRecordExcel(@Valid WechatLoginRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<WechatLoginRecordDO> list = wechatLoginRecordService.getWechatLoginRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "微信用户登录记录.xls", "数据", WechatLoginRecordRespVO.class,
                        BeanUtils.toBean(list, WechatLoginRecordRespVO.class));
    }

}