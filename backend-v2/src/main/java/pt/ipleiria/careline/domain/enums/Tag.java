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

    public static Tag getTagByName ( String name) {
        if(name == null)
            return null;
        if(name.toUpperCase().equals("URGENT"))
            return Tag.URGENT;
        if(name.toUpperCase().equals("LESS URGENT"))
            return Tag.URGENT;
        if(name.toUpperCase().equals("MEDIAN"))
            return Tag.URGENT;
        return Tag.LOW;
    }
}
