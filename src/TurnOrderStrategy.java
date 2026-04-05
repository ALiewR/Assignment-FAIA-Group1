import java.util.List;

public class TurnOrderStrategy {
    public List<Combatant> determineOrder(List<Combatant> combatants) {
        combatants.sort((a, b) -> b.atk - a.atk);
        return combatants;
    }
}
