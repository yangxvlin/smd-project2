package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:14
 * description:
 **/

public class LavaAdapter implements ITileAdapter {
    private MapTile lavaTile;

    public LavaAdapter(MapTile lavaTile) {
        this.lavaTile = lavaTile;
    }

    @Override
    public TileType getType() {
        return TileType.LAVA;
    }

    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.LAVA;
    }
}
