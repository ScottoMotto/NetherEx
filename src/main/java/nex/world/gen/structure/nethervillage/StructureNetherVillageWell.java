/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.world.gen.structure.nethervillage;

import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import nex.block.BlockVanilla;
import nex.block.BlockVanillaFence;
import nex.init.NetherExBlocks;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageWell extends StructureNetherVillage
{
    public StructureNetherVillageWell()
    {
    }

    public StructureNetherVillageWell(Controller controller, int componentType, Random rand, int x, int z)
    {
        super(controller, componentType);
        setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));

        if(getCoordBaseMode().getAxis() == EnumFacing.Axis.Z)
        {
            boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
        }
        else
        {
            boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
        }
    }

    @Override
    public void buildComponent(StructureComponent component, List<StructureComponent> components, Random rand)
    {
        generateAndAddRoadPiece((Controller) component, components, rand, boundingBox.minX - 1, boundingBox.maxY - 4, boundingBox.minZ + 1, EnumFacing.WEST, getComponentType());
        generateAndAddRoadPiece((Controller) component, components, rand, boundingBox.maxX + 1, boundingBox.maxY - 4, boundingBox.minZ + 1, EnumFacing.EAST, getComponentType());
        generateAndAddRoadPiece((Controller) component, components, rand, boundingBox.minX + 1, boundingBox.maxY - 4, boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
        generateAndAddRoadPiece((Controller) component, components, rand, boundingBox.minX + 1, boundingBox.maxY - 4, boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBoxIn)
    {
        if(averageGroundLvl < 0)
        {
            averageGroundLvl = findAverageGroundLevel(world, boundingBoxIn);

            if(averageGroundLvl < 0)
            {
                return true;
            }

            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 3, 0);
        }

        IBlockState netherBrick = Blocks.NETHER_BRICK.getDefaultState();
        IBlockState redNetherBrickFence = NetherExBlocks.FENCE_VANILLA.getDefaultState().withProperty(BlockVanillaFence.TYPE, BlockVanilla.EnumTypeFence.BRICK_NETHER_RED);

        fillWithAir(world, boundingBoxIn, 1, 0, 1, 4, 18, 4);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 1, 4, 12, 4, netherBrick, Blocks.FLOWING_LAVA.getDefaultState(), false);
        setBlockState(world, Blocks.AIR.getDefaultState(), 2, 12, 2, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 3, 12, 2, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 2, 12, 3, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 3, 12, 3, boundingBoxIn);
        setBlockState(world, redNetherBrickFence, 1, 13, 1, boundingBoxIn);
        setBlockState(world, redNetherBrickFence, 1, 14, 1, boundingBoxIn);
        setBlockState(world, redNetherBrickFence, 4, 13, 1, boundingBoxIn);
        setBlockState(world, redNetherBrickFence, 4, 14, 1, boundingBoxIn);
        setBlockState(world, redNetherBrickFence, 1, 13, 4, boundingBoxIn);
        setBlockState(world, redNetherBrickFence, 1, 14, 4, boundingBoxIn);
        setBlockState(world, redNetherBrickFence, 4, 13, 4, boundingBoxIn);
        setBlockState(world, redNetherBrickFence, 4, 14, 4, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 1, 15, 1, 4, 15, 4, netherBrick, netherBrick, false);

        for(int i = 0; i <= 5; ++i)
        {
            for(int j = 0; j <= 5; ++j)
            {
                if(j == 0 || j == 5 || i == 0 || i == 5)
                {
                    setBlockState(world, netherBrick, j, 11, i, boundingBoxIn);
                }
            }
        }

        return true;
    }

    public static class Controller extends StructureNetherVillageWell
    {
        private Biome biome;
        private int villageSize;
        private Piece piece;
        private List<Piece> pieces;
        private List<StructureComponent> pendingHouses = Lists.newArrayList();
        private List<StructureComponent> pendingRoads = Lists.newArrayList();

        public Controller()
        {
        }

        public Controller(Biome biomeIn, Random rand, int x, int z, List<Piece> piecesIn, int villageSizeIn)
        {
            super(null, 0, rand, x, z);

            biome = biomeIn;
            villageSize = villageSizeIn;
            pieces = piecesIn;
            controller = this;
            isZombieInfested = rand.nextInt(50) == 0;
            setStructureType(structureType);
        }

        public Biome getBiome()
        {
            return biome;
        }

        public int getVillageSize()
        {
            return villageSize;
        }

        public Piece getPiece()
        {
            return piece;
        }

        public List<Piece> getPieces()
        {
            return pieces;
        }

        public List<StructureComponent> getPendingHouses()
        {
            return pendingHouses;
        }

        public List<StructureComponent> getPendingRoads()
        {
            return pendingRoads;
        }

        public void setPiece(Piece pieceIn)
        {
            piece = pieceIn;
        }
    }
}
