public class Enemy extends Combatant {
    public Enemy() {
        combatantType = COMBATANT_TYPE.ENEMY;
        availableActions.add(new BasicAttack());
        name = "Enemy";
    }
}
