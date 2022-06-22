package com.alibaba.csp.sentinel.dashboard.rule.authority;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.NacosConfigUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ym.y
 * @description 从nacos sever获取授权规则
 * @date 16:41 2022/6/22
 */
@Component("authorityRuleNacosProvider")
public class AuthorityRuleNacosProvider implements DynamicRuleProvider<List<AuthorityRuleEntity>> {
    @Autowired
    private ConfigService configService;
/**
 * @description   从nacos sever读取授权规则
 * @author ym.y
 * @date  16:51 2022/6/22
 */
    @Override
    public List<AuthorityRuleEntity> getRules(String appName) throws Exception {
        return NacosConfigUtil.getRuleEntityFromNacos(configService, appName,
                NacosConfigUtil.AUTHORITY_DATA_ID_POSTFIX, AuthorityRuleEntity.class);
    }
}
