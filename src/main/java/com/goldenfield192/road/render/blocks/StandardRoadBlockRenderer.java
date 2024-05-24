package com.goldenfield192.road.render.blocks;

import cam72cam.mod.model.obj.OBJModel;
import cam72cam.mod.render.StandardModel;
import cam72cam.mod.render.obj.OBJRender;
import cam72cam.mod.render.opengl.BlendMode;
import cam72cam.mod.render.opengl.RenderState;
import cam72cam.mod.render.opengl.Texture;
import cam72cam.mod.render.opengl.VBO;
import cam72cam.mod.resource.Identifier;
import com.goldenfield192.road.Main;
import com.goldenfield192.road.render.common.ColoredFaces;
import com.goldenfield192.road.tiles.TileStandardRoad;
import util.Matrix4;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class StandardRoadBlockRenderer {
    public static final LinkedHashMap<Integer, StandardModel> paints = new LinkedHashMap<>();
    public static final LinkedHashMap<Integer, VBO> blocks = new LinkedHashMap<>();

    public static final OBJModel model;

    static {
        try {
            model = new OBJModel(new Identifier(Main.MODID, "models/block.obj"), 0);
            paints.put(0, new StandardModel());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static StandardModel render(TileStandardRoad sre) {
        return new StandardModel().addCustom((state, partialTicks) -> BlockRenderer(sre, state));
    }

    private static void BlockRenderer(TileStandardRoad sre, RenderState state) {
        RenderState copy1 = state.clone();
        //缓存基础状态
        if (!blocks.containsKey(sre.getHeight())) {
            LinkedList<String> groups = new LinkedList<>();
            groups.add("bottom");
            Matrix4 matrix4 = new Matrix4();
            for (int i = 1; i <= sre.getHeight(); i++) {
                groups.add("side" + i);
            }
            matrix4.translate(0, 0.0625 * sre.getHeight() - 1, 0);
            OBJRender.Builder builder = model.binder().builder();
            builder.draw(groups);
            builder.draw(Collections.singletonList("top"), matrix4);
            blocks.put(sre.getHeight(), builder.build());
        }
        try (VBO.Binding vbo = blocks.get(sre.getHeight()).bind(copy1)) {
            vbo.draw();
        }

        float skyLight = sre.getWorld().getSkyLightLevel(sre.getPos());
        state.translate(0, 0.0625 * sre.getHeight() + 0.002, 0)
                .texture(Texture.wrap(ColoredFaces.whiteTex))
                //TODO 光照按照内部光照等级优化
//                .lightmap(1.0f, skyLight)
                .depth_test(true)
                .depth_mask(false)
                .alpha_test(false)
                .blend(new BlendMode(BlendMode.GL_SRC_ALPHA, BlendMode.GL_ONE_MINUS_SRC_ALPHA));
        state.cull_face(true);
        if (!paints.containsKey(sre.getColor().hashCode())) {
            update(sre.getColor());
        }
        paints.getOrDefault(sre.getColor().hashCode(), new StandardModel()).render(state);
    }

    public static void update(HashMap<Integer, String> map) {
        //!!! DirectDraw >= Tessellator
        StandardModel model1 = new StandardModel().addCustom((state, partialTicks) ->
                map.forEach((position, colorValue) -> {
                    RenderState copy = state.clone();
                    int x = position >> 4;
                    int z = position % 16;
                    if(colorValue != null){
                        float[] rgb = ColoredFaces.getColor(colorValue);
                        copy.translate(x * 0.0625, 0, z * 0.0625)
                                .color(rgb[0], rgb[1], rgb[2], 1);
                        ColoredFaces.base.draw(copy);
                    }
                })
        );
        paints.put(map.hashCode(), model1);
    }

    @Deprecated
    private static String colorToKd(int colorValue) {
        return (colorValue >> 16) + " " + (colorValue >> 8 % 256) + " " + (colorValue % 256);
    }
}
