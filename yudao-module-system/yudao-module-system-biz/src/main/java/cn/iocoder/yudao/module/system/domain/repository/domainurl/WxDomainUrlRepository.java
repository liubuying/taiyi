package cn.iocoder.yudao.module.system.domain.repository.domainurl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.domainurl.vo.WxDomainUrlVO;
import cn.iocoder.yudao.module.system.domain.model.domainurl.DomainName;
import cn.iocoder.yudao.module.system.domain.request.DomainNameRequest;

public interface WxDomainUrlRepository {

    /**
     * 分页查询域名列表
     */
    PageResult<DomainName> selectPage(DomainNameRequest reqVO);


    Long deleteDomainUrl(WxDomainUrlVO domainUrlVO);

    void saveDomainUrl(DomainName domainName);
}
