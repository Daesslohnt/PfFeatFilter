package domain;

public class Character {

    private String race;
    private String characterClass;
    private int level;
    private int STR;  // Strength
    private int DEX;  // Dexterity
    private int CON;  // Constitution
    private int INT;  // Intelligence
    private int WIS;  // Wisdom
    private int CHA;  // Charisma
    private int Fort; // Fortitude Save
    private int Ref;  // Reflex Save
    private int Will; // Will Save
    private int BAB;  // Base Attack Bonus

    public Character(){
        this.STR = 10;
        this.DEX = 10;
        this.CON = 10;
        this.INT = 10;
        this.WIS = 10;
        this.CHA = 10;
    }

    public Character(String race, String characterClass, int level, int STR, int DEX, int CON,
                     int INT, int WIS, int CHA, int Fort, int Ref, int Will, int BAB) {
        this.race = race;
        this.characterClass = characterClass;
        this.level = level;
        this.STR = STR;
        this.DEX = DEX;
        this.CON = CON;
        this.INT = INT;
        this.WIS = WIS;
        this.CHA = CHA;
        this.Fort = Fort;
        this.Ref = Ref;
        this.Will = Will;
        this.BAB = BAB;
    }

    // Getters and Setters

    public static String[] getAllRaces() {
        return new String[] {"Dwarf", "Elf", "Gnome", "Half-Elf", "Half-Orc", "Halfling", "Other"};
    }

    public static String[] getAllClasses() {
        return new String[] {"Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger",
                "Rogue", "Sorcerer", "Wizard", "Other"};
    }

    public String getRace() { return race; }
    public void setRace(String race) { this.race = race; }

    public String getCharacterClass() { return characterClass; }
    public void setCharacterClass(String characterClass) { this.characterClass = characterClass; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getSTR() { return STR; }
    public void setSTR(int STR) { this.STR = STR; }

    public int getDEX() { return DEX; }
    public void setDEX(int DEX) { this.DEX = DEX; }

    public int getCON() { return CON; }
    public void setCON(int CON) { this.CON = CON; }

    public int getINT() { return INT; }
    public void setINT(int INT) { this.INT = INT; }

    public int getWIS() { return WIS; }
    public void setWIS(int WIS) { this.WIS = WIS; }

    public int getCHA() { return CHA; }
    public void setCHA(int CHA) { this.CHA = CHA; }

    public int getFort() { return Fort; }
    public void setFort(int Fort) { this.Fort = Fort; }

    public int getRef() { return Ref; }
    public void setRef(int Ref) { this.Ref = Ref; }

    public int getWill() { return Will; }
    public void setWill(int Will) { this.Will = Will; }

    public int getBAB() { return BAB; }
    public void setBAB(int BAB) { this.BAB = BAB; }
}
