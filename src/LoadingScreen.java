import java.util.ArrayList;
import java.util.List;

public class LoadingScreen extends UI {
    public LoadingScreen(Printer printer){
        super(printer);
    }
  
public void showWelcome(){
    displayLineMessage("============================================================");
    displayLineMessage("|         WELCOME TO TURN-BASED COMBAT ARENA GAME          |", true);
    displayLineMessage("============================================================");

    displayLineMessage("|                     PLAYER ROSTER                        |");
    displayLineMessage("------------------------------------------------------------");
    displayLineMessage("| No | Name    |  HP | ATK | DEF | SPD |                   |");
    displayLineMessage("|----|---------|-----|-----|-----|-----|-------------------|");
    displayLineMessage("|  1 | Warrior | 260 |  40 |  20 |  30 |                   |");
    displayLineMessage("|    | Skill : Shield Bash                                 |");
    displayLineMessage("|    | -> Deal BasicAttack dmg to target.                  |");
    displayLineMessage("|    |    Target stunned this turn + next turn.            |");
    displayLineMessage("|----|---------|-----|-----|-----|-----|-------------------|");
    displayLineMessage("|  2 | Wizard  | 200 |  50 |  10 |  20 |                   |");
    displayLineMessage("|    | Skill : Arcane Blast                                |");
    displayLineMessage("|    | -> Deal BasicAttack dmg to ALL enemies.             |");
    displayLineMessage("|    |    Each kill grants +10 ATK until end of level.     |");

    displayLineMessage("------------------------------------------------------------");
    displayLineMessage("|                    ENEMIES TYPES                         |");
    displayLineMessage("------------------------------------------------------------");
    displayLineMessage("| No | Name   |  HP | ATK | DEF | SPD |                    |");
    displayLineMessage("|----|--------|-----|-----|-----|-----|--------------------|");
    displayLineMessage("|  1 | Goblin |  55 |  35 |  15 |  25 |                    |");
    displayLineMessage("|  2 | Wolf   |  40 |  45 |   5 |  35 |                    |");
    displayLineMessage("------------------------------------------------------------");


    displayLineMessage("------------------------------------------------------------");
    displayLineMessage("|                  DIFFICULTY LEVELS                       |");
    displayLineMessage("------------------------------------------------------------");
    displayLineMessage("|  1. Easy   | Initial Spawn : 3 Goblins                   |");
    displayLineMessage("|------------|---------------------------------------------|");
    displayLineMessage("|  2. Medium | Initial Spawn : 1 Goblin + 1 Wolf           |");
    displayLineMessage("|            | Backup  Spawn : 2 Wolves                    |");
    displayLineMessage("|------------|---------------------------------------------|");
    displayLineMessage("|  3. Hard   | Initial Spawn : 2 Goblins                   |");
    displayLineMessage("|            | Backup  Spawn : 1 Goblin + 2 Wolves         |");
    displayLineMessage("------------------------------------------------------------");
    displayLineMessage("");
    }

public Player choosePlayer(){
    displayLineMessage("============================================================");
    displayLineMessage("|                 CHOOSE YOUR CHARACTER                    |", true);
    displayLineMessage("============================================================");
    displayLineMessage("|  1. Warrior  |  HP:260  ATK:40  DEF:20  SPD:30           |");
    displayLineMessage("|  2. Wizard   |  HP:200  ATK:50  DEF:10  SPD:20           |");
    displayLineMessage("------------------------------------------------------------");
    

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
    displayLineMessage("============================================================");
    displayLineMessage("|                     CHOOSE 2 ITEMS                       |", true);
    displayLineMessage("|                (Duplicates are allowed)                  |");
    displayLineMessage("============================================================");
    displayLineMessage("|  1. Smoke Bomb  |  Enemy attacks deal 0 dmg this turn    |");
    displayLineMessage("|                    and the next turn.                    |");
    displayLineMessage("|  2. Potion      |  Restore 100 HP (capped at max HP).    |");
    displayLineMessage("|  3. Power Stone |  Free use of Special Skill.            |");
    displayLineMessage("|                    (Does not affect cooldown)            |");
    displayLineMessage("------------------------------------------------------------");
    
    

    for (int i =1; i<=2;i++){
        displayLineMessage("[ Item Slot " + i + " of 2 ]");
        while (true){
            displayMessage("Your choice (1-3): ");
            int choice=getUIInt();
            if (choice==1){
                selectedItems.add(new Item("smoke bomb"));
                displayLineMessage("  >> You have chosen: Smoke Bomb");
                if (i==1) displayLineMessage("Please choose another item.");
                break;
            } else if (choice==2){
                selectedItems.add(new Item("potion"));
                displayLineMessage("  >> You have chosen: Potion");
                if (i==1) displayLineMessage("Please choose another item.");
                break;
            } else if (choice==3){
                selectedItems.add(new Item("power stone"));
                displayLineMessage(">> You have chosen: Power Stone");
                if (i==1) displayLineMessage("Please choose another item.");
                break;
            }
            displayLineMessage("[!] Invalid choice. Please enter 1,2 or 3.");
        }
    }
    return selectedItems;
}
    

    
public Level chooseLevel(){
    displayLineMessage("============================================================");
    displayLineMessage("|                  DIFFICULTY LEVELS                       |", true);
    displayLineMessage("============================================================");
    displayLineMessage("|  1. Easy   | Initial Spawn : 3 Goblins                   |");
    displayLineMessage("|------------|---------------------------------------------|");
    displayLineMessage("|  2. Medium | Initial Spawn : 1 Goblin + 1 Wolf           |");
    displayLineMessage("|            | Backup  Spawn : 2 Wolves                    |");
    displayLineMessage("|------------|---------------------------------------------|");
    displayLineMessage("|  3. Hard   | Initial Spawn : 2 Goblins                   |");
    displayLineMessage("|            | Backup  Spawn : 1 Goblin + 2 Wolves         |");
    displayLineMessage("------------------------------------------------------------");

    while (true){
        displayMessage("Enter your choice (1-3): ");
        int choice=getUIInt();
        if (choice >=1 && choice <=3){
            String[]labels={"Easy", "Medium", "Hard"};
            displayLineMessage((">>"+labels[choice-1]+" selected!"));
            displayLineMessage("============================================================");
            return new Level(choice);
        }
        displayLineMessage("[!] Invalid choice. Please enter 1,2 or 3.");
    }
}
public void showEnteringBattleMessage(){
    displayLineMessage("Loading selected settings...",true);
    displayLineMessage("Entering Battle...",true);
    displayLineMessage("============================================================");
    displayLineMessage("");
}
private String getLevelName(Level level){
    List<Combatant> initalEnemies=level.getInitialEnemies();
    if (initalEnemies.size()==3){
        return "Easy";
    } 
    if (initalEnemies.size()==2){
        if (initalEnemies.get(1).name.startsWith("Wolf")){
            return "Medium";
        } else{
            return "Hard";
        }
    }
    return "Unknown";
}

private String formatLevel(Level level){
    List<Combatant> initialEnemies=level.getInitialEnemies();
    if (initialEnemies.size()==3){
        return "Easy - 3 Goblins"; 
    }
    if (initialEnemies.size()==2){
        if (initialEnemies.get(1).name.startsWith("Wolf")){
            return "Medium - 1 Goblin + 1 Wolf | Backup: Wolf B + Wolf C";
        } else{
            return "Hard - 2 Goblins | Backup: Goblin C + Wolf A + Wolf B"; 
        }
    }
    return "Unknown";
}

    
private String formatItemName(String itemName){
    if (itemName==null || itemName.isEmpty()){
        return "";
    }
    String[] parts = itemName.split(" ");
    String result = "";

    for (int i = 0; i < parts.length; i++){
        if (parts[i].length() > 0){
            result += Character.toUpperCase(parts[i].charAt(0)) + parts[i].substring(1).toLowerCase();
            if (i < parts.length - 1){
                result += " ";
            }
        }
    }

    return result;
}


public void showBattleSetup(Player player, List<Item> items, Level level, List<Combatant> enemies, List<Combatant> turnOrder){
    displayLineMessage("============================================================");
    displayLineMessage("|                      BATTLE SETUP                        |", true);
    displayLineMessage("============================================================");
    displayLineMessage("Difficulty Level (" + getLevelName(level) + ")", true);
    displayLineMessage("------------------------------------------------------------");
    

    //players
    displayLineMessage("  PLAYER");
        displayLineMessage("  Name : " + player.name);
        displayLineMessage("  HP   : " + player.currentHP +
                           "  |  ATK : " + player.atk +
                           "  |  DEF : " + player.defence +
                           "  |  SPD : " + player.speed);
        displayLineMessage("");
    
    
    //items
    displayMessage("Items: ");
    for (int i = 0; i < items.size(); i++) {
        displayMessage(formatItemName(items.get(i).name));
        if (i < items.size() - 1) {
            displayMessage(" + ");
        }
    }
    displayLineMessage("");
    displayLineMessage("");

    //level
    displayLineMessage("Level: " + formatLevel(level));
    displayLineMessage("");

    //enemy stats
    for (Combatant enemy : enemies) {
        displayLineMessage("  ENEMIES");
        displayLineMessage("  Name : " + enemy.name);
        displayLineMessage("  HP   : " + enemy.currentHP +
                           "  |  ATK : " + enemy.atk +
                           "  |  DEF : " + enemy.defence +
                           "  |  SPD : " + enemy.speed);
        displayLineMessage("");
     
    }

    displayMessage("Turn Order: ");
    for (int i = 0; i < turnOrder.size(); i++) {
        Combatant c = turnOrder.get(i);
        displayMessage(c.name+"(SPD "+c.speed+")");
        if (i < turnOrder.size() - 1) {
            displayMessage(" -> ");
        }
    }
    displayLineMessage("");
    displayLineMessage("");
}
}



