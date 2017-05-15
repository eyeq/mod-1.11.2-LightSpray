package eyeq.lightspray.item;

import com.google.common.collect.*;
import eyeq.lightspray.LightSpray;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemLightSpray extends Item {
    public final int light;

    public ItemLightSpray(int light) {
        this.light = light;
        this.setMaxStackSize(1);
        this.setMaxDamage(64);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = player.getHeldItem(hand);
        if(!player.canPlayerEdit(pos, facing, itemStack)) {
            return EnumActionResult.FAIL;
        }
        if(!world.isAirBlock(pos)) {
            IBlockState state = world.getBlockState(pos);
            ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> propertyValueTable = null;
            if(state instanceof BlockStateContainer.StateImplementation) {
                propertyValueTable = ((BlockStateContainer.StateImplementation) state).getPropertyValueTable();
            }
            BlockStateContainer.StateImplementation newState = new BlockStateContainer.StateImplementation(state.getBlock(), state.getProperties(), propertyValueTable) {
                @Override
                public int getLightValue() {
                    return light;
                }

                @Override
                public int getLightValue(IBlockAccess world, BlockPos pos) {
                    return super.getLightValue();
                }
            };
            world.setBlockState(pos, newState);
            world.checkLight(pos);
            world.setBlockState(pos, state);
        }
        if(!player.isCreative() && itemStack.getItemDamage() == 1) {
            player.setHeldItem(hand, new ItemStack(LightSpray.sprayEmpty));
        } else {
            itemStack.damageItem(1, player);
        }
        return EnumActionResult.SUCCESS;
    }
}
