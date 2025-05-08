package cn.iocoder.yudao.module.system.domain.wecom.depart;

import cn.iocoder.yudao.module.system.dal.dataobject.wecom.depart.TaiyiWecomDepartmentDO;
import cn.iocoder.yudao.module.system.dal.mysql.wecom.depart.TaiyiWecomDepartmentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TaiyiWecomDepartmentDomainImpl extends ServiceImpl<TaiyiWecomDepartmentMapper, TaiyiWecomDepartmentDO>
        implements TaiyiWecomDepartmentDomain {
}
