package com.goldenfield192.road.registry;

import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.ItemStack;

public class RegisterTabs {
    public static final CreativeTab ROAD_MAIN = new CreativeTab("ire.main",() -> new ItemStack(RegisterItems.STANDARD_ROAD_ITEM,1));

    public static void register(){}
}
