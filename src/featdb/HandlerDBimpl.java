package featdb;

import api.HandlerDB;

import java.util.*;

public class HandlerDBimpl implements HandlerDB {

    private DB db;
    private List<String> names;
    private int N;

    public HandlerDBimpl() {
        this.db = new DB();
        names = getColumn("name");
        N = db.getRecords().size() - 1;
    }

    public List<List<String>> getAllRecords(){
        return db.getRecords();
    }

    public int getSizeOfDB() {
        return N;
    }

    public List<List<String>> getFirstNRows(int N){
        return db.getRecords().subList(0, N);
    }

    public List<String> getColumn(String columnName){
        List<String> col = new ArrayList<>();
        List<String> firstRow = db.getRecords().get(0);
        int indexOfColumn = firstRow.indexOf(columnName);

        for (int i=1; i < db.getRecords().size(); i++){
            col.add(db.getRecords().get(i).get(indexOfColumn));
        }

        return col;
    }


    public List<String> getAllSourceVariants() {
        List<String> allSources = getColumn("source");
        List<String> uniqueSources = new ArrayList<>(new LinkedHashSet<>(allSources));
        return uniqueSources;
    }
}