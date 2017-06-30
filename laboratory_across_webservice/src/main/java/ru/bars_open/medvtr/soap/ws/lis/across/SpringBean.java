package ru.bars_open.medvtr.soap.ws.lis.across;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("springBean")
public class SpringBean {

    private static final Logger log = LoggerFactory.getLogger(SpringBean.class);

    public void ololo() {
        log.info("sadasdasdasda");
    }

}
