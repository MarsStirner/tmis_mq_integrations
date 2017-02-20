package ru.bars_open.medvtr.amqp.biomaterial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.BiomaterialDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.MessageDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.ResearchDao;
import ru.bars_open.medvtr.amqp.biomaterial.dao.interfaces.TestDao;
import ru.bars_open.medvtr.amqp.biomaterial.dto.MessageContext;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Test;
import ru.bars_open.medvtr.amqp.biomaterial.util.MDCHelper;
import ru.bars_open.medvtr.mq.entities.action.Analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 16.02.2017, 19:33 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Repository("messagePersister")
public class MessagePersister {
    private static final Logger log = LoggerFactory.getLogger(MessagePersister.class);

    @Autowired
    private BiomaterialDao biomaterialDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private ResearchDao researchDao;

    @Autowired
    private TestDao testDao;

    /**
     * [C] Сохранить данные сообщения в локальную БД (с проверкой уже существующих данных) >> {@link MessagePersister#saveMessageToDb}
     * -- [C.1] Сохранить или найти биоматериал >> {@link BiomaterialDao#findOrCreate}
     * -- [C.2] Сохранить сообщение >> {@link MessageDao#createInMessage}
     * -- [C.3] Сохранить список исследований (или найти) >> {@link MessagePersister#addResearchInfo} >> {@link ResearchDao#findOrCreate}
     * -- [C.4] Сохранить список тестов (или найти) >> {@link MessagePersister#addResearchInfo} >> {@link TestDao#findOrCreate}
     *
     * @param ctx Контекстная структура со всеми данными, относящимися к обработке сообщения
     */
    public void saveMessageToDb(final MessageContext ctx) {
        //[C.1] Сохранить или найти биоматериал
        ctx.setBiomaterial(biomaterialDao.findOrCreate(ctx.getMqBiomaterial()));
        log.debug("Biomaterial={}", ctx.getBiomaterial());
        //[C.2] Сохранить сообщение
        ctx.setMessage(messageDao.createInMessage(ctx.getBody(), ctx.getUUID(), ctx.getRoutingKey(), "", ctx.getBiomaterial()));
        log.debug("Message={}", ctx.getMessage());
        //[C.3] Сохранить список исследований (или найти)
        //[C.4] Сохранить список тестов (или найти)
        addResearchInfo(ctx.getMqResearch(), ctx);
    }

    /**
     * -- [C.3] Сохранить список исследований (или найти) >> {@link MessagePersister#addResearchInfo} >> {@link ResearchDao#findOrCreate}
     * -- [C.4] Сохранить список тестов (или найти) >> {@link MessagePersister#addResearchInfo} >> {@link TestDao#findOrCreate}
     *
     * @param analysisList
     * @param result
     */
    private void addResearchInfo(final List<Analysis> analysisList, final MessageContext result) {
        log.debug("Start iterating over {} research(s)", analysisList.size());
        for (Analysis analysis : analysisList) {
            MDCHelper.push(analysis.getId());
            log.debug("Start [{}]-'{}'", analysis.getType().getCode(), analysis.getType().getName());
            final Research research = researchDao.findOrCreate(analysis, result.getBiomaterial(), result.getMessage());
            log.debug("Research={}", research);
            final Set<Test> tests = new HashSet<>(analysis.getTests().size());
            for (ru.bars_open.medvtr.mq.entities.base.util.Test test : analysis.getTests()) {
                final Test dbTest = testDao.findOrCreate(test, research, result.getMessage());
                log.debug("Test={}", dbTest);
                tests.add(dbTest);
            }
            result.addToResearchs(analysis, research, tests);
            MDCHelper.pop();
        }
    }
}
