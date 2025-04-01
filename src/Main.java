import domain.Feat;
import featdb.HandlerDBimpl;

public class Main{
    public static void main(String[] args){
        HandlerDBimpl h = new HandlerDBimpl();
        System.out.println(h.getColumn("prerequisites"));
    }
}

