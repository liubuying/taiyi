package cn.iocoder.yudao.module.system.service.domainurll;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.domainurl.vo.WxDomainUrlVO;
import cn.iocoder.yudao.module.system.domain.model.domainurl.DomainName;
import cn.iocoder.yudao.module.system.domain.repository.domainurl.WxDomainUrlRepository;
import cn.iocoder.yudao.module.system.domain.request.DomainNameRequest;
import cn.iocoder.yudao.module.system.enums.ErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WxDomainUrlServiceImpl implements WxDomainUrlService{

    @Autowired
    private WxDomainUrlRepository wxDomainUrlRepository;

    @Override
    public PageResult<DomainName> selectPage(DomainNameRequest domainNameRequest) {
        return wxDomainUrlRepository.selectPage(domainNameRequest);
    }

    @Override
    public CommonResult<Void> saveDomainUrl(WxDomainUrlVO wxDomainUrlVO) {
        if(wxDomainUrlVO == null
                || StringUtils.isBlank(wxDomainUrlVO.getDomainName())){
            return CommonResult.error(ErrorCodeConstants.PARAM_NOT_NULL);
        }
        DomainName domainName = new DomainName();
        BeanUtils.copyProperties(wxDomainUrlVO, domainName);
        domainName.setDomainDesc(wxDomainUrlVO.getDesc());
        wxDomainUrlRepository.saveDomainUrl(domainName);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<Boolean> deleteDomainUrl(WxDomainUrlVO domainUrlVO) {
        Long i = wxDomainUrlRepository.deleteDomainUrl(domainUrlVO);
        return CommonResult.success(i > 0);
    }


}
