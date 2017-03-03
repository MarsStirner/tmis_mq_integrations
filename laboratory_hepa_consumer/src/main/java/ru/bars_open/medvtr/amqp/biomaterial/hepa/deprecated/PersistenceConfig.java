package ru.bars_open.medvtr.amqp.biomaterial.hepa.deprecated;

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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;
import ru.bars_open.medvtr.mq.util.JdbcUrlBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

import static java.lang.Integer.toHexString;

/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 18:43 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Configuration("hospitalPersistenceConfig")
@EnableTransactionManagement
public class PersistenceConfig {
    private static final Logger log = LoggerFactory.getLogger("CONFIG");

    public static final String PERSISTENCE_UNIT_NAME_HOSPITAL = "hospital";

    @Bean("hospitalDatasource")
    public static DataSource hospitalDatasource(final ConfigurationHolder cfg) {
        final Config dsCfg = cfg.getConfig("polling.datasourceHospital");
        log.info("HospitalDatasource: Try initialize by settings:\n{}", dsCfg.root().render(ConfigRenderOptions.concise()));
        final String jdbcUrl = JdbcUrlBuilder.build(dsCfg.getString("rdbms"),
                                                    dsCfg.getString("host"),
                                                    dsCfg.getInt("port"),
                                                    dsCfg.getString("schema")
        );
        log.info("HospitalDatasource: JDBC URL = '{}'", jdbcUrl);
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

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME_HOSPITAL)
    @Bean("hospitalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean hospitalEntityManagerFactory(@Qualifier("hospitalDatasource") final DataSource dataSource) {
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
        result.setPackagesToScan("ru.bars_open.medvtr.db.entities");
        result.setPersistenceUnitName(PERSISTENCE_UNIT_NAME_HOSPITAL);
        log.info("hospitalEntityManagerFactory initialized [@{}] {}", toHexString(result.hashCode()), result);
        return result;
    }

    @Bean("hospitalTransactionManager")
    public PlatformTransactionManager hospitalTransactionManager(@Qualifier("hospitalEntityManagerFactory") final EntityManagerFactory emf) {
        final JpaTransactionManager result = new JpaTransactionManager();
        result.setEntityManagerFactory(emf);
        result.setPersistenceUnitName(PERSISTENCE_UNIT_NAME_HOSPITAL);
        result.setDefaultTimeout(36000);
        log.info("HospitalTransactionManager: <init> with EntityManagerFactory[@{}]", toHexString(result.hashCode()), toHexString(emf.hashCode()));
        return result;
    }


}
