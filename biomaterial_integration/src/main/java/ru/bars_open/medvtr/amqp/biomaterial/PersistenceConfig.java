package ru.bars_open.medvtr.amqp.biomaterial;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * Author: Upatov Egor <br>
 * Date: 08.11.2016, 14:51 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    public static final String SESSION_FACTORY = "databaseSessionFactory";

    private static final Logger log = LoggerFactory.getLogger("CONFIG");

    @Bean("hospitalDataSource")
    public DataSource lookupHospitalDatasource(final ConfigurationHolder cfg) {
        final Config dsCfg = cfg.getConfig("datasource");
        if (dsCfg.hasPath("jndiName")) {
            final String jndiName = dsCfg.getString("jndiName");
            log.info("Datasource: Try initialize by jndiName[{}]", jndiName);
            try {
                final DataSource result = new JndiTemplate().lookup(jndiName, DataSource.class);
                log.info("Datasource: Initialized JNDI[{}] [@{}]", jndiName, Integer.toHexString(result.hashCode()));
                return result;
            } catch (NamingException e) {
                throw new IllegalStateException("No Datasource with JNDI "+jndiName, e);
            }
        } else {
            log.info("Try initialize by settings : {}", dsCfg);
            final StringBuilder jdbcUrlBuilder = new StringBuilder("jdbc:");
            jdbcUrlBuilder.append(dsCfg.getString("rdbms"));
            jdbcUrlBuilder.append("://").append(dsCfg.getString("host")).append(':').append(dsCfg.getInt("port"));
            jdbcUrlBuilder.append('/').append(dsCfg.getString("schema"));
            log.info("JDBC URL = '{}'", jdbcUrlBuilder);
            final HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrlBuilder.toString());
            config.setUsername(dsCfg.getString("username"));
            config.setPassword(dsCfg.getString("password"));
            if(dsCfg.hasPath("connectionProperties")) {
                for (Map.Entry<String, ConfigValue> entry : dsCfg.getConfig("connectionProperties").entrySet()) {
                    config.addDataSourceProperty(entry.getKey(), entry.getValue().unwrapped());
                }
            }
            return new HikariDataSource(config);
        }
    }

    @Bean("hospitalHibernateProperties")
    public Properties hospitalHibernateProperties() {
        final Properties result = new Properties();
        result.put("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        result.put("hibernate.show_sql", "true");
        result.put("hibernate.format_sql", "true");
        result.put("hibernate.max_fetch_depth", "3");
        result.put("hibernate.hbm2ddl.auto", ""); // means "NONE"
        return result;
    }


    @Bean(SESSION_FACTORY)
    public LocalSessionFactoryBean hospitalSessionFactory(
            @Qualifier("hospitalDataSource") final DataSource dataSource, @Qualifier("hospitalHibernateProperties") final Properties props
    ) {
        final LocalSessionFactoryBean result = new LocalSessionFactoryBean();
        result.setDataSource(dataSource);
        result.setPackagesToScan("ru.bars_open.medvtr");
        result.setHibernateProperties(props);
        log.info("Initialized 'hospitalSessionFactory'[@{}]", Integer.toHexString(result.hashCode()));
        return result;
    }

    @Bean("hospitalTransactionManager")
    public HibernateTransactionManager hospitalTransactionManager(
            @Qualifier(SESSION_FACTORY) final SessionFactory sessionFactory
    ) {
        final HibernateTransactionManager result = new HibernateTransactionManager(sessionFactory);
        log.info("Initialized 'hospitalTransactionManager'[@{}]", Integer.toHexString(result.hashCode()));
        return result;
    }
}
