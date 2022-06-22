package com.alibaba.csp.sentinel.dashboard.rule.degrade;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.NacosConfigUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ym.y
 * @description
 * @date 16:53 2022/6/22
 */
@Component("degradeRuleNacosProvider")
public class DegradeRuleNacosProvider implements DynamicRuleProvider<List<DegradeRuleEntity>> {
    @Autowired
    private ConfigService configService;

    /**
     * @description   从nacos 获取降级规则
     * @author ym.y
     * @date  16:56 2022/6/22
     */
    @Override
    public List<DegradeRuleEntity> getRules(String appName) throws Exception {
        return NacosConfigUtil.getRuleEntityFromNacos(configService, appName,
                NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX,
                DegradeRuleEntity.class);
    }
}
