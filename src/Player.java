public class Player extends Combatant {
    public Player() {
        combatantType = COMBATANT_TYPE.PLAYER;
        availableActions.add(new Action(true));
        availableActions.add(new Action());
        availableActions.add(new UseItem("smoke bomb", "enemies deal no damage", ACTION_TYPE.USE_SMOKE_BOMB, 0));
        name = "Player";
        currentHP = 50;
        baseHP = 50;
    }
}
