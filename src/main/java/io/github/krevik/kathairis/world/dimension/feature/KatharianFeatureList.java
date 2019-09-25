package io.github.krevik.kathairis.world.dimension.feature;

import io.github.krevik.kathairis.world.dimension.feature.carver.KatharianWorldCaveCarver;
import io.github.krevik.kathairis.world.dimension.feature.desert.*;
import io.github.krevik.kathairis.world.dimension.feature.floating_islands.FeatureHugeFloatingIsland;
import io.github.krevik.kathairis.world.dimension.feature.forest.FeatureForestCandleBush;
import io.github.krevik.kathairis.world.dimension.feature.forest.FeatureSteppedSucculent;
import io.github.krevik.kathairis.world.dimension.feature.plainfields.FeaturePlainFields;
import io.github.krevik.kathairis.world.dimension.feature.rewarding.FeatureKatharianCloudRuins;
import io.github.krevik.kathairis.world.dimension.feature.rewarding.FeatureKatharianFloatingMiniIsland;
import io.github.krevik.kathairis.world.dimension.feature.rewarding.FeatureSmallRuins;
import io.github.krevik.kathairis.world.dimension.feature.rewarding.FeatureSoulCloudWithChests;
import io.github.krevik.kathairis.world.dimension.feature.swamp.FeatureBasicSwamp;
import io.github.krevik.kathairis.world.dimension.feature.tree.*;
import io.github.krevik.kathairis.world.dimension.structures.crystal_labirynth.CrystalLabirynthConfig;
import io.github.krevik.kathairis.world.dimension.structures.crystal_labirynth.StructureCrystalLabirynth;
import io.github.krevik.kathairis.world.dimension.surface.builder.KathairisSwampSurfaceBuilder;
import io.github.krevik.kathairis.world.dimension.surface.builder.KatharianDesertEdgeSurfaceBuilder;
import io.github.krevik.kathairis.world.dimension.surface.builder.KatharianSoftSandLakesSurfaceBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.structure.VillageStructure;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class KatharianFeatureList {

    public static final int mini_Cloud_Temple_Chance = 350;

    public static final WorldCarver<ProbabilityConfig> KATHARIAN_CAVE_WORLD_CARVER = new KatharianWorldCaveCarver(ProbabilityConfig::deserialize,256);


    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_TREE = registerFeature("katharian_tree", new KatharianTreeFeature(NoFeatureConfig::deserialize,true));
    public static final AbstractTreeFeature<NoFeatureConfig> BASIC_STANDARD_TREE = registerFeature("basic_standard_tree", new FeatureBasicKatharianTree(NoFeatureConfig::deserialize,true));
    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_TREE_1 = registerFeature("katharian_tree_1",new FeatureKatharianTallTree1(NoFeatureConfig::deserialize));
    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_TREE_2 = registerFeature("katharian_tree_2",new FeatureKatharianTallTree2(NoFeatureConfig::deserialize));
    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_TREE_3 = registerFeature("katharian_tree_3",new FeatureKatharianTallTree3(NoFeatureConfig::deserialize));
    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_HUGE_TREE_1 = registerFeature("katharian_huge_tree_1",new FeatureKatharianTreeHuge1(NoFeatureConfig::deserialize));
    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_SOUL_TREE = registerFeature("katharian_soul_tree",new FeatureKatharianSoulTree(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> KATHARIAN_CLOUD = registerFeature("katharian_cloud",new FeatureKatharianCloud(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> KATHARIAN_CACTUS = registerFeature("katharian_cactus",new FeatureKatharianCactus(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> KATHARIAN_DEAD_BUSH = registerFeature("katharian_dead_bush",new FeatureKatharianDeadBush(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> KATHARIAN_ROCK_MUSHROOM = registerFeature("katharian_rock_mushroom",new FeatureRockMushrooms(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> KATHARIAN_PLAIN_FIELDS = registerFeature("katharian_plain_fields",new FeaturePlainFields(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> KATHARIAN_CLOUD_MINI_ISLAND = registerFeature("katharian_cloud_mini_island",new FeatureKatharianFloatingMiniIsland(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> KATHARIAN_CRYSTAL_CHAMBER = registerFeature("katharian_crystal_chamber",new FeatureCrystalChamber(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> KATHARIAN_ROCKTUS = registerFeature("katharian_rocktus",new FeatureKatharianRocktus(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> SAND_LAYERS = registerFeature("sand_layers",new FeatureDesertSandLayers(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> STEPPED_SUCCULENT = registerFeature("stepped_succulent",new FeatureSteppedSucculent(NoFeatureConfig::deserialize));
    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_SWAMP_TALL_TREE_1 = registerFeature("katharian_swamp_tall_tree_1",new FeatureKatharianSwampTallTree1(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> BASIC_SWAMP_FEATURE = registerFeature("basic_swamp_feature",new FeatureBasicSwamp(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> SMALL_ROCK = registerFeature("small_rock",new FeatureDesertSmallRocks(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> KATHARIAN_FOREST_BUSH = registerFeature("katharian_forest_bush",new FeatureKatharianForestBush(NoFeatureConfig::deserialize));
    public static final Feature<BushConfig> KATHARIAN_FOREST_CANDLE_BUSH = registerFeature("katharian_forest_candle_bush",new FeatureForestCandleBush(BushConfig::deserialize));
    public static final Feature<NoFeatureConfig> KATHARIAN_HUGE_FLOATING_ISLAND = registerFeature("katharian_huge_floating_island",new FeatureHugeFloatingIsland(NoFeatureConfig::deserialize));
    //public static final Feature<NoFeatureConfig> CLOUD_MINI_TEMPLE = new CloudMiniTemple();
    public static final Feature<KatharianMinableConfig> ORE = registerFeature("ore", new BasicKatharianOreFeature(KatharianMinableConfig::deserialize));
    public static final Feature<NoFeatureConfig> FEATURE_SOUL_CLOUD_WITH_CHESTS = registerFeature("soul_clouds_with_chests",new FeatureSoulCloudWithChests(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> FEATURE_CLOUD_RUINS = registerFeature("cloud_ruins",new FeatureKatharianCloudRuins(NoFeatureConfig::deserialize));
    public static final Feature<NoFeatureConfig> FEATURE_SMALL_RUINS = registerFeature("small_ruins",new FeatureSmallRuins(NoFeatureConfig::deserialize));

    public static final SurfaceBuilder<SurfaceBuilderConfig> KATHARIAN_SWAMP_SURFACE_BUILDER = registerSurfaceBuilder("swamp", new KathairisSwampSurfaceBuilder(SurfaceBuilderConfig::deserialize));
    public static final SurfaceBuilder<SurfaceBuilderConfig> KATHARIAN_DESERT_EDGE_SURFACE_BUILDER = registerSurfaceBuilder("desert_edge", new KatharianDesertEdgeSurfaceBuilder(SurfaceBuilderConfig::deserialize));
    public static final SurfaceBuilder<SurfaceBuilderConfig> KATHARIAN_SOFT_SAND_LAKES_SURFACE_BUILDER = registerSurfaceBuilder("soft_sand_lakes", new KatharianSoftSandLakesSurfaceBuilder(SurfaceBuilderConfig::deserialize));

    public static final Structure<CrystalLabirynthConfig> CRYSTAL_LABIRYNTH = registerFeature("crystal_labirynth", new StructureCrystalLabirynth(CrystalLabirynthConfig::deserialize));

    private static <C extends IFeatureConfig, F extends Feature<C>> F registerFeature(String key, F value) {
        return (F)(Registry.<Feature<?>>register(Registry.FEATURE, "kathairis:" + key, value));
    }

    private static <C extends ISurfaceBuilderConfig, F extends SurfaceBuilder<C>> F registerSurfaceBuilder(String key, F builderIn) {
        return (F)(Registry.<SurfaceBuilder<?>>register(Registry.SURFACE_BUILDER, "kathairis:" + key, builderIn));
    }

}
