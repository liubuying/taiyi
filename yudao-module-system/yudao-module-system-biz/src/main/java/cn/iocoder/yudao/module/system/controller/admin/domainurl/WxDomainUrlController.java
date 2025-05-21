package cn.iocoder.yudao.module.system.controller.admin.domainurl;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.domainurl.vo.WxDomainUrlVO;
import cn.iocoder.yudao.module.system.domain.model.base.UserInfo;
import cn.iocoder.yudao.module.system.domain.model.domainurl.DomainName;
import cn.iocoder.yudao.module.system.domain.request.DomainNameRequest;
import cn.iocoder.yudao.module.system.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.system.service.domainurll.WxDomainUrlService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

@Slf4j
@RestController
@RequestMapping("/system/wx/domainUrl")
public class WxDomainUrlController {

    @Resource
    private WxDomainUrlService wxDomainUrlService;

    @GetMapping("/page")
    public CommonResult<PageResult<WxDomainUrlVO>> page(DomainNameRequest reqVO) {
        PageResult<DomainName> re = wxDomainUrlService.selectPage(reqVO);
        List<WxDomainUrlVO> resultData = Optional.ofNullable(re.getList())
                .orElse(new ArrayList<>()).stream()
                .map(domainName -> {
                    WxDomainUrlVO wxDomainUrlVO = new WxDomainUrlVO();
                    BeanUtils.copyProperties(domainName, wxDomainUrlVO);
                    return wxDomainUrlVO;
                }).collect(Collectors.toList());
        PageResult<WxDomainUrlVO> wxDomainUrlVOPageResult = new PageResult<>(resultData, re.getTotal());
        return CommonResult.success(wxDomainUrlVOPageResult);
    }


    @PostMapping("/saveDomainInfo")
    public CommonResult<Boolean> saveWxDomainUrl(@RequestBody WxDomainUrlVO wxDomainUrlVO) {
        try {
            Long loginUserId = getLoginUserId();
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(loginUserId);
            userInfo.setOperationTime(new Date());
            if(wxDomainUrlVO.getId() != null){
                wxDomainUrlVO.setOperator(userInfo);
            } else {
                wxDomainUrlVO.setCreator(userInfo);
            }
            wxDomainUrlService.saveDomainUrl(wxDomainUrlVO);
            return CommonResult.success(true);
        }catch (Exception e){
            log.error("更新域名数据异常,{}", Throwables.getStackTraceAsString(e));
            return CommonResult.success(false);
        }
    }


    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteWxDomainUrl(@PathVariable("id") Long id) {
        try {
            if (ObjectUtil.isNull(id)) {
                return CommonResult.error(ErrorCodeConstants.PARAM_NOT_NULL);
            }
            WxDomainUrlVO wxDomainUrlVO = new WxDomainUrlVO();
            Long loginUserId = getLoginUserId();
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(loginUserId);
            userInfo.setOperationTime(new Date());
            if (ObjectUtil.isNull(loginUserId)) {
                return CommonResult.error(ErrorCodeConstants.USER_NOT_EXISTS);
            }
            wxDomainUrlVO.setId(id);
            wxDomainUrlVO.setOperator(userInfo);
            return wxDomainUrlService.deleteDomainUrl(wxDomainUrlVO);
        }catch (Exception e){
            log.error("删除数据异常，请稍后再试 ｛｝",Throwables.getStackTraceAsString(e));
            return CommonResult.success(false);
        }
    }



}
