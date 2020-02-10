import java.util.ArrayList;
import java.util.Objects;

public class History {
    ArrayList<String> history = new ArrayList<>();

    public void add(String url){
        history.add(url);
    }

    public String getLast(){
        return (history.size() >=1) ? history.get(history.size()-1) : "0";
    }
}
