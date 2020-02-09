import java.util.ArrayList;
import java.util.Objects;

public class History {
    ArrayList<String> history;

    public void add(String url){
        history.add(url);
    }

    public String getLast(){
        if(history.size()<1) return "False";
        return history.get(history.size()-1);
    }
}
