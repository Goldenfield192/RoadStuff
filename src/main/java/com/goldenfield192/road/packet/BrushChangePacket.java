package com.goldenfield192.road.packet;

import cam72cam.mod.entity.Player;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.net.Packet;
import cam72cam.mod.serialization.TagCompound;
import cam72cam.mod.serialization.TagField;

public class BrushChangePacket extends Packet {
    @TagField("brush")
    private String brushName;

    public BrushChangePacket(String brushName) {
        this.brushName = brushName;
    }

    public BrushChangePacket() {

    }

    @Override
    protected void handle() {
        ItemStack stack;
        if(this.brushName != null){
            stack = getPlayer().getHeldItem(Player.Hand.PRIMARY);
            stack.setTagCompound(new TagCompound().setString("brush", this.brushName));
            getPlayer().setHeldItem(Player.Hand.PRIMARY,stack);
        }
    }
}
