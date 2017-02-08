package ru.bars_open.medvtr.amqp.biomaterial.entities;

import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.ReferenceBookEntity;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 19:50 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name = "rbTestTubeType")
public class RbTestTubeType extends ReferenceBookEntity{
    /**
     * Объем пробирки или ёмкости {Measurement}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="volume_id", nullable = false)
    private Measurement volume;

    @Column(name= "autocreated", nullable = false)
    private boolean autocreated = false;

    public RbTestTubeType() {
    }

    public Measurement getVolume() {
        return volume;
    }

    public void setVolume(final Measurement volume) {
        this.volume = volume;
    }

    public boolean isAutocreated() {
        return autocreated;
    }

    public void setAutocreated(final boolean autocreated) {
        this.autocreated = autocreated;
    }
}
