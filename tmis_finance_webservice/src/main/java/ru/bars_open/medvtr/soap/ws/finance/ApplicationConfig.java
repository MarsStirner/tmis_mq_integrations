package ru.bars_open.medvtr.soap.ws.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.lang.management.ManagementFactory;

/**
 * Author: Upatov Egor <br>
 * Date: 07.11.2016, 16:33 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */

@Configuration
@ComponentScan(basePackages = {"ru.bars_open.medvtr"})
public class ApplicationConfig implements WebApplicationInitializer {

    private static final Logger log = LoggerFactory.getLogger("ROOT");

    private static AnnotationConfigWebApplicationContext context;

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        final String pid = ManagementFactory.getRuntimeMXBean().getName();
        long startTime = System.currentTimeMillis();
        log.info("PID {}: Start application with", pid.split("@")[0]);
        context = new AnnotationConfigWebApplicationContext();
        context.register(ApplicationConfig.class);
        context.setServletContext(servletContext);
        context.refresh();
        log.info("End initialization of application in {} seconds. Good luck!", (System.currentTimeMillis() - startTime) / 1000f);
    }









}
