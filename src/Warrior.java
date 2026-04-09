public class Warrior extends Player {
    public Warrior() {
        name = "Warrior";
        baseHP = 260;
        currentHP = 260;
        maxHP = 260;
        atk = 40;
        oldAtk = atk;
        defence = 20;
        speed = 30;

        availableActions.add(new ShieldBash());
    }
    public Warrior(String identifier) {
        name = "Warrior " + identifier;
        baseHP = 260;
        currentHP = 260;
        maxHP = 260;
        atk = 40;
        oldAtk = atk;
        defence = 20;
        speed = 30;

        availableActions.add(new ShieldBash());
    }

}
