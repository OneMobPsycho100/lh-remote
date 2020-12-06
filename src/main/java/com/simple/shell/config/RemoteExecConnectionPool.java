package com.simple.shell.config;

import ch.ethz.ssh2.Connection;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class RemoteExecConnectionPool extends GenericObjectPool<Connection> {

    public RemoteExecConnectionPool(PooledObjectFactory<Connection> factory) {
        super(factory);
    }

    public RemoteExecConnectionPool(PooledObjectFactory<Connection> factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }

    public RemoteExecConnectionPool(PooledObjectFactory<Connection> factory, GenericObjectPoolConfig config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}
