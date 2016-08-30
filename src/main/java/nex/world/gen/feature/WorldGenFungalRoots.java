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

package nex.world.gen.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.init.ModBlocks;

import java.util.Random;

public class WorldGenFungalRoots extends WorldGenerator
{
    @Override
    public boolean generate(World world, Random rand, BlockPos position)
    {
        for(int i = 0; i < 64; ++i)
        {
            BlockPos newPos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if(world.isAirBlock(newPos) && ModBlocks.FUNGAL_ROOTS.canPlaceBlockAt(world, newPos))
            {
                ModBlocks.FUNGAL_ROOTS.generate(world, newPos);
            }
        }

        return true;
    }
}
