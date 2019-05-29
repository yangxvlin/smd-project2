package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:08
 * description: Finish Tile adapter
 **/

public class FinishAdapter implements ITileAdapter {
    private MapTile finishTile;

    public FinishAdapter(MapTile finishTile) {
        this.finishTile = finishTile;
    }

    @Override
    public TileType getType() {
        return TileType.FINISH;
    }

    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.FINISH;
    }
}
