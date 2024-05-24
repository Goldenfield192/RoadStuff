package com.goldenfield192.road.tiles;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.entity.Player;
import cam72cam.mod.entity.boundingbox.IBoundingBox;
import cam72cam.mod.entity.sync.TagSync;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.serialization.SerializationException;
import cam72cam.mod.serialization.TagCompound;
import cam72cam.mod.serialization.TagField;
import cam72cam.mod.util.Facing;
import com.goldenfield192.road.registry.BrushRegister;
import com.goldenfield192.road.registry.RegisterItems;
import com.goldenfield192.road.serialization.IntegerStringMapMapper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

import static com.goldenfield192.road.utils.VectorHelper.fromDegree;

public class TileStandardRoad extends BlockEntity {
    @TagSync
    @TagField("height")
    private int height;

    @TagSync
    @TagField(value = "color", mapper = IntegerStringMapMapper.class)
    private final HashMap<Integer, String> color = new HashMap<>();

    public TileStandardRoad() {
        this.height = 1;
        try {
            TagCompound tc = new TagCompound()
                    .setInteger("height", height)
                    .setMap("color", color, Object::toString, v -> new TagCompound().setString("string", v));
            save(tc);
        } catch (SerializationException ignore) {
        }
    }

    public int getHeight() {
        return this.height;
    }

    public void addHeight() {
        this.height++;
    }

    public HashMap<Integer, String> getColor() {
        return color;
    }

    public void setColor(int x, int z, String color) {
        if (color != null) {
            this.color.put(x << 4 | z, color);
            this.markDirty();
        }
    }

    public boolean isTop() {
        return getWorld().isAir(getPos().up()) || getWorld().isReplaceable(getPos().up());
    }

    //Standard methods
    @Override
    public void onBreak() {
        super.onBreak();
    }

    @Override
    public boolean tryBreak(Player player) {
        return super.tryBreak(player);
    }

    @Override
    public ItemStack onPick() {
        return new ItemStack(RegisterItems.STANDARD_ROAD_ITEM, 1);
    }

    @Override
    public boolean onClick(Player player, Player.Hand hand, Facing facing, Vec3d hit) {
        if (player.getHeldItem(Player.Hand.PRIMARY).is(RegisterItems.STANDARD_ROAD_ITEM)) {
            if (this.height < 16) {
                this.addHeight();
                this.markDirty();
            }
        } else if (player.getHeldItem(Player.Hand.PRIMARY).is(RegisterItems.BRUSH_ITEM)) {
            int xCenter = (int) (hit.x == 1 ? 15 : hit.x * 16);
            int zCenter = (int) (hit.z == 1 ? 15 : hit.z * 16);
            BrushRegister.Brush brush = BrushRegister.getBrush(player.getHeldItem(Player.Hand.PRIMARY)
                    .getTagCompound().getString("brush"));

            paint(xCenter, zCenter, brush, fromDegree(player.getYawHead()));
            this.markDirty();
        }
        return true;
    }

    private void paint(int xCenter, int zCenter, BrushRegister.Brush brush, Facing facing) {
        Vec3i pos = this.getPos();
        System.out.println(pos);
        Map<Pair<Integer, Integer>, TileStandardRoad> tileMap = new HashMap<>();
        //获取画布区域
        //This is bad
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                for (int y = -1; y <= 1; y++) {
                    TileStandardRoad tile = getWorld().getBlockEntity(pos.add(x, y, z), TileStandardRoad.class);
                    if (tile != null && Math.abs(tile.getHeight() + y * 16 - this.height) <= 8 && tile.isTop()) {
                        tileMap.put(new ImmutablePair<>(x, z), tile);
                    }
                }
            }
        }

        int index = 0;
        int horizontalMin = xCenter - brush.getxOffset() + 1;
        int horizontalMax = xCenter - brush.getxOffset() + 1 + brush.getWidth();
        int verticalMin = zCenter - brush.getyOffset() + 1;
        int verticalMax = zCenter - brush.getyOffset() + 1 + brush.getHeight();
        switch (facing) {
            case WEST:
                for (int x = horizontalMin; x < horizontalMax; x++) {
                    for (int z = verticalMin; z < verticalMax; z++) {
                        final int xB = x < 0 ? x / 16 - 1 : x / 16;
                        final int zB = z < 0 ? z / 16 - 1 : z / 16;
                        Pair<Integer, Integer> vec = new ImmutablePair<>(xB, zB);
                        if (tileMap.containsKey(vec)) {
                            tileMap.get(vec).setColor(x - vec.getLeft() * 16, z - vec.getRight() * 16,
                                    brush.getColorMap().get(index));
                        }
                        index++;
                    }
                }
                break;
            case EAST:
                for (int x = horizontalMax; x > horizontalMin; x--) {
                    for (int z = verticalMax; z > verticalMin; z--) {
                        final int xB = x < 0 ? x / 16 - 1 : x / 16;
                        final int zB = z < 0 ? z / 16 - 1 : z / 16;
                        Pair<Integer, Integer> vec = new ImmutablePair<>(xB, zB);
                        if (tileMap.containsKey(vec)) {
                            tileMap.get(vec).setColor(x - vec.getLeft() * 16, z - vec.getRight() * 16,
                                    brush.getColorMap().get(index));
                        }
                        index++;
                    }
                }
                break;
            //TODO Need fix for N/S
            case SOUTH:
                for (int x = verticalMin; x < verticalMax; x++) {
                    for (int z = horizontalMax; z > horizontalMin; z--) {
                        final int xB = x < 0 ? x / 16 - 1 : x / 16;
                        final int zB = z < 0 ? z / 16 - 1 : z / 16;
                        Pair<Integer, Integer> vec = new ImmutablePair<>(xB, zB);
                        if (tileMap.containsKey(vec)) {
                            tileMap.get(vec).setColor(z - vec.getRight() * 16, x - vec.getLeft() * 16,
                                    brush.getColorMap().get(index));
                        }
                        index++;
                    }
                }
                break;
            default:
                // 90 deg clockwise from west
                for (int x = verticalMax; x > verticalMin; x--) {
                    for (int z = horizontalMin; z < horizontalMax; z++) {
                        final int xB = x < 0 ? x / 16 - 1 : x / 16;
                        final int zB = z < 0 ? z / 16 - 1 : z / 16;
                        Pair<Integer, Integer> vec = new ImmutablePair<>(zB, xB);
                        if (tileMap.containsKey(vec)) {
                            tileMap.get(vec).setColor( x - vec.getLeft() * 16,z - vec.getRight() * 16,
                                    brush.getColorMap().get(index));
                        }
                        index++;
                    }
                }
                break;
        }
    }


    @Override
    public IBoundingBox getRenderBoundingBox() {
        return IBoundingBox.INFINITE;
    }

    @Override
    public IBoundingBox getBoundingBox() {
        return IBoundingBox.from(new Vec3d(0, 0, 0), new Vec3d(1, 0.0625 * height, 1));
    }
}
