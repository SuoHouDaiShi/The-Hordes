package net.smileycorp.hordes.common.hordeevent;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;

public class HordeSpawnEntry {
	
	protected final Class<?  extends EntityLiving> clazz;
	protected final int weight;
	protected final int minDay;
	protected final int maxDay;
	protected NBTTagCompound nbt = null;
	
	public HordeSpawnEntry(Class<?  extends EntityLiving> clazz, int weight, int minDay, int maxDay) {
		this.clazz=clazz;
		this.weight=weight;
		this.minDay=minDay;
		this.maxDay=maxDay;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getMinDay() {
		return minDay;
	}
	
	public int getMaxDay() {
		return maxDay;
	}
	
	@Override
	public String toString() {
		String str = "HordeSpawnEntry[clazz="+clazz+",weight="+weight+",minDay="+minDay+",maxDay="+maxDay+"]";
		return nbt==null ? str : str + "{" + nbt.toString() + "}";
	}

	public void setTagCompound(NBTTagCompound nbt) {
		this.nbt=nbt;
	}
	
	public NBTTagCompound getNBT() {
		return nbt == null ? new NBTTagCompound() : nbt;
	}
	
}
