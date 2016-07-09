/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.bartkneepkens.mailboxmod.block;

import com.bartkneepkens.mailboxmod.MailBoxMod;
import com.bartkneepkens.mailboxmod.tileentity.MailboxTileEntity;
import net.minecraft.block.Block;
import static net.minecraft.block.Block.soundTypeWood;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 *
 * @author Bart  Kneepkens
 */
public class MailboxBlock extends BlockContainer {
    
    public IIcon[] icons = new IIcon[6];
    
    protected MailboxBlock(String name, Material material) {
        super(material);
        this.setBlockName(name);
        this.setBlockTextureName(MailBoxMod.MODID + ":" + name);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(2.0F);
        this.setResistance(6.0F);
        this.setLightLevel(1.0F);
        this.setHarvestLevel("pickaxe", 3);
        this.setStepSound(soundTypeWood);
    }
    
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        for (int i = 0; i < 6; i ++) {
            if(i <= 1){
                this.icons[i] = reg.registerIcon(this.textureName + "_planks");
            }
            else{
                this.icons[i] = reg.registerIcon(this.textureName + "_side");
            }
        }
    }
    
    @Override
    public IIcon getIcon(int side, int meta) {
        return this.icons[side];
    }
    
    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new MailboxTileEntity();
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_){
        MailboxTileEntity tileentity = (MailboxTileEntity) world.getTileEntity(x, y, z);
        
        if(tileentity != null) {
            for(int i = 0; i < tileentity.getSizeInventory(); i++) {
                ItemStack itemstack = tileentity.getStackInSlot(i);
                
                if(itemstack != null) {
                    
                    while(itemstack.stackSize > 0) {
                        int j =  10;
                        
                        if(j > itemstack.stackSize) {
                            j = itemstack.stackSize;
                        }
                        
                        itemstack.stackSize -= j;
                        
                        EntityItem item = new EntityItem(world, (double)((float)x ), (double)((float)y ), (double)((float)z ), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
                        
                        if(itemstack.hasTagCompound()) {
                            item.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                        
                        world.spawnEntityInWorld(item);
                    }
                }
            }
            
        }
    }
        
        
        
    }
