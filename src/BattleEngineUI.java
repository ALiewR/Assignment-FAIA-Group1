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
    public Action selectAction(Combatant player) {
        int userChoice = -1;

        while (userChoice < 0 || userChoice >= player.availableActions.size()) {
            // display info for user to know
            displayLineMessage(player.name + " can perform one of the following actions...");
            for (int i = 0; i < player.availableActions.size(); i++) {
                displayLineMessage(i + ": " + player.availableActions.get(i).name + " - " + player.availableActions.get(i).description);
            }
            displayMessage("Select action you wish to take: ", true);
            userChoice = getUIInt();
        }

        displayLineMessage(player.availableActions.get(userChoice).name + " can be used on "
                + player.availableActions.get(userChoice).numOfTargets + " target(s).");

        // input is valid
        return player.availableActions.get(userChoice);
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
                displayLineMessage(i + ": " + possibleTargets.get(i).name);
            }
            displayMessage("Select whom you wish to target: ", true);
            userChoice = getUIInt();
        }

        // input is valid
        return possibleTargets.get(userChoice);
    }
    public void displayTurnOutcome(Combatant actor, Action action, List<Combatant> targets,
                                   boolean hasInflictStatusEffectOnTargetThisTurn,
                                   boolean isSmokeBombActive, boolean isSmokeBombExpiringThisTurn) {
        // TODO: adjust based on how to get each info needed from each class
        // whose turn is this
        displayMessage(actor.name + " --> ", true);
        // TODO: adjust how to get action type based on how its implemented
        switch(action.actionType) {
            case ATTACK:
            case SPECIAL_SKILL: { // TODO: print info of ATK bonus if ArcaneBlast used
                printAttacking(action.name);
                printingAttackImpacts(actor, targets,hasInflictStatusEffectOnTargetThisTurn, isSmokeBombActive, isSmokeBombExpiringThisTurn);
                break;
            }
            case USE_POWER_STONE: {
                if (!(action instanceof UseItem useItemAction)) return;
                printUsingItem(useItemAction.associatedItem.name);
                displayMessage("--> " + actor.getSpecialSkill().name + " triggered --> ", true);
                // call same printing functionality as special skill
                printingAttackImpacts(actor, targets,hasInflictStatusEffectOnTargetThisTurn, isSmokeBombActive, isSmokeBombExpiringThisTurn);
                displayMessage("Cooldown unchanged --> " + actor.currentSkillMaxCooldown + " (" +
                        useItemAction.associatedItem.description + ") | " + useItemAction.associatedItem.name + " consumed ");
                break;
            }
            case USE_SMOKE_BOMB: {
                if (!(action instanceof UseItem useItemAction)) return;
                printUsingItem(useItemAction.associatedItem.name);
                // no targets. just need print info line
                displayMessage("Enemy attacks deal 0 damage this turn + next ");
                break;
            }
            case USE_POTION: {
                if (!(action instanceof UseItem useItemAction)) return;
                printUsingItem(useItemAction.associatedItem.name);
                // print own health increase - yes, hardcoded potion effect to 100
                displayMessage("HP: " + actor.oldHP + " --> " + actor.currentHP + " (+100) ");
                break;
            }
        }
    }
    public void displayBackupSpawnedInfo(List<Combatant> backupEnemies) {
        displayMessage("All initial enemies eliminated --> ");
        displayMessage("Backup Spawn triggered! ", true);
        for (int i = 0; i < backupEnemies.size(); i++) {
            if (i > 0) displayMessage("+ "); // not first enemy spawned
            // TODO: adjust according to accessibility in Combatant class
            displayMessage(backupEnemies.get(i).name + " (HP:" +
                    backupEnemies.get(i).currentHP + ") ");
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
                                       boolean isSmokeBombActive, boolean isSmokeBombExpiringThisTurn) {
        for (int i = 0; i < targets.size(); i++) {
            if (i > 0) displayMessage("| "); // if not first target hit
            if (isSmokeBombActive) {
                // attack doesn't even hit
                printTargetImpactSmokeBomb(targets.get(i).name, targets.get(i).currentHP, (targets.size() == 1), isSmokeBombExpiringThisTurn);
            }
            else if (hasInflictStatusEffectOnTargetThisTurn)
                if (targets.get(i).afflictedStatusEffects.size() <= 0) {

                    printTargetImpact(targets.get(i).name, targets.get(i).oldHP, targets.get(i).currentHP,
                            actor.atk, targets.get(i).defence, (targets.size() == 1),
                            null);
                }
                else {
                    printTargetImpact(targets.get(i).name, targets.get(i).oldHP, targets.get(i).currentHP,
                            actor.atk, targets.get(i).defence, (targets.size() == 1),
                            targets.get(i).afflictedStatusEffects.get(targets.get(i).afflictedStatusEffects.size() - 1)); // last status effect taken as most recently inflicted
                }
            else
                printTargetImpact(targets.get(i).name, targets.get(i).oldHP, targets.get(i).currentHP,
                        actor.atk, targets.get(i).defence, (targets.size() == 1));
        }
    }
    private void printTargetImpact(String targetName, int oldHP, int currentHP,
                                   int attackerAtk, int targetDef, boolean isOnlyTarget) {
        displayMessage(targetName + ": ", true);
        int damageDealt = attackerAtk - targetDef;
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
            // TODO: adjust according to accessibility in Combatant class
            printPlayerCondition(players.get(i).name, players.get(i).currentHP, players.get(i).baseHP);
            if (players.get(i).isStunned()) printStunnedModified();
        }
        // print enemies
        for (Combatant eachEnemy: enemies) {
            // TODO: adjust according to accessibility in Combatant class
            printEnemyCondition(eachEnemy.name, eachEnemy.currentHP);
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
            // TODO: adjust according to accessibility in Item class
            if (!(nameCountMap.containsKey(items.get(i).name))) {
                int quantity = 0;
                if (!(items.get(i).getIsUsed())) quantity = 1; // not yet used so should be 1
                nameCountMap.put(items.get(i).name, quantity);

                // check if item was consumed this turn
                if (items.get(i).getIsUsed() &&
                        items.get(i).currentDurationLeft == items.get(i).maxDuration) {
                    nameConsumedThisTurnMap.put(items.get(i).name, true);
                }
                else nameConsumedThisTurnMap.put(items.get(i).name, false);
            }
            else { // add to count for said item
                nameCountMap.replace(items.get(i).name, nameCountMap.get(items.get(i).name) + 1);
                // check if item was consumed this turn
                if (items.get(i).getIsUsed() &&
                        items.get(i).currentDurationLeft == items.get(i).maxDuration) {
                    nameConsumedThisTurnMap.replace(items.get(i).name, true);
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
