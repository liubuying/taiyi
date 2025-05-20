package cn.iocoder.yudao.module.system.service.domainurll;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.domainurl.vo.WxDomainUrlVO;
import cn.iocoder.yudao.module.system.domain.model.domainurl.DomainName;
import cn.iocoder.yudao.module.system.domain.request.DomainNameRequest;

public interface WxDomainUrlService {

    PageResult<DomainName> selectPage(DomainNameRequest domainNameRequest);


    CommonResult<Void> saveDomainUrl(WxDomainUrlVO wxDomainUrlVO);


    CommonResult<Boolean> deleteDomainUrl(WxDomainUrlVO domainUrlVO);

}
