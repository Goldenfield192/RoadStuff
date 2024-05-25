package com.goldenfield192.road.registry;

import cam72cam.mod.ModCore;
import cam72cam.mod.resource.Identifier;
import com.goldenfield192.road.Main;
import com.goldenfield192.road.render.common.ColoredFaces;
import com.google.gson.Gson;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class BrushRegister {
    private static final Identifier registryTable = new Identifier(Main.MODID, "brush/brush.json");
    private static final HashMap<String, Brush> brushMap = new HashMap<>();

    public static final BrushRegister INSTANCE = new BrushRegister();

    public void register(){
        ArrayList<InputStream> list = new ArrayList<>();
        try {
            list = (ArrayList<InputStream>) registryTable.getResourceStreamAll();
        } catch (Exception e) {
            ModCore.catching(e);
        }

        Gson gson = new Gson();
        for (InputStream i : list) {
            String[] fileName = gson.fromJson(new InputStreamReader(i), String[].class);
            for (String str : fileName) {
                Identifier identifier = new Identifier(Main.MODID, "brush/" + str + ".png");
                BufferedImage bufferedImage;
                try{
                     bufferedImage = ImageIO.read(identifier.getResourceStream());
                }catch (Exception e){
                    ModCore.error("Do you have a broken Image? %s can't be loaded!", identifier.toString());
                    ModCore.catching(e);
                    continue;
                }
                if (bufferedImage.getWidth() > 48 || bufferedImage.getHeight() > 48) {
                    ModCore.error("Image bigger than 48*48 :%s, skipped", identifier.toString());
                    continue;
                }
                Brush brush = new Brush(bufferedImage, bufferedImage.getWidth(), bufferedImage.getHeight());
                brush.fromColor(bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(),
                        null, 0, bufferedImage.getWidth()));
                brushMap.put(str, brush);
                ModCore.info("Brush Loaded : %s", str);
            }
        }
    }

    public static Brush getBrush(String str) {
        if(brushMap.containsKey(str)) {
            return brushMap.get(str);
        }
        return brushMap.get("default");
    }

    public class Brush {
        protected BufferedImage image;
        protected int width;
        protected int height;
        protected int xOffset;
        protected int yOffset;
        protected final HashMap<Integer, String> color = new HashMap<>();


        private Brush(BufferedImage image, int width, int height) {
            this.image = image;
            this.width = width;
            this.height = height;

            this.xOffset = this.width % 2 == 1 ? this.width / 2 : this.width / 2 - 1;
            this.yOffset = this.height % 2 == 1 ? this.height / 2 : this.height / 2 - 1;
        }

        public void fromColor(int[] color) {
            int index = 0;
            for (int c : color) {
//                if(c >> 24 != 255) {
//                    this.color.put(index, null);
//                    index++;
//                    continue;
//                }
                String origin = Integer.toHexString(c & 0xFFFFFF).toUpperCase();
                System.out.println(origin);
                if("0".equals(origin)) {
                    this.color.put(index, null);
                } else {
                    this.color.put(index, ColoredFaces.colorSplit(origin));
                }
                index++;
            }
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }

        public int getxOffset() {
            return this.xOffset;
        }

        public int getyOffset() {
            return this.yOffset;
        }

        public HashMap<Integer, String> getColorMap(){
            return this.color;
        }

        @Override
        public String toString() {
            return "Brush{" +
                    "image=" + image +
                    ", width=" + width +
                    ", height=" + height +
                    ", xOffset=" + xOffset +
                    ", yOffset=" + yOffset +
                    ", color=" + color +
                    '}';
        }
    }
}
