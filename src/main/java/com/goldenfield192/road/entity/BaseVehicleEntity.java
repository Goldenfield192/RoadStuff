package com.goldenfield192.road.entity;

import cam72cam.mod.entity.CustomEntity;
import cam72cam.mod.entity.Entity;
import cam72cam.mod.entity.Player;
import cam72cam.mod.entity.boundingbox.IBoundingBox;
import cam72cam.mod.entity.custom.IClickable;
import cam72cam.mod.entity.custom.ICollision;
import cam72cam.mod.entity.custom.IRidable;
import cam72cam.mod.item.ClickResult;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.model.obj.OBJModel;
import com.goldenfield192.road.render.blocks.StandardRoadBlockRenderer;

public class BaseVehicleEntity extends CustomEntity implements IClickable, ICollision, IRidable {
    protected float minRotateRadius;
    public static OBJModel vehicleModel;
    protected int passengerCount;

    static {
        try {
            vehicleModel = StandardRoadBlockRenderer.model;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClickResult onClick(Player player, Player.Hand hand) {
        return null;
    }

    @Override
    public IBoundingBox getCollision() {
        return IBoundingBox.BLOCK;
    }

    @Override
    public boolean canFitPassenger(Entity passenger) {
        return true;
    }

    @Override
    public boolean shouldRiderSit(Entity passenger) {
        return false;
    }

    @Override
    public Vec3d getMountOffset(Entity passenger, Vec3d offset) {
        return Vec3d.ZERO;
    }

    @Override
    public Vec3d onPassengerUpdate(Entity passenger, Vec3d offset) {
        return null;
    }

    @Override
    public Vec3d onDismountPassenger(Entity passenger, Vec3d offset) {
        return Vec3d.ZERO;
    }
}
