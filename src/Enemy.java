public class Enemy extends Combatant {
    public Enemy(String name, int baseHP, int currentHP, int maxHP,
                 int atk, int defence, int speed) {
        super(name, baseHP, currentHP, maxHP, atk, defence, speed);
        combatantType = COMBATANT_TYPE.ENEMY;
        availableActions.add(new BasicAttack());
    }
}
