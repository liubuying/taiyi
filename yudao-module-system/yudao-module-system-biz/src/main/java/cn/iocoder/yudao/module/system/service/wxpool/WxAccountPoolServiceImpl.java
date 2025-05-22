package cn.iocoder.yudao.module.system.service.wxpool;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.date.DateUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.wxpool.vo.WxAccountPoolVO;
import cn.iocoder.yudao.module.system.domain.model.wxpool.WxAccountPool;
import cn.iocoder.yudao.module.system.domain.repository.wxpool.WxAccountDomainRepository;
import cn.iocoder.yudao.module.system.domain.request.WxAccountPoolRequest;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WxAccountPoolServiceImpl implements WxAccountPoolService{

    @Autowired
    private WxAccountDomainRepository wxAccountDomainRepository;

    @Override
    public PageResult<WxAccountPool> queryWxAccountPoolForPage(WxAccountPoolRequest wxAccountPoolRequest) {
        log.info("查询账号池分页数据,params:{}", JSON.toJSONString(wxAccountPoolRequest));
        try {
            return wxAccountDomainRepository.queryWxAccountPoolForPage(wxAccountPoolRequest);
        } catch (Exception e) {
            log.error("查询账号池分页数据发生错误,params:{}",JSON.toJSONString(wxAccountPoolRequest), e);
            return null;
        }
    }

    @Override
    public void saveWxAccountPool(WxAccountPoolVO wxAccountPoolVO) {
        log.info("添加/修改账号池数据, params:{}", JSON.toJSONString(wxAccountPoolVO));
        try {
            if(wxAccountPoolVO == null){
                return;
            }
            WxAccountPool wxAccountPool = new WxAccountPool();
            BeanUtils.copyProperties(wxAccountPoolVO, wxAccountPool);
            if(StringUtils.isNotBlank(wxAccountPoolVO.getExpireTime())){
                wxAccountPool.setExpireTime(DateUtils.of(wxAccountPoolVO.getExpireTime(), DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
            }
            wxAccountDomainRepository.saveWxAccountPool(wxAccountPool);
        } catch (Exception e) {
            log.error("添加/修改账号池数据,params:{}",JSON.toJSONString(wxAccountPoolVO), e);
        }
    }

    @Override
    public void deleteWxAccountPool(WxAccountPoolVO wxAccountPoolVO) {
        log.info("删除账号池数据, params:{}", JSON.toJSONString(wxAccountPoolVO));
        try {
            if(wxAccountPoolVO == null){
                return;
            }
            WxAccountPool wxAccountPool = new WxAccountPool();
            BeanUtils.copyProperties(wxAccountPoolVO, wxAccountPool);
            wxAccountDomainRepository.deleteWxAccountPoolById(wxAccountPool);
        } catch (Exception e) {
            log.error("删除账号池数据,params:{}",JSON.toJSONString(wxAccountPoolVO), e);
        }
    }

    @Override
    public Boolean bindDomainUrl(WxAccountPoolVO wxAccountPoolVO) {
        log.info("绑定账号池域名数据, params:{}", JSON.toJSONString(wxAccountPoolVO));
        try {
            if(wxAccountPoolVO == null){
                return false;
            }
            WxAccountPool wxAccountPool = new WxAccountPool();
            BeanUtils.copyProperties(wxAccountPoolVO, wxAccountPool);
            return wxAccountDomainRepository.bindDomainUrl(wxAccountPool);
        } catch (Exception e) {
            log.error("绑定账号池数据,params:{}",JSON.toJSONString(wxAccountPoolVO), e);
            return false;
        }
    }

    @Override
    public Boolean unBindDomainUrl(WxAccountPoolVO wxAccountPoolVO) {
        log.info("解绑账号池域名数据, params:{}", JSON.toJSONString(wxAccountPoolVO));
        try {
            if(wxAccountPoolVO == null){
                return false;
            }
            WxAccountPool wxAccountPool = new WxAccountPool();
            BeanUtils.copyProperties(wxAccountPoolVO, wxAccountPool);
            return wxAccountDomainRepository.unBindDomainUrl(wxAccountPool);
        } catch (Exception e) {
            log.error("绑定账号池数据,params:{}",JSON.toJSONString(wxAccountPoolVO), e);
            return false;
        }
    }

    @Override
    public Boolean bindEmployee(WxAccountPoolVO wxAccountPoolVO) {
        log.info("绑定账号池和员工数据, params:{}", JSON.toJSONString(wxAccountPoolVO));
        try {
            if(wxAccountPoolVO == null){
                return false;
            }
            WxAccountPool wxAccountPool = new WxAccountPool();
            BeanUtils.copyProperties(wxAccountPoolVO, wxAccountPool);
            wxAccountDomainRepository.bindEmployee(wxAccountPool);
        } catch (Exception e) {
            log.error("绑定账号池和员工数据,params:{}",JSON.toJSONString(wxAccountPoolVO), e);
        }
        return null;
    }

    @Override
    public Boolean unBindEmployee(WxAccountPoolVO wxAccountPoolVO) {
        log.info("解绑账号池和员工数据, params:{}", JSON.toJSONString(wxAccountPoolVO));
        try {
            if(wxAccountPoolVO == null){
                return false;
            }
            WxAccountPool wxAccountPool = new WxAccountPool();
            BeanUtils.copyProperties(wxAccountPoolVO, wxAccountPool);
            wxAccountDomainRepository.unBindEmployee(wxAccountPool);
        } catch (Exception e) {
            log.error("绑定账号池数据,params:{}",JSON.toJSONString(wxAccountPoolVO), e);
        }
        return null;
    }

    @Override
    public List<WxAccountPool> queryWxAccountByEmployeeId(WxAccountPoolRequest poolRequest) {
        log.info("查询员工绑定账号池数据, params:{}", JSON.toJSONString(poolRequest));
        try {
            // 查询当前员工绑定微信号和域名数据
            return wxAccountDomainRepository.queryWxAccountByEmployeeId(poolRequest);
        }catch (Exception e){
            log.error("查询员工绑定账号池数据,params:{}",JSON.toJSONString(poolRequest), e);
        }
         return null;
    }




}
