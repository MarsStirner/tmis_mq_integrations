package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.IdentifiedEntityWithExternal;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 16.02.2017, 20:57 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name = "Test")
public class Test extends IdentifiedEntityWithExternal {
    /**
     * Исследование, в котором запрошен индикатор {Research}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "research_id")
    private Research research;

    /**
     * Тип запрошенного теста {rbTestType}
     */
    @Column(name = "testType", nullable = false)
    private String testType;

    public Research getResearch() {
        return research;
    }

    public void setResearch(final Research research) {
        this.research = research;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(final String testType) {
        this.testType = testType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Test[").append(id).append("");
        sb.append("]{ research=").append(research.toShortString());
        sb.append(", testType='").append(testType).append('\'');
        sb.append(", externalId='").append(externalId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
