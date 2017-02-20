package ru.bars_open.medvtr.amqp.biomaterial.entities.mapped;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 16:42 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@MappedSuperclass
public abstract class IdentifiedEntityWithExternal extends IdentifiedEntity {

    /**
     * Внешний идентифкатор ТМИС
     */
    @Column(name = "externalId", nullable = false)
    protected String externalId;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }
}
