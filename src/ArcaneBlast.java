import java.util.List;
public class ArcaneBlast extends SpecialSkill
{
    public ArcaneBlast()
    {
        super("Arcane Blast");
        this.description="Deals heavy damage to multiple enemies and increases attack by 10 for each enemy killed";
        this.numOfTargets=10;
        this.targetType=TARGET_TYPE.ENEMIES;
        this.actionType=ACTION_TYPE.ARCANE_BLAST;

    }
    @Override
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext) {
        for (Combatant eachTarget: targets) {
            eachTarget.savePreviousStats();
            int damage = Math.max(0, actor.atk - eachTarget.defence);
            eachTarget.takeDamage(damage);
            if (!eachTarget.isAlive()) {
                // only save before any adding
                if (targets.indexOf(eachTarget) == 0) actor.savePreviousStats();
                StatusEffect arcaneBoost = new ArcaneBoost();
                actor.afflictedStatusEffects.add(arcaneBoost);
                arcaneBoost.applyEffect(actor);
            }
        }
        actor.resetCooldown();
    }
}
