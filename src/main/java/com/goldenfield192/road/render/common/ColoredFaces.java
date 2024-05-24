package com.goldenfield192.road.render.common;

import cam72cam.mod.model.obj.OBJModel;
import cam72cam.mod.render.opengl.DirectDraw;
import cam72cam.mod.render.opengl.VBO;
import cam72cam.mod.resource.Identifier;
import com.goldenfield192.road.Main;

import java.util.HashMap;

public class ColoredFaces {
    private static final HashMap<String, float[]> values = new HashMap<>();

    public static final DirectDraw base = new DirectDraw();
    public static final VBO pixel;
    public static final Identifier whiteTex = new Identifier(Main.MODID, "textures/blocks/pixel_white.png");

    static {
        base.vertex(0,0,0.0625).uv(0,1);
        base.vertex(0.0625,0,0.0625).uv(1,1);
        base.vertex(0.0625,0,0).uv(1,0);
        base.vertex(0,0,0).uv(0,0);
        try {
            pixel = new OBJModel(new Identifier(Main.MODID, "models/color.obj"),0).binder().builder().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateColor(String color){
        if(!values.containsKey(color)){
            String[] split = color.split("/");
            System.out.println(color);
            float[] rgb = new float[]{
                    Integer.parseInt(split[0], 16) / 255f,
                    Integer.parseInt(split[1], 16) / 255f,
                    Integer.parseInt(split[2], 16) / 255f};
            values.put(color, rgb);
        }
    }

    public static float[] getColor(String str){
        if(!values.containsKey(str)){
            updateColor(str);
        }
        return values.get(str);
    }

    public static String colorSplit(String origin) {
        return origin.substring(0, 2) + "/" + origin.substring(2, 4) + "/" + origin.substring(4, 6);
    }
}
