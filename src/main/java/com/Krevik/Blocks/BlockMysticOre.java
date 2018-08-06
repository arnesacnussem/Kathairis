package com.Krevik.Blocks;

import java.util.Random;

import com.Krevik.Main.CreativeTabsMystic;
import com.Krevik.Main.KCore;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockMysticOre extends BaseBlock
{
	int minDrop1;
	int maxDrop1;
	int minExp1;
	int maxExp1;
    public BlockMysticOre(String Name, float hardness1, float resistance, int minDrop,int maxDrop, int minExp, int maxExp)
    {
        super(Name,Material.ROCK,CreativeTabsMystic.mainTab,hardness1,resistance,SoundType.STONE);
        minDrop1=minDrop;
        maxDrop1=maxDrop;
        minExp1=minExp;
        maxExp1=maxExp;
    }
    

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return MathHelper.getInt(random, minDrop1, maxDrop1);
    }
    

    /**
     * Get the quantity dropped based on the given fortune level
     */
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState)this.getBlockState().getValidStates().iterator().next(), random, fortune))
        {
            int i = random.nextInt(fortune + 2) - 1;

            if (i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(random) * (i + 1);
        }
        else
        {
            return this.quantityDropped(random);
        }
    }
    
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
    {
    	Random random = new Random();
    	if(this==KCore.AdamantiumOre) {
    		for(int c=0;c<3+random.nextInt(3);c++) {
    			EntityWolf ew = new EntityWolf(worldIn);
    			ew.setPosition(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5);
    			ew.setAngry(true);
    			if(!worldIn.isRemote) {
        			worldIn.spawnEntity(ew);
        			ew.setAngry(true);
        			EntityPlayer ep = worldIn.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, false);
        			if(ep!=null) {
            			ew.setAttackTarget(ep);
        			}
    			}
    		}
    	}
    }
    
    /**
     * Spawns this Block's drops into the World as EntityItems.
     */
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }
    @Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        return MathHelper.getInt(rand, minExp1, maxExp1);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this);
    }
}