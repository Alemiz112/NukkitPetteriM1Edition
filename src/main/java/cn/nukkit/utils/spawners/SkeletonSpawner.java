package cn.nukkit.utils.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.mob.EntitySkeleton;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.generator.biome.Biome;
import cn.nukkit.utils.AbstractEntitySpawner;
import cn.nukkit.utils.Spawner;
import cn.nukkit.utils.SpawnResult;

public class SkeletonSpawner extends AbstractEntitySpawner {

    public SkeletonSpawner(Spawner spawnTask) {
        super(spawnTask);
    }

    @Override
    public SpawnResult spawn(Player player, Position pos, Level level) {
        SpawnResult result = SpawnResult.OK;

        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);
        int time = level.getTime() % Level.TIME_FULL;
        int light = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (pos.y > 256 || pos.y < 1 || blockId == Block.AIR) {
            result = SpawnResult.POSITION_MISMATCH;
        } else if (level.getName().equals("nether")) {
            if (blockId == Block.NETHERRACK) {
                this.spawnTask.createEntity("WitherSkeleton", pos.add(0, 2.8, 0));
            }
        } else if (biomeId == Biome.ICE_PLAINS || biomeId == Biome.TUNDRA) {
            if (time > 13184 && time < 22800) {
                this.spawnTask.createEntity("Stray", pos.add(0, 2.8, 0));
            }
        } else if (Block.transparent[blockId]) {
            result = SpawnResult.WRONG_BLOCK;
        } else if (level.getName().equals("end")) {
            result = SpawnResult.WRONG_BIOME;
        } else if (light > 7) {
            result = SpawnResult.WRONG_LIGHTLEVEL;
        } else if (time > 13184 && time < 22800) {
            this.spawnTask.createEntity(getEntityName(), pos.add(0, 2.8, 0));
        }

        return result;
    }

    @Override
    public int getEntityNetworkId() {
        return EntitySkeleton.NETWORK_ID;
    }

    @Override
    public String getEntityName() {
        return "Skeleton";
    }
}
