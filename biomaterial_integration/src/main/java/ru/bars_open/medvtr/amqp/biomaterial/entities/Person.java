package ru.bars_open.medvtr.amqp.biomaterial.entities;

import org.joda.time.LocalDateTime;
import ru.bars_open.medvtr.amqp.biomaterial.entities.mapped.IdentifiedEntityWithExternal;

import javax.persistence.*;


/**
 * Author: Upatov Egor <br>
 * Date: 02.02.2017, 16:40 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name= "Person")
public class Person extends IdentifiedEntityWithExternal {

    /**
     *  Фамилия
     */
    @Column(name="lastName", nullable=false)
    private String lastName;

    /**
     *    Имя
     */
    @Column(name="firstName", nullable=false)
    private String firstName;

    /**
     * Отчество
     */
    @Column(name="patrName", nullable=false)
    private String patrName;

    /**
     * Пол пациента
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sex_id", nullable=false)
    private RbSex sex;

    /**
     * Дата и время рождения врача
     */
    @Column(name="birthDate", nullable=false)
    private LocalDateTime birthDate;


    public Person() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getPatrName() {
        return patrName;
    }

    public void setPatrName(final String patrName) {
        this.patrName = patrName;
    }

    public RbSex getSex() {
        return sex;
    }

    public void setSex(final RbSex sex) {
        this.sex = sex;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person[").append(id);
        sb.append("]{ lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", patrName='").append(patrName).append('\'');
        sb.append(", sex=").append(sex);
        sb.append(", birthDate=").append(birthDate);
        sb.append('}');
        return sb.toString();
    }
}
