package com.goldenfield192.road.entity;

import cam72cam.mod.entity.Player;
import cam72cam.mod.item.ClickResult;

public class BikeEntity extends BaseVehicleEntity{
    public BikeEntity() {
    }

    @Override
    public ClickResult onClick(Player player, Player.Hand hand) {
        this.addPassenger(player);
        return ClickResult.ACCEPTED;
    }
}
