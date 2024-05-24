package com.goldenfield192.road.serialization;

import cam72cam.mod.serialization.SerializationException;
import cam72cam.mod.serialization.TagCompound;
import cam72cam.mod.serialization.TagField;
import cam72cam.mod.serialization.TagMapper;

import java.util.Map;

public class IntegerStringMapMapper implements TagMapper<Map<Integer,String>> {
    @Override
    public TagAccessor<Map<Integer,String>> apply(Class type, String fieldName, TagField tag) {
        return new TagAccessor<>(
                (nbt, map) -> {
                    if (map != null)
                        nbt.setMap(fieldName, map, Object::toString, value -> new TagCompound().setString("string", value));
                },
                nbt -> nbt.getMap(fieldName, Integer::parseInt, valueTag -> valueTag.getString("string"))
        );
    }
}
