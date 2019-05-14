package mod.krevik.block;

import mod.krevik.util.CreativeTabsMystic;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMysticOre extends BaseBlock
{
	int minDrop1;
	int maxDrop1;
	int minExp1;
	int maxExp1;
    public BlockMysticOre(String Name, float hardness1, float resistance, int minDrop,int maxDrop, int minExp, int maxExp)
    {
        super(Name,Material.ROCK,CreativeTabsMystic.buildingBlocks,hardness1,resistance,SoundType.STONE);
        minDrop1=minDrop;
        maxDrop1=maxDrop;
        minExp1=minExp;
        maxExp1=maxExp;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return MathHelper.getInt(random, minDrop1, maxDrop1);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), random, fortune))
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

    @Override
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

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this);
    }
}