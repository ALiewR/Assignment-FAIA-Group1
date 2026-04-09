import java.util.List;
public class ShieldBash extends SpecialSkill
{
    public ShieldBash()
    {
        super("Shield Bash");
        this.description="Deals damage and inflicts stun";
        this.numOfTargets=1;
        this.targetType=TARGET_TYPE.ENEMIES;
        this.doesInflictStatusEffectOnTarget = true;
    }
    @Override
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext) {
        execute(actor,targets,battleContext,true);
    }
    @Override
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext, boolean resetCooldown) {
        for (Combatant eachTarget: targets) {
            eachTarget.savePreviousStats();
            int damage = Math.max(0, actor.atk - eachTarget.defence);
            eachTarget.takeDamage(damage);
            StatusEffect stun = new Stun();
            eachTarget.afflictedStatusEffects.add(stun);
            stun.applyEffect(eachTarget);
        }
        if (resetCooldown)
            {
                actor.resetCooldown();
            }
}
}
