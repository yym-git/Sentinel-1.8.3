package com.alibaba.csp.sentinel.dashboard.rule.degrade;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.NacosConfigUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ym.y
 * @description
 * @date 16:57 2022/6/22
 */
@Component("degradeRuleNacosPublisher")
public class DegradeRuleNacosPublisher implements DynamicRulePublisher<List<DegradeRuleEntity>> {
    @Autowired
    private ConfigService configService;

    /**
     * @description   将规则持久化到nacos中
     * @author ym.y
     * @date  16:59 2022/6/22
     */
    @Override
    public void publish(String app, List<DegradeRuleEntity> rules) throws Exception {
        NacosConfigUtil.setRuleStringToNacos(configService,
                app,
                NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX,
                rules);
    }
}
