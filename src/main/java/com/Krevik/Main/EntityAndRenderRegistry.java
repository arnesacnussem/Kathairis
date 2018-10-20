package com.Krevik.Main;

import com.Krevik.Entities.*;
import com.Krevik.Entities.Butterflies.EntityButterfly;
import com.Krevik.Entities.Butterflies.EntityButterfly1;
import com.Krevik.Entities.Butterflies.EntityCloudShimmer;
import com.Krevik.Entities.Butterflies.EntityIllukini;
import com.Krevik.Entities.Butterflies.EntityRubySile;
import com.Krevik.Entities.Butterflies.EntitySkylight;
import com.Krevik.Renderers.*;
import com.Krevik.Renderers.Butterflies.RenderButterfly;
import com.Krevik.Renderers.Butterflies.RenderButterfly1;
import com.Krevik.Renderers.Butterflies.RenderCloudShimmer;
import com.Krevik.Renderers.Butterflies.RenderIllukini;
import com.Krevik.Renderers.Butterflies.RenderRubySile;
import com.Krevik.Renderers.Butterflies.RenderSkylight;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityAndRenderRegistry {
	public static final ResourceLocation LivingFlowerLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/livingflower.png");
	public static final ResourceLocation MysticBirdLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/mysticbird.png");
	public static final ResourceLocation MysticBird1Loc = new ResourceLocation(KCore.MODID+":"+"textures/entity/mysticbird1.png");
	public static final ResourceLocation MysticBird2Loc = new ResourceLocation(KCore.MODID+":"+"textures/entity/mysticbird2.png");
	public static final ResourceLocation MysticBird3Loc = new ResourceLocation(KCore.MODID+":"+"textures/entity/mysticbird3.png");
	public static final ResourceLocation HowlerLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/howler.png");
	public static final ResourceLocation JellyFishLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/jellyfish.png");
	public static final ResourceLocation BigTurtleLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/bigturtle.png");
	public static final ResourceLocation PoisonousScorpionLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/poisonousscorpion.png");
	public static final ResourceLocation BisonLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/bison.png");
	public static final ResourceLocation ButterflyLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/butterfly.png");
	public static final ResourceLocation Butterfly1Loc = new ResourceLocation(KCore.MODID+":"+"textures/entity/butterfly1.png");
	public static final ResourceLocation ShockingBallLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/shockingball.png");
	public static final ResourceLocation StrangeWandererLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/strangewanderer.png");
	public static final ResourceLocation SkylightLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/skylight.png");
	public static final ResourceLocation SkeletonWarriorLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/skeletonwarrior.png");
	public static final ResourceLocation SteveGhostLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/steveghost.png");
	public static final ResourceLocation DeathLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/death.png");
	public static final ResourceLocation CamelLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/camel.png");
	public static final ResourceLocation FungiteLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/fungite.png");
	public static final ResourceLocation FungiteOLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/fungiteo.png");
	public static final ResourceLocation CloudOisterLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/cloudoister.png");
	public static final ResourceLocation CloudySlimeLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/cloudyslime.png");
	public static final ResourceLocation CloudShimmerLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/cloudshimmer.png");
	public static final ResourceLocation FlyingSquidLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/flyingsquid.png");
	public static final ResourceLocation SkyrayLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/skyray.png");
	public static final ResourceLocation IllukiniLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/illukini.png");
	public static final ResourceLocation RubySileLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/rubysile.png");
	public static final ResourceLocation GeckoLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/gecko.png");
	public static final ResourceLocation GaznowelLoc = new ResourceLocation(KCore.MODID+":"+"textures/entity/gaznowel.png");

	static int id=1;

    public static void registerEntitiesAndEggs() {        
   
    				EntityRegistry.registerModEntity(LivingFlowerLoc, EntityLivingFlower.class, "Living Flower", id++, KCore.instance, 64, 3, true);
    				EntityRegistry.registerEgg(LivingFlowerLoc, 0x00ff15, 0xff0000);
	   				EntityRegistry.registerModEntity(MysticBirdLoc, EntityMysticBird.class, "Mystic Bird", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(MysticBirdLoc, 0xDFCE9B, 0xff0000);
				    EntityRegistry.registerModEntity(HowlerLoc, EntityHowler.class, "Howler", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(HowlerLoc, 0x07003a, 0x4f0000);
			   		EntityRegistry.registerModEntity(JellyFishLoc, EntityJellyFish.class, "JellyFish", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(JellyFishLoc, 0x5e0059, 0xff00f1);
				    EntityRegistry.registerModEntity(BigTurtleLoc, EntityBigTurtle.class, "BigTurtle", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(BigTurtleLoc, 0xa87001, 0x21b6d);
					EntityRegistry.registerModEntity(PoisonousScorpionLoc, EntityPoisonousScorpion.class, "PoisonousScorpion", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(PoisonousScorpionLoc, 0x030b2b, 0x0fc625);
					EntityRegistry.registerModEntity(BisonLoc, EntityBison.class, "Bison", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(BisonLoc, 0xa87801, 0x2d2400);
					EntityRegistry.registerModEntity(ButterflyLoc, EntityButterfly.class, "Butterfly", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(ButterflyLoc, 0xffffff, 0xff66e2);
					EntityRegistry.registerModEntity(Butterfly1Loc, EntityButterfly1.class, "Butterfly1", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(Butterfly1Loc, 0xffffff, 0x00c3ff);
					EntityRegistry.registerModEntity(ShockingBallLoc, EntityShockingBall.class, "ShockingBall", id++, KCore.instance, 64, 3, true);
					EntityRegistry.registerModEntity(StrangeWandererLoc, EntityStrangeWanderer.class, "StrangeWanderer", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(StrangeWandererLoc, 0xffdfa0, 0xffffff);
					EntityRegistry.registerModEntity(SkylightLoc, EntitySkylight.class, "Skylight", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(SkylightLoc, 0xffe62d, 0xffffff);
					EntityRegistry.registerModEntity(SkeletonWarriorLoc, EntitySkeletonWarrior.class, "SkeletonWarrior", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(SkeletonWarriorLoc, 0x12698049, 0x9b1594);
					EntityRegistry.registerModEntity(SteveGhostLoc, EntitySteveGhost.class, "SteveGhost", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(SteveGhostLoc, 0xffffff, 0xbca264);
					EntityRegistry.registerModEntity(DeathLoc, EntityDeath.class, "Death", id++, KCore.instance, 64, 3, true);
					EntityRegistry.registerModEntity(CamelLoc, EntityCamel.class, "Camel", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(CamelLoc, 0xffd36b, 0xf9c039);
					EntityRegistry.registerModEntity(FungiteLoc, EntityFungite.class, "Fungite", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(FungiteLoc, 0x03204f, 0xb51405);
					EntityRegistry.registerModEntity(CloudOisterLoc, EntityCloudOister.class, "CloudOister", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(CloudOisterLoc, 0xffffff, 0xff93f0);
					EntityRegistry.registerModEntity(CloudySlimeLoc, EntityCloudySlime.class, "CloudySlime", id++, KCore.instance, 64, 3, true);
                    EntityRegistry.registerEgg(CloudySlimeLoc, 0xffffff, 0x9ef3ff);
                    EntityRegistry.registerModEntity(CloudShimmerLoc, EntityCloudShimmer.class, "CloudShimmer", id++, KCore.instance, 64, 3, true);
					EntityRegistry.registerEgg(CloudShimmerLoc, 0x996600, 0x00ff00);	
                    EntityRegistry.registerModEntity(FlyingSquidLoc, EntityFlyingSquid.class, "FlyingSquid", id++, KCore.instance, 64, 3, true);
					EntityRegistry.registerEgg(FlyingSquidLoc, 0x996600, 0x00ff00);	
                    EntityRegistry.registerModEntity(SkyrayLoc, EntitySkyray.class, "Skyray", id++, KCore.instance, 512, 3, true);
					EntityRegistry.registerEgg(SkyrayLoc, 0x996600, 0x00ff00);	
                    EntityRegistry.registerModEntity(IllukiniLoc, EntityIllukini.class, "Illukini", id++, KCore.instance, 64, 3, true);
					EntityRegistry.registerEgg(IllukiniLoc, 0x996600, 0x00ff00);	
                    EntityRegistry.registerModEntity(RubySileLoc, EntityRubySile.class, "RubySile", id++, KCore.instance, 64, 3, true);
					EntityRegistry.registerEgg(RubySileLoc, 0x996600, 0x00ff00);	
                    EntityRegistry.registerModEntity(GeckoLoc, EntityGecko.class, "Gecko", id++, KCore.instance, 64, 3, true);
					EntityRegistry.registerEgg(GeckoLoc, 0x996600, 0x00ff00);
		EntityRegistry.registerModEntity(GaznowelLoc, EntityGaznowel.class, "Gaznowel", id++, KCore.instance, 64, 3, true);
		EntityRegistry.registerEgg(GaznowelLoc, 0x996600, 0x00ff00);

	}
    public static void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityLivingFlower.class, RenderLivingFlower.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityMysticBird.class, RenderMysticBird.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityHowler.class, RenderHowler.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityJellyFish.class, RenderJellyFish.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityBigTurtle.class, RenderBigTurtle.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityPoisonousScorpion.class, RenderPoisonousScorpion.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityBison.class, RenderBison.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityButterfly.class, RenderButterfly.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityButterfly1.class, RenderButterfly1.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityShockingBall.class, RenderShockingBall.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityStrangeWanderer.class, RenderStrangeWanderer.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySkylight.class, RenderSkylight.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonWarrior.class, RenderSkeletonWarrior.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySteveGhost.class, RenderSteveGhost.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityDeath.class, RenderDeath.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityCamel.class, RenderCamel.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityFungite.class, RenderFungite.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityCloudOister.class, RenderCloudOister.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityCloudySlime.class, RenderCloudySlime.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityCloudShimmer.class, RenderCloudShimmer.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityFlyingSquid.class, RenderFlyingSquid.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySkyray.class, RenderSkyray.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityIllukini.class, RenderIllukini.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityRubySile.class, RenderRubySile.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityGecko.class, RenderGecko.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityGaznowel.class, RenderGaznowel.FACTORY);

    }
}
