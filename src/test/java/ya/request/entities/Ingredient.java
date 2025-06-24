package ya.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingredient {
    @JsonProperty("_id")
    private String id;
    private String name;
    private String type;
    private Integer proteins;
    private Integer fat;
    private Integer carbohydrates;
    private Integer calories;
    private Integer price;
    private String image;

    @JsonProperty("image_mobile")
    private String imageMobile;

    @JsonProperty("image_large")
    private String imageLarge;

    @JsonProperty("__v")
    private Integer version;

    public Ingredient() {}

    public Ingredient(String id, String name, String type, Integer proteins,
                      Integer fat, Integer carbohydrates, Integer calories,
                      Integer price, String image, String imageMobile,
                      String imageLarge, Integer version) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.proteins = proteins;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.price = price;
        this.image = image;
        this.imageMobile = imageMobile;
        this.imageLarge = imageLarge;
        this.version = version;
    }
}