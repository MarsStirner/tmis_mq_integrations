package ru.bars_open.medvtr.db;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Author: Upatov Egor <br>
 * Date: 08.11.2016, 14:51 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Configuration
public class PersistenceConfig {

    public static final String PU_NAME_HOSPITAL = "hospital";
    public static final String HOSPITAL_SESSION_FACTORY = "hospitalSessionFactory";
    public static final String JNDI_NAME_HOSPITAL = "hospitalDatasource";

    private static final Logger log = LoggerFactory.getLogger("PERSISTENCE_CONFIG");

    @Bean("hospitalDataSource")
    public DataSource lookupHospitalDatasource() {
        try {
            final DataSource result = new JndiTemplate().lookup(JNDI_NAME_HOSPITAL, DataSource.class);
            log.info("Initialized DataSource[{}] [@{}]", JNDI_NAME_HOSPITAL, Integer.toHexString(result.hashCode()));
            return result;
        } catch (NamingException e) {
            final String errorMsg = ErrorMessage.format("NOT_FOUND_JNDI_DATASOURCE", JNDI_NAME_HOSPITAL);
            log.error(errorMsg);
            throw new IllegalStateException(errorMsg, e);
        }
    }

    @Bean("hospitalHibernateProperties")
    public Properties hospitalHibernateProperties() {
        final Properties result = new Properties();
        result.put("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        result.put("hibernate.show_sql", "true");
        result.put("hibernate.format_sql", "true");
        result.put("hibernate.max_fetch_depth", "3");
        result.put("hibernate.hbm2ddl.auto", "NONE");
        return result;
    }


    @Bean(HOSPITAL_SESSION_FACTORY)
    public LocalSessionFactoryBean hospitalSessionFactory(
            @Qualifier("hospitalDataSource") final DataSource dataSource,
            @Qualifier("hospitalHibernateProperties") final Properties props
    ) {
        final LocalSessionFactoryBean result = new LocalSessionFactoryBean();
        result.setDataSource(dataSource);
        result.setPackagesToScan("ru.bars_open.medvtr.db.entities");
        result.setHibernateProperties(props);
        log.info("Initialized \'hospitalSessionFactory\'[@{}]", Integer.toHexString(result.hashCode()));
        return result;
    }

    @Bean("hospitalTransactionManager")
    public HibernateTransactionManager hospitalTransactionManager(
            @Qualifier(HOSPITAL_SESSION_FACTORY) final SessionFactory sessionFactory) {
        final HibernateTransactionManager result = new HibernateTransactionManager(sessionFactory);
        log.info("Initialized \'hospitalTransactionManager\'[@{}]", Integer.toHexString(result.hashCode()));
        return result;
    }
}
