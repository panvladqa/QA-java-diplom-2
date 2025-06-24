package ya.response.entities;

import ya.request.entities.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private boolean success;
    private List<Order> orders;
    private Integer total;

    @JsonProperty("totalToday")
    private Integer totalToday;
}
