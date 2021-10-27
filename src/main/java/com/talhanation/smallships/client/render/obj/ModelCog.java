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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.*;

public class ModelCog extends AbstractShipModel<CogEntity> {

    private static final List<OBJModelInstance<CogEntity>> MODELS = Arrays.asList(
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/ship_cannon.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID,"textures/entity/ship_cannon.png"),
                            new Vector3d(-1D,1.5D,-0.7D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                if(cog.getCannonCount() >= 1) matrixStack.scale(1, 1, 1);
                                else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);
                            }
                    )
            ),
            new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/ship_cannon.obj")
                    ),
                    new OBJModelOptions<>(
                            new ResourceLocation(Main.MOD_ID,"textures/entity/ship_cannon.png"),
                            new Vector3d(1D,1.5D,-0.7D),
                            new Rotation(-90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                if(cog.getCannonCount() >= 2) matrixStack.scale(1, 1, 1);
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




    private static final List<OBJModelInstance<CogEntity>> LANTERN_LIT =      getLanternModel(new ResourceLocation(Main.MOD_ID,"textures/entity/lantern_lit.png"));
    private static final List<OBJModelInstance<CogEntity>> LANTERN_OFF =      getLanternModel(new ResourceLocation(Main.MOD_ID,"textures/entity/lantern_off.png"));

    private static final List<OBJModelInstance<CogEntity>> OAK_MODEL =      getShipModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/oak_cog.png"));
    private static final List<OBJModelInstance<CogEntity>> DARK_OAK_MODEL = getShipModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/dark_oak_cog.png"));
    private static final List<OBJModelInstance<CogEntity>> BIRCH_MODEL =    getShipModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/birch_cog.png"));
    private static final List<OBJModelInstance<CogEntity>> JUNGLE_MODEL =   getShipModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/jungle_cog.png"));
    private static final List<OBJModelInstance<CogEntity>> ACACIA_MODEL =   getShipModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/acacia_cog.png"));
    private static final List<OBJModelInstance<CogEntity>> SPRUCE_MODEL =   getShipModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/spruce_cog.png"));

    private static final List<OBJModelInstance<CogEntity>> OAK_STEER_MODEL =      getSteerModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/oak_cog.png"));
    private static final List<OBJModelInstance<CogEntity>> DARK_OAK_STEER_MODEL = getSteerModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/dark_oak_cog.png"));
    private static final List<OBJModelInstance<CogEntity>> BIRCH_STEER_MODEL =    getSteerModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/birch_cog.png"));
    private static final List<OBJModelInstance<CogEntity>> JUNGLE_STEER_MODEL =   getSteerModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/jungle_cog.png"));
    private static final List<OBJModelInstance<CogEntity>> ACACIA_STEER_MODEL =   getSteerModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/acacia_cog.png"));
    private static final List<OBJModelInstance<CogEntity>> SPRUCE_STEER_MODEL =   getSteerModel(new ResourceLocation(Main.MOD_ID,"textures/entity/cog/spruce_cog.png"));


    private static final List<OBJModelInstance<CogEntity>> WHITE =      getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/white_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> ORANGE =     getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/orange_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> MAGENTA =    getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/magenta_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> LIGHT_BLUE = getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/light_blue_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> YELLOW =     getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/yellow_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> LIME =       getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/lime_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> PINK =       getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/pink_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> GRAY =       getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/gray_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> LIGHT_GRAY = getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/light_gray_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> CYAN =       getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/cyan_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> PURPLE =     getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/purple_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> BLUE =       getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/blue_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> BROWN =      getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/brown_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> GREEN =      getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/green_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> RED =        getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/red_sail.png"));
    private static final List<OBJModelInstance<CogEntity>> BLACK =      getSailModel(new ResourceLocation(Main.MOD_ID,"textures/entity/sail/black_sail.png"));


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

    @Override
    public List<OBJModelInstance<CogEntity>> getModels(CogEntity entity) {
        List<OBJModelInstance<CogEntity>> models = new ArrayList<>(MODELS);
        models.addAll(getModelFromType(entity));
        models.addAll(getSailModelFromColor(entity));
        models.addAll(getModelSteerFromType(entity));
        models.addAll(getLanternModelFromState(entity));
        return models;
    }

    private static List<OBJModelInstance<CogEntity>> getLanternModelFromState(CogEntity cog) {
        if (cog.getLanternState()){
            return LANTERN_LIT;
        }
        else
            return LANTERN_OFF;
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

    private static List<OBJModelInstance<CogEntity>> getModelSteerFromType(CogEntity cog) {
        switch (cog.getBoatType()) {
            default:
            case OAK:
                return OAK_STEER_MODEL;

            case DARK_OAK:
                return DARK_OAK_STEER_MODEL;

            case SPRUCE:
                return SPRUCE_STEER_MODEL;

            case JUNGLE:
                return JUNGLE_STEER_MODEL;

            case BIRCH:
                return BIRCH_STEER_MODEL;

            case ACACIA:
                return ACACIA_STEER_MODEL;
        }
    }

    private static List<OBJModelInstance<CogEntity>> getSailModelFromColor(CogEntity cog) {
            switch (cog.getSailColor()) {
                default:
                case "white":
                    return WHITE;

                case "orange":
                    return ORANGE;

                case "magenta":
                    return MAGENTA;

                case "light_blue":
                    return LIGHT_BLUE;

                case "yellow":
                    return YELLOW;

                case "lime":
                    return LIME;

                case "pink":
                    return PINK;

                case "purple":
                    return PURPLE;

                case "gray":
                    return GRAY;

                case "light_gray":
                    return LIGHT_GRAY;

                case "cyan":
                    return CYAN;

                case "blue":
                    return BLUE;

                case "brown":
                    return BROWN;

                case "green":
                    return GREEN;

                case "red":
                    return RED;

                case "black":
                    return BLACK;

            }
    }

    private static List<OBJModelInstance<CogEntity>> getSteerModel(ResourceLocation texture) {
        List<OBJModelInstance<CogEntity>> models = new ArrayList<>(MODELS);
        models.add(new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/cog_steer.obj")
                    ),
                    new OBJModelOptions<>(
                            texture,
                            new Vector3d(0D,1D,0D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                matrixStack.scale(1.3F, 1.3F, 1.3F);

                                if (cog.getSteerState(0)){
                                    matrixStack.mulPose(Vector3f.YP.rotationDegrees(3));
                                }
                                else if (cog.getSteerState(1)) {
                                    matrixStack.mulPose(Vector3f.YP.rotationDegrees(-3));
                                }
                                else {
                                    matrixStack.mulPose(Vector3f.YP.rotationDegrees(0));
                                }

                            }
                    )
        ));

        return models;
    }


    private static List<OBJModelInstance<CogEntity>> getLanternModel(ResourceLocation texture) {
        List<OBJModelInstance<CogEntity>> models = new ArrayList<>(MODELS);
        models.add(new OBJModelInstance<>(
                    new OBJModel(
                            new ResourceLocation(Main.MOD_ID, "models/entity/ship_lantern.obj")
                    ),
                    new OBJModelOptions<>(
                            texture,
                            new Vector3d(0D,4D,2.25D),
                            new Rotation(90F, Vector3f.YP),
                            (cog, matrixStack, partialTicks) -> {
                                if(cog.getLanternCount() >= 1) matrixStack.scale(0.7F, 0.7F, 0.7F);
                                else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);
                            }
                    )
        ));

        models.add(new OBJModelInstance<>(
                new OBJModel(
                        new ResourceLocation(Main.MOD_ID, "models/entity/ship_lantern.obj")
                ),
                new OBJModelOptions<>(
                        texture,
                        new Vector3d(0D,4D,-1.7D),
                        new Rotation(90F, Vector3f.YP),
                        (cog, matrixStack, partialTicks) -> {
                            if(cog.getLanternCount() >= 2) matrixStack.scale(0.7F, 0.7F, 0.7F);
                            else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);
                        }
                )
        ));
        return models;
    }

    private static List<OBJModelInstance<CogEntity>> getShipModel(ResourceLocation texture) {
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

    private static List<OBJModelInstance<CogEntity>> getSailModel(ResourceLocation color) {
        List<OBJModelInstance<CogEntity>> models = new ArrayList<>(MODELS);
        models.add(new OBJModelInstance<>(
                new OBJModel(
                        new ResourceLocation(Main.MOD_ID, "models/entity/cog_segel_1_0.obj")
                ),
                new OBJModelOptions<>(
                        color,
                        new Vector3d(0D, 1.0D, 0D),
                        new Rotation(90F, Vector3f.YP),

                        (cog, matrixStack, partialTicks) ->{
                            if(cog.getSailState() == 0) matrixStack.scale(1.3F, 1.3F, 1.3F);
                            else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);
                        }
                )
        ));

        models.add(new OBJModelInstance<>(
                new OBJModel(
                        new ResourceLocation(Main.MOD_ID, "models/entity/cog_segel_1_1.obj")
                ),
                new OBJModelOptions<>(
                        color,
                        new Vector3d(0D, 1.0D, 0D),
                        new Rotation(90F, Vector3f.YP),

                        (cog, matrixStack, partialTicks) ->{
                            if(cog.getSailState() == 1) matrixStack.scale(1.3F, 1.3F, 1.3F);
                            else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);
                        }
                )
        ));

        models.add(new OBJModelInstance<>(
                new OBJModel(
                        new ResourceLocation(Main.MOD_ID, "models/entity/cog_segel_1_2.obj")
                ),
                new OBJModelOptions<>(
                        color,
                        new Vector3d(0D, 1.0D, 0D),
                        new Rotation(90F, Vector3f.YP),

                        (cog, matrixStack, partialTicks) ->{
                            if(cog.getSailState() == 2) matrixStack.scale(1.3F, 1.3F, 1.3F);
                            else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);
                        }
                )
        ));

        models.add(new OBJModelInstance<>(
                new OBJModel(
                        new ResourceLocation(Main.MOD_ID, "models/entity/cog_segel_1_3.obj")
                ),
                new OBJModelOptions<>(
                        color,
                        new Vector3d(0D, 1.0D, 0D),
                        new Rotation(90F, Vector3f.YP),

                        (cog, matrixStack, partialTicks) ->{
                            if(cog.getSailState() == 3) matrixStack.scale(1.3F, 1.3F, 1.3F);
                            else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);
                        }
                )
        ));

        models.add(new OBJModelInstance<>(
                new OBJModel(
                        new ResourceLocation(Main.MOD_ID, "models/entity/cog_segel_1_4.obj")
                ),
                new OBJModelOptions<>(
                        color,
                        new Vector3d(0D, 1.0D, 0D),
                        new Rotation(90F, Vector3f.YP),

                        (cog, matrixStack, partialTicks) ->{
                            if(cog.getSailState() == 4) matrixStack.scale(1.3F, 1.3F, 1.3F);
                            else matrixStack.scale(0.0001F, 0.0001F, 0.0001F);
                        }
                )
        ));

        return models;
    }
}
