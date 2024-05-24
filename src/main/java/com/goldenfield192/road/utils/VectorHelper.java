package com.goldenfield192.road.utils;

import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.util.Facing;

public class VectorHelper {
    public static Facing fromVec3d(Vec3d vec3d) {
        return vec3d.x == 0 ?
                vec3d.z > 0 ? Facing.SOUTH : Facing.NORTH
                : vec3d.x > 0 ? Facing.EAST : Facing.WEST;
    }

    public static Facing fromVec3i(Vec3i vec3i) {
        return fromVec3d(new Vec3d(vec3i.x, 0, vec3i.z));
    }

    public static Facing fromDegree(double yawHead) {
        yawHead = ((-yawHead % 360) + 360) % 360;
        int yaw = ((int) ((yawHead + 90 / (90 * 2)) * 90)) / 90 * 90 / 90;
        if (yaw >= 315 || yaw <= 45)
            return Facing.SOUTH;
        if (yaw >= 225)
            return Facing.WEST;
        if (yaw <= 135)
            return Facing.EAST;
        return Facing.NORTH;
    }
}

