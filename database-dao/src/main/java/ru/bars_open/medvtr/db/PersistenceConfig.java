package ru.bars_open.medvtr.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Author: Upatov Egor <br>
 * Date: 08.11.2016, 14:51 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Configuration
public class PersistenceConfig {

    public static final String PERSISTENCE_UNIT_NAME_HOSPITAL = "hospital";

    private static final Logger log = LoggerFactory.getLogger("PERSISTENCE_CONFIG");

    @Bean("hospitalDataSource")
    public DataSource lookupHospitalDatasource() throws NamingException {
        return new JndiTemplate().lookup("hospitalDatasource", DataSource.class);
    }

    @Bean("hospitalVendorAdapter")
    public JpaVendorAdapter jpaVendorAdapter() {
        final HibernateJpaVendorAdapter result = new HibernateJpaVendorAdapter();
        result.setShowSql(true);
        result.setGenerateDdl(false);
        result.setDatabase(Database.MYSQL);
        return result;
    }

    @Bean("hospitalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("hospitalDataSource") final DataSource dataSource,
            @Qualifier("hospitalVendorAdapter") final JpaVendorAdapter jpaVendorAdapter) {
        final LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
        result.setDataSource(dataSource);
        result.setJpaVendorAdapter(jpaVendorAdapter);
        result.setPackagesToScan("ru.bars_open.medvtr.db.entities");
        result.setPersistenceUnitName(PERSISTENCE_UNIT_NAME_HOSPITAL);
        log.info("hospitalEntityManagerFactory initialized [@{}] {}", Integer.toHexString(result.hashCode()), result);
        return result;
    }



}
