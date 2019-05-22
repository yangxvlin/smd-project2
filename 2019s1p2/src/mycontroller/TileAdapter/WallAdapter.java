package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:07
 * description:
 **/

public class WallAdapter implements ITileAdapter {
    private MapTile wallTile;

    public WallAdapter(MapTile wallTile) {
        this.wallTile = wallTile;
    }

    @Override
    public TileType getType() {
        return TileType.WALL;
    }

    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.WALL;
    }
}
