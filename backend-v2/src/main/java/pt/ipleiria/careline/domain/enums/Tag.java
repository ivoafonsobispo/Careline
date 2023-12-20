package pt.ipleiria.careline.domain.enums;

public enum Tag {
    URGENT("#FF0000","Urgent"),
    LESS_URGENT("#00FF00", "Less urgent"),
    MEDIAN("#FFFF00", "Median"),
    LOW("#FFFF00", "Low");

    private String codigoHexadecimal;
    private String name;

    Tag(String codigoHexadecimal, String name) {
        this.codigoHexadecimal = codigoHexadecimal;
        this.name = name;
    }

    public String getCodigoHexadecimal() {
        return codigoHexadecimal;
    }

    public String getName() {
        return name;
    }
}
