public class Player extends Combatant {
    public Player() {
        combatantType = COMBATANT_TYPE.PLAYER;
        availableActions.add(new BasicAttack());
        availableActions.add(new ArcaneBlast());
        availableActions.add(new ShieldBash());
        availableActions.add(new Defend());
        availableActions.add(new UseItem(new Potion()));
        availableActions.add(new UseItem(new PowerStone()));
        availableActions.add(new UseItem(new SmokeBomb()));
        name = "Player";
        currentHP = 50;
        baseHP = 50;
    }
}
