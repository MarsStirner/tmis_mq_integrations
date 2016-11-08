package ru.bars_open.medvtr.db.entities.mapped;

import org.joda.time.LocalDateTime;
import ru.bars_open.medvtr.db.entities.Person;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 07.11.2016, 18:24 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:   Суперкласс, наследующий от Deletable и имеющий в БД поля - createDatetime, modifyDatetime, createPerson_id, modifyPerson_id<br>
 */
@MappedSuperclass
public abstract class EditableByPersonEntity extends DeletableEntity {

    /**
     * Время создания записи
     */
    @Column(name = "createDatetime", nullable = false)
    protected LocalDateTime createDatetime;

    /**
     * Автор записи
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createPerson_id")
    protected Person createPerson;


    /**
     * Время изменения записи
     */
    @Column(name = "modifyDatetime", nullable = false)
    protected LocalDateTime modifyDatetime;

    /**
     * Последний менявший запись пользователь
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifyPerson_id")
    protected Person modifyPerson;


}
