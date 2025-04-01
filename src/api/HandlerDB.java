package api;

import java.util.List;

public interface HandlerDB {

    public List<List<String>> getAllRecords();

    public int getSizeOfDB();

    public List<String> getAllSourceVariants();
}
