package com.deepan.target.myretail.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/3/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersistenceConfigurationTest {

    private PersistenceConfiguration target;
    @Mock
    private Environment env;

    @Before
    public void setUp() throws Exception {
        target = new PersistenceConfiguration();
        target.setEnv(env);
        when(env.getProperty("cassandra.contactpoints")).thenReturn("127.0.0.1");
        when(env.getProperty("cassandra.port")).thenReturn("9042");
        when(env.getProperty("cassandra.keyspace")).thenReturn("myretailspace");
    }

    @Test
    public void testCluster() throws Exception {
        CassandraClusterFactoryBean clusterBean = target.cluster();
        clusterBean.afterPropertiesSet();
        assertNotNull(clusterBean.getObject());
        assertEquals("[/127.0.0.1:9042]", Arrays.toString(clusterBean.getObject().getMetadata().getAllHosts().toArray()));
    }

    @Test
    public void testMappingContext() throws Exception {
        CassandraMappingContext mappingContext = target.mappingContext();
        assertNotNull(mappingContext);
    }

    @Test
    public void testConverter() throws Exception {
        CassandraConverter converter = target.converter();
        assertNotNull(converter);
    }

    @Test
    public void testSession() throws Exception {
        CassandraSessionFactoryBean sessionBean = target.session();
        assertNotNull(sessionBean);
        sessionBean.afterPropertiesSet();
        assertEquals("myretailspace", sessionBean.getObject().getLoggedKeyspace());
        assertNotNull(sessionBean.getConverter());
        assertNotNull(sessionBean.getSchemaAction());
    }

    @Test
    public void testCassandraTemplate() throws Exception {

    }
}