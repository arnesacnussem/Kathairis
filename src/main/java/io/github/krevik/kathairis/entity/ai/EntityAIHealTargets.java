package io.github.krevik.kathairis.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityAIHealTargets extends EntityAIBase
{
    private final EntityCreature creature;
    private final World world;
    private EntityPlayer target;

    public EntityAIHealTargets(EntityCreature theCreatureIn)
    {
        this.creature = theCreatureIn;
        this.world = theCreatureIn.world;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        return world.getClosestPlayerToEntity(creature,5)!=null;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return false;
    }

    @Override
    public void startExecuting()
    {
        EntityLivingBase entity = world.getNearestAttackablePlayer(creature,5,5);
        if(entity!=null){
            if(entity instanceof EntityPlayer){
                target=(EntityPlayer)entity;
            }
        }
        if(target!=null) {
            if (creature.getRNG().nextInt(30)==0) {
                target.heal(1);
            }
        }
    }

}