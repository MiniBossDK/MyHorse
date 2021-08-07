package com.hiphurra.myhorse.enums;

public enum PermissionNode {
    HELP("myhorse.help"),
    INFO("myhorse.info"),
    KILL("myhorse.kill"),
    SET_CHEST("myhorse.setchest"),
    LIST("myhorse.list"),
    SET_OWNER("myhorse.setowner"),
    GOTO("myhorse.goto"),
    COMEHERE("myhorse.comehere"),
    GOAWAY("myhorse.goaway"),
    BUY("myhorse.buy"),
    SELL("myhorse.sell"),
    CLAIM("myhorse.claim"),
    SELECT("myhorse.select"),
    NAME("myhorse.name"),
    LOCK("myhorse.lock"),
    UNLOCK("myhorse.unlock"),
    ADDFRIEND("myhorse.addfriend"),
    REMOVEFRIEND("myhorse.removefriend"),
    CHEST("myhorse.chest"),
    REMOVE_CHEST("myhorse.removechest"),
    UPDATES("myhorse.updates"),
    ADMIN("myhorse.admin"),
    RELOAD("myhorse.reload"),
    BYPASS_CHEST("myhorse.bypass.chest"),
    BYPASS_LEAD("myhorse.bypass.lead"),
    BYPASS_MOUNT("myhorse.bypass.mount"),
    SPAWN("myhorse.spawn"),
    STABLE("myhorse.stable");

    private final String node;

    PermissionNode(String node) {
        this.node = node;
    }

    public String getNode() {
        return node;
    }
}
