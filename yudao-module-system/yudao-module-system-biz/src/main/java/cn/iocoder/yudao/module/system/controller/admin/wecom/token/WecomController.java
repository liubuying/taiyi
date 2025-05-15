package cn.iocoder.yudao.module.system.controller.admin.wecom.token;


import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.wecom.token.request.JsApiInfoRequest;
import cn.iocoder.yudao.module.system.controller.admin.wecom.token.vo.JsApiInfoVO;
import cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants;
import cn.iocoder.yudao.module.system.util.cache.RedisUtils;
import cn.iocoder.yudao.module.system.util.encryption.Sha1Utils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理后台 - 用户")
@RestController
@RequestMapping("/system/wecom")
@Validated
@Slf4j
public class WecomController {

    private static final String CORP_ID = "ww7d23c84b96863bc3";
    private static final String SECRET = "fU3zd_ULx5HOw5ETKBQuXnfP35QeoPOH5ZriBHf3CFE";
    private static final String agentId = "1000002";

    /**
     * 获取微信企业号 access_token
     *
     * @return access_token
     */
    @RequestMapping(name = "getJsApiInfo",  method = RequestMethod.POST)
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<JsApiInfoVO> getJsApiInfo(@RequestBody JsApiInfoRequest request) {
        try {
            log.info("corpId:{},agentId:{}", CORP_ID, agentId);
            String jsapiTicketKey = RedisUtils.buildKey(RedisKeyConstants.JSAPI_TICKET_PREFIX, RedisKeyConstants.DEFAULT_PREFIX, CORP_ID, agentId);
            // 获取 access_token

            boolean existsJsapiTicket = RedisUtils.exists(jsapiTicketKey);
            if (existsJsapiTicket) {
                // 调用zhenli 接口获取 access_token和jsapi_ticket
            }
            String jsApiTicketValue = RedisUtils.getValue(jsapiTicketKey);
            Assert.notNull(jsApiTicketValue, "jsapi_ticket 不能为空");

            // 获取 jsapi_ticket
            // 拼接 url  jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value
            // 数据sha1加密
            // 返回数据加密结果
            String timestamp = String.valueOf(System.currentTimeMillis());
            String randomString = Sha1Utils.generateRandomString();
            String callbackUrl = request.getCallbackUrl();
            String stringToSign = "jsApiTicketValue=" + jsApiTicketValue +
                    "&noncestr=" + randomString +
                    "&timestamp=" + timestamp +
                    "&url=" + callbackUrl;
            String signature = Sha1Utils.sha1(stringToSign);
            JsApiInfoVO jsApiInfoVO = JsApiInfoVO.builder()
                    .callbackUrl(callbackUrl)
                    .nonceStr(randomString)
                    .timestamp(timestamp)
                    .signature(signature)
                    .build();

            return CommonResult.success(jsApiInfoVO);
        } catch (Exception e) {
            return null;
        }
    }



    /**
     * 获取微信企业号 jsapi_ticket
     *
     * */
}
