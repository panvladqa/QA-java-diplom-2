package ya.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Order {
    @JsonProperty("_id")
    private String id;
    private List<String> ingredients;
    private String status;
    private String name;

    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonProperty("updatedAt")
    private Date updatedAt;
    private Integer number;

    public Order() {
    }

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
