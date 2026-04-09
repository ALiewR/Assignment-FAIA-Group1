public class Goblin extends Enemy {
    public Goblin() {
        name = "Goblin";
        baseHP = 55;
        currentHP = 55;
        maxHP = 55;
        atk = 35;
        oldAtk = atk;
        defence = 15;
        speed = 25;
    }
    public Goblin(String identifier) {
        name = "Goblin " + identifier;
        baseHP = 55;
        currentHP = 55;
        maxHP = 55;
        atk = 35;
        oldAtk = atk;
        defence = 15;
        speed = 25;
    }
}
