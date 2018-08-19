package com.Krevik.Main;

import java.util.Random;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class FunctionHelper {

	public Random random = new Random();
	public FunctionHelper() {
		
	}
	
	public int getRandomInteger(int min, int max) {
		return min+random.nextInt(((max+1)-min));
	}
	
	public int getRandomInteger(long Seed, int min, int max) {
		int result=0;
		Random rand = new Random(Seed);
		result=min+rand.nextInt(((max+1)-min));
		return result;
	}
	
    private BlockPos getRandomDestination(Entity entity,int maxX, int maxZ) {
    	BlockPos tmp;
    	double X=entity.posX-random.nextInt(maxX)+random.nextInt(10);
    	double Z=entity.posZ-random.nextInt(maxZ)+random.nextInt(10);
    	double Y=KCore.functionHelper.getRandomInteger(80,120);
    	tmp = new BlockPos(X,Y,Z);
    	return tmp;
    }
    
    public void updateJellyFishMotion(Entity entity) {
    	if(!entity.world.isRemote) {
        	BlockPos Destination = this.getRandomDestination(entity,10,10);
        	if(!entity.world.isAirBlock(Destination)) {
        		Destination = this.getRandomDestination(entity,10,10);
        	}
        	if(entity.world.isAirBlock(Destination)) {
            	entity.motionX=(Destination.getX()-entity.posX)/20;
            	entity.motionY=(Destination.getY()-entity.posY)/1000;
            	entity.motionZ=(Destination.getZ()-entity.posZ)/20;
        	}
    	}

    }
    
    public void JellyFishFlee(Entity entity) {
    	BlockPos Destination = this.getRandomDestination(entity,15,15);
    	if(!entity.world.isAirBlock(Destination)) {
    		Destination = this.getRandomDestination(entity,15,15);
    	}
    	if(entity.world.isAirBlock(Destination)) {
        	entity.motionX=(Destination.getX()-entity.posX)/20;
        	entity.motionY=(Destination.getY()-entity.posY)/1000;
        	entity.motionZ=(Destination.getZ()-entity.posZ)/20;
    	}
    }
    
    public boolean canSustainPlantRemake(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable)
    {
    	boolean can=false;
    	if(state==KCore.CorruptedDirt.getDefaultState() || state==KCore.CorruptedGrass.getDefaultState() || state==Blocks.GRASS.getDefaultState() 
    			|| state==Blocks.DIRT.getDefaultState()||state==Blocks.FARMLAND.getDefaultState()){
    		can=true;
    	}
    	return can;
    }
    
    public ItemStack getRandomReward1() {
    	ItemStack is=null;
    	Item[] rewards = {KCore.AdamantiumIngot,KCore.TitaniumIngot,KCore.MysticGem,KCore.CondensedBlueCloudDust,KCore.CottonCandy,
    			KCore.MagicBeansItem,KCore.CrystalsCluster,KCore.Heart,KCore.PotWithLivingFlower,Item.getItemFromBlock(KCore.ShinyTreeSapling),
    			KCore.BlueFruit,KCore.ShinyStick,KCore.Gooseberry,Item.getItemFromBlock(KCore.EnergyShard)};
    	is=new ItemStack(rewards[random.nextInt(rewards.length)],random.nextInt(6)+1);
    	if(is.getItem().equals(KCore.CloudBoots)) {is = new ItemStack(KCore.CloudBoots,1);}
    	
    	return is;
    }
    
    public ItemStack getCloudTempleReward() {
    	ItemStack is=null;
    	Item[] rewards = {KCore.AdamantiumIngot,KCore.TitaniumIngot,KCore.MysticGem,KCore.CondensedBlueCloudDust,KCore.CottonCandy,
    			KCore.MagicBeansItem,KCore.CrystalsCluster,KCore.Heart,KCore.PotWithLivingFlower,Item.getItemFromBlock(KCore.ShinyTreeSapling),
    			KCore.BlueFruit,KCore.ShinyStick,KCore.Gooseberry,KCore.CloudBoots,KCore.CloudEssence,Item.getItemFromBlock(KCore.EnergyShard)};
    	is=new ItemStack(rewards[random.nextInt(rewards.length)],random.nextInt(6)+4);
    	if(is.getItem().equals(KCore.CloudBoots)) {is = new ItemStack(KCore.CloudBoots,1);}
    	
    	return is;
    }
    
    public ItemStack getEasterReward() {
    	ItemStack is=null;
    	Item[] rewards = {KCore.TitaniumIngot,KCore.MysticGem,KCore.CottonCandy,KCore.SweetMuffin,KCore.IceCreams,KCore.BittenCookie,
    			KCore.MagicBeansItem,KCore.CrystalsCluster,Item.getItemFromBlock(KCore.ShinyTreeSapling),
    			KCore.BlueFruit,KCore.Gooseberry,KCore.CloudEssence,Item.getItemFromBlock(KCore.EnergyShard),KCore.JellyFishTentacle};
    	is=new ItemStack(rewards[random.nextInt(rewards.length)],random.nextInt(20)+20);
    	if(is.getItem().equals(KCore.CloudBoots)) {is = new ItemStack(KCore.CloudBoots,1);}
    	
    	return is;
    }
    
    public static int getItemBurnTime(ItemStack stack)
    {
        if (stack.isEmpty())
        {
            return 0;
        }
        else
        {
            int burnTime = net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack);
            if (burnTime >= 0) return burnTime;
            Item item = stack.getItem();

            if (item == Item.getItemFromBlock(KCore.EnergyShard))
            {
                return 200;
            }else {
            	return 0;
            }
        }
    }

    public static boolean isItemFuel(ItemStack stack)
    {
        return getItemBurnTime(stack) > 0;
    }
    
    public boolean isAvailableBlockToGenOn(World world,BlockPos pos) {
    	boolean is=false;
    	IBlockState state = world.getBlockState(pos);
    	Block block = state.getBlock();
    	if((block==KCore.CorruptedDirt||block==KCore.CorruptedGrass||block==KCore.ForgottenSand||block==KCore.WeatheredRock)) {
    		if(world.isAirBlock(pos.up())) {
    			is=true;
    		}
    	}    	
    	return is;
    }
    
    public float degToRad(float degrees)
    {
        return degrees * (float)Math.PI / 180 ;
    }
    

    public Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
    	Matrix4f matrix = new Matrix4f();
    	matrix.setIdentity();
    	Matrix4f.translate(translation, matrix, matrix);
    	Matrix4f.rotate((float)Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
    	Matrix4f.rotate((float)Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
    	Matrix4f.rotate((float)Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
    	Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
    	return matrix;
    }

}
