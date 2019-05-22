package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:15
 * description:
 **/

public class WaterAdapter implements ITileAdapter {
    private MapTile waterTile;

    public WaterAdapter(MapTile waterTile) {
        this.waterTile = waterTile;
    }

    @Override
    public TileType getType() {
        return TileType.WATER;
    }

    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.WATER;
    }
}
