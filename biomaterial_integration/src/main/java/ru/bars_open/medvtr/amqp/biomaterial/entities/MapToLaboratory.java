package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.IdentifiedEntity;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 17.02.2017, 16:07 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name = "mapToLaboratory")
public class MapToLaboratory extends IdentifiedEntity{

    /**
     * К какой лаборатории приписан
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="laboratory_id", nullable = false)
    private RbLaboratory laboratory;

    /**
     * Тип исследования {rbResearchType} (NULL-любой)
     */
    @Column(name="researchType", nullable = false)
    private String researchType;

    /**
     * Тип теста {rbTestType} (NULL-любой)
     */
    @Column(name="testType")
    private String testType;

    /**
     * Тип биоматериала {rbBiomaterialType} (NULL-любой)
     */
    @Column(name="biomaterialType")
    private String biomaterialType;

    /**
     * Тип пробирки {rbTestTubeType} (NULL-любой)
     */
    @Column(name="testTubeType")
    private String testTubeType;

    public MapToLaboratory() {
    }

    public RbLaboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(final RbLaboratory laboratory) {
        this.laboratory = laboratory;
    }

    public String getResearchType() {
        return researchType;
    }

    public void setResearchType(final String researchType) {
        this.researchType = researchType;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(final String testType) {
        this.testType = testType;
    }

    public String getBiomaterialType() {
        return biomaterialType;
    }

    public void setBiomaterialType(final String biomaterialType) {
        this.biomaterialType = biomaterialType;
    }

    public String getTestTubeType() {
        return testTubeType;
    }

    public void setTestTubeType(final String testTubeType) {
        this.testTubeType = testTubeType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MapToLaboratory[").append(id);
        sb.append("] laboratory=").append(laboratory.toShortString());
        sb.append(", researchType='").append(researchType).append('\'');
        sb.append(", testType='").append(testType).append('\'');
        sb.append(", biomaterialType='").append(biomaterialType).append('\'');
        sb.append(", testTubeType='").append(testTubeType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
