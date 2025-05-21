package cn.iocoder.yudao.module.system.controller.admin.wxpool;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.wxpool.vo.WxAccountPoolVO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.domain.enums.YesOrNoEnum;
import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import cn.iocoder.yudao.module.system.domain.model.wxpool.WxAccountPool;
import cn.iocoder.yudao.module.system.domain.request.WxAccountPoolRequest;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.system.service.wxpool.WxAccountPoolService;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;


@Tag(name = "管理后台 - 微信池")
@RestController
@RequestMapping("/system/wx/pool")
@Slf4j
public class WxAccountPoolController {

    @Resource
    private WxAccountPoolService wxAccountPoolService;
    @Resource
    private AdminUserService userService;

    /**
     * 查询分页数据
     */
    @GetMapping("/queryWxPool")
    @Operation(summary = "微信账号池分页列表")
    //@PreAuthorize("@ss.hasPermission('system:wx:pool')")
    public CommonResult<PageResult<WxAccountPoolVO>> queryWxAccountPoolForPage(@Valid WxAccountPoolRequest request) {

        try {
            log.info("查询微信账号池分页数据,param:{}", JSON.toJSONString(request));
            PageResult<WxAccountPool> wxAccountPoolPageResult = wxAccountPoolService.queryWxAccountPoolForPage(request);
            if(wxAccountPoolPageResult == null || CollectionUtils.isEmpty(wxAccountPoolPageResult.getList())){
                PageResult<WxAccountPoolVO> emptyPageResult = new PageResult<>(Collections.emptyList(), 0L);
                return CommonResult.success(emptyPageResult);
            }
            List<WxAccountPoolVO> result = wxAccountPoolPageResult.getList().stream().map(wxAccountPool -> {
                WxAccountPoolVO wxAccountPoolVO = new WxAccountPoolVO();

                BeanUtils.copyProperties(wxAccountPool, wxAccountPoolVO);
                wxAccountPoolVO.setAccountType(wxAccountPool.getAccountType().getStatus());
                return wxAccountPoolVO;
            }).collect(Collectors.toList());
            PageResult<WxAccountPoolVO> wxAccountPoolVOPageResult = new PageResult<>(result, wxAccountPoolPageResult.getTotal());
            //log.info("查询微信账号池分页数据,result:{}", JSON.toJSONString(result));
            return CommonResult.success(wxAccountPoolVOPageResult);
        } catch (Exception e) {
            log.info("查询微信池账号异常,param:{},e:{}",JSON.toJSONString(request), Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    @PostMapping("/saveWxAccountPool")
    @Operation(summary = "添加微信账号")
    public CommonResult<Boolean> saveWxAccountPool(@RequestBody WxAccountPoolVO wxAccountPoolVO) {
        WxAccountPoolRequest request = new WxAccountPoolRequest();
        try {
            Long loginUserId = getLoginUserId();
            AdminUserDO user = userService.getUser(loginUserId);
            // todo lxw 修改
            wxAccountPoolVO.setCompanyId(user.getTenantId().toString());
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(loginUserId);
            userInfo.setUserName(user.getUsername());
            userInfo.setOperationTime(new Date());
            if(null != wxAccountPoolVO.getId() ) {
                wxAccountPoolVO.setOperator(userInfo);
            } else {
                wxAccountPoolVO.setCreator(userInfo);
            }
            wxAccountPoolService.saveWxAccountPool(wxAccountPoolVO);
            return CommonResult.success(true);
        } catch (Exception e) {
            log.info("查询微信池账号异常,param:{},e:{}",JSON.toJSONString(request), Throwables.getStackTraceAsString(e));
            return CommonResult.error(9999,"系统异常");
        }
    }


    private CommonResult<AdminUserDO> getUser(){
        Long loginUserId = getLoginUserId();
        AdminUserDO user = userService.getUser(loginUserId);
        if(user == null){
            return CommonResult.error(12223,"用户不存在");
        }
        return CommonResult.success(user);
    }


    @DeleteMapping("/deleteWxAccountPool")
    @Operation(summary = "删除微信账号")
    public CommonResult<Boolean> deleteWxAccountPool(@RequestParam("id") Long id) {
        WxAccountPoolRequest request = new WxAccountPoolRequest();
        try {
            WxAccountPoolVO wxAccountPoolVO = new WxAccountPoolVO();
            wxAccountPoolVO.setId(id);
            Long loginUserId = getLoginUserId();
            AdminUserDO user = userService.getUser(loginUserId);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(loginUserId);
            userInfo.setUserName(user.getUsername());
            userInfo.setOperationTime(new Date());
            wxAccountPoolVO.setOperator(userInfo);
            wxAccountPoolVO.setDeleted(YesOrNoEnum.YES.getStatus());
            wxAccountPoolService.deleteWxAccountPool(wxAccountPoolVO);
            return CommonResult.success(true);
        } catch (Exception e) {
            log.info("查询微信池账号异常,param:{},e:{}",JSON.toJSONString(request), Throwables.getStackTraceAsString(e));
            return CommonResult.error(9999,"系统异常");
        }
    }



}
