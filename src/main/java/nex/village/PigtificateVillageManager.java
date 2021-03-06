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

package nex.village;

import com.google.common.collect.Maps;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class PigtificateVillageManager
{
    private static final Map<World, PigtificateVillageCollection> pigtificateVillages = Maps.newHashMap();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|PigtificateVillageManager");

    public static void init(World world)
    {
        String dimension = world.provider.getDimensionType().name().toLowerCase();
        String id = PigtificateVillageCollection.fileNameForProvider(world.provider);
        PigtificateVillageCollection pigtificateVillageCollection = (PigtificateVillageCollection) world.getPerWorldStorage().getOrLoadData(PigtificateVillageCollection.class, id);

        if(pigtificateVillageCollection == null)
        {
            LOGGER.info("The Pigtificate Village data for " + dimension + " was created successfully.");

            pigtificateVillages.put(world, new PigtificateVillageCollection(world));
            world.getPerWorldStorage().setData(id, pigtificateVillages.get(world));
        }
        else
        {
            LOGGER.info("The Pigtificate Village data for " + dimension + " was read successfully.");

            pigtificateVillages.put(world, pigtificateVillageCollection);
            pigtificateVillages.get(world).setWorldsForAll(world);
        }
    }

    public static PigtificateVillageCollection getPigtificateVillages(World world)
    {
        return pigtificateVillages.get(world);
    }
}
