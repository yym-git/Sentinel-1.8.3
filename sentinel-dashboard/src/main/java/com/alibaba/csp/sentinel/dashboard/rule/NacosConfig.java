package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author ym.y
 * @description nacos配置信息
 * @date 15:35 2022/6/22
 */
@Configuration
public class NacosConfig {
    @Value("${nacos.address}")
    private String address;
    @Value("${nacos.namespace}")
    private String namespce;

    @Bean
    public ConfigService nacosConfigService() throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, address);
        //namespace为空，即为public
        properties.put(PropertyKeyConst.NAMESPACE, namespce);
        return ConfigFactory.createConfigService(properties);
    }
}
