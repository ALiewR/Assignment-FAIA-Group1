public class Wolf extends Enemy {
    public Wolf() {
        name = "Wolf";
        baseHP = 40;
        currentHP = 40;
        maxHP = 40;
        atk = 45;
        oldAtk = atk;
        defence = 5;
        speed = 35;
    }
    public Wolf(String identifier) {
        name = "Wolf " + identifier;
        baseHP = 40;
        currentHP = 40;
        maxHP = 40;
        atk = 45;
        oldAtk = atk;
        defence = 5;
        speed = 35;
    }
}
