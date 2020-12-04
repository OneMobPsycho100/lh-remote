package com.simple.shell.config;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({RemoteShellExecutor.class, RemoteShellProperties.class, GenericObjectPool.class})
public class RemoteShellAutoConfigure {

    /**
     *
     * @return {@link RemoteShellProperties}
     */
    @Bean
    @ConfigurationProperties(prefix = RemoteShellProperties.PREFIX)
    @ConditionalOnProperty(prefix = RemoteShellProperties.PREFIX,value = {"ip","osUsername"})
    @ConditionalOnMissingBean(RemoteShellProperties.class)
    public RemoteShellProperties remoteShellProperties(){
        return new RemoteShellProperties();
    }


    @Bean
    @ConfigurationProperties(prefix = RemoteExecPoolConfigure.PREFIX)
    @ConditionalOnMissingBean(RemoteExecPoolConfigure.class)
    public RemoteExecPoolConfigure remoteExecPoolConfigure() {
        RemoteExecPoolConfigure remoteExecPoolConfigure = new RemoteExecPoolConfigure();
        remoteExecPoolConfigure.setJmxEnabled(false);
        return remoteExecPoolConfigure;
    }

    @Bean
    @ConditionalOnBean(RemoteShellProperties.class)
    @ConditionalOnSingleCandidate(RemoteShellProperties.class)
    @ConditionalOnMissingBean(RemoteExecConnectionFactory.class)
    public RemoteExecConnectionFactory remoteExecConnectionFactory(
            @Autowired RemoteShellProperties remoteShellProperties) {
        return new RemoteExecConnectionFactory(remoteShellProperties);
    }

    @Bean
    @ConditionalOnBean({RemoteExecConnectionFactory.class, RemoteExecPoolConfigure.class})
    @ConditionalOnMissingBean(RemoteExecConnectionPool.class)
    public RemoteExecConnectionPool remoteExecConnectionPool(
            @Autowired RemoteExecConnectionFactory remoteExecConnectionFactory,
            @Autowired RemoteExecPoolConfigure remoteExecPoolConfigure) {
        return new RemoteExecConnectionPool(remoteExecConnectionFactory,remoteExecPoolConfigure);
    }

    @Bean
    @ConditionalOnBean({RemoteExecConnectionPool.class, RemoteShellProperties.class})
    @ConditionalOnMissingBean(RemoteShellExecutor.class)
    public RemoteShellExecutor remoteShellExecutor(
            @Autowired RemoteShellProperties remoteShellProperties,
            @Autowired RemoteExecConnectionPool remoteExecConnectionPool) {
        return new RemoteShellExecutor(remoteExecConnectionPool,remoteShellProperties.getCharset(),remoteShellProperties.getTimeout());
    }
}
