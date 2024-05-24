package com.goldenfield192.road.render.items;

import cam72cam.mod.entity.Player;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.render.GlobalRender;
import cam72cam.mod.render.opengl.RenderState;
import cam72cam.mod.render.opengl.VBO;
import com.goldenfield192.road.render.common.ColoredFaces;

public class BrushGlobalRenderer {
    public static void renderMouseover(Player player, ItemStack stack, Vec3i pos, Vec3d hit, RenderState state, float partialTicks) {
        pos = pos.up();
        Vec3d cameraPos = GlobalRender.getCameraPos(partialTicks);
        Vec3d inBlockPos = new Vec3d((int)(hit.x*16)/16d,hit.y,(int)(hit.z*16)/16d);
        Vec3d offset = inBlockPos.subtract(cameraPos).add(new Vec3d(- 1/16d,0.004,0));
        state.translate(offset);
        try(VBO.Binding binding = ColoredFaces.pixel.bind(state)){
            binding.draw();
        }
    }
}
