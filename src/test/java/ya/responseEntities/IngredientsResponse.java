package ya.responseEntities;

import lombok.Getter;
import lombok.Setter;
import ya.requestEntities.Ingredient;

import java.util.List;

@Getter
@Setter
public class IngredientsResponse {


    private String success;

    public List<Ingredient> data;

    public IngredientsResponse(String success, List<Ingredient> data) {
        this.success = success;
        this.data = data;
    }
}