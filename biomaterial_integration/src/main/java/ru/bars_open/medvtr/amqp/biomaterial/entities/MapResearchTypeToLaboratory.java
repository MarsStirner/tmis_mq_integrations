package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.IdentifiedEntity;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 07.02.2017, 15:49 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Сопоставление типов исследований с лабораториями
 * ( по оригинальному коду типа исследования, типу биоматериала, типу пробирки)
 * с возможностью замены кода и наименования типа при отправке
 */
@Entity
@Table(name = "mapResearchTypeToLaboratory")
public class MapResearchTypeToLaboratory extends IdentifiedEntity{

    /**
     * К какой лаборатории приписан
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratory_id", nullable = false)
    private RbLaboratory laboratory;

    /**
     * Тип исследования
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "researchType_id", nullable = false)
    private RbResearchType researchType;

    /**
     * Тип биоматериала (NULL - любая)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biomaterialType_id", nullable = true)
    private RbBiomaterialType biomaterialType;

    /**
     * Тип пробирки (NULL - любая)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testTubeType_id", nullable = true)
    private RbTestTubeType testTubeType;

    /**
     * Заменять при отправке код типа исследования (NULL - без изменений)
     */
    @Column(name ="overrideResearchTypeCode")
    private String overrideResearchTypeCode;

    /**
     * Заменять при отправке наименование типа исследования (NULL - без изменений)
     */
    @Column(name ="overrideResearchTypeName")
    private String overrideResearchTypeName;

    public MapResearchTypeToLaboratory() {
    }

    public RbLaboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(final RbLaboratory laboratory) {
        this.laboratory = laboratory;
    }

    public RbResearchType getResearchType() {
        return researchType;
    }

    public void setResearchType(final RbResearchType researchType) {
        this.researchType = researchType;
    }

    public RbBiomaterialType getBiomaterialType() {
        return biomaterialType;
    }

    public void setBiomaterialType(final RbBiomaterialType biomaterialType) {
        this.biomaterialType = biomaterialType;
    }

    public RbTestTubeType getTestTubeType() {
        return testTubeType;
    }

    public void setTestTubeType(final RbTestTubeType testTubeType) {
        this.testTubeType = testTubeType;
    }

    public String getOverrideResearchTypeCode() {
        return overrideResearchTypeCode;
    }

    public void setOverrideResearchTypeCode(final String overrideResearchTypeCode) {
        this.overrideResearchTypeCode = overrideResearchTypeCode;
    }

    public String getOverrideResearchTypeName() {
        return overrideResearchTypeName;
    }

    public void setOverrideResearchTypeName(final String overrideResearchTypeName) {
        this.overrideResearchTypeName = overrideResearchTypeName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MapResearchTypeToLaboratory[").append(id);
        sb.append("]{ laboratory=").append(laboratory != null ? laboratory.toShortString() : "null");
        sb.append(", researchType=").append(researchType != null ? researchType.toShortString() : "null");
        sb.append(", biomaterialType=").append(biomaterialType != null ? biomaterialType.toShortString() : "null");
        sb.append(", testTubeType=").append(testTubeType != null ? testTubeType.toShortString() : "null");
        sb.append(", overrideResearchTypeCode='").append(overrideResearchTypeCode).append('\'');
        sb.append(", overrideResearchTypeName='").append(overrideResearchTypeName).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
