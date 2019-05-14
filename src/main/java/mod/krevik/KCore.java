package mod.krevik;

import mod.krevik.biome.*;
import mod.krevik.block.*;
import mod.krevik.block.plants.*;
import mod.krevik.capability.CapabilityHandler;
import mod.krevik.command.SandstormCommand;
import mod.krevik.item.*;
import mod.krevik.network.KathairisPacketHandler;
import mod.krevik.potion.DissolutionPotion;
import mod.krevik.potion.StunPotion;
import mod.krevik.recipe.ChargerRecipes;
import mod.krevik.recipe.RecipeHandler;
import mod.krevik.util.CreativeTabsMystic;
import mod.krevik.util.FunctionHelper;
import mod.krevik.util.IProxy;
import mod.krevik.util.RegistryHelper;
import mod.krevik.world.dimension.KathairisDataStorage;
import mod.krevik.world.dimension.WorldProviderMystic;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = KCore.MODID, name = KCore.NAME, version = KCore.VERSION, acceptedMinecraftVersions = "[1.12]")
public class KCore {

	@SidedProxy(serverSide = "mod.krevik.server.ServerProxy", clientSide = "mod.krevik.client.ClientProxy")
	public static IProxy proxy;
	
	public static final String MODID = "kathairis";
	public static final String NAME = "Kathairis";
	public static final String VERSION = "@VERSION@";
	
	@Mod.Instance(MODID)
	public static KCore instance;
	
	public static final Logger LOGGER = LogManager.getLogger(KCore.MODID);

    public static final RegistryHelper regHelper = new RegistryHelper();


    public static final int DIMENSION_ID = DimensionManager.getNextFreeDimId();
    public static final DimensionType Mystic_DIMENSION = DimensionType.register("KATHAIRIS", "_kathairis", DIMENSION_ID, WorldProviderMystic.class, false);
    public static int updateRendererCount=0;

	public static Item.ToolMaterial TITANIUM = EnumHelper.addToolMaterial("titanium", 3, 1000, 7F, 2.5F, 12).setRepairItem(new ItemStack(KCore.TitaniumIngot));
	public static ItemArmor.ArmorMaterial TITANIUMARMOR = EnumHelper.addArmorMaterial("titanium", KCore.MODID+":titanium", 22, new int[]{3, 5, 7, 3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1F).setRepairItem(new ItemStack(KCore.TitaniumIngot));
	
	public static Item.ToolMaterial MYSTIC = EnumHelper.addToolMaterial("mystic", 3, 2000, 12F, 3.5F, 12).setRepairItem(new ItemStack(KCore.MysticGem));
	public static ItemArmor.ArmorMaterial MYSTICARMOR = EnumHelper.addArmorMaterial("mystic", KCore.MODID+":mystic", 30, new int[]{3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2F).setRepairItem(new ItemStack(KCore.MysticGem));
	
	public static ItemArmor.ArmorMaterial CLOUDARMOR = EnumHelper.addArmorMaterial("cloud", KCore.MODID+":cloud", 5, new int[]{1, 2, 3, 1}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0F).setRepairItem(new ItemStack(KCore.CloudPearl));

	public static Item.ToolMaterial REVENUM = EnumHelper.addToolMaterial("revenum", 3, 800, 7F, 3F, 18).setRepairItem(new ItemStack(KCore.RevenumIngot));
	public static ItemArmor.ArmorMaterial REVENUMARMOR = EnumHelper.addArmorMaterial("revenum", KCore.MODID+":revenum", 15, new int[]{3, 5, 7, 3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2F).setRepairItem(new ItemStack(KCore.RevenumIngot));
	
	public static Item.ToolMaterial CRYSTAL = EnumHelper.addToolMaterial("crystal", 3, 1000, 2F, 4F, 22).setRepairItem(new ItemStack(KCore.CrystalsCluster));
	public static Item.ToolMaterial DARKNESS = EnumHelper.addToolMaterial("darkness", 3, 100, 1F, 3.5F, 21).setRepairItem(new ItemStack(KCore.DarknessEssence));
	public static Item.ToolMaterial MAGNETHIUM = EnumHelper.addToolMaterial("magnethium", 3, 800, 6F, 3F, 20).setRepairItem(new ItemStack(KCore.Magnethium_Shard));
    public static Item.ToolMaterial DEATH = EnumHelper.addToolMaterial("death", 3, 800, 6F, 3F, 30);


	@GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticPortal)
    public static final BlockMysticPortal MysticPortal = (BlockMysticPortal) new BlockMysticPortal(Ref.MysticPortal, null,-1f,-1f, SoundType.GLASS).setLightLevel(1F);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.KatharianDirt)
    public static final BaseBlock KatharianDirt = new BaseBlock(Ref.KatharianDirt, Material.GROUND, CreativeTabsMystic.buildingBlocks, 2F, 2F, SoundType.GROUND);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.KatharianGrass)
    public static final BlockKatharianGrass KatharianGrass = new BlockKatharianGrass();
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MythicStone)
    public static final BaseBlock MythicStone = new BaseBlock(Ref.MythicStone, Material.ROCK, CreativeTabsMystic.buildingBlocks, 2.5F, 2.5F, SoundType.STONE);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticPlanks)
    public static final BlockMysticPlanks MysticPlanks = new BlockMysticPlanks(Ref.MysticPlanks);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticLog)
    public static final BlockMysticLog MysticLog = new BlockMysticLog(Ref.MysticLog);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticLeaves)
    public static final BlockMysticLeafWithChristmas MysticLeaves = new BlockMysticLeafWithChristmas(Ref.MysticLeaves, Material.LEAVES, CreativeTabsMystic.buildingBlocks);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticSapling)
    public static final BlockMysticSapling MysticSapling = new BlockMysticSapling(Ref.MysticSapling);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticFungus)
    public static final BlockMysticFungus MysticFungus = (BlockMysticFungus) new BlockMysticFungus(Ref.MysticFungus).setLightLevel(0.9F);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticMiniGrass)
    public static final BlockMysticMiniGrass MysticMiniGrass= new BlockMysticMiniGrass(Ref.MysticMiniGrass);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticTallGrass)
    public static final BlockMysticTallGrass MysticTallGrass= new BlockMysticTallGrass(Ref.MysticTallGrass,true);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticFlower)
    public static final BlockMysticFlower MysticFlower = (BlockMysticFlower) new BlockMysticFlower(Ref.MysticFlower).setLightLevels(0.9F);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticNightFlower)
    public static final BlockMysticNightFlower MysticNightFlower = new BlockMysticNightFlower(Ref.MysticNightFlower);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumOre)
    public static final BlockMysticOre TitaniumOre = new BlockMysticOre(Ref.TitaniumOre,5F,15F,1,1,0,0);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumBlock)
    public static final BaseBlock TitaniumBlock = new BaseBlock(Ref.TitaniumBlock,Material.IRON,CreativeTabsMystic.buildingBlocks,7F,20F,SoundType.METAL);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticMultiGrass)
    public static final BlockMysticMultiGrass MysticMultiGrass= new BlockMysticMultiGrass(Ref.MysticMultiGrass,false);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MythicStoneTiles)
    public static final BaseBlock MythicStoneTiles = new BaseBlock(Ref.MythicStoneTiles, Material.ROCK, CreativeTabsMystic.buildingBlocks, 2.5F, 2.5F, SoundType.STONE);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShinyLog)
    public static final BlockMysticLog ShinyLog = (BlockMysticLog) new BlockMysticLog(Ref.ShinyLog).setLightLevel(0.3F);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShinyLeaves)
    public static final BlockMysticLeaf ShinyLeaves = (BlockMysticLeaf) new BlockMysticLeaf(Ref.ShinyLeaves, Material.LEAVES, CreativeTabsMystic.buildingBlocks).setLightLevel(0.3F);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShinyPlanks)
    public static final BlockMysticPlanks ShinyPlanks = (BlockMysticPlanks) new BlockMysticPlanks(Ref.ShinyPlanks).setLightLevel(0.3F);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ForgottenSand)
    public static final BlockKatharianSand ForgottenSand = new BlockKatharianSand(Ref.ForgottenSand, Material.GROUND, CreativeTabsMystic.buildingBlocks, 1.5F, 1.5F, SoundType.GROUND);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MovingSand)
    public static final BaseBlock MovingSand = new BaseBlock(Ref.MovingSand, Material.GOURD, CreativeTabsMystic.buildingBlocks, 1.5F, 1.5F, SoundType.GROUND);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.WeatheredRock)
    public static final BaseBlock WeatheredRock = new BaseBlock(Ref.WeatheredRock, Material.ROCK, CreativeTabsMystic.buildingBlocks, 1.5F, 1.5F, SoundType.STONE);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.HardenedWeatheredRock)
    public static final BaseBlock HardenedWeatheredRock = new BaseBlock(Ref.HardenedWeatheredRock, Material.ROCK, CreativeTabsMystic.buildingBlocks, 2.5F, 2.5F, SoundType.STONE);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.WeatheredRockTiles)
    public static final BaseBlock WeatheredRockTiles = new BaseBlock(Ref.WeatheredRockTiles, Material.ROCK, CreativeTabsMystic.buildingBlocks, 2.5F, 2.5F, SoundType.STONE);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.WeatheredRockBricks)
    public static final BaseBlock WeatheredRockBricks = new BaseBlock(Ref.WeatheredRockBricks, Material.ROCK, CreativeTabsMystic.buildingBlocks, 2.5F, 2.5F, SoundType.STONE);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticDeadGrass)
    public static final BlockMysticDeadGrass MysticDeadGrass = new BlockMysticDeadGrass(Ref.MysticDeadGrass,true);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.GemsOre)
    public static final BlockMysticOre GemsOre = new BlockMysticOre(Ref.GemsOre,5F,15F,1,1,5,10);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShinyRock)
    public static final BaseBlock ShinyRock = (BaseBlock)new BaseBlock(Ref.ShinyRock, Material.ROCK, CreativeTabsMystic.buildingBlocks, 5F, 5F, SoundType.STONE).setLightLevel(2F);
    
    @GameRegistry.ObjectHolder(KCore.MODID+":"+Ref.BlueCloud)
    public static final BlockMysticCloud BlueCloud = (BlockMysticCloud) new BlockMysticCloud(Ref.BlueCloud).setLightLevel(0.1F).setLightOpacity(0);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.RevenumOre)
    public static final BlockMysticOre RevenumOre = new BlockMysticOre(Ref.RevenumOre,25F,50F,1,1,0,0);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.VioletCrystal)
    public static final BlockCrystal VioletCrystal = (BlockCrystal) new BlockCrystal(Ref.VioletCrystal).setLightOpacity(0);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.YellowCrystal)
    public static final BlockCrystal YellowCrystal = (BlockCrystal) new BlockCrystal(Ref.YellowCrystal).setLightOpacity(0);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.BlueCrystal)
    public static final BlockCrystal BlueCrystal = (BlockCrystal) new BlockCrystal(Ref.BlueCrystal).setLightOpacity(0);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.BlueFruitPlant)
    public static final BlockBlueFruitPlant BlueFruitPlant = (BlockBlueFruitPlant) new BlockBlueFruitPlant(Ref.BlueFruitPlant).setLightOpacity(0);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.EyePlant)
    public static final BlockEyePlant EyePlant = new BlockEyePlant(Ref.EyePlant);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MagicBeans)
    public static final BlockMagicBeans MagicBeans = (BlockMagicBeans) new BlockMagicBeans(Ref.MagicBeans).setLightOpacity(0);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MythicStonePillar)
    public static final BlockMythicStonePillar MythicStonePillar = new BlockMythicStonePillar(Ref.MythicStonePillar);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.HardenedWeatheredRockStairs)
    public static final BlockMysticStairs HardenedWeatheredRockStairs = new BlockMysticStairs(HardenedWeatheredRock.getDefaultState(),Ref.HardenedWeatheredRockStairs,Material.ROCK,CreativeTabsMystic.buildingBlocks,2.5F,2.5F,SoundType.STONE);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticWoodStairs)
    public static final BlockMysticStairs MysticWoodStairs = new BlockMysticStairs(MysticPlanks.getDefaultState(),Ref.MysticWoodStairs,Material.WOOD,CreativeTabsMystic.buildingBlocks,4F,4F,SoundType.WOOD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShinyWoodStairs)
    public static final BlockMysticStairs ShinyWoodStairs = new BlockMysticStairs(ShinyPlanks.getDefaultState(),Ref.ShinyWoodStairs,Material.WOOD,CreativeTabsMystic.buildingBlocks,4F,4F,SoundType.WOOD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.JadeVine_empty)
    public static final BlockJadeVines JadeVine_empty = new BlockJadeVines(Ref.JadeVine_empty,CreativeTabsMystic.plants,0.1F,0.1F,SoundType.PLANT);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.JadeVine_top)
    public static final BlockJadeVines JadeVine_top = new BlockJadeVines(Ref.JadeVine_top,null,0.1F,0.1F,SoundType.PLANT);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.JadeVine_mid)
    public static final BlockJadeVines JadeVine_mid = new BlockJadeVines(Ref.JadeVine_mid,null,0.1F,0.1F,SoundType.PLANT);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.JadeVine_bottom)
    public static final BlockJadeVines JadeVine_bottom = new BlockJadeVines(Ref.JadeVine_bottom,null,0.1F,0.1F,SoundType.PLANT);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MythicStoneStairs)
    public static final BlockMysticStairs MythicStoneStairs = new BlockMysticStairs(MythicStone.getDefaultState(),Ref.MythicStoneStairs,Material.ROCK,CreativeTabsMystic.buildingBlocks,2.5F,2.5F,SoundType.STONE);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticWoodFence)
    public static final BlockMysticFence MysticWoodFence = new BlockMysticFence(Ref.MysticWoodFence,Material.WOOD,CreativeTabsMystic.buildingBlocks,4F,4F,SoundType.WOOD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShinyWoodFence)
    public static final BlockMysticFence ShinyWoodFence = new BlockMysticFence(Ref.ShinyWoodFence,Material.WOOD,CreativeTabsMystic.buildingBlocks,4F,4F,SoundType.WOOD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShinyWoodFenceGate)
    public static final BlockMysticFenceGate ShinyWoodFenceGate = new BlockMysticFenceGate(Ref.ShinyWoodFenceGate,Material.WOOD,CreativeTabsMystic.buildingBlocks,4F,4F,SoundType.WOOD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticWoodFenceGate)
    public static final BlockMysticFenceGate MysticWoodFenceGate = new BlockMysticFenceGate(Ref.MysticWoodFenceGate,Material.WOOD,CreativeTabsMystic.buildingBlocks,4F,4F,SoundType.WOOD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShinyTreeSapling)
    public static final BlockMysticSapling ShinyTreeSapling = new BlockMysticSapling(Ref.ShinyTreeSapling);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MythicStoneWall)
    public static final BlockMysticWall MythicStoneWall = new BlockMysticWall(KCore.MythicStone,Ref.MythicStoneWall,Material.ROCK,CreativeTabsMystic.buildingBlocks,2.5F,2.5F,SoundType.STONE);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.HardenedWeatheredRockWall)
    public static final BlockMysticWall HardenedWeatheredRockWall = new BlockMysticWall(KCore.HardenedWeatheredRock,Ref.HardenedWeatheredRockWall,Material.ROCK,CreativeTabsMystic.buildingBlocks,2.5F,2.5F,SoundType.STONE);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MudBlock)
    public static final BaseBlock MudBlock = new BaseBlock(Ref.MudBlock, Material.GROUND, CreativeTabsMystic.buildingBlocks, 1.5F, 1.5F, SoundType.GROUND);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MudBricks)
    public static final BaseBlock MudBricks = new BaseBlock(Ref.MudBricks, Material.ROCK, CreativeTabsMystic.buildingBlocks, 2F, 2F, SoundType.STONE);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ButterflyFlower)
    public static final BlockButterflyFlower ButterflyFlower = new BlockButterflyFlower(Ref.ButterflyFlower,false);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.BlueCloudBricks)
    public static final BaseBlock BlueCloudBricks = new BaseBlock(Ref.BlueCloudBricks, Material.CLOTH, CreativeTabsMystic.buildingBlocks, 1F, 1F, SoundType.CLOTH);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.BlueCondensedCloud)
    public static final BaseBlock BlueCondensedCloud = new BaseBlock(Ref.BlueCondensedCloud, Material.CLOTH, CreativeTabsMystic.buildingBlocks, 1F, 1F, SoundType.CLOTH);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.YellowCloud)
    public static final BlockMysticCloud YellowCloud = new BlockMysticCloud(Ref.YellowCloud);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.YellowCloudBlock)
    public static final BaseBlock YellowCloudBlock = new BaseBlock(Ref.YellowCloudBlock, Material.CLOTH, CreativeTabsMystic.buildingBlocks, 1F, 1F, SoundType.CLOTH);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.YellowCondensedCloud)
    public static final BaseBlock YellowCondensedCloud = new BaseBlock(Ref.YellowCondensedCloud, Material.CLOTH, CreativeTabsMystic.buildingBlocks, 1F, 1F, SoundType.CLOTH);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.CloudParticleEmitter)
    public static final BlockCloudParticleEmitter CloudParticleEmitter = new BlockCloudParticleEmitter(Ref.CloudParticleEmitter);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Succulent)
    public static final BlockSucculent Succulent = new BlockSucculent(Ref.Succulent);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.GooseberryBlock)
    public static final BlockGooseberry GooseberryBlock = new BlockGooseberry(Ref.GooseberryBlock);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Charger)
    public static final BlockCharger Charger = (BlockCharger)new BlockCharger(Ref.Charger).setLightOpacity(0);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.EnergyShard)
    public static final BlockEnergyShard EnergyShard = new BlockEnergyShard(Ref.EnergyShard);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SwampGas)
    public static final BlockSwampGas SwampGas = new BlockSwampGas(Ref.SwampGas);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.EasterEgg)
    public static final BlockEasterEgg EasterEgg = new BlockEasterEgg(Ref.EasterEgg);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SoulPlanks)
    public static final BlockMysticPlanks SoulPlanks = new BlockMysticPlanks(Ref.SoulPlanks);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SoulLog)
    public static final BlockMysticLog SoulLog = new BlockMysticLog(Ref.SoulLog);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SoulLeaves)
    public static final BlockMysticLeaf SoulLeaves = new BlockMysticLeaf(Ref.SoulLeaves, Material.LEAVES, CreativeTabsMystic.buildingBlocks);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SoulSapling)
    public static final BlockMysticSapling SoulSapling = new BlockMysticSapling(Ref.SoulSapling);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TurtleShellPlate)
    public static final BlockTurtleShellPlate TurtleShellPlate = new BlockTurtleShellPlate(Ref.TurtleShellPlate,Material.IRON,CreativeTabsMystic.buildingBlocks,4F,9999F,SoundType.METAL);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.LuminescentGnarl)
    public static final BlockLuminescentGnarl LuminescentGnarl = new BlockLuminescentGnarl(Ref.LuminescentGnarl,Material.WOOD,CreativeTabsMystic.plants,1F,1F,SoundType.WOOD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.BoneBricks)
    public static final BaseBlock BoneBricks = new BaseBlock(Ref.BoneBricks, Material.ROCK, CreativeTabsMystic.buildingBlocks, 1F, 1F, SoundType.STONE);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ChiseledBoneBricks)
    public static final BaseBlock ChiseledBoneBricks = new BaseBlock(Ref.ChiseledBoneBricks, Material.ROCK, CreativeTabsMystic.buildingBlocks, 1F, 1F, SoundType.STONE);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SoulStoneBricks)
    public static final BaseBlock SoulStoneBricks = new BaseBlock(Ref.SoulStoneBricks, Material.ROCK, CreativeTabsMystic.buildingBlocks, 1F, 1F, SoundType.STONE);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SoulStoneBricksWithBones)
    public static final BaseBlock SoulStoneBricksWithBones = new BaseBlock(Ref.SoulStoneBricksWithBones, Material.ROCK, CreativeTabsMystic.buildingBlocks, 1F, 1F, SoundType.STONE);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.DeadLichen)
    public static final BlockDeadLichen DeadLichen = new BlockDeadLichen(Ref.DeadLichen);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.CursedFlower)
    public static final BlockCursedFlower CursedFlower = new BlockCursedFlower(Ref.CursedFlower);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SteppedSucculent)
    public static final BlockSteppedSucculent SteppedSucculent = new BlockSteppedSucculent(Ref.SteppedSucculent);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MythicStoneStandingSign)
    public static final BlockMythicStoneStandingSign MythicStoneStandingSign = new BlockMythicStoneStandingSign(Ref.MythicStoneStandingSign);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MythicStoneWallSign)
    public static final BlockMythicStoneWallSign MythicStoneWallSign = new BlockMythicStoneWallSign(Ref.MythicStoneWallSign);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Magnethium)
    public static final BaseBlock Magnethium = new BaseBlock(Ref.Magnethium, Material.ROCK, CreativeTabsMystic.buildingBlocks, 10F, 10F, SoundType.STONE);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.HardenedWeatheredRockBricksStairs)
    public static final BlockMysticStairs HardenedWeatheredRockBricksStairs = new BlockMysticStairs(HardenedWeatheredRock.getDefaultState(),Ref.HardenedWeatheredRockBricksStairs,Material.ROCK,CreativeTabsMystic.buildingBlocks,2.5F,2.5F,SoundType.STONE);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShinyLogBark)
    public static final BaseBlock ShinyLogBark = new BaseBlock(Ref.ShinyLogBark,Material.WOOD, CreativeTabsMystic.buildingBlocks,3F,3F,SoundType.WOOD);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticWoodHalfSlab)
    public static final BlockMysticSlabBase MysticWoodHalfSlab = new BlockMysticSlabBase(false,Ref.MysticWoodHalfSlab,Material.WOOD, CreativeTabsMystic.buildingBlocks,SoundType.WOOD,3F,3F);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticWoodDoubleSlab)
    public static final BlockMysticSlabBase MysticWoodDoubleSlab = new BlockMysticSlabBase(true,Ref.MysticWoodDoubleSlab,Material.WOOD, null,SoundType.WOOD,3F,3F);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShinyWoodHalfSlab)
    public static final BlockMysticSlabBase ShinyWoodHalfSlab = new BlockMysticSlabBase(false,Ref.ShinyWoodHalfSlab,Material.WOOD, CreativeTabsMystic.buildingBlocks,SoundType.WOOD,3F,3F);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShinyWoodDoubleSlab)
    public static final BlockMysticSlabBase ShinyWoodDoubleSlab = new BlockMysticSlabBase(true,Ref.ShinyWoodDoubleSlab,Material.WOOD, null,SoundType.WOOD,3F,3F);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SoulWoodHalfSlab)
    public static final BlockMysticSlabBase SoulWoodHalfSlab = new BlockMysticSlabBase(false,Ref.SoulWoodHalfSlab,Material.WOOD, CreativeTabsMystic.buildingBlocks,SoundType.WOOD,3F,3F);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SoulWoodDoubleSlab)
    public static final BlockMysticSlabBase SoulWoodDoubleSlab = new BlockMysticSlabBase(true,Ref.SoulWoodDoubleSlab,Material.WOOD, null,SoundType.WOOD,3F,3F);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SoulWoodStairs)
    public static final BlockMysticStairs SoulWoodStairs = new BlockMysticStairs(SoulPlanks.getDefaultState(),Ref.SoulWoodStairs,Material.WOOD,CreativeTabsMystic.buildingBlocks,4F,4F,SoundType.WOOD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticLogBark)
    public static final BaseBlock MysticLogBark = new BaseBlock(Ref.MysticLogBark,Material.WOOD, CreativeTabsMystic.buildingBlocks,3F,3F,SoundType.WOOD);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SoulLogBark)
    public static final BaseBlock SoulLogBark = new BaseBlock(Ref.SoulLogBark,Material.WOOD, CreativeTabsMystic.buildingBlocks,3F,3F,SoundType.WOOD);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Mystic_Gem_Block)
    public static final BaseBlock Mystic_Gem_Block = (BaseBlock) new BaseBlock(Ref.Mystic_Gem_Block,Material.GLASS, CreativeTabsMystic.buildingBlocks,5F,5F,SoundType.GLASS).setTickRandomly(true);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Stripped_Mystic_Log)
    public static final BlockMysticLog Stripped_Mystic_Log = new BlockMysticLog(Ref.Stripped_Mystic_Log);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Stripped_Shiny_Log)
    public static final BlockMysticLog Stripped_Shiny_Log = new BlockMysticLog(Ref.Stripped_Shiny_Log);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Stripped_Soul_Log)
    public static final BlockMysticLog Stripped_Soul_Log = new BlockMysticLog(Ref.Stripped_Soul_Log);
   
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Mythic_Cobblestone)
    public static final BaseBlock Mythic_Cobblestone = new BaseBlock(Ref.Mythic_Cobblestone, Material.ROCK, CreativeTabsMystic.buildingBlocks, 2.5F, 2.5F, SoundType.STONE);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Mystic_Wood_Trap_Door)
    public static final BlockMysticWoodTrapdoor Mystic_Wood_Trap_Door = new BlockMysticWoodTrapdoor(Ref.Mystic_Wood_Trap_Door);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Shiny_Wood_Trap_Door)
    public static final BlockMysticWoodTrapdoor Shiny_Wood_Trap_Door = new BlockMysticWoodTrapdoor(Ref.Shiny_Wood_Trap_Door);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Soul_Wood_Trap_Door)
    public static final BlockMysticWoodTrapdoor Soul_Wood_Trap_Door = new BlockMysticWoodTrapdoor(Ref.Soul_Wood_Trap_Door);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Mystic_Wood_Door)
    public static final BlockMysticDoors Mystic_Wood_Door = new BlockMysticDoors(Ref.Mystic_Wood_Door,Material.WOOD,null,4F,4F,SoundType.WOOD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Shiny_Wood_Door)
    public static final BlockMysticDoors Shiny_Wood_Door = new BlockMysticDoors(Ref.Shiny_Wood_Door,Material.WOOD,null,4F,4F,SoundType.WOOD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Soul_Wood_Door)
    public static final BlockMysticDoors Soul_Wood_Door = new BlockMysticDoors(Ref.Soul_Wood_Door,Material.WOOD,null,4F,4F,SoundType.WOOD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Plant_Blue_Cloud)
    public static final BlockCloudPlant Plant_Blue_Cloud = new BlockCloudPlant(Ref.Plant_Blue_Cloud);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Plant_Yellow_Cloud)
    public static final BlockCloudPlant Plant_Yellow_Cloud = new BlockCloudPlant(Ref.Plant_Yellow_Cloud);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Solis_Crystals)
    public static final BaseBlock Solis_Crystals = (BaseBlock) new BaseBlock(Ref.Solis_Crystals, Material.GLASS, CreativeTabsMystic.buildingBlocks, 5F, 5F, SoundType.GLASS).setLightLevel(0.5F).setLightOpacity(0);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Refined_Cloud_Blue)
    public static final BlockRefinedCloud Refined_Cloud_Blue = (BlockRefinedCloud) new BlockRefinedCloud(Ref.Refined_Cloud_Blue).setLightOpacity(50);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Refined_Cloud_Yellow)
    public static final BlockRefinedCloud Refined_Cloud_Yellow = (BlockRefinedCloud) new BlockRefinedCloud(Ref.Refined_Cloud_Yellow).setLightOpacity(50);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Block_Iron_Gold)
    public static final BaseBlock Block_Iron_Gold = new BaseBlock(Ref.Block_Iron_Gold, Material.IRON, CreativeTabsMystic.buildingBlocks, 5F, 5F, SoundType.METAL);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Gecko_Eggs)
    public static final BlockGeckoEggs Gecko_Eggs = new BlockGeckoEggs(Ref.Gecko_Eggs);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Redwood_log_full)
    public static final BlockRedwoodLog Redwood_log_full = new BlockRedwoodLog(Ref.Redwood_log_full);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Redwood_log_size_6)
    public static final BlockRedwoodLog Redwood_log_size_6 = new BlockRedwoodLog(Ref.Redwood_log_size_6);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Redwood_log_size_5)
    public static final BlockRedwoodLog Redwood_log_size_5 = new BlockRedwoodLog(Ref.Redwood_log_size_5);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Redwood_log_size_4)
   	public static final BlockRedwoodLog Redwood_log_size_4 = new BlockRedwoodLog(Ref.Redwood_log_size_4);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Redwood_log_size_3)
    public static final BlockRedwoodLog Redwood_log_size_3 = new BlockRedwoodLog(Ref.Redwood_log_size_3);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Redwood_log_size_2)
    public static final BlockRedwoodLog Redwood_log_size_2 = new BlockRedwoodLog(Ref.Redwood_log_size_2);
    
   	@GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Redwood_log_size_1)
    public static final BlockRedwoodLog Redwood_log_size_1 = new BlockRedwoodLog(Ref.Redwood_log_size_1);
    
   	@GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Redwood_planks)
   	public static final BlockMysticPlanks Redwood_planks = new BlockMysticPlanks(Ref.Redwood_planks);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Mythic_Stone_Bricks)
    public static final BlockMythicStoneBricks Mythic_Stone_Bricks = new BlockMythicStoneBricks(Ref.Mythic_Stone_Bricks, Material.ROCK, CreativeTabsMystic.buildingBlocks, 2.5F, 2.5F, SoundType.STONE);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Layered_Sand)
    public static final BlockLayeredSand Layered_Sand = new BlockLayeredSand(Ref.Layered_Sand,CreativeTabsMystic.buildingBlocks);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.bison_Stars)
    public static final BlockBisonStars bison_Stars = new BlockBisonStars(Ref.bison_Stars);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.christmas_gift)
    public static final BlockChristmasGift christmas_gift = new BlockChristmasGift(Ref.christmas_gift);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.baurble)
    public static final BlockBaurble baurble = new BlockBaurble(Ref.baurble);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.snowdrop_cyprepedium)
    public static final BlockSnowdropCyprepedium snowdrop_Cyprepedium = new BlockSnowdropCyprepedium(Ref.snowdrop_cyprepedium);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.glowvines)
    public static final BlockGlowVines glowvines = (BlockGlowVines) new BlockGlowVines(Ref.glowvines,true).setLightLevel(0.6f);


    // @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Butterfly_Analysing_Table)
    //public static final BlockButterflyAnalysingTable Butterfly_Analysing_Table = new BlockButterflyAnalysingTable(Ref.Butterfly_Analysing_Table);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ "MysticForest")
	public static final BiomeMysticForest MysticForest = new BiomeMysticForest(new Biome.BiomeProperties("Mystic Forest").setBaseBiome("Mystic Forest").setBaseHeight(0.8f).setHeightVariation(0.8f).setWaterColor(132240255));
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ "MysticPlains")
	public static final BiomeMysticPlains MysticPlains = new BiomeMysticPlains(new Biome.BiomeProperties("Mystic Plains").setBaseBiome("Mystic Plains").setBaseHeight(0.5f).setHeightVariation(0.1f).setWaterColor(132240255));
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ "MysticDesert")
	public static final BiomeMysticDesert MysticDesert = new BiomeMysticDesert(new Biome.BiomeProperties("Mystic Desert").setBaseBiome("Mystic Desert").setBaseHeight(0.5f).setHeightVariation(0.1f).setWaterColor(132240255).setRainDisabled());
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ "MysticSwamps")
	public static final BiomeMysticSwamps MysticSwamps = new BiomeMysticSwamps(new Biome.BiomeProperties("Mystic Swamps").setBaseBiome("Mystic Swamps").setBaseHeight(0.4f).setHeightVariation(0.1f).setWaterColor(132240255));
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ "Floating Islands")
	public static final BiomeFloatingIslands FloatingIslands = new BiomeFloatingIslands(new Biome.BiomeProperties("Floating Islands").setBaseBiome("Floating Islands").setBaseHeight(-1f).setHeightVariation(0.5f).setWaterColor(132240255));
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ "Katharian Ocean")
    public static final BiomeKatharianOcean KatharianOcean = new BiomeKatharianOcean(new Biome.BiomeProperties("Katharian Ocean").setBaseBiome("Katharian Ocean").setBaseHeight(-1.4f).setHeightVariation(0.5f).setWaterColor(132240255));


    public static void dimRegistry()
	{
		DimensionManager.registerDimension(DIMENSION_ID, KCore.Mystic_DIMENSION);
	}
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticGem)
	public static final BaseItem MysticGem = new BaseItem(Ref.MysticGem, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumIngot)
	public static final BaseItem TitaniumIngot = new BaseItem(Ref.TitaniumIngot, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumRod)
	public static final BaseItem TitaniumRod = new BaseItem(Ref.TitaniumRod, CreativeTabsMystic.miscellaneous);
	
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumHelmet)
	public static final ItemMysticArmor TitaniumHelmet = new ItemMysticArmor(Ref.TitaniumHelmet, CreativeTabsMystic.armors,KCore.TITANIUMARMOR,5,EntityEquipmentSlot.HEAD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumChestplate)
	public static final ItemMysticArmor TitaniumChestplate = new ItemMysticArmor(Ref.TitaniumChestplate, CreativeTabsMystic.armors,KCore.TITANIUMARMOR,5,EntityEquipmentSlot.CHEST);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumLeggins)
	public static final ItemMysticArmor TitaniumLeggins = new ItemMysticArmor(Ref.TitaniumLeggins, CreativeTabsMystic.armors,KCore.TITANIUMARMOR,5,EntityEquipmentSlot.LEGS);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumBoots)
	public static final ItemMysticArmor TitaniumBoots = new ItemMysticArmor(Ref.TitaniumBoots, CreativeTabsMystic.armors,KCore.TITANIUMARMOR,5,EntityEquipmentSlot.FEET);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumAxe)
	public static final ItemMysticAxe TitaniumAxe = new ItemMysticAxe(Ref.TitaniumAxe, CreativeTabsMystic.tools,KCore.TITANIUM);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumPickaxe)
	public static final ItemMysticPickaxe TitaniumPickaxe = new ItemMysticPickaxe(Ref.TitaniumPickaxe, CreativeTabsMystic.tools,KCore.TITANIUM);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumShovel)
	public static final ItemMysticShovel TitaniumShovel = new ItemMysticShovel(Ref.TitaniumShovel, CreativeTabsMystic.tools,KCore.TITANIUM);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumHoe)
	public static final ItemMysticHoe TitaniumHoe = new ItemMysticHoe(Ref.TitaniumHoe, CreativeTabsMystic.tools,KCore.TITANIUM);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TitaniumSword)
	public static final ItemMysticSword TitaniumSword = new ItemMysticSword(Ref.TitaniumSword, CreativeTabsMystic.weapons,KCore.TITANIUM);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticHelmet)
	public static final ItemMysticArmor MysticHelmet = new ItemMysticArmor(Ref.MysticHelmet, CreativeTabsMystic.armors,KCore.MYSTICARMOR,8,EntityEquipmentSlot.HEAD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticChestplate)
	public static final ItemMysticArmor MysticChestplate = new ItemMysticArmor(Ref.MysticChestplate, CreativeTabsMystic.armors,KCore.MYSTICARMOR,8,EntityEquipmentSlot.CHEST);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticLeggins)
	public static final ItemMysticArmor MysticLeggins = new ItemMysticArmor(Ref.MysticLeggins, CreativeTabsMystic.armors,KCore.MYSTICARMOR,8,EntityEquipmentSlot.LEGS);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticBoots)
	public static final ItemMysticArmor MysticBoots = new ItemMysticArmor(Ref.MysticBoots, CreativeTabsMystic.armors,KCore.MYSTICARMOR,8,EntityEquipmentSlot.FEET);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticAxe)
	public static final ItemMysticAxe MysticAxe = new ItemMysticAxe(Ref.MysticAxe, CreativeTabsMystic.tools,KCore.MYSTIC);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticPickaxe)
	public static final ItemMysticPickaxe MysticPickaxe = new ItemMysticPickaxe(Ref.MysticPickaxe, CreativeTabsMystic.tools,KCore.MYSTIC);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticShovel)
	public static final ItemMysticShovel MysticShovel = new ItemMysticShovel(Ref.MysticShovel, CreativeTabsMystic.tools,KCore.MYSTIC);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticHoe)
	public static final ItemMysticHoe MysticHoe = new ItemMysticHoe(Ref.MysticHoe, CreativeTabsMystic.tools,KCore.MYSTIC);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MysticSword)
	public static final ItemMysticSword MysticSword = new ItemMysticSword(Ref.MysticSword, CreativeTabsMystic.weapons,KCore.MYSTIC);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Heart)
    public static final ItemMysticFood Heart = new ItemMysticFood(Ref.Heart, CreativeTabsMystic.miscellaneous, 0, 0, false);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.PotWithLivingFlower)
    public static final BaseItem PotWithLivingFlower = new BaseItem(Ref.PotWithLivingFlower, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.BlueCloudDust)
    public static final BaseItem BlueCloudDust = new BaseItem(Ref.BlueCloudDust, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.CondensedBlueCloudDust)
    public static final BaseItem CondensedBlueCloudDust = new BaseItem(Ref.CondensedBlueCloudDust, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.CloudBoots)
	public static final ItemMysticArmor CloudBoots = new ItemMysticArmor(Ref.CloudBoots, CreativeTabsMystic.armors,KCore.CLOUDARMOR,9,EntityEquipmentSlot.FEET);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.RevenumIngot)
	public static final BaseItem RevenumIngot = new BaseItem(Ref.RevenumIngot, CreativeTabsMystic.miscellaneous);
	
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.RevenumHelmet)
	public static final ItemMysticArmor RevenumHelmet = new ItemMysticArmor(Ref.RevenumHelmet, CreativeTabsMystic.armors,KCore.REVENUMARMOR,5,EntityEquipmentSlot.HEAD);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.RevenumChestplate)
	public static final ItemMysticArmor RevenumChestplate = new ItemMysticArmor(Ref.RevenumChestplate, CreativeTabsMystic.armors,KCore.REVENUMARMOR,5,EntityEquipmentSlot.CHEST);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.RevenumLeggins)
	public static final ItemMysticArmor RevenumLeggins = new ItemMysticArmor(Ref.RevenumLeggins, CreativeTabsMystic.armors,KCore.REVENUMARMOR,5,EntityEquipmentSlot.LEGS);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.RevenumBoots)
	public static final ItemMysticArmor RevenumBoots = new ItemMysticArmor(Ref.RevenumBoots, CreativeTabsMystic.armors,KCore.REVENUMARMOR,5,EntityEquipmentSlot.FEET);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.RevenumAxe)
	public static final ItemMysticAxe RevenumAxe = new ItemMysticAxe(Ref.RevenumAxe, CreativeTabsMystic.tools,KCore.REVENUM);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.RevenumPickaxe)
	public static final ItemMysticPickaxe RevenumPickaxe = new ItemMysticPickaxe(Ref.RevenumPickaxe, CreativeTabsMystic.tools,KCore.REVENUM);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.RevenumSword)
	public static final ItemMysticSword RevenumSword = new ItemMysticSword(Ref.RevenumSword, CreativeTabsMystic.weapons,KCore.REVENUM);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.VioletCrystalShard)
    public static final BaseItem VioletCrystalShard = new BaseItem(Ref.VioletCrystalShard, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.YellowCrystalShard)
    public static final BaseItem YellowCrystalShard = new BaseItem(Ref.YellowCrystalShard, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.BlueCrystalShard)
    public static final BaseItem BlueCrystalShard = new BaseItem(Ref.BlueCrystalShard, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.CottonCandy)
    public static final ItemMysticFood CottonCandy = new ItemMysticFood(Ref.CottonCandy, CreativeTabsMystic.food, 4, 0.6F, false);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.BlueFruit)
    public static final ItemMysticSeedFood BlueFruit = new ItemMysticSeedFood(Ref.BlueFruit, 3,0.4F,KCore.BlueFruitPlant,KCore.KatharianGrass);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.CrystalsCluster)
	public static final BaseItem CrystalsCluster = new BaseItem(Ref.CrystalsCluster, CreativeTabsMystic.miscellaneous);
	
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.CrystalPickaxe)
	public static final ItemMysticPickaxe CrystalPickaxe = new ItemMysticPickaxe(Ref.CrystalPickaxe, CreativeTabsMystic.tools,KCore.CRYSTAL);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.JellyFishTentacle)
	public static final BaseItem JellyFishTentacle = new BaseItem(Ref.JellyFishTentacle, CreativeTabsMystic.miscellaneous);
	
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.TurtleShell)
	public static final BaseItem TurtleShell = new BaseItem(Ref.TurtleShell, CreativeTabsMystic.miscellaneous);
	
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MagicBeansItem)
    public static final ItemMysticSeedFood MagicBeansItem = (ItemMysticSeedFood) new ItemMysticSeedFood(Ref.MagicBeansItem, 0,0.1F,KCore.MagicBeans,KCore.KatharianGrass).setAlwaysEdible();
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.BisonMeat)
    public static final ItemMysticFood BisonMeat = new ItemMysticFood(Ref.BisonMeat,CreativeTabsMystic.food, 6,0.9F,false);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.CookedBisonMeat)
    public static final ItemMysticFood CookedBisonMeat = new ItemMysticFood(Ref.CookedBisonMeat,CreativeTabsMystic.food, 8,1.2F,false);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ButterflyFlowerNectar)
	public static final BaseItem ButterflyFlowerNectar = new BaseItem(Ref.ButterflyFlowerNectar, CreativeTabsMystic.miscellaneous);
	
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.YellowCloudDust)
	public static final BaseItem YellowCloudDust = new BaseItem(Ref.YellowCloudDust, CreativeTabsMystic.miscellaneous);
	
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.CondensedYellowCloudDust)
	public static final BaseItem CondensedYellowCloudDust = new BaseItem(Ref.CondensedYellowCloudDust, CreativeTabsMystic.miscellaneous);
	
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.GlassJar)
	public static final ItemGlassJar GlassJar = new ItemGlassJar(Ref.GlassJar, CreativeTabsMystic.miscellaneous);
	
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShinyStick)
	public static final BaseItem ShinyStick = new BaseItem(Ref.ShinyStick, CreativeTabsMystic.miscellaneous);
	
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Gooseberry)
    public static final ItemMysticFood Gooseberry = new ItemMysticFood(Ref.Gooseberry,CreativeTabsMystic.food, 2,0.4F,false);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.CloudEssence)
	public static final BaseItem CloudEssence = new BaseItem(Ref.CloudEssence, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ShockWand)
	public static final BaseWand ShockWand = new BaseWand(Ref.ShockWand, CreativeTabsMystic.weapons);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.JarWithSwampGas)
	public static final ItemGlassJar JarWithSwampGas = new ItemGlassJar(Ref.JarWithSwampGas, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.BittenCookie)
    public static final ItemMysticFood BittenCookie = new ItemMysticFood(Ref.BittenCookie,null, 3,0.7F,false);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.IceCreams)
    public static final ItemMysticFood IceCreams = new ItemMysticFood(Ref.IceCreams,null, 3,0.7F,false);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.SweetMuffin)
    public static final ItemMysticFood SweetMuffin = new ItemMysticFood(Ref.SweetMuffin,null, 3,0.7F,false);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.ScorpionVenom)
	public static final BaseItem ScorpionVenom = new BaseItem(Ref.ScorpionVenom, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.CrystalBlend)
	public static final BaseItem CrystalBlend = new BaseItem(Ref.CrystalBlend, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.WingsPiece)
	public static final BaseItem WingsPiece = new BaseItem(Ref.WingsPiece, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.BigWings)
	public static final BaseItem BigWings = new BaseItem(Ref.BigWings, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.DarknessSword)
	public static final ItemMysticSword DarknessSword = new ItemMysticSword(Ref.DarknessSword, CreativeTabsMystic.weapons,KCore.DARKNESS);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.DarknessEssence)
	public static final BaseItem DarknessEssence = new BaseItem(Ref.DarknessEssence, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.DeathWand)
	public static final BaseWand DeathWand = new BaseWand(Ref.DeathWand, CreativeTabsMystic.weapons);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.MythicStoneSign)
	public static final ItemMythicStoneSign MythicStoneSign = new ItemMythicStoneSign(Ref.MythicStoneSign, CreativeTabsMystic.buildingBlocks);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.CloudPearl)
	public static final BaseItem CloudPearl = new BaseItem(Ref.CloudPearl, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Fungal_Drug)
    public static final ItemMysticFood Fungal_Drug = (ItemMysticFood) new ItemMysticFood(Ref.Fungal_Drug,CreativeTabsMystic.food, 2,0.4F,false).setAlwaysEdible();
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Mystic_Wood_Door_Item)
	public static final ItemMysticDoor Mystic_Wood_Door_Item = new ItemMysticDoor(Ref.Mystic_Wood_Door_Item, CreativeTabsMystic.buildingBlocks,Mystic_Wood_Door);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Shiny_Wood_Door_Item)
	public static final ItemMysticDoor Shiny_Wood_Door_Item = new ItemMysticDoor(Ref.Shiny_Wood_Door_Item, CreativeTabsMystic.buildingBlocks,Shiny_Wood_Door);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Soul_Wood_Door_Item)
	public static final ItemMysticDoor Soul_Wood_Door_Item = new ItemMysticDoor(Ref.Soul_Wood_Door_Item, CreativeTabsMystic.buildingBlocks,Soul_Wood_Door);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Magnethium_Shard)
	public static final BaseItem Magnethium_Shard = new BaseItem(Ref.Magnethium_Shard, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Ingot_Iron_Gold)
	public static final BaseItem Ingot_Iron_Gold = new BaseItem(Ref.Ingot_Iron_Gold, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Ritual_Blade)
	public static final ItemRitualBlade Ritual_Blade = new ItemRitualBlade(Ref.Ritual_Blade, CreativeTabsMystic.weapons);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Solis_Crystal)
	public static final BaseItem Solis_Crystal = new BaseItem(Ref.Solis_Crystal, CreativeTabsMystic.miscellaneous);
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Nectar_bowl)
	public static final ItemMysticFood Nectar_Bowl = (ItemMysticFood) new ItemMysticFood(Ref.Nectar_bowl, CreativeTabsMystic.food,4,0.4F,false).setAlwaysEdible();
    
    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Magnethium_Axe)
 	public static final ItemMysticAxe Magnethium_Axe = new ItemMysticAxe(Ref.Magnethium_Axe, CreativeTabsMystic.tools,KCore.MAGNETHIUM);
     
     @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Magnethium_Pickaxe)
 	public static final ItemMysticPickaxe Magnethium_Pickaxe = new ItemMysticPickaxe(Ref.Magnethium_Pickaxe, CreativeTabsMystic.tools,KCore.MAGNETHIUM);
     
     @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Magnethium_Shovel)
 	public static final ItemMysticShovel Magnethium_Shovel = new ItemMysticShovel(Ref.Magnethium_Shovel, CreativeTabsMystic.tools,KCore.MAGNETHIUM);
     
     @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Magnethium_Hoe)
 	public static final ItemMysticHoe Magnethium_Hoe = new ItemMysticHoe(Ref.Magnethium_Hoe, CreativeTabsMystic.tools,KCore.MAGNETHIUM);
     
     @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Magnethium_Sword)
 	public static final ItemMysticSword Magnethium_Sword = new ItemMysticSword(Ref.Magnethium_Sword, CreativeTabsMystic.weapons,KCore.MAGNETHIUM);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.music_disc_8bit)
    public static final ItemBasicKathairisMusicDisc Music_Disc_8bit = new ItemBasicKathairisMusicDisc(Ref.music_disc_8bit, proxy.music_disc_8bit,"In-universe by YungBlitzkrieg");

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.music_disc_jazzy)
    public static final ItemBasicKathairisMusicDisc Music_Disc_jazzy = new ItemBasicKathairisMusicDisc(Ref.music_disc_jazzy, proxy.music_disc_jazzy,"Jazzy by YungBlitzkrieg");

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.skyray_feather)
    public static final BaseItem skyray_feather = new BaseItem(Ref.skyray_feather, CreativeTabsMystic.miscellaneous);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.howler_fur)
    public static final BaseItem howler_fur = new BaseItem(Ref.howler_fur, CreativeTabsMystic.miscellaneous);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.katharian_arrow)
    public static final ItemKatharianArrow katharian_arrow = new ItemKatharianArrow(Ref.katharian_arrow);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.Scythe)
    public static final ItemMysticSword Scythe = new ItemMysticSword(Ref.Scythe, CreativeTabsMystic.weapons,KCore.DEATH);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.christmas_chocolate)
    public static final ItemMysticFood christmas_chocolate = new ItemMysticFood(Ref.christmas_chocolate,null, 3,0.7F,false);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.candy_cane)
    public static final ItemMysticFood candy_cane = new ItemMysticFood(Ref.candy_cane,null, 3,0.7F,false);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.butterfly_catcher)
    public static final ItemButterflyCatcher butterfly_catcher = new ItemButterflyCatcher(Ref.butterfly_catcher);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.butterfly_common_1)
    public static final ItemButterfly butterfly_common_1= new ItemButterfly(Ref.butterfly_common_1,CreativeTabsMystic.miscellaneous, EnumRarity.COMMON);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.butterfly_common_2)
    public static final ItemButterfly butterfly_common_2= new ItemButterfly(Ref.butterfly_common_2,CreativeTabsMystic.miscellaneous, EnumRarity.COMMON);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.butterfly_common_moth)
    public static final ItemButterfly butterfly_common_moth= new ItemButterfly(Ref.butterfly_common_moth,CreativeTabsMystic.miscellaneous, EnumRarity.COMMON);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.butterfly_illukini)
    public static final ItemButterfly butterfly_illukini= new ItemButterfly(Ref.butterfly_illukini,CreativeTabsMystic.miscellaneous, EnumRarity.UNCOMMON);

    @GameRegistry.ObjectHolder(KCore.MODID +":"+ Ref.butterfly_cloud_shimmer)
    public static final ItemButterfly butterfly_cloud_shimmer= new ItemButterfly(Ref.butterfly_cloud_shimmer,CreativeTabsMystic.miscellaneous, EnumRarity.RARE);

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        regHelper.initModels();
        //Butterfly_Analysing_Table.initModel();
    }
    
    public static void registerRecipes() {
        new RecipeHandler().addRecipes();
    }

    public static KathairisDataStorage data = KathairisDataStorage.initialise();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityHandler.registerCapabilities();
    	WorldServer.MAX_ENTITY_RADIUS=12D;
    	KathairisPacketHandler.init();

    	dimRegistry();
        proxy.preInit(event);

        //logger = (Logger)event.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
		registerRecipes();
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
	
	
    
    public class Ref {
        public static final String MysticGem = "mysticgem";
        public static final String TitaniumIngot = "titaniumingot";
        public static final String TitaniumRod = "titaniumrod";
        public static final String TitaniumHelmet = "titaniumhelmet";
        public static final String TitaniumChestplate = "titaniumchestplate";
        public static final String TitaniumLeggins = "titaniumleggins";
        public static final String TitaniumBoots = "titaniumboots";
        public static final String TitaniumAxe = "titaniumaxe";
        public static final String TitaniumPickaxe = "titaniumpickaxe";
        public static final String TitaniumShovel = "titaniumshovel";
        public static final String TitaniumHoe = "titaniumhoe";
        public static final String TitaniumSword = "titaniumsword";
        public static final String MysticHelmet = "mystichelmet";
        public static final String MysticChestplate = "mysticchestplate";
        public static final String MysticLeggins = "mysticleggins";
        public static final String MysticBoots = "mysticboots";
        public static final String MysticAxe = "mysticaxe";
        public static final String MysticPickaxe = "mysticpickaxe";
        public static final String MysticShovel = "mysticshovel";
        public static final String MysticHoe = "mystichoe";
        public static final String MysticSword = "mysticsword";
        public static final String Heart = "heart";
        public static final String PotWithLivingFlower = "potwithlivingflower";
        public static final String BlueCloudDust = "blueclouddust";
        public static final String CondensedBlueCloudDust = "condensedblueclouddust";
        public static final String CloudBoots = "cloudboots";
        public static final String RevenumIngot = "revenumingot";
        public static final String RevenumHelmet = "revenumhelmet";
        public static final String RevenumChestplate = "revenumchestplate";
        public static final String RevenumLeggins = "revenumleggins";
        public static final String RevenumBoots = "revenumboots";
        public static final String RevenumAxe = "revenumaxe";
        public static final String RevenumPickaxe = "revenumpickaxe";
        public static final String RevenumSword = "revenumsword";
        public static final String VioletCrystalShard = "violetcrystalshard";
        public static final String YellowCrystalShard = "yellowcrystalshard";
        public static final String BlueCrystalShard = "bluecrystalshard";
        public static final String CottonCandy = "cottoncandy";
        public static final String BlueFruit = "bluefruit";
        public static final String CrystalsCluster = "crystalscluster";
        public static final String CrystalPickaxe = "crystalpickaxe";
        public static final String TurtleShell = "turtleshell";
        public static final String JellyFishTentacle = "jellyfishtentacle";  
        public static final String MagicBeansItem = "magicbeansitem";
        public static final String BisonMeat = "bisonmeat";
        public static final String CookedBisonMeat = "cookedbisonmeat";
        public static final String ButterflyFlowerNectar="butterflyflowernectar";
        public static final String GlassJar = "glassjar";
        public static final String ShinyStick = "shinystick";
        public static final String Gooseberry = "gooseberry";
        public static final String CloudEssence = "cloudessence";
        public static final String MysticPortal = "mysticportal";
        public static final String KatharianDirt = "corrupteddirt";
        public static final String KatharianGrass = "corruptedgrass";
        public static final String MythicStone = "mythicstone";
        public static final String MysticPlanks = "mysticplanks";
        public static final String MysticLog = "mysticlog";
        public static final String MysticLeaves = "mysticleaves";
        public static final String MysticSapling = "mysticsapling";
        public static final String MysticFungus = "mysticfungus";
        public static final String MysticMiniGrass = "mysticminigrass";
        public static final String MysticTallGrass = "mystictallgrass";
        public static final String MysticFlower = "mysticflower";
        public static final String MysticNightFlower = "mysticnightflower";
        public static final String TitaniumOre = "titaniumore";
        public static final String TitaniumBlock = "titaniumblock";
        public static final String MythicStoneTiles = "mythicstonetiles";
        public static final String ShinyLog = "shinylog";
        public static final String ShinyLeaves = "shinyleaves";
        public static final String ShinyPlanks = "shinyplanks";
        public static final String ForgottenSand = "forgottensand";
        public static final String MovingSand = "movingsand";
        public static final String WeatheredRock = "weatheredrock";
        public static final String HardenedWeatheredRock = "hardenedweatheredrock";
        public static final String WeatheredRockTiles = "hardenedweatheredrocktiles";
        public static final String WeatheredRockBricks = "hardenedweatheredrockbricks";
        public static final String MysticDeadGrass = "mysticdeadgrass";
        public static final String GemsOre = "gemsore";
        public static final String ShinyRock = "shinyrock";
        public static final String MysticMultiGrass = "mysticmultigrass";
        public static final String BlueCloud = "bluecloud";
        public static final String RevenumOre = "revenumore";
        public static final String VioletCrystal = "violetcrystal";
        public static final String YellowCrystal = "yellowcrystal";
        public static final String BlueCrystal = "bluecrystal";
        public static final String BlueFruitPlant = "bluefruitplant";
        public static final String EyePlant = "eyeplant";
        public static final String MagicBeans = "magicbeans";
        public static final String MythicStonePillarBase = "mythicstonepillarbase";
        public static final String MythicStonePillar = "mythicstonepillar";
        public static final String HardenedWeatheredRockStairs = "hardenedweatheredrockstairs";
        public static final String MysticWoodStairs = "mysticwoodstairs";
        public static final String ShinyWoodStairs = "shinywoodstairs";
        public static final String JadeVine_empty = "jadevine_empty";
        public static final String JadeVine_top = "jadevine_top";
        public static final String JadeVine_mid = "jadevine_mid";
        public static final String JadeVine_bottom = "jadevine_bottom";
        public static final String MythicStoneStairs = "mythicstonestairs";
        public static final String MysticWoodFence = "mysticwoodfence";
        public static final String ShinyWoodFence = "shinywoodfence";
        public static final String MysticWoodFenceGate = "mysticwoodfencegate";
        public static final String ShinyWoodFenceGate = "shinywoodfencegate";
        public static final String ShinyTreeSapling = "shinytreesapling";
        public static final String MythicStoneWall = "mythicstonewall";
        public static final String HardenedWeatheredRockWall = "hardenedweatheredrockwall";
        public static final String MudBlock = "mudblock";
        public static final String MudBricks = "mudbricks";
        public static final String ButterflyFlower = "butterflyflower";
        public static final String BlueCondensedCloud = "bluecondensedcloud";
        public static final String BlueCloudBricks = "bluecloudbricks";
        public static final String YellowCloud = "yellowcloud";
        public static final String YellowCondensedCloud = "yellowcondensedcloud";
        public static final String YellowCloudBlock = "yellowcloudblock";
        public static final String YellowCloudDust = "yellowclouddust";
        public static final String CondensedYellowCloudDust = "condensedyellowclouddust";
        public static final String CloudParticleEmitter = "cloudparticleemitter";
        public static final String Succulent = "succulent";
        public static final String GooseberryBlock = "gooseberryblock";
        public static final String Charger = "charger";
        public static final String EnergyShard = "energyshard";
        public static final String ShockWand = "shockwand";
        public static final String SwampGas="swampgas";
        public static final String JarWithSwampGas="jarwithswampgas";
        public static final String BittenCookie = "bittencookie";
        public static final String SweetMuffin = "sweetmuffin";
        public static final String IceCreams = "icecreams";
        public static final String EasterEgg = "easteregg";
        public static final String SoulPlanks = "soulplanks";
        public static final String SoulLog = "soullog";
        public static final String SoulLeaves = "soulleaves";
        public static final String SoulSapling = "soulsapling";
        public static final String ScorpionVenom = "scorpionvenom";
        public static final String TurtleShellPlate = "turtleshellplate";
        public static final String CrystalBlend = "crystalblend";
        public static final String LuminescentGnarl = "luminescentgnarl";
        public static final String WingsPiece = "wingspiece";
        public static final String BigWings = "bigwings";
        public static final String BoneBricks = "bonebricks";
        public static final String ChiseledBoneBricks = "chiseledbonebricks";
        public static final String SoulStoneBricks = "soulstonebricks";
        public static final String SoulStoneBricksWithBones = "soulstonebrickswithbones";
        public static final String BoneLadder = "boneladder";
        public static final String DeadLichen = "deadlichen";
        public static final String CursedFlower = "cursedflower";
        public static final String DarknessSword = "darknesssword";
        public static final String DarknessEssence = "darknessessence";
        public static final String DeathWand =  "deathwand";
        public static final String SteppedSucculent =  "steppedsucculent";
        public static final String MythicStoneStandingSign = "mythicstonestandingsign";
        public static final String MythicStoneWallSign = "mythicstonewallsign";
        public static final String MythicStoneSign = "mythicstonesign";
        public static final String Magnethium = "magnethium";
        public static final String HardenedWeatheredRockBricksStairs = "hardenedweatheredrockbricksstairs";
        public static final String ShinyLogBark = "shinylogbark";
        public static final String CloudPearl = "cloudpearl";
        public static final String MysticWoodHalfSlab = "mysticwoodhalfslab";
        public static final String MysticWoodDoubleSlab = "mysticwooddoubleslab";
        public static final String ShinyWoodHalfSlab="shinywoodhalfslab";
        public static final String ShinyWoodDoubleSlab="shinywooddoubleslab";
        public static final String SoulWoodHalfSlab="soulwoodhalfslab";
        public static final String SoulWoodDoubleSlab="soulwooddoubleslab";
        public static final String SoulWoodStairs="soulwoodstairs";
        public static final String Fungal_Drug="fungal_drug";
        public static final String MysticLogBark="mysticlogbark";
        public static final String SoulLogBark="soullogbark";
        public static final String Mystic_Gem_Block="mystic_gem_block";
        public static final String Stripped_Mystic_Log="stripped_mystic_log";
        public static final String Stripped_Shiny_Log="stripped_shiny_log";
        public static final String Stripped_Soul_Log="stripped_soul_log";
        public static final String Mythic_Cobblestone="mythic_cobblestone";
        public static final String Mystic_Wood_Trap_Door="mystic_wood_trap_door";
        public static final String Shiny_Wood_Trap_Door="shiny_wood_trap_door";
        public static final String Soul_Wood_Trap_Door="soul_wood_trap_door";
        public static final String Mystic_Wood_Door="mystic_wood_door";
        public static final String Shiny_Wood_Door="shiny_wood_door";
        public static final String Soul_Wood_Door="soul_wood_door";
        public static final String Mystic_Wood_Door_Item="mystic_wood_door_item";
        public static final String Shiny_Wood_Door_Item="shiny_wood_door_item";
        public static final String Soul_Wood_Door_Item="soul_wood_door_item";
        public static final String Plant_Blue_Cloud="plant_blue_cloud";
        public static final String Plant_Yellow_Cloud="plant_yellow_cloud";
        public static final String Magnethium_Shard="magnethium_shard";
        public static final String Butterfly_Analysing_Table="butterfly_analysing_table";
        public static final String Solis_Crystals="solis_crystals";
        public static final String Hell_Plant="hell_plant";
        public static final String Refined_Cloud_Blue="refined_cloud_blue";
        public static final String Refined_Cloud_Yellow="refined_cloud_yellow";
        public static final String Ingot_Iron_Gold="ingot_iron_gold";
        public static final String Block_Iron_Gold="block_iron_gold";
        public static final String Ritual_Blade="ritual_blade";
        public static final String Solis_Crystal="solis_crystal";
        public static final String Gecko_Eggs="gecko_eggs";
        public static final String Nectar_bowl="nectar_bowl";
        public static final String Redwood_log_full="redwood_log_full";
        public static final String Redwood_log_size_6="redwood_log_size_6";
        public static final String Redwood_log_size_5="redwood_log_size_5";
        public static final String Redwood_log_size_4="redwood_log_size_4";
        public static final String Redwood_log_size_3="redwood_log_size_3";
        public static final String Redwood_log_size_2="redwood_log_size_2";
        public static final String Redwood_log_size_1="redwood_log_size_1";
        public static final String Redwood_planks="redwood_planks";
        public static final String Magnethium_Axe="magnethium_axe";
        public static final String Magnethium_Pickaxe="magnethium_pickaxe";
        public static final String Magnethium_Shovel="magnethium_shovel";
        public static final String Magnethium_Hoe="magnethium_hoe";
        public static final String Magnethium_Sword="magnethium_sword";
        public static final String Mythic_Stone_Bricks="mythic_stone_bricks";
        public static final String Layered_Sand="layered_sand";
        public static final String music_disc_jazzy="music_disc_jazzy";
        public static final String music_disc_8bit="music_disc_8bit";
        public static final String skyray_feather="skyray_feather";
        public static final String howler_fur="howler_fur";
        public static final String katharian_arrow="katharian_arrow";
        public static final String Scythe="scythe";
        public static final String bison_Stars="bison_stars";
        public static final String christmas_chocolate="christmas_chocolate";
        public static final String candy_cane="candy_cane";
        public static final String christmas_gift="christmas_gift";
        public static final String baurble="baurble";
        public static final String butterfly_catcher="butterfly_catcher";
        public static final String snowdrop_cyprepedium="snowdrop_cyprepedium";

        public static final String butterfly_common_1="butterfly_common_1";
        public static final String butterfly_common_2="butterfly_common_2";
        public static final String butterfly_illukini="butterfly_illukini";
        public static final String butterfly_common_moth="butterfly_common_moth";
        public static final String butterfly_cloud_shimmer="butterfly_cloud_shimmer";

        public static final String glowvines="glowvines";
    }
    

    
			
	public enum GUI_ENUM 
	{
	    CHARGER,OLDMAN,DEATH,BUTTERFLYANALYSINGTABLE
	}

    public static FunctionHelper functionHelper = new FunctionHelper();
    public static ChargerRecipes chargerRecipes = new ChargerRecipes();

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new SandstormCommand());
    }

    @GameRegistry.ObjectHolder("stun")
    public static final Potion stun_potion=new StunPotion(true,0xf51896);
    @GameRegistry.ObjectHolder("dissolution")
    public static final Potion dissolution_potion=new DissolutionPotion(true,0xf51896);

    public static void onRegisterPotions(RegistryEvent.Register<Potion> event) {
        registerPotion("stun", new StunPotion(true, 0x56CBFD).setPotionName(KCore.MODID + ".effect.stun").registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "ab79a316-db47-11e8-9f8b-f2801f1b9fd1", 0D, 0), event);
        registerPotion("dissolution", new DissolutionPotion(true, 0x56CBFD).setPotionName(KCore.MODID + ".effect.dissolution").registerPotionAttributeModifier(SharedMonsterAttributes.LUCK, "00e48ce2-e4c8-11e8-9f32-f2801f1b9fd1", 0D, 0), event);

    }

    public static void registerPotion(String name, Potion potion, RegistryEvent.Register<Potion> event){
        event.getRegistry().register(potion.setRegistryName(new ResourceLocation(KCore.MODID, name)));
    }
}