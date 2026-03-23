public class Player extends Combatant {
    public Player() {
        combatantType = COMBATANT_TYPE.PLAYER;
        availableActions.add(new Action(true));
        availableActions.add(new Action());
        availableActions.add(new UseItem("smoke bomb", "enemies deal no damage", ACTION_TYPE.USE_SMOKE_BOMB, 0));
        availableActions.add(new UseItem("potion", "heal player 100 HP", ACTION_TYPE.USE_POTION, 0));
        name = "Player";
        currentHP = 50;
        baseHP = 50;
    }
}
