package cn.iocoder.yudao.module.system.domain.repository.wxpool;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.domainurl.vo.WxDomainUrlVO;
import cn.iocoder.yudao.module.system.domain.request.WxAccountPoolRequest;
import cn.iocoder.yudao.module.system.domain.model.wxpool.WxAccountPool;

public interface WxAccountDomainRepository {

    /**
     * 查询wx 账号池数据接口
     */
    PageResult<WxAccountPool> queryWxAccountPoolForPage(WxAccountPoolRequest wxAccountPoolRequest);

    /**
     * 修改账号池数据接口
     */
    void saveWxAccountPool(WxAccountPool wxAccountPool);


    /**
     * 删除 账号池数据接口
     */
    void deleteWxAccountPoolById(WxAccountPool wxAccountPool);


    Boolean bindDomainUrl(WxAccountPool wxAccountPool);

    Boolean unBindDomainUrl(WxAccountPool wxAccountPool);

    Boolean bindEmployee(WxAccountPool wxAccountPool);

    Boolean unBindEmployee(WxAccountPool wxAccountPool);
}
