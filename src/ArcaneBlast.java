import java.util.List;
public class ArcaneBlast extends SpecialSkill
{
    public ArcaneBlast()
    {
        super("Arcane Blast");
        this.description="Deals heavy damage to multiple enemies and increases attack by 10 for each enemy killed";
        this.numOfTargets=10;
        this.targetType=TARGET_TYPE.ENEMIES;

    }
    @Override
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext) {
        for (Combatant eachTarget: targets) {
            eachTarget.oldHP = eachTarget.currentHP;
            int damage=actor.atk*2- eachTarget.defence;
            eachTarget.currentHP-=Math.max(damage,0);
            //eachTarget.afflictedStatusEffects.add(new StatusEffect());
        }
}
}
