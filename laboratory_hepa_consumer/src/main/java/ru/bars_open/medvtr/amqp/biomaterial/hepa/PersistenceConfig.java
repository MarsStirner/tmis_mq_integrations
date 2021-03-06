package ru.bars_open.medvtr.amqp.biomaterial.hepa;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigValue;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.JdbcUrlBuilder;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

import static java.lang.Integer.toHexString;

/**
 * Author: Upatov Egor <br>
 * Date: 08.11.2016, 14:51 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Configuration("hepaPersistenceConfig")
@EnableTransactionManagement
public class PersistenceConfig {

    private static final Logger log = LoggerFactory.getLogger("CONFIG");
    public static final String PERSISTENCE_UNIT_NAME_HEPA = "hepa";

    @Bean("hepaDatasource")
    public DataSource hepaDatasource(final ConfigurationHolder cfg) {
        final Config dsCfg = cfg.getConfig("datasource");
        if (dsCfg.hasPath("jndiName")) {
            final String jndiName = dsCfg.getString("jndiName");
            log.info("HepaDatasource: Try initialize by jndiName[{}]", jndiName);
            try {
                final DataSource result = new JndiTemplate().lookup(jndiName, DataSource.class);
                log.info("HepaDatasource: Initialized JNDI[{}] [@{}]", jndiName, toHexString(result.hashCode()));
                return result;
            } catch (NamingException e) {
                throw new IllegalStateException("No Datasource with JNDI " + jndiName, e);
            }
        }
        log.info("HepaDatasource: Try initialize by settings:\n{}", dsCfg.root().render(ConfigRenderOptions.concise().setFormatted(true)));
        final String jdbcUrl = JdbcUrlBuilder.build(
                dsCfg.getString("rdbms"),
                dsCfg.getString("host"),
                dsCfg.getInt("port"),
                dsCfg.getString("schema")
        );
        log.info("HepaDatasource: JDBC URL = '{}'", jdbcUrl);
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(dsCfg.getString("username"));
        config.setPassword(dsCfg.getString("password"));
        if (dsCfg.hasPath("connectionProperties")) {
            for (Map.Entry<String, ConfigValue> entry : dsCfg.getConfig("connectionProperties").entrySet()) {
                config.addDataSourceProperty(entry.getKey(), entry.getValue().unwrapped());
            }
        }
        return new HikariDataSource(config);
    }

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME_HEPA)
    @Bean("hepaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean hepaEntityManagerFactory(@Qualifier("hepaDatasource") final DataSource dataSource) {
        final LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
        result.setDataSource(dataSource);

        final Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        hibernateProperties.put("hibernate.show_sql", "false");
        hibernateProperties.put("hibernate.format_sql", "true");
        hibernateProperties.put("hibernate.max_fetch_depth", "3");
        hibernateProperties.put("hibernate.hbm2ddl.auto", ""); // means "NONE"
        result.setJpaProperties(hibernateProperties);

        result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        result.setPackagesToScan("ru.bars_open.medvtr.amqp.biomaterial.hepa.entities");
        result.setPersistenceUnitName(PERSISTENCE_UNIT_NAME_HEPA);
        log.info("hepaEntityManagerFactory initialized [@{}] {}", toHexString(result.hashCode()), result);
        return result;
    }

    @Bean(name = "hepaTransactionManager")
    public PlatformTransactionManager hepaTransactionManager(@Qualifier("hepaEntityManagerFactory") final EntityManagerFactory emf) {
        final JpaTransactionManager result = new JpaTransactionManager();
        result.setEntityManagerFactory(emf);
        result.setPersistenceUnitName(PERSISTENCE_UNIT_NAME_HEPA);
        result.setDefaultTimeout(36000);
        log.info("HepaTransactionManager: <init> with EntityManagerFactory[@{}]", toHexString(result.hashCode()), toHexString(emf.hashCode()));
        return result;
    }
}
