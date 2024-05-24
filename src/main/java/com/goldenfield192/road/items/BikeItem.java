package com.goldenfield192.road.items;

import cam72cam.mod.entity.EntityRegistry;
import cam72cam.mod.entity.Player;
import cam72cam.mod.item.ClickResult;
import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.CustomItem;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.util.Facing;
import cam72cam.mod.world.World;
import com.goldenfield192.road.entity.BikeEntity;
import com.goldenfield192.road.registry.RegisterTabs;

import java.util.Collections;
import java.util.List;

public class BikeItem extends CustomItem {
    public BikeItem(String modID, String name) {
        super(modID, name);
    }

    @Override
    public List<CreativeTab> getCreativeTabs() {
        return Collections.singletonList(RegisterTabs.ROAD_MAIN);
    }

    @Override
    public ClickResult onClickBlock(Player player, World world, Vec3i pos, Player.Hand hand, Facing facing, Vec3d inBlockPos) {
        BikeEntity bike = (BikeEntity) EntityRegistry.create(world, BikeEntity.class);
        bike.setPosition(new Vec3d(pos));
        world.spawnEntity(bike);
        return ClickResult.ACCEPTED;
    }
}
