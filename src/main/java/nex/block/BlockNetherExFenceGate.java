/*
 * Copyright (C) 2016.  LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nex.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;

public class BlockNetherExFenceGate extends BlockFenceGate
{
    public BlockNetherExFenceGate(String name, Material material)
    {
        super(BlockPlanks.EnumType.OAK);

        ReflectionHelper.setPrivateValue(Block.class, this, material, "field_149764_J", "blockMaterial");
        ReflectionHelper.setPrivateValue(Block.class, this, material.getMaterialMapColor(), "field_181083_K", "blockMapColor");

        String[] nameParts = name.split("_");
        String gateName = nameParts[0] + StringUtils.capitalize(nameParts[1]) + StringUtils.capitalize(nameParts[2]);
        String gateType = nameParts[3];

        for(int i = 4; i < nameParts.length; i++)
        {
            gateType += StringUtils.capitalize(nameParts[i]);
        }

        useNeighborBrightness = true;

        setCreativeTab(NetherEx.CREATIVE_TAB);
        setSoundType(SoundType.STONE);
        setRegistryName(NetherEx.MOD_ID + ":" + name);
        setUnlocalizedName(NetherEx.MOD_ID + ":" + gateName + "." + gateType);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        EnumFacing.Axis facing = state.getValue(FACING).getAxis();

        Block northBlock = worldIn.getBlockState(pos.north()).getBlock();
        Block eastBlock = worldIn.getBlockState(pos.east()).getBlock();
        Block southBlock = worldIn.getBlockState(pos.south()).getBlock();
        Block westBlock = worldIn.getBlockState(pos.west()).getBlock();

        if(facing == EnumFacing.Axis.Z && ((westBlock instanceof BlockWall || westBlock instanceof BlockNetherExWall) || (eastBlock instanceof BlockWall || eastBlock instanceof BlockNetherExWall)) || facing == EnumFacing.Axis.X && ((northBlock instanceof BlockWall || northBlock instanceof BlockNetherExWall) || (southBlock instanceof BlockWall || southBlock instanceof BlockNetherExWall)))
        {
            return state.withProperty(IN_WALL, true);
        }

        return state.withProperty(IN_WALL, false);
    }
}
