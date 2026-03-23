public class Player extends Combatant {
    public Player() {
        combatantType = COMBATANT_TYPE.PLAYER;
        availableActions.add(new Action(true));
        availableActions.add(new Action());
        name = "Player";
        currentHP = 50;
        baseHP = 50;
    }
}
