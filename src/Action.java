import java.util.List;

public class Action {
    public String name = "action";
    public String description = "can do this";
    public ACTION_TYPE actionType = ACTION_TYPE.ATTACK;
    public int numOfTargets = 1;
    public boolean doesInflictStatusEffectOnTarget = false;
    public Action() {};
    public Action(boolean inflictSE) {
        doesInflictStatusEffectOnTarget = inflictSE;
    }
    public void execute(Combatant actor, List<Combatant> targets) {
        
    }
}
