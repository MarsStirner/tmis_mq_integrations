package ru.bars_open.medvtr.soap.ws.lis.across;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.group.bars.ObjectFactory;
import ru.group.bars.SetAnalysisResults;
import ru.group.bars.SetAnalysisResultsResponse;

import javax.xml.bind.JAXBElement;


@Endpoint
public class LisResultsService {
    private final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    @Autowired
    private SpringBean bean;

    private static final Logger log = LoggerFactory.getLogger(LisResultsService.class);

    public static final String NAMESPACE_URI = "http://www.bars.group.ru";

    @PayloadRoot(localPart = "setAnalysisResults", namespace = NAMESPACE_URI)
    @ResponsePayload
    public JAXBElement<SetAnalysisResultsResponse> onMessage(@RequestPayload SetAnalysisResults request) {
        log.info("Endpoint received person[firstName={},lastName={}]", request.getBiomaterialDefects(),
                request.getOrderMisId());

        String greeting = "Hello " + request.getOrderBarCode() + " " + request.getResultDoctorLisId() + "!";


        SetAnalysisResultsResponse response = OBJECT_FACTORY.createSetAnalysisResultsResponse();
        response.setReturn(111);
        bean.ololo();
        log.info("Endpoint sending greeting='{}'", response);
        return OBJECT_FACTORY.createSetAnalysisResultsResponse(response);
    }
}
