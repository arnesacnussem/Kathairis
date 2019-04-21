package io.github.krevik.kathairis.item;

import io.github.krevik.kathairis.entity.EntityMysticWandShoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemMysticWand extends Item {
    public ItemMysticWand(ItemGroup group) {
        super(new Item.Properties().group(group).maxStackSize(1).defaultMaxDamage(100).rarity(EnumRarity.RARE));
    }

    public int getItemEnchantability() {
        return 0;
    }

    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_) {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if(!world.isRemote) {
            EntityMysticWandShoot bullet = new EntityMysticWandShoot(world);
            bullet.setPosition(player.posX + player.getForward().x, player.posY + player.getEyeHeight() + player.getForward().y, player.posZ + player.getForward().z);
            bullet.accelerationX = player.getForward().normalize().x;
            bullet.accelerationY = player.getForward().normalize().y;
            bullet.accelerationZ = player.getForward().normalize().z;
            world.spawnEntity(bullet);
            player.getHeldItem(hand).damageItem(1, player);
        }
        return super.onItemRightClick(world, player, hand);
    }
}