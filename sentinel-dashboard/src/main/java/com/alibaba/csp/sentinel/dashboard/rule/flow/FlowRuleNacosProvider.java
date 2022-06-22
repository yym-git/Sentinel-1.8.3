package com.alibaba.csp.sentinel.dashboard.rule.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.NacosConfigUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.util.List;

/**
 * @author ym.y
 * @description
 * @date 17:00 2022/6/22
 */
@Component("flowRuleNacosProvider")
public class FlowRuleNacosProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {
    @Autowired
    private ConfigService configService;

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        return NacosConfigUtil.getRuleEntityFromNacos(configService,
                appName,
                NacosConfigUtil.FLOW_DATA_ID_POSTFIX,
                FlowRuleEntity.class);
    }
}
