package com.hiphurra.myhorse.enums;

public enum LanguageString {


    ComeHere(MessageType.INFO),
    YouPutAChestOnHorse(MessageType.INFO),
    HorseHasNoChest(MessageType.INFO),
    ChestHasBeenRemoved(MessageType.INFO),
    ChestHasBeenRemovedFailure(MessageType.SEVERE),
    YouClaimedAHorse(MessageType.SUCCESS),
    NotAHorse(MessageType.SEVERE),
    YouSelectedHorse(MessageType.INFO),
    AlreadyOwnThatHorse(MessageType.INFO),
    YouCannotClaimThisHorse(MessageType.SEVERE),
    YouMountedOwnedHorse(MessageType.INFO),
    CannotUseLockedHorse(MessageType.SEVERE),
    HorseLocked(MessageType.INFO),
    NoHorseWithSuchName(MessageType.SEVERE),
    HorseUnlocked(MessageType.INFO),
    InvalidCommand(MessageType.SEVERE),
    UseGotoCommand(MessageType.INFO),
    UseGotoCommandSuccess(MessageType.SUCCESS),
    AlreadyHorseFriend(MessageType.SEVERE),
    NotHorseFriend(MessageType.SEVERE),
    YouAddedFriendToHorse(MessageType.SUCCESS),
    YouRemovedFriendToHorse(MessageType.SUCCESS),
    PlayerNeverPlayedOnTheServer(MessageType.SEVERE),
    CannotUseNameTags(MessageType.SEVERE),
    UseCommandToNameYourHorse(MessageType.INFO),
    SetHorseName(MessageType.SUCCESS),
    NoPermissionForCommand(MessageType.SEVERE),
    YourOwnedHorsesList(MessageType.INFO),
    YouSetHorseForSale(MessageType.SUCCESS),
    InfoCancelHorseSale(MessageType.INFO),
    NoPriceOnHorse(MessageType.SEVERE),
    YouCancelledHorseForSale(MessageType.INFO),
    YouDoNotHaveEnoughMoneyToBuyHorse(MessageType.SEVERE),
    AreYouSureYouWantToBuyHorse(MessageType.INFO),
    PlayerBoughtYourHorse(MessageType.SUCCESS),
    YouBoughtHorse(MessageType.SUCCESS),
    CannotBuyOwnHorse(MessageType.SEVERE),
    YourHorseDied(MessageType.SEVERE),
    AlreadyHasHorseWithThatName(MessageType.SEVERE),
    NoHorseSelected(MessageType.SEVERE),
    InvalidHorseName(MessageType.SEVERE),
    YouTrustedPlayerOnAllHorses(MessageType.SUCCESS),
    YouUntrustedPlayerOnAllHorses(MessageType.SUCCESS),
    NotHorseFriendOnAnyHorse(MessageType.SEVERE),
    HorseNotAllowedInWorld(MessageType.SEVERE);



    private final MessageType messageType;

    LanguageString(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
