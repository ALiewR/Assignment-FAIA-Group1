import java.util.List;
public class Defend extends Action{
    public Defend()
    {
        super("Defend","Increases defence for the current and next round",ACTION_TYPE.ATTACK,1);
        this.targetType=TARGET_TYPE.SELF;
        this.actionType=ACTION_TYPE.DEFEND;
    }
    @Override
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battlecontext)
    {
        StatusEffect defBoost = new DefenceBoost();
        actor.afflictedStatusEffects.add(defBoost);
        defBoost.applyEffect(actor);
    }

}
