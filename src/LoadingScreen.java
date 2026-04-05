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
}
public Player choosePlayer(){
    displayLineMessage("Choose your character:");
    displayLineMessage("1. Default Player");

    while (true){
        displayMessage("Enter your choice: ");
        int choice=getUIInt();
        if (choice==1){
            return new Player();
        }
        displayLineMessage("Invalid choice. Please try again.");
    }
}
public List<Item> chooseItems(){
    List<Item> selectedItems=new ArrayList<>();
    displayLineMessage("Choose your item setup:");
    displayLineMessage("1. Smoke Bomb");
    displayLineMessage("2. Potion");
    displayLineMessage("3. Power Stone");
    displayLineMessage("4. No item");

    while (true){
        displayMessage("Enter your choice: ");
        int choice=getUIInt();
        if (choice==1){
            selectedItems.add(new Item("smoke bomb"));
            return selectedItems;
        } else if (choice==2){
            selectedItems.add(new Item("potion"));
            return selectedItems;
        } else if (choice==3){
            selectedItems.add(new Item("power stone"));
            return selectedItems;
        } else if (choice==4){

            return selectedItems;
        }
        displayLineMessage("Invalid choice. Please try again.");
    }
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
}