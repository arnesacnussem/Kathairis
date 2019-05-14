package mod.krevik.world.dimension;

import mod.krevik.KCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nullable;

public class KathairisDataStorage extends WorldSavedData
{
	private static final String DATA_NAME = KCore.MODID + "_data";
	private boolean isDeathSpawned;
	private boolean isDeathFighting;
	private boolean isDeathDefeated;
	private static int sandstormTime;
	private static double sandstormX;
	private static double sandstormZ;
	private static boolean shouldAddSandstormFog;

	private static float fogTime;
	private static float lastFogTime;

	public KathairisDataStorage()
	{
		super(DATA_NAME);
		isDeathSpawned=false;
		isDeathFighting=false;
		isDeathDefeated=false;
		shouldAddSandstormFog=false;
		sandstormTime=0;
		sandstormX=0;
		sandstormZ=0;
		fogTime=0;
		lastFogTime=0;
	}

	public static boolean getShouldAddSandstormFog(){
		return shouldAddSandstormFog;
	}

	public static void setShouldAddSandstormFog(boolean c){
		shouldAddSandstormFog=c;
	}


	@Nullable
	public static float getFogTime(){
		return fogTime;
	}

	public static void setFogTime(float f){
		fogTime=f;
	}

	@Nullable
	public static float getLastFogTime(){
		return lastFogTime;
	}

	public static void setLastFogTime(float f){
		lastFogTime=f;
	}

	public KathairisDataStorage(String name)
	{
		super(name);
	}
	

	public static KathairisDataStorage getDataInstance(World world)
	{

		MapStorage storage = world.getMapStorage();
		KathairisDataStorage instance = (KathairisDataStorage) storage.getOrLoadData(KathairisDataStorage.class, DATA_NAME);
		if (instance == null) {
			instance = new KathairisDataStorage();
			storage.setData(DATA_NAME, instance);
		}
		return (KathairisDataStorage) storage.getOrLoadData(KathairisDataStorage.class, DATA_NAME);
	}
	
	public static KathairisDataStorage initialise() {
		KathairisDataStorage instance = new KathairisDataStorage();
		return instance;
	}
	
	public void setSandstormX(double c)
	{
		sandstormX=c;
		markDirty();
	}
	public void setSandstormZ(double c)
	{
		sandstormZ=c;
		markDirty();
	}
	
	public void setSandstormTime(int c)
	{
		sandstormTime=c;
		markDirty();
	}

	public void setIsDeathSpawned(boolean b)
	{
		this.isDeathSpawned=b;
		markDirty();
	}
	
	public void setIsDeathFighting(boolean b)
	{
		this.isDeathFighting=b;
		markDirty();
	}
	
	public void setIsDeathDefeated(boolean b)
	{
		this.isDeathDefeated=b;
		markDirty();
	}

	
	@Nullable
	public static int getSandstormTime()
	{
		return sandstormTime;
	}
	
	@Nullable
	public static double getSandstormX()
	{
		return sandstormX;
	}
	
	@Nullable
	public static double getSandstormZ()
	{
		return sandstormZ;
	}
	
	@Nullable
	public Boolean getIsDeathSpawned()
	{
		return this.isDeathSpawned;
	}
	
	@Nullable
	public Boolean getIsDeathDefeated()
	{
		return this.isDeathDefeated;
	}
	
	@Nullable
	public Boolean getIsDeathFighting()
	{
		return this.isDeathFighting;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		this.isDeathSpawned=nbt.getBoolean("is");
		this.isDeathFighting=nbt.getBoolean("fighting");
		this.isDeathDefeated=nbt.getBoolean("defeated");
		sandstormTime=nbt.getInteger("sandstormtime");
		sandstormX=nbt.getDouble("sandstormx");
		sandstormZ=nbt.getDouble("sandstormz");
		fogTime=nbt.getFloat("fogTime");
		lastFogTime=nbt.getFloat("lastFogTime");
		shouldAddSandstormFog=nbt.getBoolean("shouldAddSandstormFog");

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{	
		nbt.setBoolean("is", isDeathSpawned);
		nbt.setBoolean("fighting", isDeathFighting);
		nbt.setBoolean("defeated", isDeathDefeated);
		nbt.setInteger("sandstormtime", sandstormTime);
		nbt.setDouble("sandstormx", sandstormX);
		nbt.setDouble("sandstormz", sandstormZ);
		nbt.setFloat("fogTime",fogTime);
		nbt.setFloat("lastFogTime",lastFogTime);
		nbt.setBoolean("shouldAddSandstormFog", shouldAddSandstormFog);

		return nbt;
	}
}