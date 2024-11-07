package repository;

import java.util.ArrayList;
import java.util.List;
import model.Production;

public class Productions {
    List<Production> productions;
    
    public Productions(){
        productions = new ArrayList<>();
    }

    public void add(Production production){
        productions.add(production);
    }

    public int size(){
        return productions.size();
    }
}
