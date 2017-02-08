package ru.bars_open.medvtr.amqp.biomaterial;

import ru.bars_open.medvtr.amqp.biomaterial.entities.MapResearchTypeToLaboratory;
import ru.bars_open.medvtr.amqp.biomaterial.entities.Research;
import ru.bars_open.medvtr.mq.entities.action.Analysis;

/**
 * Author: Upatov Egor <br>
 * Date: 08.02.2017, 14:30 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class SendResearchToLabaratoryStruct {
    private final Analysis analysis;
    private final Research research;
    private final MapResearchTypeToLaboratory mapping;

    public SendResearchToLabaratoryStruct(
            final Analysis analysis, final Research research, final MapResearchTypeToLaboratory mapping
    ) {
        this.mapping = mapping;
        this.research = research;
        this.analysis = analysis;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public Research getResearch() {
        return research;
    }

    public MapResearchTypeToLaboratory getMapping() {
        return mapping;
    }
}
