
package ru.bars_open.medvtr.mq.entities.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.bars_open.medvtr.mq.entities.action.Analysis;
import ru.bars_open.medvtr.mq.entities.base.TakenTissue;


/**
 * BiologicalMaterialMessage
 * <p>
 * Сообщения содержащее даные о заборе биоматериала
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "takenTissue",
    "analysis"
})
public class BiologicalMaterialMessage implements Serializable
{

    /**
     * Забор тканей/биоматериала
     * (Required)
     * 
     */
    @JsonProperty("takenTissue")
    @JsonPropertyDescription("\u0417\u0430\u0431\u043e\u0440 \u0442\u043a\u0430\u043d\u0435\u0439/\u0431\u0438\u043e\u043c\u0430\u0442\u0435\u0440\u0438\u0430\u043b\u0430")
    private TakenTissue takenTissue;
    /**
     * Экшены - анализы
     * 
     */
    @JsonProperty("analysis")
    @JsonPropertyDescription("\u042d\u043a\u0448\u0435\u043d\u044b - \u0430\u043d\u0430\u043b\u0438\u0437\u044b")
    private List<Analysis> analysis = new ArrayList<Analysis>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 3074203788904358552L;

    /**
     * Забор тканей/биоматериала
     * (Required)
     * 
     */
    @JsonProperty("takenTissue")
    public TakenTissue getTakenTissue() {
        return takenTissue;
    }

    /**
     * Забор тканей/биоматериала
     * (Required)
     * 
     */
    @JsonProperty("takenTissue")
    public void setTakenTissue(TakenTissue takenTissue) {
        this.takenTissue = takenTissue;
    }

    /**
     * Экшены - анализы
     * 
     */
    @JsonProperty("analysis")
    public List<Analysis> getAnalysis() {
        return analysis;
    }

    /**
     * Экшены - анализы
     * 
     */
    @JsonProperty("analysis")
    public void setAnalysis(List<Analysis> analysis) {
        this.analysis = analysis;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(takenTissue).append(analysis).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BiologicalMaterialMessage) == false) {
            return false;
        }
        BiologicalMaterialMessage rhs = ((BiologicalMaterialMessage) other);
        return new EqualsBuilder().append(takenTissue, rhs.takenTissue).append(analysis, rhs.analysis).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
