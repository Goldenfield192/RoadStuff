package com.goldenfield192.road.items;

import cam72cam.mod.entity.Player;
import cam72cam.mod.entity.sync.TagSync;
import cam72cam.mod.item.ClickResult;
import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.CustomItem;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.serialization.TagField;
import cam72cam.mod.util.Facing;
import cam72cam.mod.world.World;
import com.goldenfield192.road.registry.RegisterGUIs;
import com.goldenfield192.road.registry.RegisterTabs;

import java.util.Collections;
import java.util.List;

public class BrushItem extends CustomItem {
    @TagSync
    @TagField("color")
    public String color = "00/00/00";

    @TagSync
    @TagField("brush")
    public String brush = "default";

    public BrushItem(String modID, String name) {
        super(modID, name);
    }

    @Override
    public List<CreativeTab> getCreativeTabs() {
        return Collections.singletonList(RegisterTabs.ROAD_MAIN);
    }

    @Override
    public ClickResult onClickBlock(Player player, World world, Vec3i pos, Player.Hand hand, Facing facing, Vec3d inBlockPos) {
//        TileStandardRoad tileStandardRoad = world.getBlockEntity(pos, TileStandardRoad.class);
//        System.out.println("success");
//        if(tileStandardRoad != null){
//            tileStandardRoad.setColor((int)(inBlockPos.x*16),(int)(inBlockPos.z*16),0xFFFFFF);
//            System.out.println("success");
//        }
        return ClickResult.ACCEPTED;
    }

    @Override
    public void onClickAir(Player player, World world, Player.Hand hand) {
        if (world.isClient && hand == Player.Hand.PRIMARY) {
            RegisterGUIs.BRUSH.open(player);
        }
    }

    @Override
    public int getStackSize() {
        return 1;
    }
}
