public class Enemy extends Combatant {
    public Enemy() {
        combatantType = COMBATANT_TYPE.ENEMY;
        availableActions.add(new Action());
        name = "Enemy";
    }
    public Enemy(String name){
        this();
        this.name=name;

        if (name.startsWith("Wolf")){
            this.speed=35;
        }else if (name.startsWith("Goblin")){
            this.speed=25;
        }
    }
}
