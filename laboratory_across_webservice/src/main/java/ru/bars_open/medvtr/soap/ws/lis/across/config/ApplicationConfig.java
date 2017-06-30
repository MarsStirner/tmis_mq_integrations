package ru.bars_open.medvtr.soap.ws.lis.across.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
@ComponentScan("ru.bars_open.medvtr.soap.ws.lis.across")
public class ApplicationConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final Logger log = LoggerFactory.getLogger("CONFIG");

    private static ApplicationContext APPLICATION_CONTEXT;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //create the root Spring application context
        final AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));

        ApplicationConfig.APPLICATION_CONTEXT = rootContext;

        MessageDispatcherServlet dispatcherServlet = new MessageDispatcherServlet(rootContext);
        dispatcherServlet.setTransformWsdlLocations(true);
        dispatcherServlet.setTransformSchemaLocations(true);

        //register and map the dispatcher servlet
        //note Dispatcher servlet with constructor arguments
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");

        FilterRegistration.Dynamic logContextFilter = servletContext.addFilter("log-context-filter", new LoggingContextFilter());
        logContextFilter.addMappingForUrlPatterns(null, true, "/*");
    }

    @Override
    protected String[] getServletMappings() {
        return new String[0];
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }


}
