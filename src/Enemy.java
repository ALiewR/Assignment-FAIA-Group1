public class Enemy extends Combatant {
    public Enemy() {
        combatantType = COMBATANT_TYPE.ENEMY;
        availableActions.add(new Action());
        name = "Enemy";
    }
    public Enemy(String name){
        this();
        this.name=name;
    }
}
