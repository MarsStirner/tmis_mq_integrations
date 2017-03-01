package ru.bars_open.medvtr.amqp.biomaterial.hepa;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import ru.bars_open.medvtr.amqp.biomaterial.hepa.util.AutowiringSpringBeanJobFactory;
import ru.bars_open.medvtr.mq.util.ConfigurationHolder;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;

import static org.quartz.SimpleScheduleBuilder.repeatHourlyForever;

/**
 * Author: Upatov Egor <br>
 * Date: 27.02.2017, 11:54 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Configuration
public class SchedulerConfig {

    private static final Logger log = LoggerFactory.getLogger("CONFIG");

    @Bean("resultPollingJobDetail")
    public JobDetail resultPollingJobDetail() {
        return JobBuilder.newJob().ofType(PollingJob.class).storeDurably().withIdentity(JobKey.jobKey("polling", "result")).build();
    }


    @Bean
    public Trigger trigger(@Qualifier("resultPollingJobDetail") final JobDetail job, final ConfigurationHolder cfg) {
        final TriggerBuilder<Trigger> builder = TriggerBuilder.newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("polling", "result"));
        if (cfg.hasPath("polling.cron")) {
            builder.withSchedule(CronScheduleBuilder.cronSchedule(cfg.getString("polling.cron")));
        } else {
            builder.withSchedule(repeatHourlyForever());
        }
        builder.usingJobData(new JobDataMap(Collections.singletonMap("jobDetail", job)));
        final Trigger result = builder.build();
        log.info("Trigger for result polling from HepaDB: {}", result);
        return result;
    }

    @Bean(name = "autowiringJobFactory")
    public JobFactory autowiringSpringBeanJobFactory(final ApplicationContext context) {
        final AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(context);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
            final Set<Trigger> triggers, final JobFactory jobFactory
    ) throws SchedulerException {
        final SchedulerFactoryBean result = new SchedulerFactoryBean();
        result.setJobFactory(jobFactory);
        final Properties props = new Properties();
        props.setProperty(StdSchedulerFactory.PROP_SCHED_SKIP_UPDATE_CHECK, "true");
        result.setQuartzProperties(props);
        result.setTriggers(triggers.toArray(new Trigger[triggers.size()]));
        log.info("Create ScheduleFactory[@{}] with Triggers: {}", Integer.toHexString(result.hashCode()), triggers);
        return result;
    }

}
