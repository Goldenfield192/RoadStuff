package com.goldenfield192.road.items;

import cam72cam.mod.entity.Player;
import cam72cam.mod.item.ClickResult;
import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.CustomItem;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.util.Facing;
import cam72cam.mod.world.World;
import com.goldenfield192.road.registry.RegisterBlocks;
import com.goldenfield192.road.registry.RegisterTabs;
import com.goldenfield192.road.tiles.TileStandardRoad;

import java.util.Collections;
import java.util.List;

public class StandardRoadItem extends CustomItem {
    public StandardRoadItem(String modID, String name) {
        super(modID, name);
    }

    @Override
    public List<CreativeTab> getCreativeTabs() {
        return Collections.singletonList(RegisterTabs.ROAD_MAIN);
    }

    @Override
    public ClickResult onClickBlock(Player player, World world, Vec3i pos, Player.Hand hand, Facing facing, Vec3d inBlockPos) {
        TileStandardRoad sre = world.getBlockEntity(pos, TileStandardRoad.class);
        if (sre != null && sre.getHeight() < 16) {
            return ClickResult.ACCEPTED;
        }
        Vec3i placement = pos.offset(facing);
        world.setBlock(placement, RegisterBlocks.STANDARD_ROAD_BLOCK);
        return ClickResult.ACCEPTED;
    }
}
