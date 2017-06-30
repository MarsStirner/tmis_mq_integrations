package ru.bars_open.medvtr.soap.ws.lis.across.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import ru.bars_open.medvtr.soap.ws.lis.across.LisResultsService;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@EnableWs
@Configuration
public class WsConfig extends WsConfigurerAdapter {
    private static final Logger log = LoggerFactory.getLogger("CONFIG");

    @Bean(name = "webserviceWsdl")
    public Resource wsdl() {
        return new ClassPathResource("/webservice.wsdl");
    }


    @Bean(name = "LisResultWebService")
    public Wsdl11Definition defaultWsdl11Definition(
            @Qualifier("webserviceWsdl") final Resource wsdl
    ) {
        return new SimpleWsdl11Definition(wsdl);
    }

    @Bean(name = "xsdSchemas")
    public Set<Resource> xsdSchemasFromWsdl(
            @Qualifier("webserviceWsdl") final Resource wsdl
    ) {
        final DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = domFactory.newDocumentBuilder();
            final Document doc = builder.parse(wsdl.getInputStream());
            final XPath xPath = XPathFactory.newInstance().newXPath();
            final NodeList nodeList = (NodeList) xPath.evaluate(
                    "/definitions/types/schema",
                    doc,
                    XPathConstants.NODESET
            );
            if (nodeList.getLength() == 0) {
                log.warn("Can't extract XSD:SCHEMA from WSDL");
                return null;
            }

            final Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            final Set<Resource> result = new HashSet<>(nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                final StringWriter writer = new StringWriter();
                transformer.transform(new DOMSource(nodeList.item(i)), new StreamResult(writer));
                final String xsdValue = writer.toString();
                if (log.isDebugEnabled()) {
                    log.debug("XSD extracted from WSDL:\n{}", xsdValue);
                }
                result.add(new ByteArrayResource(xsdValue.getBytes(StandardCharsets.UTF_8)));
            }
            return result;
        } catch (Exception e) {
            log.error("Can't extract XSD:SCHEMA from WSDL", e);
            return null;
        }
    }

    @Bean(name = "soapLoggingInterceptor")
    public EndpointInterceptor soapLoggingInterceptor() {
        final SoapLoggingHandler result = new SoapLoggingHandler();
        result.setLogRequest(true);
        result.setLogResponse(true);
        result.setLoggerName("SOAP");
        return result;
    }

    @Bean(name = "soapValidatingInterceptor")
    public EndpointInterceptor soapValidatingInterceptor(
            @Qualifier("xsdSchemas") final Set<Resource> xsdSchemas
    ) {
        if (xsdSchemas == null || xsdSchemas.isEmpty()) {
            return null;
        }
        final PayloadValidatingInterceptor result = new PayloadValidatingInterceptor();
        result.setValidateRequest(true);
        result.setValidateResponse(true);
        result.setDetailElementName(new QName(LisResultsService.NAMESPACE_URI, "error", "lis"));
        result.setSchemas(xsdSchemas.toArray(new Resource[0]));
        return result;
    }

    @Override
    public void addInterceptors(final List<EndpointInterceptor> interceptors) {
        super.addInterceptors(interceptors);
        interceptors.add(soapLoggingInterceptor());
        interceptors.add(soapValidatingInterceptor(xsdSchemasFromWsdl(wsdl())));
    }
}
