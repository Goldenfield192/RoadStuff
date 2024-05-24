package com.goldenfield192.road;

import cam72cam.mod.ModCore;
import cam72cam.mod.ModEvent;
import cam72cam.mod.entity.EntityRegistry;
import cam72cam.mod.net.Packet;
import cam72cam.mod.net.PacketDirection;
import cam72cam.mod.render.BlockRender;
import cam72cam.mod.render.EntityRenderer;
import cam72cam.mod.render.GlobalRender;
import cam72cam.mod.render.ItemRender;
import cam72cam.mod.resource.Identifier;
import com.goldenfield192.road.entity.BikeEntity;
import com.goldenfield192.road.packet.BrushChangePacket;
import com.goldenfield192.road.registry.*;
import com.goldenfield192.road.render.blocks.StandardRoadBlockRenderer;
import com.goldenfield192.road.render.entity.VehicleRenderer;
import com.goldenfield192.road.render.items.BrushGlobalRenderer;
import com.goldenfield192.road.tiles.TileStandardRoad;

import java.util.Random;

@net.minecraftforge.fml.common.Mod(Main.MODID)
@net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = Main.MODID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD)
public class Main extends ModCore.Mod {
    public static final String MODID = "gfsroad";
    public static final Random RANDOM = new Random();

    static {
        try {
            ModCore.register(new Main());
        } catch (Exception e) {
            throw new RuntimeException("Could not load mod " + MODID, e);
        }
    }

    @Override
    public String modID() {
        return Main.MODID;
    }

    @Override
    public void commonEvent(ModEvent event) {
        switch (event){
            case CONSTRUCT:
                RegisterBlocks.register();
                RegisterTabs.register();
                RegisterItems.register();
                RegisterGUIs.register();

                EntityRegistry.register(this, BikeEntity::new, 512);

                Packet.register(BrushChangePacket::new, PacketDirection.ClientToServer);
                break;
            case INITIALIZE:
                BrushRegister instance = new BrushRegister();
                instance.register();
                break;
            case FINALIZE:
        }
    }

    @Override
    public void clientEvent(ModEvent event) {
        switch (event){
            case CONSTRUCT:
                BlockRender.register(RegisterBlocks.STANDARD_ROAD_BLOCK, StandardRoadBlockRenderer::render, TileStandardRoad.class);

                ItemRender.register(RegisterItems.STANDARD_ROAD_ITEM, new Identifier(Main.MODID,"items/tex1"));
                ItemRender.register(RegisterItems.BIKE_ITEM, new Identifier(Main.MODID,"items/tex1"));
                ItemRender.register(RegisterItems.BRUSH_ITEM, new Identifier(Main.MODID, "items/brush"));

                EntityRenderer.register(BikeEntity.class, new  VehicleRenderer());
                break;
            case SETUP:
                GlobalRender.registerItemMouseover(RegisterItems.BRUSH_ITEM, BrushGlobalRenderer::renderMouseover);
        }
    }

    @Override
    public void serverEvent(ModEvent event) {

    }
}
