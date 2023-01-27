package pro.sky.recipesnew.model;

public class Ingredient {
    private String title;
    private int quantity;
    private String measureUnit;

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    @Override
    public String toString() {
        return title + " - " +
                 quantity + " " +
                measureUnit ;
    }
}
