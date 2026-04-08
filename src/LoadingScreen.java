import java.util.ArrayList;
import java.util.List;

public class LoadingScreen extends UI {
    public LoadingScreen(Printer printer){
        super(printer);
    }
  
public void showWelcome(){
    displayLineMessage("============================================================");
    displayLineMessage("         WELCOME TO TURN-BASED COMBAT ARENA GAME", true);
    displayLineMessage("============================================================");
    displayLineMessage("");

    displayLineMessage("PLAYERS:");
    displayLineMessage("1. Warrior | HP: 260 | ATK: 40 | DEF: 20 | SPD: 30");
    displayLineMessage("   Special Skill: Shield Bash");
    displayLineMessage("   Effects: Deal BasicAttack damage and stun target for current + next turn");
    displayLineMessage("");
    displayLineMessage("2. Wizard | HP: 200 | ATK: 50 | DEF: 10 | SPD: 20");
    displayLineMessage("   Special Skill: Aracne Blast");
    displayLineMessage("   Effects: Deal dmage to all enemies. Each kill adds +10 ATK until end of level");
    displayLineMessage("");

    displayLineMessage("ENEMIES");
    displayLineMessage("1. Golbin | HP: 55 | ATK: 35 | DEF: 15 | SPD: 25");
    displayLineMessage("1. Wolf | HP: 40 | ATK: 45 | DEF: 5 | SPD: 35");

    displayLineMessage("DIFFICULTY LEVELS:");
    displayLineMessage("1. Easy   -> Initial Spawn: 3 Goblins");
    displayLineMessage("2. Medium -> Initial Spawn: 1 Goblin + 1 Wolf | Backup Spawn: 2 Wolves");
    displayLineMessage("3. Hard   -> Initial Spawn: 2 Goblins | Backup Spawn: 1 goblin + 2 Wolves");

}
public Player choosePlayer(){
    displayLineMessage("Choose your character:");
    displayLineMessage("1. Warrior");
    displayLineMessage("2. Wizard");

    while (true){
        displayMessage("Enter your choice: ");
        int choice=getUIInt();
        if (choice==1){
            return new Warrior();
        } else if (choice==2){
            return new Wizard();
        }
        displayLineMessage("Invalid choice. Please try again.");
    }
}
public List<Item> chooseItems(){
    List<Item> selectedItems=new ArrayList<>();
    displayLineMessage("Choose 2 single-use items.(You can choose the same items.)");
    displayLineMessage("1. Smoke Bomb");
    displayLineMessage("2. Potion");
    displayLineMessage("3. Power Stone");

    for (int i =1; i<=2;i++){
        while (true){
            displayMessage("Enter item choice"+i+":");
            int choice=getUIInt();
            if (choice==1){
                selectedItems.add(new Item("smoke bomb"));
                break;
            } else if (choice==2){
                selectedItems.add(new Item("potion"));
                break;
            } else if (choice==3){
                selectedItems.add(new Item("power stone"));
                break;
            }
            displayLineMessage("Invalid choice. Please try again.");
        }
    }
    return selectedItems;
}
    

    
public Level chooseLevel(){
    displayLineMessage("Choose a level:");
    displayLineMessage("1. Easy");
    displayLineMessage("2. Medium");
    displayLineMessage("3. Hard");

    while (true){
        displayMessage("Enter your choice: ");
        int choice=getUIInt();
        if (choice >=1 && choice <=3){
            return new Level(choice);
        }
        displayLineMessage("Invalid choice. Please try again.");
    }
}
public void showEnteringBattleMessage(){
    displayLineMessage("");
    displayLineMessage("Loading selected settings...",true);
    displayLineMessage("Entering Battle...",true);
    displayLineMessage("============================================================");
}
}


