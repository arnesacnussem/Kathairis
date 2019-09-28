package io.github.krevik.kathairis;

import io.github.krevik.kathairis.block.*;
import io.github.krevik.kathairis.block.TreesForSaplings.ElderwillowTree;
import io.github.krevik.kathairis.block.TreesForSaplings.MysticTree;
import io.github.krevik.kathairis.block.TreesForSaplings.ShinyTree;
import io.github.krevik.kathairis.block.TreesForSaplings.SoulTree;
import io.github.krevik.kathairis.enchantement.KathairisEnchantments;
import io.github.krevik.kathairis.entity.*;
import io.github.krevik.kathairis.entity.butterfly.*;
import io.github.krevik.kathairis.init.*;
import io.github.krevik.kathairis.item.*;
import io.github.krevik.kathairis.util.IItemGroupProvider;
import io.github.krevik.kathairis.util.ModReference;
import io.github.krevik.kathairis.util.ModUtil;
import io.github.krevik.kathairis.util.networking.PacketHandler;
import io.github.krevik.kathairis.world.dimension.ModDimensionKathairis;
import io.github.krevik.kathairis.world.dimension.biome.biomes.*;
import io.github.krevik.kathairis.world.dimension.feature.KatharianFeatureList;
import io.netty.buffer.Unpooled;
import joptsimple.internal.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryManager;

import javax.annotation.Nonnull;

import static io.github.krevik.kathairis.init.ModItemGroups.BUILDING_BLOCKS;
import static io.github.krevik.kathairis.util.ModReference.MOD_ID;
import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

/**
 * @author Cadiboo
 */
@EventBusSubscriber(modid = MOD_ID, bus = MOD)
public final class ModEventSubscriber {


	@SubscribeEvent
	public static void onRegisterFeatures(final RegistryEvent.Register<Feature<?>> event) {
		KatharianFeatureList.putStructures();
	}
		@SubscribeEvent
	public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {

		final Block mysticPlanks;
		final Block shinyPlanks;
		final Block soulPlanks;
		final Block elderwillowPlanks;
		final Block kathairisStone;
		final Block kathairisCobblestone;
		final Block weatheredRock;
		final Block hardenedWeatheredRock;
		final Block hardenedWeatheredRockBricks;
		final Block mudBricks;
		final Block kathairisStoneBricks;
		final Block kathairisSandstone;

		event.getRegistry().registerAll(
				//logs and stripped logs
				setup(new BlockKathairisLog(), "mystic_log"),
				setup(new BlockKathairisLog(), "shiny_log"),
				setup(new BlockKathairisLog(), "soul_log"),
				setup(new BlockKathairisLog(), "elderwillow_log"),
				setup(new BlockKathairisLog(), "mystic_log_stripped"),
				setup(new BlockKathairisLog(), "shiny_log_stripped"),
				setup(new BlockKathairisLog(), "soul_log_stripped"),
				setup(new BlockKathairisLog(), "elderwillow_log_stripped"),
				//leaves
				setup(new BlockKathairisLeaves(), "mystic_leaves"),
				setup(new BlockKathairisLeaves(), "shiny_leaves"),
				setup(new BlockKathairisLeaves(), "soul_leaves"),
				setup(new BlockKathairisLeaves(), "elderwillow_leaves"),
				//planks
				setup(mysticPlanks = new BlockKathairisPlanks(), "mystic_planks"),
				setup(shinyPlanks = new BlockKathairisPlanks(), "shiny_planks"),
				setup(soulPlanks = new BlockKathairisPlanks(), "soul_planks"),
				setup(elderwillowPlanks = new BlockKathairisPlanks(), "elderwillow_planks"),
				//ores and crystals
				setup(new BlockKathairisOre(1, 2, 4, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(7f)), "titanium_ore"),
				setup(new BlockKathairisOre(1, 1, 3, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(4f)), "revenum_ore"),
				setup(new BlockCrystal(), "blue_crystal"),
				setup(new BlockCrystal(), "violet_crystal"),
				setup(new BlockCrystal(), "yellow_crystal"),
				//building blocks
				setup(new BlockBaurble(), "baurble"),
				setup(new BlockLayeredSand(), "layered_sand"), //TODO ADD LOOT TABLE
				setup(new BlockSolisCrystals(), "solis_crystals"),
				setup(kathairisSandstone = new BlockKathairisSandstone(), "kathairis_sandstone"),
				setup(new BlockMysticGemBlock(), "mystic_gem_block"),
				setup(new BlockCondensedCloud(), "blue_cloud_bricks"),
				setup(new BlockCondensedCloud(), "yellow_cloud_block"),
				setup(kathairisStone = new BlockKathairisStone(), "kathairis_stone"),
				setup(new BlockKathairisDirt(), "kathairis_dirt"),
				setup(new BlockKathairisGrass(), "kathairis_grass"),
				setup(new BlockTitaniumBlock(), "titanium_block"),
				setup(new BlockKathairisSand(), "kathairis_sand"),
				setup(new BlockKathairisCloud(() -> ModItems.CLOUD_DUST_BLUE), "blue_cloud"),
				setup(new BlockSoftSand(), "soft_sand"),
				setup(new BlockKathairisPortal(), "kathairis_portal"),
				setup(new BlockKathairisCloud(() -> ModItems.CLOUD_DUST_YELLOW), "yellow_cloud"),
				setup(weatheredRock = new BlockWeatheredRock(), "weathered_rock"),
				setup(kathairisCobblestone = new BlockKathairisCobblestone(), "kathairis_cobblestone"),
				setup(new BlockKathairisStoneTiles(), "kathairis_stone_tiles"),
				setup(new BlockShinyRock(), "shiny_rock"),
				setup(kathairisStoneBricks = new BlockKathairisStoneBricks(), "kathairis_stone_bricks"),
				setup(new BlockMudBlock(), "mud_block"),
				setup(mudBricks = new BlockMudBricks(), "mud_bricks"),
				setup(new BlockMagnethium(), "magnethium"),
				setup(new BlockIronGoldBlock(), "iron_gold_block"),
				setup(hardenedWeatheredRock = new BlockHardenedWeatheredRock(), "hardened_weathered_rock"),
				setup(hardenedWeatheredRockBricks = new BlockHardenedWeatheredRockBricks(), "hardened_weathered_rock_bricks"),
				setup(new BlockHardenedWeatheredRockTiles(), "hardened_weathered_rock_tiles"),
				setup(new BlockRefinedCloud(), "blue_cloud_refined"),
				setup(new BlockRefinedCloud(), "yellow_cloud_refined"),
				setup(new BlockCondensedCloud(), "blue_cloud_condensed"),
				setup(new BlockCondensedCloud(), "yellow_cloud_condensed"),
				//fences and fence_gates
				setup(new BlockKathairisFence(Material.WOOD, 3f, SoundType.WOOD), "mystic_wood_fence"),
				setup(new BlockKathairisFenceGate(Material.WOOD, 3f, SoundType.WOOD), "mystic_wood_fence_gate"),
				setup(new BlockKathairisFence(Material.WOOD, 3f, SoundType.WOOD), "shiny_wood_fence"),
				setup(new BlockKathairisFenceGate(Material.WOOD, 3f, SoundType.WOOD), "shiny_wood_fence_gate"),
				setup(new BlockKathairisFence(Material.WOOD, 3f, SoundType.WOOD), "soul_wood_fence"),
				setup(new BlockKathairisFenceGate(Material.WOOD, 3f, SoundType.WOOD), "soul_wood_fence_gate"),
				setup(new BlockKathairisFence(Material.WOOD, 3f, SoundType.WOOD), "elderwillow_wood_fence"),
				setup(new BlockKathairisFenceGate(Material.WOOD, 3f, SoundType.WOOD), "elderwillow_wood_fence_gate"),
				//walls
				setup(new BlockKathairisWall(Material.ROCK, 2.5f, SoundType.STONE), "kathairis_stone_wall"),
				setup(new BlockKathairisWall(Material.ROCK, 2.5f, SoundType.STONE), "kathairis_cobblestone_wall"),
				setup(new BlockKathairisWall(Material.ROCK, 1.5f, SoundType.STONE), "weathered_rock_wall"),
				setup(new BlockKathairisWall(Material.ROCK, 2f, SoundType.STONE), "hardened_weathered_rock_wall"),
				setup(new BlockKathairisWall(Material.ROCK, 2f, SoundType.STONE), "hardened_weathered_rock_bricks_wall"),
				setup(new BlockKathairisWall(Material.ROCK, 2f, SoundType.STONE), "mud_bricks_wall"),
				setup(new BlockKathairisWall(Material.ROCK, 2.5f, SoundType.STONE), "kathairis_stone_bricks_wall"),
				setup(new BlockKathairisWall(Material.ROCK, 2.5f, SoundType.STONE), "kathairis_sandstone_wall"),
				//stairs
				setup(new BlockKathairisStairs(mysticPlanks.getDefaultState(), Material.WOOD, 3f, SoundType.WOOD), "mystic_wood_stairs"),
				setup(new BlockKathairisStairs(shinyPlanks.getDefaultState(), Material.WOOD, 3f, SoundType.WOOD), "shiny_wood_stairs"),
				setup(new BlockKathairisStairs(soulPlanks.getDefaultState(), Material.WOOD, 3f, SoundType.WOOD), "soul_wood_stairs"),
				setup(new BlockKathairisStairs(kathairisStone.getDefaultState(), Material.ROCK, 2.5f, SoundType.STONE), "kathairis_stone_stairs"),
				setup(new BlockKathairisStairs(kathairisCobblestone.getDefaultState(), Material.ROCK, 2.5f, SoundType.STONE), "kathairis_cobblestone_stairs"),
				setup(new BlockKathairisStairs(weatheredRock.getDefaultState(), Material.ROCK, 2.5f, SoundType.STONE), "weathered_rock_stairs"),
				setup(new BlockKathairisStairs(hardenedWeatheredRock.getDefaultState(), Material.ROCK, 1.5f, SoundType.STONE), "hardened_weathered_rock_stairs"),
				setup(new BlockKathairisStairs(hardenedWeatheredRockBricks.getDefaultState(), Material.ROCK, 2f, SoundType.STONE), "hardened_weathered_rock_bricks_stairs"),
				setup(new BlockKathairisStairs(mudBricks.getDefaultState(), Material.ROCK, 2f, SoundType.STONE), "mud_bricks_stairs"),
				setup(new BlockKathairisStairs(kathairisStoneBricks.getDefaultState(), Material.ROCK, 2f, SoundType.STONE), "kathairis_stone_bricks_stairs"),
				setup(new BlockKathairisStairs(elderwillowPlanks.getDefaultState(), Material.WOOD, 2f, SoundType.WOOD), "elderwillow_wood_stairs"),
				setup(new BlockKathairisStairs(kathairisSandstone.getDefaultState(), Material.ROCK, 2f, SoundType.STONE), "kathairis_sandstone_stairs"),
				//slabs
				setup(new BlockKathairisSlab(Material.ROCK, 2.5f, SoundType.STONE), "kathairis_stone_slab"),
				setup(new BlockKathairisSlab(Material.ROCK, 2.5f, SoundType.STONE), "kathairis_cobblestone_slab"),
				setup(new BlockKathairisSlab(Material.ROCK, 2.5f, SoundType.STONE), "kathairis_stone_bricks_slab"),
				setup(new BlockKathairisSlab(Material.ROCK, 2f, SoundType.STONE), "mud_bricks_slab"),
				setup(new BlockKathairisSlab(Material.WOOD, 3f, SoundType.WOOD), "mystic_wood_slab"),
				setup(new BlockKathairisSlab(Material.WOOD, 3f, SoundType.WOOD), "shiny_wood_slab"),
				setup(new BlockKathairisSlab(Material.WOOD, 3f, SoundType.WOOD), "soul_wood_slab"),
				setup(new BlockKathairisSlab(Material.ROCK, 1.5f, SoundType.STONE), "weathered_rock_slab"),
				setup(new BlockKathairisSlab(Material.ROCK, 2f, SoundType.STONE), "hardened_weathered_rock_slab"),
				setup(new BlockKathairisSlab(Material.ROCK, 2f, SoundType.STONE), "hardened_weathered_rock_bricks_slab"),
				setup(new BlockKathairisSlab(Material.WOOD, 3f, SoundType.WOOD), "elderwillow_wood_slab"),
				setup(new BlockKathairisSlab(Material.ROCK, 2f, SoundType.STONE), "kathairis_sandstone_slab"),
				//plants
				setup(new BlockBisonStars(), "bison_stars"),
				setup(new BlockKathairisPlant(), "eye_plant"),
				setup(new BlockKathairisMiniGrass(), "kathairis_mini_grass"),
				setup(new BlockKathairisTallGrass(), "kathairis_tall_grass"),
				setup(new BlockKathairisNightFlower(), "kathairis_night_flower"),
				setup(new BlockKathairisMultiGrass(), "kathairis_multi_grass"),
				setup(new BlockFrupPlant(), "frup_plant"),
				setup(new BlockKathairisFungi(), "kathairis_fungi"),
				setup(new BlockGooseberry(Material.LEAVES, 1f, 1f, SoundType.PLANT), "gooseberry_bush"),
				setup(new BlockKathairisSucculent(), "kathairis_succulent"),
				setup(new BlockSteppedSucculent(), "stepped_succulent"),
				setup(new BlockKathairisPlant(), "vilyria"),
				setup(new BlockGlowVines(), "glowvines"),
				setup(new BlockJadeVines(), "jade_vines"),
				setup(new BlockKathairisDeadGrass(), "kathairis_dead_grass"),
				setup(new BlockMagicBeans(), "magic_beans"),
				setup(new BlockButterflyFlower(), "butterfly_flower"),
				setup(new BlockCloudFlower(), "blue_cloud_flower"),
				setup(new BlockCloudFlower(), "yellow_cloud_flower"),
				setup(new BlockSnowdropCyprepedium(), "snowdrop_cyprepedium"),
				setup(new BlockFluoFungi(), "fluo_fungi"),
				setup(new BlockForestCandle(), "forest_candle"),
				setup(new BlockKathairisRocktus(), "rocktus"),
				setup(new BlockPurplePalm(),"purple_palm"),
				setup(new BlockBrinePustule(), "brine_pustule"),
				setup(new BlockWillowVineMain(), "willow_vine_main"),
				setup(new BlockWillowVineTip(), "willow_vine_tip"),
				setup(new BlockKatharianSapling(new MysticTree()), "mystic_sapling"),
				setup(new BlockKatharianSapling(new SoulTree()), "soul_sapling"),
				setup(new BlockKatharianSapling(new ShinyTree()), "shiny_sapling"),
				setup(new BlockKatharianSapling(new ElderwillowTree()), "elderwillow_sapling"),
				//doors and trapdoors
				setup(new BlockKathairisDoors(Material.WOOD, SoundType.WOOD, 3f), "mystic_wood_doors"),
				setup(new BlockKathairisDoors(Material.WOOD, SoundType.WOOD, 3f), "shiny_wood_doors"),
				setup(new BlockKathairisDoors(Material.WOOD, SoundType.WOOD, 3f), "soul_wood_doors"),
				setup(new BlockKathairisDoors(Material.WOOD, SoundType.WOOD, 3f), "elderwillow_wood_doors"),
				setup(new BlockKathairisTrapdoor(Material.WOOD, SoundType.WOOD, 3f), "mystic_wood_trapdoor"),
				setup(new BlockKathairisTrapdoor(Material.WOOD, SoundType.WOOD, 3f), "shiny_wood_trapdoor"),
				setup(new BlockKathairisTrapdoor(Material.WOOD, SoundType.WOOD, 3f), "soul_wood_trapdoor"),
				setup(new BlockKathairisTrapdoor(Material.WOOD, SoundType.WOOD, 3f), "elderwillow_wood_trapdoor")
				);

	}

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		registry.registerAll(
				setup(new ItemMysticGem(), "mystic_gem"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MATERIALS)), "titanium_ingot"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MATERIALS)), "titanium_rod"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MATERIALS)), "revenum_ingot"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS).rarity(Rarity.EPIC)), "cloud_essence"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS).rarity(Rarity.RARE)), "howler_fur"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MATERIALS).rarity(Rarity.RARE)), "crystal_cluster"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MATERIALS).rarity(Rarity.RARE)), "magnethium_shard"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "crystal_shard_yellow"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "crystal_shard_blue"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "crystal_shard_violet"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "tortoise_shell"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "butterfly_flower_nectar"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MATERIALS)), "iron_gold_ingot"),
				setup(new ItemKathairisArmor(ModArmorMaterials.TITANIUM, EquipmentSlotType.HEAD), "titanium_helmet"),
				setup(new ItemKathairisArmor(ModArmorMaterials.TITANIUM, EquipmentSlotType.CHEST), "titanium_chestplate"),
				setup(new ItemKathairisArmor(ModArmorMaterials.TITANIUM, EquipmentSlotType.LEGS), "titanium_leggings"),
				setup(new ItemKathairisArmor(ModArmorMaterials.TITANIUM, EquipmentSlotType.FEET), "titanium_boots"),
				setup(new ItemKathairisSword(ModItemTiers.TITANIUM), "titanium_sword"),
				setup(new ItemKathairisHoe(ModItemTiers.TITANIUM), "titanium_hoe"),
				setup(new ItemKathairisAxe(ModItemTiers.TITANIUM), "titanium_axe"),
				setup(new ItemKathairisPickaxe(ModItemTiers.TITANIUM), "titanium_pickaxe"),
				setup(new ItemKathairisShovel(ModItemTiers.TITANIUM), "titanium_shovel"),
				setup(new ItemKathairisArmor(ModArmorMaterials.REVENUM, EquipmentSlotType.HEAD), "revenum_helmet"),
				setup(new ItemKathairisArmor(ModArmorMaterials.REVENUM, EquipmentSlotType.CHEST), "revenum_chestplate"),
				setup(new ItemKathairisArmor(ModArmorMaterials.REVENUM, EquipmentSlotType.LEGS), "revenum_leggings"),
				setup(new ItemKathairisArmor(ModArmorMaterials.REVENUM, EquipmentSlotType.FEET), "revenum_boots"),
				setup(new ItemKathairisSword(ModItemTiers.REVENUM), "revenum_sword"),
				setup(new ItemKathairisHoe(ModItemTiers.REVENUM), "revenum_hoe"),
				setup(new ItemKathairisAxe(ModItemTiers.REVENUM), "revenum_axe"),
				setup(new ItemKathairisPickaxe(ModItemTiers.REVENUM), "revenum_pickaxe"),
				setup(new ItemKathairisShovel(ModItemTiers.REVENUM), "revenum_shovel"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS).rarity(Rarity.EPIC)), "skyray_feather"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS).rarity(Rarity.EPIC)), "solis_crystal"),
				setup(new ItemKathairisArmor(ModArmorMaterials.MYSTIC, EquipmentSlotType.HEAD), "mystic_helmet"),
				setup(new ItemKathairisArmor(ModArmorMaterials.MYSTIC, EquipmentSlotType.CHEST), "mystic_chestplate"),
				setup(new ItemKathairisArmor(ModArmorMaterials.MYSTIC, EquipmentSlotType.LEGS), "mystic_leggings"),
				setup(new ItemKathairisArmor(ModArmorMaterials.MYSTIC, EquipmentSlotType.FEET), "mystic_boots"),
				setup(new ItemKathairisSword(ModItemTiers.MYSTIC), "mystic_sword"),
				setup(new ItemKathairisHoe(ModItemTiers.MYSTIC), "mystic_hoe"),
				setup(new ItemKathairisAxe(ModItemTiers.MYSTIC), "mystic_axe"),
				setup(new ItemKathairisPickaxe(ModItemTiers.MYSTIC), "mystic_pickaxe"),
				setup(new ItemKathairisShovel(ModItemTiers.MYSTIC), "mystic_shovel"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS).rarity(Rarity.EPIC)), "cloud_pearl"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MATERIALS)), "shiny_stick"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "cloud_dust_blue"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "cloud_dust_yellow"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "condensed_cloud_dust_blue"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "condensed_cloud_dust_yellow"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "jar_with_swamp_gas"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "crystal_blend"),
				setup(new Item(new Item.Properties().food(ModFoods.HEART).group(ModItemGroups.FOOD)), "heart"),
				setup(new Item(new Item.Properties().food(ModFoods.COTTON_CANDY).group(ModItemGroups.FOOD)), "cotton_candy"),
				setup(new Item(new Item.Properties().food(ModFoods.BISON_MEAT).group(ModItemGroups.FOOD)), "bison_meat"),
				setup(new Item(new Item.Properties().food(ModFoods.COOKED_BISON_MEAT).group(ModItemGroups.FOOD)), "cooked_bison_meat"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS).rarity(Rarity.RARE)), "jellyfish_tentacle"),
				setup(new BlockNamedItem(ModBlocks.GOOSEBERRY_BUSH, new Item.Properties().food(ModFoods.GOOSEBERRY).group(ModItemGroups.FOOD)), "gooseberries"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "venom_sac"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "glass_jar"),
				setup(new Item(new Item.Properties().group(ModItemGroups.FOOD).food(ModFoods.NECTAR_BOWL)), "nectar_bowl"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS).rarity(Rarity.COMMON)), "butterfly_common_1"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS).rarity(Rarity.COMMON)), "butterfly_common_2"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS).rarity(Rarity.COMMON)), "butterfly_common_moth"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS).rarity(Rarity.UNCOMMON)), "butterfly_illukini"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS).rarity(Rarity.RARE)), "butterfly_cloud_shimmer"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "butterfly_catcher"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "wings_piece"),
				setup(new Item(new Item.Properties().group(ModItemGroups.FOOD).food(ModFoods.FUNGAL_DRUG)), "fungal_drug"),
				setup(new Item(new Item.Properties().food(ModFoods.EASTER_FOOD).group(ModItemGroups.FOOD)), "bitten_cookie"),
				setup(new Item(new Item.Properties().food(ModFoods.EASTER_FOOD).group(ModItemGroups.FOOD)), "candy_cane"),
				setup(new Item(new Item.Properties().food(ModFoods.EASTER_FOOD).group(ModItemGroups.FOOD)), "christmas_chocolate"),
				setup(new Item(new Item.Properties().food(ModFoods.EASTER_FOOD).group(ModItemGroups.FOOD)), "ice_creams"),
				setup(new Item(new Item.Properties().food(ModFoods.EASTER_FOOD).group(ModItemGroups.FOOD)), "sweet_muffin"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MISCELLANEOUS)), "pot_with_living_flower"),
				setup(new ItemKathairisPickaxe(ModItemTiers.MAGNETHIUM), "magnethium_pickaxe"),
				setup(new ItemKathairisAxe(ModItemTiers.MAGNETHIUM), "magnethium_axe"),
				setup(new ItemKathairisShovel(ModItemTiers.MAGNETHIUM), "magnethium_shovel"),
				setup(new ItemKathairisSword(ModItemTiers.MAGNETHIUM), "magnethium_sword"),
				setup(new ItemKathairisHoe(ModItemTiers.MAGNETHIUM), "magnethium_hoe"),
				setup(new ItemKathairisPickaxe(ModItemTiers.CRYSTAL), "crystal_pickaxe"),
				setup(new BlockNamedItem(ModBlocks.FRUP_PLANT, new Item.Properties().food(ModFoods.FRUP).group(ModItemGroups.FOOD)), "frup"),
				setup(new BlockNamedItem(ModBlocks.MAGIC_BEANS, new Item.Properties().food(ModFoods.MAGIC_BEANS).group(ModItemGroups.FOOD)), "magic_beans"),
				setup(new ItemMysticWand(ModItemGroups.WEAPONS),"mystic_wand"),
				setup(new BlockNamedItem(ModBlocks.BRINE_PUSTULE, new Item.Properties().food(ModFoods.MINERAL_FRUIT).group(ModItemGroups.FOOD)),"mineral_fruit")
		);
		//ModEntities.registerEggs(event);
		for (final Block block : ModUtil.getModEntries(ForgeRegistries.BLOCKS)) {

			if(IItemGroupProvider.class.isAssignableFrom(block.getClass())){
				registry.register(setup(new BlockItem(block, new Item.Properties().group(((IItemGroupProvider)(block)).getItemGroup())), block.getRegistryName()));
			}
		}

	}


	@SubscribeEvent
	public static void onRegisterSounds(final RegistryEvent.Register<SoundEvent> event) {

		event.getRegistry().registerAll(
				setupSound("mob", "bird"),

				setupSound("mob", "howler", "living"),
				setupSound("mob", "howler", "hurt"),
				setupSound("mob", "howler", "dead"),

				setupSound("mob", "jellyfish", "living"),
				setupSound("mob", "jellyfish", "hurt"),
				setupSound("mob", "jellyfish", "dead"),

				setupSound("mob", "turtle", "dead"),

				setupSound("mob", "bison", "living"),
				setupSound("mob", "bison", "hurt"),
				setupSound("mob", "bison", "dead"),

				setupSound("mob", "ghost", "living"),
				setupSound("mob", "ghost", "attack"),
				setupSound("mob", "ghost", "dead"),

				setupSound("mob", "death", "living"),

				setupSound("mob", "camel", "ambient"),
				setupSound("mob", "camel", "breath"),
				setupSound("mob", "camel", "hurt"),
				setupSound("mob", "camel", "dead"),

				setupSound("mob", "oldman", "ambient"),

				setupSound("mob", "skyray", "ambient"),
				setupSound("mob", "skyray", "hurt"),

				//

				setupSound("scary", "flower"),

				//Hardcoding "kathairis" because its referring to the dimension not the mod
				setupSound("kathairis", "music", "day"),
				setupSound("kathairis", "music", "night"),
				setupSound("kathairis", "music", "xmas"),

				setupSound("music_disc", "jazzy"),
				setupSound("music_disc", "8bit"),

				setupSound("pickaxe", "turn"),
				setupSound("magic_shoot"),

				setupSound("sandstorm")

		);

	}

	@SubscribeEvent
	public static void onRegisterModDimensions(final RegistryEvent.Register<ModDimension> event) {
		event.getRegistry().registerAll(
						new ModDimensionKathairis(ModReference.KATHAIRIS)
		);
	}

	/*@SubscribeEvent
	public static void onDimensionTypeRegister(final RegistryEvent.Register<DimensionType> event){
		event.getRegistry().registerAll(
				setup(Kathairis.KATHAIRIS=DimensionManager.registerDimension(ModReference.KATHAIRIS, ModDimensions.KATHAIRIS, new PacketBuffer(Unpooled.buffer(16)),true),ModReference.KATHAIRIS)
		);
	}*/

	@SubscribeEvent
	public static void onFMLCommonSetup(final FMLCommonSetupEvent event){
		PacketHandler.register();
		Kathairis.KATHAIRIS=DimensionManager.registerDimension(ModReference.KATHAIRIS, ModDimensions.KATHAIRIS, new PacketBuffer(Unpooled.buffer(16)),true);
	}

	@SubscribeEvent
	public static void onDimensionRegisterEvent(final RegisterDimensionsEvent event){
		Kathairis.KATHAIRIS=DimensionManager.registerDimension(ModReference.KATHAIRIS, ModDimensions.KATHAIRIS, new PacketBuffer(Unpooled.buffer(16)),true);
	}

	@SubscribeEvent
	public static void onRegisterBiomes(final RegistryEvent.Register<Biome> event) {
		event.getRegistry().registerAll(
				setup(new BiomeKatharianRiver(),"kathairis_river"),
				setup(new BiomeKatharianDesert(),"katharian_desert"),
				setup(new BiomeKatharianDesertEdge(),"katharian_desert_edge"),
				setup(new BiomeKatharianSoftSandLakes(),"soft_sand_lakes"),
				setup(new BiomeKatharianForest(),"katharian_forest"),
				setup(new BiomeKatharianDenseForest(),"katharian_dense_forest"),
				setup(new BiomeKatharianPlainFields(),"plain_fields"),
				setup(new BiomeKatharianSwamps(),"katharian_swamp"),
				setup(new BiomeKatharianHugeDesertMountains(),"huge_desert_mountains")
		);
	}

	@SubscribeEvent
	public static void onRegisterEnchantements(final RegistryEvent.Register<Enchantment> event){
		event.getRegistry().registerAll(
				setup(KathairisEnchantments.ENCHANTMENT_ETHEREAL,"ethereal")
		);
	}

	@SubscribeEvent
	public static void onRegisterParticleTypes(final RegistryEvent.Register<ParticleType<?>> event){
		//ModParticles.registerParticles();
	}


	@SubscribeEvent
	public static void onRegisterEntityTypes(final RegistryEvent.Register<EntityType<?>> event){
		event.getRegistry().registerAll(
				setup(ModEntities.COMMON_BUTTERFLY1=EntityType.Builder.<EntityButterfly>create(EntityButterfly::new, EntityClassification.AMBIENT).size(0.6F,0.5F).build("kathairis:common_butterfly1"),"common_butterfly1"),
				setup(ModEntities.COMMON_BUTTERFLY2=EntityType.Builder.<EntityButterfly1>create(EntityButterfly1::new, EntityClassification.AMBIENT).size(0.6F,0.5F).build("kathairis:common_butterfly2"),"common_butterfly2"),
				setup(ModEntities.CLOUD_SHIMMER=EntityType.Builder.<EntityCloudShimmer>create(EntityCloudShimmer::new, EntityClassification.AMBIENT).size(0.6F,0.5F).build("kathairis:cloud_shimmer"),"cloud_shimmer"),
				setup(ModEntities.ILLUKINI=EntityType.Builder.<EntityIllukini>create(EntityIllukini::new, EntityClassification.AMBIENT).size(0.6F,0.5F).setTrackingRange(200).build("kathairis:illukini"),"illukini"),
				setup(ModEntities.RUBY_SILE=EntityType.Builder.<EntityRubySile>create(EntityRubySile::new, EntityClassification.AMBIENT).size(0.6F,0.5F).setTrackingRange(200).build("kathairis:ruby_sile"),"ruby_sile"),
				setup(ModEntities.SKYLIGHT=EntityType.Builder.<EntitySkylight>create(EntitySkylight::new, EntityClassification.AMBIENT).size(0.15F,0.15F).setTrackingRange(200).build("kathairis:skylight"),"skylight"),
				setup(ModEntities.BIG_TURTLE=EntityType.Builder.<EntityBigTurtle>create(EntityBigTurtle::new, EntityClassification.CREATURE).size(0.9F,1.2F).setTrackingRange(200).build("kathairis:big_turtle"),"big_turtle"),
				setup(ModEntities.BISON=EntityType.Builder.<EntityBison>create(EntityBison::new, EntityClassification.CREATURE).size(1.5F,1.7F).setTrackingRange(200).build("kathairis:bison"),"bison"),
				setup(ModEntities.CACTI_SPORE=EntityType.Builder.<EntityCactiSpore>create(EntityCactiSpore::new, EntityClassification.MONSTER).size(1F,1F).setTrackingRange(200).build("kathairis:cacti_spore"),"cacti_spore"),
				setup(ModEntities.CAMEL=EntityType.Builder.<EntityCamel>create(EntityCamel::new, EntityClassification.CREATURE).size(1.6F,1.5F).setTrackingRange(200).build("kathairis:camel"),"camel"),
				setup(ModEntities.CLOUD_OISTER=EntityType.Builder.<EntityCloudOister>create(EntityCloudOister::new, EntityClassification.CREATURE).size(0.6F,0.6F).setTrackingRange(200).build("kathairis:cloud_oister"),"cloud_oister"),
				setup(ModEntities.CLOUDY_SLIME=EntityType.Builder.<EntityCloudySlime>create(EntityCloudySlime::new, EntityClassification.CREATURE).size(1.4F,1.4F).setTrackingRange(200).build("kathairis:cloudy_slime"),"cloudy_slime"),
				setup(ModEntities.FLYING_SQUID=EntityType.Builder.<EntityFlyingSquid>create(EntityFlyingSquid::new, EntityClassification.CREATURE).size(1.5F,2F).setTrackingRange(200).build("kathairis:flying_squid"),"flying_squid"),
				setup(ModEntities.FUNGITE=EntityType.Builder.<EntityFungite>create(EntityFungite::new, EntityClassification.MONSTER).size(1.5F,2.4F).setTrackingRange(200).build("kathairis:fungite"),"fungite"),
				setup(ModEntities.GAZNOWEL=EntityType.Builder.<EntityGaznowel>create(EntityGaznowel::new, EntityClassification.MONSTER).size(1F,2F).setTrackingRange(200).build("kathairis:gaznowel"),"gaznowel"),
				setup(ModEntities.GECKO=EntityType.Builder.<EntityGecko>create(EntityGecko::new, EntityClassification.CREATURE).size(0.7F,0.25F).setTrackingRange(200).build("kathairis:gecko"),"gecko"),
				setup(ModEntities.HOWLER=EntityType.Builder.<EntityHowler>create(EntityHowler::new, EntityClassification.MONSTER).size(0.85F,1F).setTrackingRange(200).build("kathairis:howler"),"howler"),
				setup(ModEntities.JELLY_FISH=EntityType.Builder.<EntityJellyFish>create(EntityJellyFish::new, EntityClassification.MONSTER).size(1f,1f).setTrackingRange(200).build("kathairis:jelly_fish"),"jelly_fish"),
				setup(ModEntities.LIVING_FLOWER=EntityType.Builder.<EntityLivingFlower>create(EntityLivingFlower::new, EntityClassification.AMBIENT).size(0.3F,0.5F).setTrackingRange(200).build("kathairis:living_flower"),"living_flower"),
				setup(ModEntities.MYSTIC_BIRD=EntityType.Builder.<EntityMysticBird>create(EntityMysticBird::new, EntityClassification.AMBIENT).size(0.5F,0.7F).setTrackingRange(200).build("kathairis:mystic_bird"),"mystic_bird"),
				setup(ModEntities.PHASM=EntityType.Builder.<EntityPhasm>create(EntityPhasm::new, EntityClassification.MONSTER).size(1F,2F).setTrackingRange(200).build("kathairis:phasm"),"phasm"),
				setup(ModEntities.POISONOUS_SCORPION=EntityType.Builder.<EntityPoisonousScorpion>create(EntityPoisonousScorpion::new, EntityClassification.MONSTER).size(0.4F,0.7F).setTrackingRange(200).build("kathairis:poisonous_scorpion"),"poisonous_scorpion"),
				setup(ModEntities.SKYRAY=EntityType.Builder.<EntitySkyray>create(EntitySkyray::new, EntityClassification.AMBIENT).size(10F,2.5F).setTrackingRange(200).build("kathairis:skyray"),"skyray"),
				setup(ModEntities.STRANGE_WANDERER=EntityType.Builder.<EntityStrangeWanderer>create(EntityStrangeWanderer::new, EntityClassification.AMBIENT).size(1F,2F).setTrackingRange(200).build("kathairis:strange_wanderer"),"strange_wanderer"),
				setup(ModEntities.MYSTIC_WAND_SHOOT=EntityType.Builder.<EntityMysticWandShoot>create(EntityMysticWandShoot::new, EntityClassification.MISC).size(1F,1F).setTrackingRange(200).build("kathairis:mystic_wand_shoot"),"mystic_wand_shoot")
		);
		ModEntities.registerPlacementTypes();
		ModEntities.registerEntitySpawns();
	}


	public static SoundEvent setupSound(@Nonnull final String... nameParts) {
		final ResourceLocation name = new ResourceLocation(MOD_ID, Strings.join(nameParts, "."));
		final String registryName = Strings.join(nameParts, "_");
		return setup(new SoundEvent(name), registryName);
	}

	public static <T extends IForgeRegistryEntry> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(MOD_ID, name));
	}

	public static <T extends IForgeRegistryEntry> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}

}
