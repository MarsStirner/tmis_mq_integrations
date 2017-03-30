package ru.bars_open.medvtr.amqp.biomaterial.hepa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

import static ru.bars_open.medvtr.amqp.biomaterial.hepa.entities.listeners.StupidEncodingConverterListener.convertFromDb;

/**
 * Author: Upatov Egor <br>
 * Date: 10.02.2017, 18:29 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name ="client" )
public class Client extends IdentifiedEntity{

    @Column(name = "name1")
    private String lastName;

    @Column(name = "name2")
    private String firstName;

    @Column(name = "name3")
    private String patrName;

    @Column(name = "yob")
    private LocalDate birthDate;

    @Column(name = "gender", nullable = true)
    private Integer sex;

    @Column(name = "comment")
    private String comment;

    @Column(name="id_mis")
    private Integer externalId;

    public Client() {
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(final Integer sex) {
        this.sex = sex;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public Integer getExternalId() {
        return externalId;
    }

    public void setExternalId(final Integer externalId) {
        this.externalId = externalId;
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client[").append(id);
        sb.append("]{ lastName='").append(convertFromDb(lastName)).append('\'');
        sb.append(", firstName='").append(convertFromDb(firstName)).append('\'');
        sb.append(", patrName='").append(convertFromDb(patrName)).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", sex=").append(sex);
        sb.append(", comment='").append(convertFromDb(comment)).append('\'');
        sb.append(", externalId='").append(externalId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
