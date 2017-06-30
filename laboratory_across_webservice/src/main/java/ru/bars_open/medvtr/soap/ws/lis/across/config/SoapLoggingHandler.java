package ru.bars_open.medvtr.soap.ws.lis.across.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.server.endpoint.AbstractLoggingInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.SoapEnvelopeLoggingInterceptor;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.StringWriter;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 17.05.2016, 1:48 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class SoapLoggingHandler extends SoapEnvelopeLoggingInterceptor {


    @Override
    protected void logMessageSource(String logMessage, Source source) throws TransformerException {
        if (source != null) {
            Transformer transformer = createTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));
            String message = logMessage + writer.toString();
            logMessage(message);
        }
    }
}