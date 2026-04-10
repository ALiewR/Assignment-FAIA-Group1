import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleEngineUI extends UI {
    public BattleEngineUI(Printer printer) {
        super(printer);
    }

    /*
    * called by BattleEngine
    * BattleEngine decides WHEN the message is needed.
    * BattleEngineUI decides HOW the message is formatted
    */
    public void displayRoundHeader(int round) {
        displayLineMessage("Round " + round, true);
    }
    public Action selectAction(String playerName, List<Action> availableActions) {
        int userChoice = -1;

        while (userChoice < 0 || userChoice >= availableActions.size()) {
            // display info for user to know
            displayLineMessage(playerName + " can perform one of the following actions...");
            for (int i = 0; i < availableActions.size(); i++) {
                displayLineMessage(i+1 + ": " + availableActions.get(i).getName() + " - " + availableActions.get(i).getDescription());
            }
            displayMessage("Select action you wish to take: ", true);
            userChoice = getUIInt() - 1;
        }

        displayLineMessage(availableActions.get(userChoice).getName() + " can be used on "
                + availableActions.get(userChoice).getNumOfTargets() + " target(s).");

        // input is valid
        return availableActions.get(userChoice);
    }
    public void displayTurnStunned(String combatantName) {
        displayMessage(combatantName + " --> STUNNED: ", true);
        displayMessage("Turn skipped ");
    }
    public void displayTurnEliminated(String combatantName) {
        displayMessage(combatantName + " --> ELIMINATED: ", true);
        displayMessage("Skipped ");
    }
    public Combatant selectTarget(String actionName, List<Combatant> possibleTargets) {
        int userChoice = -1;

        while (userChoice < 0 || userChoice >= possibleTargets.size()) {
            // display info for user to know
            displayLineMessage(actionName + " can be used on...");
            for (int i = 0; i < possibleTargets.size(); i ++) {
                displayLineMessage(i+1 + ": " + possibleTargets.get(i).getName());
            }
            displayMessage("Select whom you wish to target: ", true);
            userChoice = getUIInt() - 1;
        }

        // input is valid
        return possibleTargets.get(userChoice);
    }
    public void displayTurnOutcome(Combatant actor, Action action, List<Combatant> targets,
                                   boolean hasInflictStatusEffectOnTargetThisTurn,
                                   boolean isSmokeBombActive, boolean isSmokeBombExpiringThisTurn) {
        // whose turn is this
        displayMessage(actor.getName() + " --> ", true);
        switch(action.getActionType()) {
            case ATTACK: {
                printAttacking(action.getName());
                printingAttackImpacts(actor, targets,hasInflictStatusEffectOnTargetThisTurn, isSmokeBombActive, isSmokeBombExpiringThisTurn, false);
                break;
            }
            case DEFEND: {
                printAttacking(action.getName());
                displayMessage(actor.getName() + ": ", true);
                displayMessage(" Defence increased by 10!");
                break;
            }
            case SPECIAL_SKILL: {
                printAttacking(action.getName());
                printingAttackImpacts(actor, targets,hasInflictStatusEffectOnTargetThisTurn, isSmokeBombActive, isSmokeBombExpiringThisTurn, false);
                displayMessage("| Cooldown set to " + actor.getSkillCooldown() + " ");
                break;
            }
            case ARCANE_BLAST: { // hits all enemies & increases atk
                printAttacking(action.getName());
                displayMessage("All Enemies: ", true);
                printingAttackImpacts(actor,targets,hasInflictStatusEffectOnTargetThisTurn, isSmokeBombActive, isSmokeBombExpiringThisTurn, true);
                displayMessage("| Cooldown set to " + actor.getSkillCooldown() + " ");
                break;
            }
            case USE_POWER_STONE: {
                if (!(action instanceof UseItem useItemAction)) return;
                printUsingItem(useItemAction.getAssociatedItem().getName());
                displayMessage("--> " + actor.getSpecialSkill().getName() + " triggered --> ", true);
                // call same printing functionality as special skill
                printingAttackImpacts(actor, targets,hasInflictStatusEffectOnTargetThisTurn, isSmokeBombActive, isSmokeBombExpiringThisTurn, false);
                displayMessage("Cooldown unchanged --> " + actor.getSkillCooldown() + " (" +
                        useItemAction.getAssociatedItem().description + ") | " + useItemAction.getAssociatedItem().getName() + " consumed ");
                break;
            }
            case USE_SMOKE_BOMB: {
                if (!(action instanceof UseItem useItemAction)) return;
                printUsingItem(useItemAction.getAssociatedItem().getName());
                // no targets. just need print info line
                displayMessage("Enemy attacks deal 0 damage this turn + next ");
                break;
            }
            case USE_POTION: {
                if (!(action instanceof UseItem useItemAction)) return;
                printUsingItem(useItemAction.getAssociatedItem().getName());
                // print own health increase - yes, hardcoded potion effect to 100
                displayMessage("HP: " + actor.getPreviousHP() + " --> " + actor.getCurrentHP() + " (+100) ");
                break;
            }
        }
    }
    public void displayBackupSpawnedInfo(List<Combatant> backupEnemies) {
        displayMessage("All initial enemies eliminated --> ");
        displayMessage("Backup Spawn triggered! ", true);
        for (int i = 0; i < backupEnemies.size(); i++) {
            if (i > 0) displayMessage("+ "); // not first enemy spawned
            displayMessage(backupEnemies.get(i).getName() + " (HP:" +
                    backupEnemies.get(i).getCurrentHP() + ") ");
        }
        displayLineMessage("enter simultaneously");
    }
    public void displayEndOfRoundInfo(BattleContext battleContext, int roundNum, int specialSkillsCooldown) {
        displayMessage("End of Round " + roundNum + ": ", true);

        // display players & enemies info
        printCombatantsCondition(battleContext.getPlayers(), battleContext.getEnemies());

        // display item info
        printItems(battleContext.getItems());

        // display item effect turn lasting info
        if (battleContext.getIsSmokeBombActive()) {
            int durationLeft = 0;
            // find smoke bomb & get its duration left
            for (Item eachItem: battleContext.getActiveItems()) {
                if (eachItem.itemType == ITEM_TYPE.SMOKE_BOMB &&
                        durationLeft < eachItem.currentDurationLeft) { // eg 2 smoke bombs used, use the one with longest duration left
                    durationLeft = eachItem.currentDurationLeft;
                }
            }
            displayMessage("| Effect: " + durationLeft + " turn remaining ");
        }

        // display cooldown info
        displayLineMessage("| Special Skills Cooldown: " + specialSkillsCooldown + " rounds ");

        // print round divider
        displayLineMessage("----------------------------------------", true);
    }
    private void printAttacking(String attackName) {
        displayMessage(attackName + " --> ", true);
    }
    // used for impact of ATTACKS
    private void printingAttackImpacts(Combatant actor, List<Combatant> targets,
                                       boolean hasInflictStatusEffectOnTargetThisTurn,
                                       boolean isSmokeBombActive, boolean isSmokeBombExpiringThisTurn,
                                       boolean isArcaneBlast) {
        int oldAtk = actor.getPreviousAttack();
        for (int i = 0; i < targets.size(); i++) {
            if (i > 0) displayMessage("| "); // if not first target hit
            if (isSmokeBombActive) {
                // attack doesn't even hit
                printTargetImpactSmokeBomb(targets.get(i).getName(), targets.get(i).getCurrentHP(), (targets.size() == 1), isSmokeBombExpiringThisTurn);
            }
            else if (hasInflictStatusEffectOnTargetThisTurn) {
                if (targets.get(i).getAfflictedStatusEffects().size() <= 0) {

                    printTargetImpact(targets.get(i).getName(), targets.get(i).getPreviousHP(), targets.get(i).getCurrentHP(),
                            actor.getAttack(), targets.get(i).getDefence(), (targets.size() == 1),
                            null);
                }
                else {
                    printTargetImpact(targets.get(i).getName(), targets.get(i).getPreviousHP(), targets.get(i).getCurrentHP(),
                            actor.getAttack(), targets.get(i).getDefence(), (targets.size() == 1),
                            targets.get(i).getAfflictedStatusEffects().get(targets.get(i).getAfflictedStatusEffects().size() - 1)); // last status effect taken as most recently inflicted
                }
            }
            else
                printTargetImpact(targets.get(i).getName(), targets.get(i).getPreviousHP(), targets.get(i).getCurrentHP(),
                        actor.getAttack(), targets.get(i).getDefence(), (targets.size() == 1));

            // if arcane blast, print atk increase info
            if (isArcaneBlast && !targets.get(i).isAlive()) {
                int newAtk = oldAtk + 10; // yes hardcoded 10 effect
                if (newAtk > actor.getAttack()) newAtk = actor.getAttack();
                displayMessage("| ATK: " + oldAtk + " --> " + newAtk +
                        " (+10 per Arcane Blast kill) ");
                oldAtk = newAtk;
            }
        }
    }
    private void printTargetImpact(String targetName, int oldHP, int currentHP,
                                   int attackerAtk, int targetDef, boolean isOnlyTarget) {
        displayMessage(targetName + ": ", true);
        int damageDealt = attackerAtk - targetDef;
        if (damageDealt < 0) damageDealt = 0;
        displayMessage("HP: " + oldHP + " --> " + currentHP + " ");
        // if target eliminated, print extra tag
        if (currentHP <= 0) displayMessage("X ELIMINATED ");
        displayMessage("(dmg: " + attackerAtk + "-" + targetDef + "=" + damageDealt + ") ");
        // if not the only target, print extra to acknowledge that this target is still alive
        if (currentHP > 0 && !isOnlyTarget)
            displayMessage("| " + targetName + " survives ");
    }
    // if inflicts status effect on target this turn
    private void printTargetImpact(String targetName, int oldHP, int currentHP,
                                   int attackerAtk, int targetDef, boolean isOnlyTarget,
                                   StatusEffect statusEffectAfflicted) {
        printTargetImpact(targetName, oldHP, currentHP, attackerAtk, targetDef, isOnlyTarget);
        displayMessage("| " + targetName + " ");
        if (statusEffectAfflicted == null) return;
        if (statusEffectAfflicted.statusEffectType == STATUS_EFFECT_TYPE.STUNNED) {
            displayMessage("STUNNED ");
        }
        // print duration
        displayMessage("(" + statusEffectAfflicted.maxDuration + " turns) ");
    }
    // if attack misses due to smoke bomb
    private void printTargetImpactSmokeBomb(String targetName, int currentHP, boolean isOnlyTarget, boolean isExpiring) {
        displayMessage(targetName + ": ", true);
        displayMessage("0 damage (Smoke Bomb ");
        if (isExpiring) displayMessage("last use) | Smoke Bomb effect expires ");
        else displayMessage("active) ");
        displayMessage("| " + targetName + " HP: " + currentHP + " ");
        // if not the only target, print extra to acknowledge that this target is still alive
        if (currentHP > 0 && !isOnlyTarget)
            displayMessage("| " + targetName + " survives ");
    }
    private void printUsingItem(String itemName) {
        displayMessage( "Item --> " + itemName + " used: ", true);
    }
    private void printCombatantsCondition(List<Combatant> players, List<Combatant> enemies) {
        // print players
        for (int i = 0; i < players.size(); i++) {
            if (i > 0) displayMessage("| "); // if not first player
            printPlayerCondition(players.get(i).getName(), players.get(i).getCurrentHP(), players.get(i).getBaseHP());
            if (players.get(i).isStunned()) printStunnedModified();
        }
        // print enemies
        for (Combatant eachEnemy: enemies) {
            printEnemyCondition(eachEnemy.getName(), eachEnemy.getCurrentHP());
            if (eachEnemy.isStunned()) printStunnedModified();
        }
    }
    private void printPlayerCondition(String name, int currentHP, int baseHP) {
        displayMessage(name + " HP: " + currentHP + "/" + baseHP + " ");
    }
    private void printEnemyCondition(String name, int currentHP) {
        displayMessage("| " + name + " HP: " + currentHP + " ");
    }
    private void printStunnedModified() {
        displayMessage("[STUNNED] ");
    }
    private void printItems(List<Item> items) {
        Map<String, Integer> nameCountMap = new HashMap<>();
        Map<String, Boolean> nameConsumedThisTurnMap = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            // put into a dict of item: quantity then individually print
            if (!(nameCountMap.containsKey(items.get(i).getName()))) {
                int quantity = 0;
                if (!(items.get(i).getIsUsed())) quantity = 1; // not yet used so should be 1
                nameCountMap.put(items.get(i).getName(), quantity);

                // check if item was consumed this turn
                if (items.get(i).getIsUsed() &&
                        items.get(i).currentDurationLeft == items.get(i).maxDuration) {
                    nameConsumedThisTurnMap.put(items.get(i).getName(), true);
                }
                else nameConsumedThisTurnMap.put(items.get(i).getName(), false);
            }
            else { // add to count for said item
                nameCountMap.replace(items.get(i).getName(), nameCountMap.get(items.get(i).getName()) + 1);
                // check if item was consumed this turn
                if (items.get(i).getIsUsed() &&
                        items.get(i).currentDurationLeft == items.get(i).maxDuration) {
                    nameConsumedThisTurnMap.replace(items.get(i).getName(), true);
                } // no replacing else since don't want to override and old true with new false
            }
        }

        // print all items
        for (Map.Entry<String, Integer> entry: nameCountMap.entrySet()) {
            printItem(entry.getKey(), entry.getValue(), nameConsumedThisTurnMap.get(entry.getKey()));
        }
    }
    private void printItem(String itemName, int itemCount, boolean isConsumedThisTurn) {
        displayMessage("| " + itemName + ": " + itemCount + " ");
        if (isConsumedThisTurn)
            displayMessage("<-- consumed ");
    }
    public void printStatusEffectExpires(String statusEffectName) {
        displayMessage("| " + statusEffectName + " expires ");
    }
    public void printSpecialSkillCooldown(int newCooldown) {
        displayMessage("| Cooldown set to " + newCooldown + " ");
    }

   
    }


