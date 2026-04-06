import java.util.List;
public class BasicAttack extends Action
{
    public BasicAttack()
    {
        super("Basic Attack", "Deals standard damage to one enemy", ACTION_TYPE.ATTACK,1);
        this.targetType=TARGET_TYPE.ENEMIES;

    }
    @Override
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext) {
        for (Combatant eachTarget: targets) {
            eachTarget.oldHP = eachTarget.currentHP;
            int damage=actor.atk - eachTarget.defence;
            eachTarget.currentHP-=Math.max(damage,0);
        }
}
}
