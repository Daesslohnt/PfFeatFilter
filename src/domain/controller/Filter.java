package domain.controller;

import domain.Feat;
import domain.Character;
import domain.Prerequisite;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Filter {

    public List<Feat> filterFeats(Character currecntCharacter, List<Feat> feats, String name, String type, String source) {
        List<Feat> filtered = new ArrayList<>();
        Set<String> standardTypes = Set.of("General", "Combat", "Item Creation", "Metamagic");

        for (Feat feat : feats) {
            // Name Filter (case-insensitive, teilweise Übereinstimmung)
            if (name != null && !name.isEmpty() &&
                    !feat.getName().toLowerCase().contains(name.toLowerCase())) {
                continue;
            }

            // Type Filter
            if (type != null) {
                if ("Other".equals(type)) {
                    if (feat.getType().size() == 1){ // single type
                        if (standardTypes.contains(feat.getType().get(0))) {
                            continue;
                        }
                    } else { // multiple types: if there is at least on non-standard type than "add to filtered"
                        boolean atLeastOneOther = false;
                        for (String t: feat.getType()){
                            if (!standardTypes.contains(t)){
                                atLeastOneOther = true;
                                break;
                            }
                        }
                        if (!atLeastOneOther){
                            continue;
                        }
                    } // TODO: handle Exception if size of type < 0
                } else {
                    if (feat.getType().size() == 1){ // single type
                        if (!type.equals(feat.getType().get(0))) {
                            continue;
                        }
                    } else { // multiple types
                        boolean atLeastOneOther = false;
                        for (String t: feat.getType()){
                            if (t.equals(type)){
                                atLeastOneOther = true;
                                break;
                            }
                        }
                        if (!atLeastOneOther){
                            continue;
                        }
                    }
                }
            }

            //Source Filter
            if (source != null && !source.isEmpty() && !source.equals(feat.getSource())) {
                continue;
            }

            filtered.add(feat);
        }

        if (currecntCharacter != null){
            filtered = filterByPrerequisites(currecntCharacter, filtered);
        }

        return filtered;
    }

    private List<Feat> filterByPrerequisites(Character currentCharacter, List<Feat> feats){
        List<Feat> filtered = new ArrayList<>();

        for (Feat feat : feats){
            List<Prerequisite> prerequisites = feat.getPrerequisites();
            if (prerequisites == null){
                continue;
            } else if (prerequisites.size() == 0) {
                filtered.add(feat);
                continue;
            }
            boolean isFulfilled = false;
            for (int i = 0; i < prerequisites.size(); i++){
                Object func = prerequisites.get(i).getFunctionality();
                String cat = prerequisites.get(i).getCategory();
                if (cat.equals("BAB") && currentCharacter.getBAB() == (int) func){
                    isFulfilled = true;
                } else if (cat.equals("Strength") && currentCharacter.getSTR() >= (int) func) {
                    isFulfilled = true;
                } else if (cat.equals("Dexterity") && currentCharacter.getDEX() >= (int) func) {
                    isFulfilled = true;
                } else if (cat.equals("Constitution") && currentCharacter.getCON() >= (int) func) {
                    isFulfilled = true;
                } else if (cat.equals("Intelligence") && currentCharacter.getINT() >= (int) func) {
                    isFulfilled = true;
                } else if (cat.equals("Wisdom") && currentCharacter.getWIS() >= (int) func) {
                    isFulfilled = true;
                } else if (cat.equals("Charisma") && currentCharacter.getCHA() >= (int) func) {
                    isFulfilled = true;
                } else if (cat.equals("Caster Level") && currentCharacter.getLevel() >= (int) func) {
                    isFulfilled = true;
                } else if (cat.equals("General Level") && currentCharacter.getLevel() >= (int) func) {
                    isFulfilled = true;
                } else if (cat.equals("Race")) {
                    String r = currentCharacter.getRace();
                    if (r != null && r.toLowerCase().equals((String) func))
                        isFulfilled = true;
                } else if (cat.equals("Will") && currentCharacter.getWill() >= (int) func) {
                    isFulfilled = true;
                } else if (cat.equals("Fort") && currentCharacter.getFort() >= (int) func) {
                    isFulfilled = true;
                } else if (cat.equals("Ref") && currentCharacter.getRef() >= (int) func) {
                    isFulfilled = true;
                } else {
                    isFulfilled = false;
                    break;
                }
            }
            if (isFulfilled) filtered.add(feat);
        }
        return filtered;
    }
}
