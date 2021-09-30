package com.talhanation.smallships.client.render.obj;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.CogEntity;
import de.maxhenkel.corelib.client.obj.OBJModel;
import de.maxhenkel.corelib.client.obj.OBJModelInstance;
import de.maxhenkel.corelib.client.obj.OBJModelOptions;
import de.maxhenkel.corelib.math.Rotation;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelCog extends AbstractShipModel<CogEntity> {

    private static final List<OBJModelInstance<CogEntity>> MODELS = Arrays.asList(
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/cog_steer.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/oak_cog.png"),
                            new Vector3d(0D,1D,0D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                matrixStack.scale(1.3F, 1.3F, 1.3F);

                                if (cog.getSteerState(0)){
                                    matrixStack.mulPose(Vector3f.YN.rotationDegrees(3));
                                }
                                else if (cog.getSteerState(1)) {
                                    matrixStack.mulPose(Vector3f.YN.rotationDegrees(-3));
                                }
                                else {
                                    matrixStack.mulPose(Vector3f.YN.rotationDegrees(0));
                                }

                            }
                    )
            ),
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/cog_segel_1_4.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/oak_sail.png"),
                            new Vector3d(0D,1D,0D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                if(cog.getSailState() == 4) matrixStack.scale(1.3F, 1.3F, 1.3F);
                                else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);

                            }
                    )
            ),
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/cog_segel_1_3.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/oak_sail.png"),
                            new Vector3d(0D,1D,0D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                if(cog.getSailState() == 3) matrixStack.scale(1.3F, 1.3F, 1.3F);
                                else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);

                            }
                    )
            ),
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/cog_segel_1_2.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/oak_sail.png"),
                            new Vector3d(0D,1D,0D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                if(cog.getSailState() == 2) matrixStack.scale(1.3F, 1.3F, 1.3F);
                                else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);

                            }
                    )
            ),
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/cog_segel_1_1.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/oak_sail.png"),
                            new Vector3d(0D,1D,0D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                if(cog.getSailState() == 1) matrixStack.scale(1.3F, 1.3F, 1.3F);
                                else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);

                            }
                    )
            ),
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/cog_segel_1_0.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/oak_sail.png"),
                            new Vector3d(0D,1D,0D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                if(cog.getSailState() == 0) matrixStack.scale(1.3F, 1.3F, 1.3F);
                                else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);

                            }
                    )
            ),
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/cog_chest_1.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/oak_cog.png"),
                            new Vector3d(0D,1D,0D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                if(cog.Cargo_1) matrixStack.scale(1.3F, 1.3F, 1.3F);
                                else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);

                            }
                    )
            ),
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/cog_chest_2.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/oak_cog.png"),
                            new Vector3d(0D,1D,0D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                if(cog.Cargo_2) matrixStack.scale(1.3F, 1.3F, 1.3F);
                                else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);

                            }
                    )
            ),
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/cog_chest_3.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/oak_cog.png"),
                            new Vector3d(0D,1D,0D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                if(cog.Cargo_3) matrixStack.scale(1.3F, 1.3F, 1.3F);
                                else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);

                            }
                    )
            ),
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/cog_chest_4.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/oak_cog.png"),
                            new Vector3d(0D,1D,0D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                if(cog.Cargo_4) matrixStack.scale(1.3F, 1.3F, 1.3F);
                                else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);

                            }
                    )
            )
    );


    private static final List<OBJModelInstance<CogEntity>> OAK_MODEL = getPlaneModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/oak_cog.png"));
    private static final List<OBJModelInstance<CogEntity>> DARK_OAK_MODEL = getPlaneModel(new ResourceLocation("textures/block/dark_oak_planks.png"));
    private static final List<OBJModelInstance<CogEntity>> BIRCH_MODEL = getPlaneModel(new ResourceLocation("textures/block/birch_planks.png"));
    private static final List<OBJModelInstance<CogEntity>> JUNGLE_MODEL = getPlaneModel(new ResourceLocation("textures/block/jungle_planks.png"));
    private static final List<OBJModelInstance<CogEntity>> ACACIA_MODEL = getPlaneModel(new ResourceLocation("textures/block/acacia_planks.png"));
    private static final List<OBJModelInstance<CogEntity>> SPRUCE_MODEL = getPlaneModel(new ResourceLocation("textures/block/spruce_planks.png"));

    /*
        new ResourceLocation(Main.MOD_ID,"textures/entity/cog/oak_cog.png"));
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/spruce_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/birch_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/jungle_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/acacia_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/dark_oak_cog.png"),
        */
    public ModelCog(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void translateName(CogEntity plane, MatrixStack matrixStack, boolean left) {
        if (left) {
            matrixStack.translate(8.01D / 16D, -20D / 16D, -1D);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(90F));
        } else {
            matrixStack.translate(-8.01D / 16D, -20D / 16D, -1D);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90F));
        }
    }

    protected String getSailColorTexture(CogEntity cog, MatrixStack matrixStack) {
        return null;
    }

    public static String getWoodTypeTexture(CogEntity cog) {
        switch (cog.getBoatType()) {
            default:
            case OAK:
                return "OAK_MODEL";
            case DARK_OAK:
                return "DARK_OAK_MODEL";
            case SPRUCE:
                return "SPRUCE_MODEL";
            case JUNGLE:
                return "JUNGLE_MODEL";
            case BIRCH:
                return "BIRCH_MODEL";
            case ACACIA:
                return "ACACIA_MODEL";
        }
    }

    @Override
    public List<OBJModelInstance<CogEntity>> getModels(CogEntity entity) {
        return getModelFromType(entity);
    }


    private static List<OBJModelInstance<CogEntity>> getModelFromType(CogEntity cog) {
        switch (cog.getBoatType()) {
            default:
            case OAK:
                return OAK_MODEL;
            case DARK_OAK:
                return DARK_OAK_MODEL;
            case SPRUCE:
                return SPRUCE_MODEL;
            case JUNGLE:
                return JUNGLE_MODEL;
            case BIRCH:
                return BIRCH_MODEL;
            case ACACIA:
                return ACACIA_MODEL;
        }
    }

    private static List<OBJModelInstance<CogEntity>> getPlaneModel(ResourceLocation texture) {

        List<OBJModelInstance<CogEntity>> models = new ArrayList<>(MODELS);
        models.add(new OBJModelInstance<>(
                new OBJModel(
                        new ResourceLocation(Main.MOD_ID, "models/entity/cog_base.obj")
                ),
                new OBJModelOptions<>(
                        texture,
                        new Vector3d(0D, 1.0D, 0D),
                        new Rotation(90F, Vector3f.YP),

                        (cog, matrixStack, partialTicks) ->
                                matrixStack.scale(1.3F, 1.3F, 1.3F)
                )
        ));

        return models;
    }

}