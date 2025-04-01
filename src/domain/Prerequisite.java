package domain;

public abstract class Prerequisite {
    private String name;
    private String category;
    protected Object functionality;

    public Prerequisite(String name, String category){
        this.name = name;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getFunctionality() {
        return functionality;
    }
}

