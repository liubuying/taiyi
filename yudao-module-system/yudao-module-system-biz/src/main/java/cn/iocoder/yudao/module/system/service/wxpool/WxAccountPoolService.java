package cn.iocoder.yudao.module.system.service.wxpool;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.wxpool.vo.WxAccountPoolVO;
import cn.iocoder.yudao.module.system.domain.model.wxpool.WxAccountPool;
import cn.iocoder.yudao.module.system.domain.request.WxAccountPoolRequest;

import javax.validation.Valid;
import java.util.List;

public interface WxAccountPoolService {

    /**
     * 查询账号池分页数据
     * @param wxAccountPoolRequest
     * @return pageResult
     */
    PageResult<WxAccountPool> queryWxAccountPoolForPage(WxAccountPoolRequest wxAccountPoolRequest);

    Long saveWxAccountPool(WxAccountPoolVO wxAccountPoolVO);

    void deleteWxAccountPool(WxAccountPoolVO wxAccountPoolVO);


    Boolean bindDomainUrl(WxAccountPoolVO wxAccountPoolVO);

    Boolean unBindDomainUrl(WxAccountPoolVO wxAccountPoolVO);

    Boolean bindEmployee(WxAccountPoolVO wxAccountPoolVO);

    Boolean unBindEmployee(WxAccountPoolVO wxAccountPoolVO);

    /**
     * 根据员工id查询管理的账号池
     * @param poolRequest
     * @return
     */
    List<WxAccountPool> queryWxAccountByEmployeeId(WxAccountPoolRequest poolRequest);

}
