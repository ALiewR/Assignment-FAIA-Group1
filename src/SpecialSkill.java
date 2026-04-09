import java.util.List;
public abstract class SpecialSkill extends Action 
{
    public SpecialSkill(String name)
    {
        super(name,"Special Skill",ACTION_TYPE.SPECIAL_SKILL,1);
        this.targetType=TARGET_TYPE.ENEMIES;
    }
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext, boolean resetCooldown)
    {
        this.execute(actor,targets,battleContext);
        if (resetCooldown)
        {
            actor.resetCooldown();
        }
    }
}
