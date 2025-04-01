package domain.controller;

import java.util.*;

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
        // 6: benefit, 7: normal, 8: special, 9: source, 10: fulltext

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

    public List<Feat> filterFeats(String name, String type, String source) {
        List<Feat> allFeats = new ArrayList<>();
        for (int i = 1; i < database.getSizeOfDB(); i++){
            allFeats.add(convertFeatRecordToFeat(database.getAllRecords().get(i)));
        }
        return new Filter().filterFeats(currentCharacter, allFeats, name, type, source);
    }


}
