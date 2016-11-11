package ru.bars_open.medvtr.soap.ws.finance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.SoapEnvelopeLoggingInterceptor;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 07.11.2016, 16:33 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@EnableWs
@Configuration
@ComponentScan(basePackages = {"ru.bars_open.medvtr"})
public class ApplicationConfig extends WsConfigurerAdapter {

    @Bean(name = "payment-service")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema payments) {
        final DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PaymentServicePort");
        wsdl11Definition.setLocationUri("/ws/payment-service");
        wsdl11Definition.setTargetNamespace("ru.bars_open.medvtr.soap.ws.finance");
        wsdl11Definition.setSchema(payments);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema studentsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("payments.xsd"));
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        final SoapEnvelopeLoggingInterceptor loggingInterceptor = new SoapEnvelopeLoggingInterceptor();
        loggingInterceptor.setLoggerName("SOAP");
        super.addInterceptors(interceptors);
    }
}
