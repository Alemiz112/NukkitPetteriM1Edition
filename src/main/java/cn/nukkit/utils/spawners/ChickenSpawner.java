package cn.nukkit.utils.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.passive.EntityChicken;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.AbstractEntitySpawner;
import cn.nukkit.utils.Spawner;
import cn.nukkit.utils.SpawnResult;

public class ChickenSpawner extends AbstractEntitySpawner {

    public ChickenSpawner(Spawner spawnTask) {
        super(spawnTask);
    }

    public SpawnResult spawn(Player player, Position pos, Level level) {
        SpawnResult result = SpawnResult.OK;

        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (blockId != Block.GRASS) {
            result = SpawnResult.WRONG_BLOCK;
        } else if (pos.y > 256 || pos.y < 1 || blockId == Block.AIR) {
            result = SpawnResult.POSITION_MISMATCH;
        } else if (level.getName().equals("nether") || level.getName().equals("end")) {
            result = SpawnResult.WRONG_BIOME;
        } else {
            this.spawnTask.createEntity(getEntityName(), pos.add(0, 1.7, 0));
        }

        return result;
    }

    @Override
    public int getEntityNetworkId() {
        return EntityChicken.NETWORK_ID;
    }

    @Override
    public String getEntityName() {
        return "Chicken";
    }
}
