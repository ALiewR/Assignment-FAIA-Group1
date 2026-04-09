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
        for (Combatant eachTarget: targets) {
            eachTarget.savePreviousStats();
            int damage = Math.max(0, actor.atk - eachTarget.defence);
            eachTarget.takeDamage(damage);
            StatusEffect stun = new Stun();
            actor.afflictedStatusEffects.add(stun);
            stun.applyEffect(actor);
        }
        actor.resetCooldown();
}
}
