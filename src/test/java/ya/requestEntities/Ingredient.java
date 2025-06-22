package ya.requestEntities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingredient {

    private String id;
    private String name;
    private String type;
    private String proteins;
    private String fat;
    private String carbohydrates;
    private String calories;
    private String price;
    private String image;
    private String imageMobile;
    private String imageLarge;
    private String version;

    public Ingredient(
            String id,
            String name,
            String type,
            String proteins,
            String fat,
            String carbohydrates,
            String calories,
            String price,
            String image,
            String imageMobile,
            String imageLarge,
            String version) {
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

    public String getId() {
        return id;
    }
}