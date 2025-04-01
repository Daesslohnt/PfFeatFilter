package domain.controller;

import java.util.*;
import java.util.stream.Collectors;

import domain.Prerequisite;
import featdb.HandlerDBimpl;
import domain.Character;

import domain.Feat;

public class Controller {
    private Character currentCharacter;
    private boolean characterSubmitted;
    private HandlerDBimpl database;

    public Controller() {
        currentCharacter = new Character();
        characterSubmitted = false;
        database = new HandlerDBimpl();
    }

    public void saveCharacter(String race, String characterClass, int level, int STR, int DEX, int CON,
                              int INT, int WIS, int CHA, int Fort, int Ref, int Will, int BAB) {
        currentCharacter.setRace(race);
        currentCharacter.setCharacterClass(characterClass);
        currentCharacter.setLevel(level);
        currentCharacter.setSTR(STR);
        currentCharacter.setDEX(DEX);
        currentCharacter.setCON(CON);
        currentCharacter.setINT(INT);
        currentCharacter.setWIS(WIS);
        currentCharacter.setCHA(CHA);
        currentCharacter.setFort(Fort);
        currentCharacter.setRef(Ref);
        currentCharacter.setWill(Will);
        currentCharacter.setBAB(BAB);
        characterSubmitted = true;
    }

    private Feat convertFeatRecordToFeat(List<String> featRecord) {
        // Annahme über die Reihenfolge der Felder in der List<String>:
        // 1: name, 2: type, 3: description, 4: prerequisites, 5: prerequisiteFeats,
        // 6: benefit, 7: normal, 8: special, 9: source, 10: fulltext,
        // 11: teamwork, 12: critical, 13: grit, 14: style,
        // 15: performance, 16: racial, 17: companionFamiliar

        // Sicherstellen, dass die Liste genügend Elemente enthält
        if (featRecord == null) {
            throw new IllegalArgumentException("Ungültiges Feat-Record Format");
        }

        return new Feat(
                featRecord.get(1),  // name
                featRecord.get(2),  // type
                featRecord.get(3),  // description
                featRecord.get(4),  // prerequisites
                featRecord.get(5),  // prerequisiteFeats
                featRecord.get(6),  // benefit
                featRecord.get(7),  // normal
                featRecord.get(8),  // special
                featRecord.get(9),  // source
                featRecord.get(10)  // fulltext
        );
    }

    private boolean parseBoolean(String value) {
        if (value == null) return false;
        value = value.trim().toLowerCase();
        return value.equals("1") || value.equals("true") || value.equals("yes") || value.equals("t");
    }

    public List<Feat> filterFeats(String name, String type, String source) {
        List<Feat> allFeats = new ArrayList<>();
        for (int i = 1; i < database.getSizeOfDB(); i++){
            allFeats.add(convertFeatRecordToFeat(database.getAllRecords().get(i)));
        }
        List<Feat> filtered = new ArrayList<>();
        Set<String> standardTypes = Set.of("General", "Combat", "Item Creation", "Metamagic");

        for (Feat feat : allFeats) {
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

        if (characterSubmitted){
            filtered = filterByPrerequisites(filtered);
        }

        return filtered;
    }

    private List<Feat> filterByPrerequisites(List<Feat> feats){
        List<Feat> filtered = new ArrayList<>();

        for (Feat feat : feats){
            List<Prerequisite> prerequisites = feat.getPrerequisites();
            if (prerequisites == null){
                continue;
            } else if (prerequisites.size() == 0) {
                filtered.add(feat);
                continue;
            }
            for (int i = 0; i < prerequisites.size(); i++){
                Object func = prerequisites.get(i).getFunctionality();
                String cat = prerequisites.get(i).getCategory();
                if (cat.equals("BAB") && currentCharacter.getBAB() == (int) func){
                    filtered.add(feat);
                } else if (cat.equals("Strength") && currentCharacter.getSTR() >= (int) func) {
                    filtered.add(feat);
                } else if (cat.equals("Dexterity") && currentCharacter.getDEX() >= (int) func) {
                    filtered.add(feat);
                } else if (cat.equals("Constitution") && currentCharacter.getCON() >= (int) func) {
                    filtered.add(feat);
                } else if (cat.equals("Intelligence") && currentCharacter.getINT() >= (int) func) {
                    filtered.add(feat);
                } else if (cat.equals("Wisdom") && currentCharacter.getWIS() >= (int) func) {
                    filtered.add(feat);
                } else if (cat.equals("Charisma") && currentCharacter.getCHA() >= (int) func) {
                    filtered.add(feat);
                } else if (cat.equals("Caster Level") && currentCharacter.getLevel() >= (int) func) {
                    filtered.add(feat);
                } else if (cat.equals("General Level") && currentCharacter.getLevel() >= (int) func) {
                    filtered.add(feat);
                } else {
                    break;
                }
            }
        }
        return filtered;
    }
}
