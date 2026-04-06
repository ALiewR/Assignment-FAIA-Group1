import java.util.List;
public class ShieldBash extends SpecialSkill
{
    public ShieldBash()
    {
        super("Shield Bash");
        this.description="Deals damage and inflicts stun";
        this.numOfTargets=1;
        this.targetType=TARGET_TYPE.ENEMIES;
        
    }
    @Override
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext) {
        for (Combatant eachTarget: targets) {
            eachTarget.oldHP = eachTarget.currentHP;
            int damage=actor.atk - eachTarget.defence;
            eachTarget.currentHP-=Math.max(damage,0);
            eachTarget.afflictedStatusEffects.add(new StatusEffect());
        }
}
}
