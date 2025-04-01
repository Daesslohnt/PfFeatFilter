package domain;

public class PrerequisiteCategoric extends Prerequisite{

    private String functionality;

    public PrerequisiteCategoric(String name, String category, String functionality){
        super(name, category);
        this.functionality = functionality;
    }

    @Override
    public String getFunctionality() {
        return functionality;
    }

    public void setFunctionality(String functionality) {
        this.functionality = functionality;
    }
}
