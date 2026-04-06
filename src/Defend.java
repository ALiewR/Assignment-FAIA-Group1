import java.util.List;
public class Defend extends Action{
    public Defend()
    {
        super("Defend","Increases defence for the current and next round",ACTION_TYPE.ATTACK,1);
        this.targetType=TARGET_TYPE.SELF;
    }
    @Override
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battlecontext)
    {
        actor.defence+=10;
    }

}
