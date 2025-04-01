package domain;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Feat {
    private String name;
    private String type;
    private List<String> typeItems;
    private String description;
    private String prerequisites;
    private List<Prerequisite> prerequisiteItems;
    private String prerequisiteFeats;
    private String benefit;
    private String normal;
    private String special;
    private String source;
    private String fulltext;

    public Feat(){}

    public Feat(String name, String type, String description, String prerequisites, String prerequisiteFeats,
                String benefit, String normal, String special, String source, String fulltext) {
        this.name = name;
        setType(type);
        this.description = description;
        setPrerequisites(prerequisites);
        this.prerequisiteFeats = prerequisiteFeats;
        this.benefit = benefit;
        this.normal = normal;
        this.special = special;
        this.source = source;
        this.fulltext = fulltext;
    }

    public List<String> splitByComma(String inputString) {
        if (inputString == null || inputString.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(inputString.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getType() {
        return typeItems;
    }
    public String getTypeAsString(){
        return this.type;
    }
    public void setType(String type) {
        this.typeItems = splitByComma(type);
        this.type = type;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Prerequisite> getPrerequisites() {
        return prerequisiteItems;
    }
    public String getPrerequisitesAsString(){
        return prerequisites;
    }
    public void setPrerequisites(String prerequisites) {
        List<String> prerequisiteStrings = splitByComma(prerequisites);
        this.prerequisiteItems = categorizePrerequisites(prerequisiteStrings);
        this.prerequisites = prerequisites;
    }

    public String getPrerequisiteFeats() { return prerequisiteFeats; }
    public void setPrerequisiteFeats(String prerequisiteFeats) { this.prerequisiteFeats = prerequisiteFeats; }

    public String getBenefit() { return benefit; }
    public void setBenefit(String benefit) { this.benefit = benefit; }

    public String getNormal() { return normal; }
    public void setNormal(String normal) { this.normal = normal; }

    public String getSpecial() { return special; }
    public void setSpecial(String special) { this.special = special; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getFulltext() { return fulltext; }
    public void setFulltext(String fulltext) { this.fulltext = fulltext; }

    public String shortVersion(){
        return name.toUpperCase() + " (" + type + ")\n" + description + "\nPrerequisites: " + prerequisites +
                "\nPrerequisites Feats: " + prerequisiteFeats + "\nBenefit: " + benefit +
                "\nNormal: " + normal + "\nSpecial: " + special;
    }

    @Override
    public String toString(){
        return shortVersion();
    }

    private int extractIntFromString(String s){
        String replaced = s.replaceAll("[^1-9]", "").trim();
        if (replaced.isEmpty()){
            return -1;
        }
        return Integer.parseInt(replaced);
    }

    private boolean containsCharacteristics(String item){
        return item.contains("str") || item.contains("dex") || item.contains("con") || item.contains("int") ||
                item.contains("wis") || item.contains("cha");
    }

    private List<Prerequisite> categorizePrerequisites(List<String> items){
        List<Prerequisite> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b(?:str(?:ength)?|dex(?:terity)?|con(?:stitution)?|int(?:elligence)?|wis(?:dom)?|cha(?:risma)?)\\b");
        String[] racesArray = Character.getAllRaces();
        for (int i = 0; i < racesArray.length; i++){
            racesArray[i] = racesArray[i].toLowerCase();
        }
        Set<String> racesSet = new HashSet<>(Set.of(racesArray));

        for (String item : items){
            String iteml = item.toLowerCase();
            iteml = iteml.replaceAll("\"", "");
            if (iteml.contains("base attack bonus")){ // BAB
                PrerequisiteNumeric p = new PrerequisiteNumeric(item, "BAB", extractIntFromString(iteml));
                result.add(p);
            } else if (pattern.matcher(iteml).find()) { // Basic Characteristics
                if (iteml.contains("str")){
                    PrerequisiteNumeric p = new PrerequisiteNumeric(item, "Strength", extractIntFromString(iteml));
                    result.add(p);
                } else if (iteml.contains("dex")) {
                    PrerequisiteNumeric p = new PrerequisiteNumeric(item, "Dexterity", extractIntFromString(iteml));
                    result.add(p);
                } else if (iteml.contains("con")) {
                    PrerequisiteNumeric p = new PrerequisiteNumeric(item, "Constitution", extractIntFromString(iteml));
                    result.add(p);
                } else if (iteml.contains("int")) {
                    PrerequisiteNumeric p = new PrerequisiteNumeric(item, "Intelligence", extractIntFromString(iteml));
                    result.add(p);
                } else if (iteml.contains("wis")) {
                    PrerequisiteNumeric p = new PrerequisiteNumeric(item, "Wisdom", extractIntFromString(iteml));
                    result.add(p);
                } else if(iteml.contains("cha")) {
                    PrerequisiteNumeric p = new PrerequisiteNumeric(item, "Charisma", extractIntFromString(iteml));
                    result.add(p);
                }
            } else if (iteml.contains("level")) { // Either Caster Level or General Level
                int itemLevel = extractIntFromString(item);
                if (itemLevel != -1 && iteml.contains("caster")){
                    PrerequisiteNumeric p = new PrerequisiteNumeric(item, "Caster Level", itemLevel);
                    result.add(p);
                } else if (itemLevel != -1) {
                    PrerequisiteNumeric p = new PrerequisiteNumeric(item, "General Level", itemLevel);
                    result.add(p);
                }
            } else if (racesSet.contains(iteml)){ // Race
                PrerequisiteCategoric p = new PrerequisiteCategoric(item, "Race", item);
                result.add(p);
            }
        }

        return result;
    }
}
