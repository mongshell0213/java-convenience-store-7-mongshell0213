package repository;

import java.util.ArrayList;
import java.util.List;
import model.Production;

public class Productions {
    List<Production> productions;
    
    public Productions(){
        productions = new ArrayList<>();
    }

    public void add(String[] input){
        String name = input[0];
        int price=0;
        int quantity=0;
        price = Integer.parseInt(input[1]);
        quantity = Integer.parseInt(input[2]);
        String promotion = input[3];
        productions.add(new Production(name,price,quantity,promotion));
    }

    public int size(){
        return productions.size();
    }
}
