package domain;

public class PrerequisiteNumeric extends Prerequisite{

    private Integer functionality;

    public PrerequisiteNumeric(String name, String category, int functionality){
        super(name, category);
        this.functionality = functionality;
    }

    @Override
    public Integer getFunctionality() {
        return functionality;
    }

    public void setFunctionality(int functionality) {
        this.functionality = functionality;
    }
}
