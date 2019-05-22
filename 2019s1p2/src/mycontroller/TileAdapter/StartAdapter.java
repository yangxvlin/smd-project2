package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:08
 * description:
 **/

public class StartAdapter implements ITileAdapter {
    private MapTile startTile;

    public StartAdapter(MapTile startTile) {
        this.startTile = startTile;
    }

    @Override
    public TileType getType() {
        return TileType.START;
    }

    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.START;
    }
}
