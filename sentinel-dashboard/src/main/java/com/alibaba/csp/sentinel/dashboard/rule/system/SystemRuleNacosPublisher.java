package com.alibaba.csp.sentinel.dashboard.rule.system;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.NacosConfigUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ym.y
 * @description
 * @date 17:27 2022/6/22
 */
@Component("systemRuleNacosPublisher")
public class SystemRuleNacosPublisher implements DynamicRulePublisher<List<SystemRuleEntity>> {
    @Autowired
    private ConfigService configService;

    @Override
    public void publish(String app, List<SystemRuleEntity> rules) throws Exception {
        NacosConfigUtil.setRuleStringToNacos(configService,
                app,
                NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX,
                rules);
    }
}
