import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SpeedBasedTurnOrderStrategy implements TurnOrderStrategy{
    @Override
    public List<Combatant> determineOrder(List<Combatant>combatants){
        List<Combatant>sorted=new ArrayList<>(combatants);
        Collections.sort(sorted,new Comparator<Combatant>(){
            @Override
            public int compare(Combatant c1, Combatant c2){
                return c2.getSpeed()-c1.getSpeed(); //higher speed goes first

            }
        
        });
        return sorted;
    }
    @Override
    public String getName(){
        return "Speed-Based Turn Order";
    }

}
