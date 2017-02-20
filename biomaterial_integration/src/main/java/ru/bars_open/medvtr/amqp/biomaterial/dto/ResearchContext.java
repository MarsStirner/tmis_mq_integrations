package ru.bars_open.medvtr.amqp.biomaterial.dto;

import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Test;
import ru.bars_open.medvtr.mq.entities.action.Analysis;

import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 17.02.2017, 18:14 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class ResearchContext {
    private final Analysis analysis;
    private final Research research;
    private final Set<Test> tests;

    public ResearchContext(final Analysis analysis, final Research research, final Set<Test> tests) {

        this.analysis = analysis;
        this.research = research;
        this.tests = tests;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public Research getResearch() {
        return research;
    }

    public Set<Test> getTests() {
        return tests;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    public Integer getId() {
        return analysis.getId();
    }

    public String getResearchType() {
        return research.getResearchType();
    }
}
