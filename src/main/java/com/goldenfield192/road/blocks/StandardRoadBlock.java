package com.goldenfield192.road.blocks;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.block.BlockTypeEntity;
import com.goldenfield192.road.tiles.TileStandardRoad;

public class StandardRoadBlock extends BlockTypeEntity {
    public StandardRoadBlock(String modID, String name) {
        super(modID, name);
    }

    @Override
    protected BlockEntity constructBlockEntity() {
        return new TileStandardRoad();
    }
}
