package com.goldenfield192.road.registry;

import com.goldenfield192.road.Main;
import com.goldenfield192.road.items.BikeItem;
import com.goldenfield192.road.items.BrushItem;
import com.goldenfield192.road.items.StandardRoadItem;

public class RegisterItems {
    public static final StandardRoadItem STANDARD_ROAD_ITEM = new StandardRoadItem(Main.MODID, "standard_road_item");
    public static final BikeItem BIKE_ITEM = new BikeItem(Main.MODID, "bike_spawn_item");
    public static final BrushItem BRUSH_ITEM = new BrushItem(Main.MODID, "brush_item");

    public static void register(){}
}
