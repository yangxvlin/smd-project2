package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:13
 * description:
 **/

public class HealthAdapter implements ITileAdapter {
    private MapTile healthTile;

    public HealthAdapter(MapTile healthTile) {
        this.healthTile = healthTile;
    }

    @Override
    public TileType getType() {
        return TileType.HEALTH;
    }

    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.HEALTH;
    }
}
