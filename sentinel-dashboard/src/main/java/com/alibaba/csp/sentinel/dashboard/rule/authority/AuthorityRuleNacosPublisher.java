package com.alibaba.csp.sentinel.dashboard.rule.authority;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.NacosConfigUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ym.y
 * @description 将授权规则持久化到nacos sever中
 * @date 16:46 2022/6/22
 */
@Component("authorityRuleNacosPublisher")
public class AuthorityRuleNacosPublisher implements DynamicRulePublisher<List<AuthorityRuleEntity>> {
    @Autowired
    private ConfigService configService;

    /**
     * @description   将授权规则持久化到nacos中
     * @author ym.y
     * @date  16:51 2022/6/22
     */
    @Override
    public void publish(String app, List<AuthorityRuleEntity> rules) throws Exception {
        NacosConfigUtil.setRuleStringToNacos(configService,
                app,
                NacosConfigUtil.AUTHORITY_DATA_ID_POSTFIX,
                rules);
    }
}
