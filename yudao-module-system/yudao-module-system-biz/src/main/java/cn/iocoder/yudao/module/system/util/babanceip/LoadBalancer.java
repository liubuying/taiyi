package cn.iocoder.yudao.module.system.util.babanceip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LoadBalancer {
    private static final int MAX_CONNECTIONS = 6;
    private static final Random random = new Random();

    /**
     * 获取一个合理的 IP 地址，使用权重 + 随机策略
     *
     * @param totals              所有可用 IP 列表
     * @param ipLoginCountMap     当前每个 IP 的登录账号数量
     * @return                    选中的 IP
     */
    public static String selectBalancedIp(List<String> totals, Map<String, Long> ipLoginCountMap) {
        if (totals == null || totals.isEmpty()) {
            return null; // 没有可用 IP
        }

        // 构建带权重的 IP 列表
        List<String> weightedIpList = new ArrayList<>();

        for (String ip : totals) {
            long count = ipLoginCountMap.getOrDefault(ip, 0L);
            int weight = Math.max(0, MAX_CONNECTIONS - (int) count); // 权重 = 剩余容量

            for (int i = 0; i < weight; i++) {
                weightedIpList.add(ip);
            }
        }

        // 如果有权重数据，则随机选取一个
        if (!weightedIpList.isEmpty()) {
            return weightedIpList.get(random.nextInt(weightedIpList.size()));
        }

        // 否则直接随机选一个 IP（说明所有 IP 都满载）
        return totals.get(random.nextInt(totals.size()));
    }

}
