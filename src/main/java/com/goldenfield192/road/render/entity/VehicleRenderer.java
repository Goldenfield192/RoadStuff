package com.goldenfield192.road.render.entity;

import cam72cam.mod.render.IEntityRender;
import cam72cam.mod.render.opengl.RenderState;
import cam72cam.mod.render.opengl.VBO;
import com.goldenfield192.road.entity.BaseVehicleEntity;

public class VehicleRenderer implements IEntityRender<BaseVehicleEntity> {
    @Override
    public void render(BaseVehicleEntity entity, RenderState state, float partialTicks) {
        state.translate(0,1,0);
        state.cull_face(false);
        try(VBO.Binding bind = BaseVehicleEntity.vehicleModel.binder().bind(state)){
            bind.draw();
        }
    }

    @Override
    public void postRender(BaseVehicleEntity entity, RenderState state, float partialTicks) {
        state.translate(0,1,0);
        try(VBO.Binding bind = BaseVehicleEntity.vehicleModel.binder().bind(state)){
            bind.draw();
        }
    }
}
