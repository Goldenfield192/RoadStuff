package com.goldenfield192.road.gui;

import cam72cam.mod.gui.screen.IScreen;
import cam72cam.mod.gui.screen.IScreenBuilder;
import cam72cam.mod.gui.screen.TextField;
import cam72cam.mod.render.opengl.RenderState;
import com.goldenfield192.road.packet.BrushChangePacket;
import com.goldenfield192.road.registry.BrushRegister;

import java.util.ArrayList;
import java.util.List;

public class BrushGUi implements IScreen {
    private TextField colorInput;
    private List<String> colors = new ArrayList<>();

    @Override
    public void init(IScreenBuilder screen) {
        this.colorInput = new TextField(screen,-100,25, 200,20);
        this.colorInput.setFocused(true);
    }

    @Override
    public void onEnterKey(IScreenBuilder builder) {
        builder.close();
    }

    @Override
    public void onClose() {
        if(this.colorInput.getText().isEmpty() || BrushRegister.getBrush(this.colorInput.getText()) == null) {
            return;
        }

        new BrushChangePacket(this.colorInput.getText()).sendToServer();
    }

    @Override
    public void draw(IScreenBuilder builder, RenderState state) {
        IScreen.super.draw(builder, state);
    }
}