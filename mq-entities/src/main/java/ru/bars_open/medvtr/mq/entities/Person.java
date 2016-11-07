package ru.bars_open.medvtr.mq.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.apache.commons.lang3.StringUtils;


/**
 * Описание пользователя МИС
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    @JsonProperty(value = "id", required = true)
    @JsonPropertyDescription("Идентифкатор МИС")
    private Integer id;

    @JsonProperty("lastName")
    @JsonPropertyDescription("Фамилия")
    private String lastName;

    @JsonProperty("firstName")
    @JsonPropertyDescription("Имя")
    private String firstName;

    @JsonProperty("patrName")
    @JsonPropertyDescription("Отчество")
    private String patrName;

    public Person() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person[").append(id);
        sb.append("]{ lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", patrName='").append(patrName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
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

    public String getFullName() {
        final StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(lastName)) {
            sb.append(lastName);
        }
        if (StringUtils.isNotEmpty(firstName)) {
            if (sb.length() != 0) {
                sb.append(' ');
            }
            sb.append(firstName);
        }
        if (StringUtils.isNotEmpty(patrName)) {
            if (sb.length() != 0) {
                sb.append(' ');
            }
            sb.append(patrName);
        }
        return sb.toString();
    }
}