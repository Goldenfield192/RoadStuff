package com.goldenfield192.road.registry;

import cam72cam.mod.gui.GuiRegistry;
import cam72cam.mod.resource.Identifier;
import com.goldenfield192.road.Main;
import com.goldenfield192.road.gui.BrushGUi;

public class RegisterGUIs {
    public static final GuiRegistry.GUI BRUSH = GuiRegistry.register(new Identifier(Main.MODID,"brush"), BrushGUi::new);

    public static void register(){}
}
