package cn.iocoder.yudao.module.system.api.workcaht.biz.TokenRefresh;

import cn.iocoder.yudao.module.system.api.workwe.config.WorkWeChatConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 企业微信Token定时刷新任务
 */
@Component
public class TokenScheduleTask {
    private static final Logger log = LoggerFactory.getLogger(TokenScheduleTask.class);

    @Autowired
    private TokenRefresher tokenRefresher;
    
    @Autowired
    private WorkWeChatConfig workWeChatConfig;
    
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 定时刷新所有企业应用的token
     * 每隔7000秒执行一次（略小于企业微信token的2小时有效期）
     * 可以通过配置文件调整刷新间隔
     */
    @Scheduled(fixedRateString = "${work-wechat.token-cache-expiry:7000}000")
    public void refreshTokens() {
        // 检查是否启用了自动刷新
        if (!workWeChatConfig.getEnableAutoRefresh()) {
            log.debug("自动刷新token功能已禁用，跳过执行");
            return;
        }
        
        log.info("开始执行token定时刷新任务");
        try {
            tokenRefresher.refreshAllTokens(redisTemplate, workWeChatConfig);
            log.info("token定时刷新任务执行完成");
        } catch (Exception e) {
            log.error("token定时刷新任务执行失败", e);
        }
    }
} 