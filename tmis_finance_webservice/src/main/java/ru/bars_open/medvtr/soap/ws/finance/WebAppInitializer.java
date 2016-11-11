package ru.bars_open.medvtr.soap.ws.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.lang.management.ManagementFactory;

public class WebAppInitializer implements WebApplicationInitializer {

    private static final Logger log = LoggerFactory.getLogger("ROOT");
    private static AnnotationConfigWebApplicationContext context;

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        final String pid = ManagementFactory.getRuntimeMXBean().getName();
        log.info("PID {}: Start application", pid.split("@")[0]);
        context = new AnnotationConfigWebApplicationContext();
        context.setId("TMIS Finance WS");
        context.register(ApplicationConfig.class);
        context.setServletContext(servletContext);
        final MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
           final ServletRegistration.Dynamic dynamic = servletContext.addServlet("spring-ws",servlet);
        dynamic.addMapping("/*");
        dynamic.setLoadOnStartup(1);
        log.info("[{}] End initialization of application. Good luck!", context.getId());
    }

}
