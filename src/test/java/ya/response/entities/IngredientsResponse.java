package ya.response.entities;

import lombok.Getter;
import lombok.Setter;
import ya.request.entities.Ingredient;

import java.util.List;

@Getter
@Setter
public class IngredientsResponse {
    private boolean success;
    private List<Ingredient> data;

    public IngredientsResponse() {}

    public IngredientsResponse(boolean success, List<Ingredient> data) {
        this.success = success;
        this.data = data;
    }
}