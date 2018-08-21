package com.Krevik.Main;

import java.util.ArrayList;

import com.Krevik.Blocks.BaseBlock;
import com.Krevik.Blocks.BlockMysticLeaf;
import com.Krevik.Blocks.BlockMysticSlabBase;
import com.Krevik.Items.BaseItem;
import com.Krevik.Items.ItemMysticArmor;
import com.Krevik.Items.ItemMysticFood;
import com.Krevik.Items.ItemMysticSword;
import com.Krevik.Items.MysticTool;

public class RegistryHelper {
	
	public RegistryHelper() {
		
	}
	
	public ArrayList<BaseBlock> blocksList = new ArrayList();
	public ArrayList<BlockMysticLeaf> leavesBlocksList = new ArrayList();
	public ArrayList<BlockMysticSlabBase> slabList = new ArrayList();
	public ArrayList<BaseItem> itemList = new ArrayList();
	public ArrayList<MysticTool> toolList = new ArrayList();
	public ArrayList<ItemMysticArmor> armorList = new ArrayList();
	public ArrayList<ItemMysticFood> foodList = new ArrayList();
	public ArrayList<ItemMysticSword> swordList = new ArrayList();

	public void initModels() {
		for(int x=0;x<blocksList.size();x++) {
			blocksList.get(x).initModel();
		}
		for(int x=0;x<leavesBlocksList.size();x++) {
			leavesBlocksList.get(x).initModel();
		}
		for(int x=0;x<slabList.size();x++) {
			slabList.get(x).initModel();
		}
		KCore.MythicStoneSign.initModel();
		KCore.Charger.initModel();
		for(int x=0;x<itemList.size();x++) {
			itemList.get(x).initModel();
		}
		for(int x=0;x<toolList.size();x++) {
			toolList.get(x).initModel();
		}
		for(int x=0;x<armorList.size();x++) {
			armorList.get(x).initModel();
		}
		for(int x=0;x<foodList.size();x++) {
			foodList.get(x).initModel();
		}
		for(int x=0;x<swordList.size();x++) {
			swordList.get(x).initModel();
		}
	}
}
