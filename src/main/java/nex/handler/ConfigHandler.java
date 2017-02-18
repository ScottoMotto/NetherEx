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

package nex.handler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import nex.NetherEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
@Config(modid = NetherEx.MOD_ID, name = "NetherEx")
public class ConfigHandler
{

    private static Configuration config;
    public static Client client = new Client();
    public static Dimension dimension = new Dimension();
    public static Block block = new Block();
    public static PotionEffect potion_effect = new PotionEffect();
    public static Entity entity = new Entity();
    public static Biome biome = new Biome();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|ConfigHandler");

    public static class Client
    {
        public static Visual visual = new Visual();

        public static class Visual
        {
            public static boolean disableNetherFog = false;
        }
    }

    public static class Dimension
    {
        public static Nether nether = new Nether();

        public static class Nether
        {
            public static boolean generateSoulSand = false;
            public static boolean generateGravel = false;
            public static boolean isLavaInfinite = false;
            public static boolean enablePortalFix = true;
        }
    }

    public static class Block
    {
        public static SoulSand soul_sand = new SoulSand();
        public static Magma magma = new Magma();
        public static Rime rime = new Rime();
        public static Thornstalk thornstalk = new Thornstalk();
        public static Hyphae hyphae = new Hyphae();

        public static class SoulSand
        {
            public static boolean doesNetherwartUseNewGrowthSystem = true;
            public static boolean allowAllHoesToTill = false;
            public static boolean doesRequireIchor = true;
        }

        public static class Magma
        {
            public static boolean turnIntoLava = false;
        }

        public static class Rime
        {
            public static boolean canFreezeWater = true;
            public static boolean canFreezeLava = true;
            public static boolean canFreezeMobs = true;
        }

        public static class Thornstalk
        {
            public static boolean canDestroyItems = false;

            @Config.Comment("Mobs the Thornstalk shouldn't hurt")
            public static String[] blacklist = new String[]{"minecraft:wither_skeleton", "minecraft:zombie_pigman", "nex:monster_spinout"};
        }

        public static class Hyphae
        {
            public static boolean doesSpread = false;
        }
    }

    public static class PotionEffect
    {
        public static Freeze freeze = new Freeze();

        public static Spore spore = new Spore();

        public static class Freeze
        {
            @Config.Comment({"The higher the number, the rarer it is to thaw", "The lower the number, the more common it is to thaw"})
            @Config.RangeInt(min = 1, max = 2048)
            public static int chanceOfThawing = 1024;

            @Config.Comment("Mobs that shouldn't freeze")
            public static String[] blacklist = new String[]{"minecraft:blaze", "minecraft:polar_bear", "nex:monster_wight", "nex:monster_ember", "nex:monster_spinout"};
        }

        public static class Spore
        {
            @Config.Comment({"The higher the number, the rarer it is to spawn a spore", "The lower the number, the more common it is to spawn a spore"})
            @Config.RangeInt(min = 1, max = 256)
            public static int chanceOfSporeSpawning = 128;

            @Config.Comment("Mobs that shouldn't spawn Spores")
            public static String[] blacklist = new String[]{"nex:monster_spore_creeper", "nex:monster_spore", "nex:neutral_mogus"};
        }
    }

    public static class Entity
    {
        public static Ember ember = new Ember();
        public static Nethermite nethermite = new Nethermite();
        public static Spinout spinout = new Spinout();
        public static SporeCreeper spore_creeper = new SporeCreeper();
        public static Spore spore = new Spore();
        public static GhastQueen ghast_queen = new GhastQueen();

        public static class Ember
        {
            @Config.Comment({"The higher the number, the rarer it is to set a player on fire", "The lower the number, the more common it is to set a player on fire"})
            @Config.RangeInt(min = 1, max = 64)
            public static int chanceOfSettingPlayerOnFire = 4;
        }

        public static class Nethermite
        {
            @Config.Comment({"The higher the number, the rarer it is for a Nethermite to spawn", "The lower the number, the more common it is for a Nethermite to spawn"})
            @Config.RangeInt(min = 1, max = 64)
            public static int chanceOfSpawning = 24;

            @Config.Comment("Blocks the Nethermite should spawn from")
            public static String[] whitelist = new String[]{"minecraft:netherrack", "nex:block_netherrack"};
        }

        public static class Spinout
        {

            @Config.Comment({"The lower the number, the less time a Spinout spins", "The higher the number, the more time a Spinout spins"})
            @Config.RangeInt(min = 1, max = 512)
            public static int spinTime = 6;

            @Config.Comment({"The lower the number, the less time a Spinout goes without spinning", "The higher the number, the more time a Spinout goes without spinning"})
            @Config.RangeInt(min = 1, max = 512)
            public static int spinCooldown = 2;
        }

        public static class SporeCreeper
        {
            @Config.Comment({"The higher the number, the rarer it is for s Spore Creeper to spawn a Spore on death", "The lower the number, the more common it is for a Spore Creeper to spawn a Spore on death"})
            @Config.RangeInt(min = 1, max = 64)
            public static int chanceOfSporeSpawning = 12;
        }

        public static class Spore
        {
            @Config.Comment({"The lower the number, the less it takes a Spore to grow", "The higher the number, the more time it takes for a Spore to grow"})
            @Config.RangeInt(min = 1, max = 512)
            public static int growthTime = 60;

            @Config.Comment({"The lower the number, the less Spore Creeper spawn from a Spore", "The higher the number, the more Spore Creeper spawn from a Spore"})
            @Config.RangeInt(min = 1, max = 64)
            public static int creeperSpawns = 3;

        }

        public static class GhastQueen
        {
            @Config.Comment({"The lower the number, the less cooldown the Ghast Queen has after spawning Ghastlings", "The higher the number, the more cooldown the Ghast Queen has after spawning Ghastlings"})
            @Config.RangeInt(min = 1, max = 512)
            public static int ghastlingSpawnCooldown = 10;

            @Config.Comment({"The lower the number, the less Ghastling spawn", "The higher the number, the more Ghastling spawn"})
            @Config.RangeInt(min = 1, max = 64)
            public static int ghastlingSpawns = 4;
        }
    }

    public static class Biome
    {
        public static Hell hell = new Hell();
        public static RuthlessSands ruthless_sands = new RuthlessSands();
        public static FungiForest fungi_forest = new FungiForest();
        public static TorridWasteland torrid_wasteland = new TorridWasteland();
        public static ArcticAbyss arctic_abyss = new ArcticAbyss();

        public static class Hell
        {
            public static boolean generateBiome = true;
            public static boolean generateLavaSprings = true;
            public static boolean generateFire = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateBrownMushrooms = true;
            public static boolean generateRedMushrooms = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateMagma = true;
            public static boolean generateLavaTraps = true;
            public static boolean generateVillages = true;
            public static boolean generateGraves = true;

            @Config.Comment({"The lower the number, the rarer the Hell is", "The higher the number, the more common the Hell biome is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int biomeRarity = 10;

            @Config.Comment({"The lower the number, the rarer Lava Springs are", "The higher the number, the more common Lava Springs are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaSpringRarity = 8;

            @Config.Comment({"The lower the number, the rarer Fire is", "The higher the number, the more common Fire is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int fireRarity = 10;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass2Rarity = 10;

            @Config.LangKey("config.nex:biome.hell.quartzOreRarity")
            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number, the rarer Magma is", "The higher the number, the more common Magma is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int magmaRarity = 4;

            @Config.Comment({"The lower the number, the rarer Lava Traps are", "The higher the number, the more common Lava Traps are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaTrapRarity = 16;

            @Config.Comment({"The higher the number, the rarer Villages are", "The lower the number, the more common Villages are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int villageRarity = 1;

            @Config.Comment({"The higher the number, the rarer Graves are", "The lower the number, the more common Graves are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int graveRarity = 24;
        }

        public static class RuthlessSands
        {
            public static boolean generateBiome = true;
            public static boolean generateLavaSprings = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateLavaTraps = true;
            public static boolean generateThornstalk = true;
            public static boolean generateAncientAltars = true;

            @Config.Comment({"The lower the number, the rarer The Ruthless Sands is", "The higher the number, the more common the Ruthless Sands biome is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int biomeRarity = 8;

            @Config.Comment({"The lower the number, the rarer Lava Springs are", "The higher the number, the more common Lava Springs are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaSpringRarity = 8;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass2Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number, the rarer Lava Traps are", "The higher the number, the more common Lava Traps are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaTrapRarity = 16;

            @Config.Comment({"The lower the number, the rarer Thornstalk is", "The higher the number, the more common Thornstalk is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int thornstalkRarity = 10;

            @Config.Comment({"The higher the number, the rarer Ancient Altars are", "The lower the number, the more common Ancient Altars are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int ancientAltarRarity = 40;
        }

        public static class FungiForest
        {
            public static boolean generateBiome = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateElderMushrooms = true;
            public static boolean generateEnokiMushrooms = true;

            @Config.Comment({"The lower the number, the rarer the Fungi Forest biome is", "The higher the number, the more common the Fungi Forest biome is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int biomeRarity = 4;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass2Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number, the rarer Elder Mushrooms are", "The higher the number, the more common Elder Mushrooms are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int elderMushroomRarity = 32;

            @Config.Comment({"The lower the number, the rarer Enoki Mushrooms are", "The higher the number, the more common Enoki Mushrooms are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int enokiMushroomRarity = 4;
        }

        public static class TorridWasteland
        {
            public static boolean generateBiome = true;
            public static boolean generateLavaSprings = true;
            public static boolean generateFire = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateBasalt = true;
            public static boolean generateMagma = true;
            public static boolean generateLavaTraps = true;
            public static boolean generateLavaPits = true;
            public static boolean generateBlazingPyramids = true;

            @Config.Comment({"The lower the number, the rarer the Torrid Wasteland biome is", "The higher the number, the more common the Torrid Wateland biome is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int biomeRarity = 6;

            @Config.Comment({"The lower the number, the rarer Lava Springs are", "The higher the number, the more common Lava Springs are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaSpringRarity = 24;

            @Config.Comment({"The lower the number, the rarer Fire is", "The higher the number, the more common Fire is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int fireRarity = 32;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass2Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number, the rarer Basalt is", "The higher the number, the more common Basalt is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int basaltRarity = 12;

            @Config.Comment({"The lower the number, the rarer Magma is", "The higher the number, the more common Magma is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int magmaRarity = 12;

            @Config.Comment({"The lower the number, the rarer Lava Traps are", "The higher the number, the more common Lava Traps are is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaTrapRarity = 48;

            @Config.Comment({"The lower the number, the rarer Lava Pis are", "The higher the number, the more common Lava Pits are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaPitRarity = 8;

            @Config.Comment({"The higher the number, the rarer Blazing Pyramids are", "The lower the number, the more common Blazing Pyramids are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int blazingPyramidRarity = 4;
        }

        public static class ArcticAbyss
        {
            public static boolean generateBiome = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateRimeOre = true;
            public static boolean generateIchorPits = true;

            @Config.Comment({"The lower the number, the rarer the Arctic Abyss biome is", "The higher the number, the more common the Arctic Abyss biome is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int biomeRarity = 2;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass2Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number, the rarer Rime Ore is", "The higher the number, the more common Rime Ore is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int rimeOreRarity = 16;

            @Config.Comment({"The higher the number, the rarer Ichor Pits are", "The lower the number, the more common Ichor Pits are"})
            @Config.RangeInt(min = 1, max = 64)
            public static int ichorPitRarity = 16;

            @Config.Comment({"The higher the number, the rarer it is for mobs to Freeze in the Arctic Abyss biome", "The lower the number, the more common it is for mobs to Freeze in the Arctic Abyss biome"})
            @Config.RangeInt(min = 1, max = 2048)
            public static int chanceOfFreezing = 512;
        }
    }

    public static Configuration getConfig()
    {
        if(config == null)
        {
            try
            {
                MethodHandle CONFIGS = MethodHandles.lookup().unreflectGetter(ReflectionHelper.findField(ConfigManager.class, "CONFIGS"));
                Map<String, Configuration> configsMap = (Map<String, Configuration>) CONFIGS.invokeExact();

                String fileName = "NetherEx.cfg";
                Optional<Map.Entry<String, Configuration>> entryOptional = configsMap.entrySet().stream().filter(entry -> fileName.equals(new File(entry.getKey()).getName())).findFirst();

                entryOptional.ifPresent(stringConfigurationEntry -> config = stringConfigurationEntry.getValue());
            }
            catch(Throwable ignored)
            {
            }
        }

        return config;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if(event.getModID().equals(NetherEx.MOD_ID))
        {
            ConfigManager.load(NetherEx.MOD_ID, Config.Type.INSTANCE);
            LOGGER.info("Configuration has been saved.");
        }
    }
}
