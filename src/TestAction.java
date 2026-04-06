import java.util.*;
public class TestAction {
    public static void main(String[] args)
    {
        Combatant actor =new Player();
        actor.atk=20;

        Combatant target=new Enemy();
        target.defence=5;
        target.currentHP=50;
        List<Combatant>targets=new ArrayList<>();
        targets.add(target);

        //Action action = new Action("Basic Attack", "Deals damage", ACTION_TYPE.ATTACK,1);
        Action action = new BasicAttack();

        BattleContext context= new BattleContext();
        System.out.println("Before Attack:");
        System.out.println("Target HP: " + target.currentHP);

        action.execute(actor,targets,context);
        System.out.println("\nAfter Attack:");
        System.out.println("Target HP: " + target.currentHP);
    }
    }
    
