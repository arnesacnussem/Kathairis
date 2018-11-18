package com.Krevik.Capabilities.EnteringKathairis;

import com.Krevik.Main.KCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public interface IWasInKathairisCapability {
    ResourceLocation ID = new ResourceLocation(KCore.MODID, "wasInKathairis");
    void setEntity(EntityLivingBase entity);
    boolean wasInKathairisBefore();
    void setWasInKathairis(boolean was);
}