package api.dto;

public class UnknownData {
    private Integer id;
    private String name;
    private Integer year;
    private String color;
    private String pantone_values;

    public UnknownData(Integer id, String name, Integer year, String color, String pantone_values) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.color = color;
        this.pantone_values = pantone_values;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getPantone_values() {
        return pantone_values;
    }
}
