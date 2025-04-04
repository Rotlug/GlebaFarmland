package com.github.rotlug.glebafarmland;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Glebafarmland.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final int MIN_SUPER_VAL = -64;
    private static final int MAX_SUPER_VAL = 64;

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue NO_TRAMPLE = BUILDER
            .comment("Whether to stop crop trampling")
            .define("noTrample", true);
    private static final ModConfigSpec.BooleanValue NO_RANDOM_TICK = BUILDER
            .comment("Whether to stop random ticks (Water from source blocks and moisture decay)")
            .define("noRandomTick", true);
    private static final ModConfigSpec.BooleanValue STURDY_FARMLAND = BUILDER
            .comment("When enabled, farmland will not check for blocks above it, and can be built on top of without turning to dirt")
            .define("sturdyFarmland", true);
    private static final ModConfigSpec.BooleanValue SPLASH_POTION_WATERING = BUILDER
            .comment("If splash water potions can be used to moisten farmland")
            .define("splashPotionWatering", true);
    private static final ModConfigSpec.IntValue SPLASH_WATER_AREA = BUILDER
            .comment("The range of splash water bottles for watering, 0 to water only the block hit")
            .defineInRange("splashWaterArea", 1, 0, 8);
    private static final ModConfigSpec.BooleanValue BUCKET_WATERING = BUILDER
            .comment("If buckets can be used to moisten farmland")
            .define("bucketWatering", true);
    private static final ModConfigSpec.BooleanValue BOTTLE_WATERING = BUILDER
            .comment("If bottles can be used to moisten farmland")
            .define("bottleWatering", true);
    private static final ModConfigSpec.BooleanValue RAIN_WATERING = BUILDER
            .comment("If rain will moisten farmland")
            .define("rainWatering", true);
    private static final ModConfigSpec.BooleanValue DAILY_RESET = BUILDER
            .comment("If the farmland will decay daily")
            .define("dailyReset", true);
    private static final ModConfigSpec.IntValue DAILY_TIME_MIN = BUILDER
            .comment("The time of day daily reset logic will occur (within 10 ticks)")
            .defineInRange("dailyTimeMin", 0, 0, 24000);
    private static final ModConfigSpec.IntValue DAILY_DRY_CHANCE = BUILDER
            .comment("The chance that farmland will become dry")
            .defineInRange("dailyDryChance", 100, 0, 100);
    private static final ModConfigSpec.IntValue DAILY_DECAY_CHANCE = BUILDER
            .comment("The chance that dry farmland will decay into dirt")
            .defineInRange("dailyDecayChance", 50, 0, 100);
    private static final ModConfigSpec.BooleanValue SHOVEL_REVERTING = BUILDER
            .comment("If shovels can turn farmland to dirt when right clicking")
            .define("shovelReverting", true);


    // Watering Cans
    // Super level 1
    private static final ModConfigSpec.IntValue SUPER_1_FORWARD = BUILDER
            .comment("How far forward the first level of the watering can's super will extend")
            .defineInRange("supers.super1.forward", 2, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ModConfigSpec.IntValue SUPER_1_BACK = BUILDER
            .comment("How far back the first level of the watering can's super will extend")
            .defineInRange("supers.super1.back", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ModConfigSpec.IntValue SUPER_1_LEFT = BUILDER
            .comment("How far left the first level of the watering can's super will extend")
            .defineInRange("supers.super1.left", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ModConfigSpec.IntValue SUPER_1_RIGHT = BUILDER
            .comment("How far right the first level of the watering can's super will extend")
            .defineInRange("supers.super1.right", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);

    // Super level 2
    private static final ModConfigSpec.IntValue SUPER_2_FORWARD = BUILDER
            .comment("How far forward the second level of the watering can's super will extend")
            .defineInRange("supers.super2.forward", 2, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ModConfigSpec.IntValue SUPER_2_BACK = BUILDER
            .comment("How far back the second level of the watering can's super will extend")
            .defineInRange("supers.super2.back", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ModConfigSpec.IntValue SUPER_2_LEFT = BUILDER
            .comment("How far left the second level of the watering can's super will extend")
            .defineInRange("supers.super2.left", 1, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ModConfigSpec.IntValue SUPER_2_RIGHT = BUILDER
            .comment("How far right the second level of the watering can's super will extend")
            .defineInRange("supers.super2.right", 1, MIN_SUPER_VAL, MAX_SUPER_VAL);

    // Super level 3
    private static final ModConfigSpec.IntValue SUPER_3_FORWARD = BUILDER
            .comment("How far forward the third level of the watering can's super will extend")
            .defineInRange("supers.super3.forward", 4, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ModConfigSpec.IntValue SUPER_3_BACK = BUILDER
            .comment("How far back the third level of the watering can's super will extend")
            .defineInRange("supers.super3.back", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ModConfigSpec.IntValue SUPER_3_LEFT = BUILDER
            .comment("How far left the third level of the watering can's super will extend")
            .defineInRange("supers.super3.left", 1, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ModConfigSpec.IntValue SUPER_3_RIGHT = BUILDER
            .comment("How far right the third level of the watering can's super will extend")
            .defineInRange("supers.super3.right", 1, MIN_SUPER_VAL, MAX_SUPER_VAL);

    // Super level 4
    private static final ModConfigSpec.IntValue SUPER_4_FORWARD = BUILDER
            .comment("How far forward the fourth level of the watering can's super will extend")
            .defineInRange("supers.super4.forward", 4, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ModConfigSpec.IntValue SUPER_4_BACK = BUILDER
            .comment("How far back the fourth level of the watering can's super will extend")
            .defineInRange("supers.super4.back", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ModConfigSpec.IntValue SUPER_4_LEFT = BUILDER
            .comment("How far left the fourth level of the watering can's super will extend")
            .defineInRange("supers.super4.left", 2, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ModConfigSpec.IntValue SUPER_4_RIGHT = BUILDER
            .comment("How far right the fourth level of the watering can's super will extend")
            .defineInRange("supers.super4.right", 2, MIN_SUPER_VAL, MAX_SUPER_VAL);

    // Nether
    private static final ModConfigSpec.BooleanValue ALLOW_NETHER = BUILDER
            .comment("Allow watering cans to work in the Nether.")
            .define("nether.allow", false);
    private static final ModConfigSpec.BooleanValue ALLOW_NETHERITE_CAN_USE_ANYWAYS = BUILDER
            .comment("Allows the Netherite Watering Can to be used in the nether, regardless of the previous option.")
            .define("nether.allowNetheriteCanAnyways", true);


    // Interactions
    private static final ModConfigSpec.BooleanValue EXTINGUISH_FIRES = BUILDER
            .comment("Whether watering cans extinguish fires & campfires.")
            .define("interactions.extinguishFires", true);

    private static final ModConfigSpec.IntValue MUD_ODDS = BUILDER
            .comment("The chance out of 100 that dirt is converted into mud, 0 will disable the mechanic.")
            .defineInRange("interactions.mudOdds", 25, 0, 100);

    private static final ModConfigSpec.IntValue BONEMEAL_ODDS = BUILDER
            .comment("The chance out of 100 that bonemeal is applied to a watered crop, 0 will disable the mechanic.")
            .defineInRange("interactions.bonemealOdds", 25, 0, 100);

    public static int super1forward;
    public static int super1back;
    public static int super1left;
    public static int super1right;
    public static int super2forward;
    public static int super2back;
    public static int super2left;
    public static int super2right;
    public static int super3forward;
    public static int super3back;
    public static int super3left;
    public static int super3right;
    public static int super4forward;
    public static int super4back;
    public static int super4left;
    public static int super4right;

    public static int bonemealOdds;
    public static boolean allowNether;
    public static boolean allowNetheriteCanAnyways;
    public static boolean extinguishFires;
    public static int mudOdds;

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean noTrample;
    public static boolean splashPotionWatering;
    public static int splashWaterArea;
    public static boolean bucketWatering;
    public static boolean bottleWatering;
    public static boolean rainWatering;
    public static boolean dailyReset;
    public static int dailyTimeMin;
    public static int dailyDryChance;
    public static int dailyDecayChance;
    public static boolean sturdyFarmland;
    public static boolean noRandomTick;
    public static boolean shovelReverting;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        noTrample = NO_TRAMPLE.get();
        splashPotionWatering = SPLASH_POTION_WATERING.get();
        splashWaterArea = SPLASH_WATER_AREA.get();
        bucketWatering = BUCKET_WATERING.get();
        bottleWatering = BOTTLE_WATERING.get();
        rainWatering = RAIN_WATERING.get();
        dailyReset = DAILY_RESET.get();
        dailyTimeMin = DAILY_TIME_MIN.get();
        dailyDryChance = DAILY_DRY_CHANCE.get();
        dailyDecayChance = DAILY_DECAY_CHANCE.get();
        sturdyFarmland = STURDY_FARMLAND.get();
        noRandomTick = NO_RANDOM_TICK.get();
        shovelReverting = SHOVEL_REVERTING.get();

        // Watering Cans
        super1forward = SUPER_1_FORWARD.get();
        super1back = SUPER_1_BACK.get();
        super1left = SUPER_1_LEFT.get();
        super1right = SUPER_1_RIGHT.get();
        super2forward = SUPER_2_FORWARD.get();
        super2back = SUPER_2_BACK.get();
        super2left = SUPER_2_LEFT.get();
        super2right = SUPER_2_RIGHT.get();
        super3forward = SUPER_3_FORWARD.get();
        super3back = SUPER_3_BACK.get();
        super3left = SUPER_3_LEFT.get();
        super3right = SUPER_3_RIGHT.get();
        super4forward = SUPER_4_FORWARD.get();
        super4back = SUPER_4_BACK.get();
        super4left = SUPER_4_LEFT.get();
        super4right = SUPER_4_RIGHT.get();
        bonemealOdds = BONEMEAL_ODDS.get();
        allowNether = ALLOW_NETHER.get();
        extinguishFires = EXTINGUISH_FIRES.get();
        mudOdds = MUD_ODDS.get();
        allowNetheriteCanAnyways = ALLOW_NETHERITE_CAN_USE_ANYWAYS.get();
    }
}
