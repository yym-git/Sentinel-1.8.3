package com.alibaba.csp.sentinel.dashboard.rule.system;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.NacosConfigUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ym.y
 * @description
 * @date 17:24 2022/6/22
 */
@Component("systemRuleNacosProvider")
public class SystemRuleNacosProvider implements DynamicRuleProvider<List<SystemRuleEntity>> {
    @Autowired
    private ConfigService configService;

    @Override
    public List<SystemRuleEntity> getRules(String appName) throws Exception {
        return NacosConfigUtil.getRuleEntityFromNacos(configService,
                appName,
                NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX,
                SystemRuleEntity.class);
    }
}
