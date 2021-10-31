package com.talhanation.smallships.client.render.obj;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.projectile.CannonBall;
import de.maxhenkel.corelib.client.obj.OBJEntityRenderer;
import de.maxhenkel.corelib.client.obj.OBJModel;
import de.maxhenkel.corelib.client.obj.OBJModelInstance;
import de.maxhenkel.corelib.client.obj.OBJModelOptions;
import de.maxhenkel.corelib.math.Rotation;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelCannonBall extends OBJEntityRenderer<CannonBall> {

    public ModelCannonBall(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public List<OBJModelInstance<CannonBall>> getModels(CannonBall cannonBall) {
        List<OBJModelInstance<CannonBall>> models = new ArrayList<>(MODELS);
        return models;
    }


    private static final List<OBJModelInstance<CannonBall>> MODELS = Arrays.asList(
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/cannonball.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID, "textures/entity/cannonball_texture.png"),
                            new Vector3d(1D, 1D, 1D),
                            new Rotation(90F, Vector3f.YP),
                            (cannonBall, matrixStack, partialTicks) -> {
                                matrixStack.scale(1.5F, 1.5F, 1.5F);
                            }
                    )
            )
    );

}
