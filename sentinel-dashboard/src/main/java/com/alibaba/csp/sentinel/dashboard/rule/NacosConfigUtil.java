package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.util.JSONUtils;
import com.alibaba.csp.sentinel.slots.block.Rule;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ym.y
 * @description
 * @date 16:03 2022/6/22
 */
public final class NacosConfigUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(NacosConfigUtil.class);
    public static final String GROUP_ID = "DEFAULT_GROUP";
    public static final String FLOW_DATA_ID_POSTFIX = "-flow-rules";
    public static final String DEGRADE_DATA_ID_POSTFIX = "-degrade-rules";
    public static final String SYSTEM_DATA_ID_POSTFIX = "-system-rules";
    public static final String PARAM_FLOW_DATA_ID_POSTFIX = "-param-flow-rules";
    public static final String AUTHORITY_DATA_ID_POSTFIX = "-authority-rules";
    public static final String DASHBOARD_POSTFIX = "-sentinel-dashboard";
    public static final String CLUSTER_MAP_DATA_ID_POSTFIX = "-cluster-map";

    /**
     * cc for `cluster-client`
     */
    public static final String CLIENT_CONFIG_DATA_ID_POSTFIX = "-cc-config";
    /**
     * cs for `cluster-server`
     */
    public static final String SERVER_TRANSPORT_CONFIG_DATA_ID_POSTFIX = "-cs-transport-config";
    public static final String SERVER_FLOW_CONFIG_DATA_ID_POSTFIX = "-cs-flow-config";
    public static final String SERVER_NAMESPACE_SET_DATA_ID_POSTFIX = "-cs-namespace-set";

    private NacosConfigUtil() {
    }

    /**
     * @param configService nacos config service
     * @param app           运用名称
     * @param postfix       规则后缀，eg：NacosConfigUtil.FLOW_DATA_ID_POSTFIX
     * @param rules         规则对象
     * @param <T>
     * @description 将规则序列化成JSON文本，存储到Nacos server中
     */
    public static <T> void setRuleStringToNacos(ConfigService configService, String app, String postfix, List<T> rules) throws NacosException {
        AssertUtil.notEmpty(app, "app name connot be enmpty");
        if (rules == null) {
            return;
        }
        List<Rule> ruleForApp = rules.stream().map(rule -> {
            RuleEntity ruleEntity = (RuleEntity) rule;
            LOGGER.info("=====>:{}", ruleEntity.getClass());
            Rule rule1 = ruleEntity.toRule();
            LOGGER.info("=====>:{}", rule1.getClass());
            return rule1;
        }).collect(Collectors.toList());
        String dataId = getDataId(app, postfix);

        //两种存储方式这是入参不同
        //控制台微服务使用，即可以起到拦截的作用
        configService.publishConfig(dataId, NacosConfigUtil.GROUP_ID, JSONUtils.toJSONString(ruleForApp));
        //给控制台显示使用，由于数据太多,会出现转化异常,虽然可以提供控制台显示,但是无法对微服务进行保护
        configService.publishConfig(dataId + DASHBOARD_POSTFIX, NacosConfigUtil.GROUP_ID, JSONUtils.toJSONString(rules));

    }


    /**
     * 从nacos server中查询规则，并将其方序列化为Rule实体
     *
     * @param configService nacos config service
     * @param appName       应用名称
     * @param postfix       规则后缀 eg.NacosConfigUtil.FLOW_DATA_ID_POSTFIX
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> getRuleEntityFromNacos(ConfigService configService, String appName, String postfix, Class<T> clazz) throws NacosException {
        String rules = configService.getConfig(getDataId(appName, postfix) + DASHBOARD_POSTFIX,
                NacosConfigUtil.GROUP_ID, 5000);
        if (StringUtils.isBlank(rules)) {
            return new ArrayList<>();
        }
        return JSONUtils.parseObject(clazz, rules);
    }

    private static String getDataId(String appName, String postfix) {
        return appName + postfix;
    }
}
