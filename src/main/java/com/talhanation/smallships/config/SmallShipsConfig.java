package com.talhanation.smallships.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;
import java.util.ArrayList;

@Mod.EventBusSubscriber
public class SmallShipsConfig {


    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec CONFIG;

    public static ForgeConfigSpec.IntValue VERSION;
    public static final int NEW_VERSION = 3;

    public static ForgeConfigSpec.BooleanValue PlaySwimmSound;
    public static ForgeConfigSpec.DoubleValue SailSpeedFactor;
    public static ForgeConfigSpec.DoubleValue GalleySpeedFactor;
    public static ForgeConfigSpec.DoubleValue GalleyTurnFactor;
    public static ForgeConfigSpec.DoubleValue WarGalleySpeedFactor;
    public static ForgeConfigSpec.DoubleValue WarGalleyTurnFactor;
    public static ForgeConfigSpec.DoubleValue CogSpeedFactor;
    public static ForgeConfigSpec.DoubleValue CogTurnFactor;
    public static ForgeConfigSpec.DoubleValue DrakkarSpeedFactor;
    public static ForgeConfigSpec.DoubleValue DrakkarTurnFactor;
    public static ForgeConfigSpec.ConfigValue<ArrayList<String>> PassengerBlackList;

    static {
        VERSION = BUILDER.comment("\n" +"Version, do not change!##")
                .defineInRange("Version", 0, 0, Integer.MAX_VALUE);

        BUILDER.comment("Small Ships Config:").push("Ships");

        /*SailSpeedFactor = BUILDER.comment("\n" +"----Sail Speed Factor for when the sprint key is pressed while riding a ship.----" + "\n" +
                "\t" +"(takes effect after restart)" + "\n" +
                "\t" +"default: 1.0")
                .worldRestart()
                .defineInRange("SailSpeedFactor", 1.0, 0.0, 2.0);
            */
            /*
            this.PassengerBlackList = builder.comment("Passenger Blacklist: List of entities that are NOT allowed to mount a ship.")
                    .define("cargoCart.pullEntities", new ArrayList<String>(Arrays.asList(
                            "minecraft:enderdragon",
                            "minecraft:phantom",
                            "minecraft:biest")));
            */
        GalleySpeedFactor = BUILDER.comment("\n" +"----Galley Speed Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1.3")
                .worldRestart()
                .defineInRange("GalleySpeedFactor", 1.3, 0.0, 2.0);

        GalleyTurnFactor = BUILDER.comment("\n" +"----Galley Turn Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.5")
                .worldRestart()
                .defineInRange("GalleyTurnFactor", 0.5, 0.0, 1.0);

        CogSpeedFactor = BUILDER.comment("\n" +"----Cog Speed Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1.25" )
                .worldRestart()
                .defineInRange("CogSpeedFactor", 1.25, 0.0, 2.0);

        CogTurnFactor = BUILDER.comment("\n" +"----Cog Turn Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.1")
                .worldRestart()
                .defineInRange("CogTurnFactor", 0.1, 0.0, 1.0);

        WarGalleySpeedFactor = BUILDER.comment("\n" +"----War Galley Speed Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1.15")
                .worldRestart()
                .defineInRange("WarGalleySpeedFactor", 1.15, 0.0, 2.0);

        WarGalleyTurnFactor = BUILDER.comment("\n" +"----War Galley Turn Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.4")
                .worldRestart()
                .defineInRange("WarGalleyTurnFactor", 0.4, 0.0, 1.0);

        DrakkarSpeedFactor = BUILDER.comment("\n" +"----Drakkar Speed Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1.3")
                .worldRestart()
                .defineInRange("DrakkarSpeedFactor", 1.3, 0.0, 2.0);

        DrakkarTurnFactor = BUILDER.comment("\n" +"----Drakkar Turn Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.3")
                .worldRestart()
                .defineInRange("DrakkarTurnFactor", 0.3, 0.0, 1.0);

        PlaySwimmSound = BUILDER.comment("\n" + "----Should Ships Make Swimming sounds?----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: true")
                .define("PlaySwimmSound", true);



        CONFIG = BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
        if (VERSION.get() != NEW_VERSION) {
            configData = CommentedFileConfig.builder(path)
                    .sync()
                    .autosave()
                    .writingMode(WritingMode.REPLACE)
                    .build();
            spec.setConfig(configData);
            VERSION.set(NEW_VERSION);
            configData.save();
        }
    }
}
