package org.apereo.cas.mgmt.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.CasManagementConfigurationProperties;
import org.apereo.cas.mgmt.MgmtManagerFactory;
import org.apereo.cas.mgmt.SamlController;
import org.apereo.cas.mgmt.authentication.CasUserProfileFactory;
import org.apereo.cas.services.ServicesManager;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *Configuration for register end point features.
 *
 * @author Travis Schmidt
 * @since 6.1
 */
@Configuration("casManagementSamlConfiguration")
@EnableConfigurationProperties({CasConfigurationProperties.class, CasManagementConfigurationProperties.class})
@Slf4j
public class CasManagementSamlConfiguration {

    @Autowired
    @Qualifier("managerFactory")
    private ObjectProvider<MgmtManagerFactory> managerFactory;

    @Autowired
    private CasManagementConfigurationProperties managementProperties;

    @Autowired
    @Qualifier("casUserProfileFactory")
    private ObjectProvider<CasUserProfileFactory> casUserProfileFactory;

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @Bean
    public SamlController samlController() {
        return new SamlController(casUserProfileFactory.getIfAvailable(),
                managerFactory.getIfAvailable(),
                managementProperties,
                servicesManager);
    }
}