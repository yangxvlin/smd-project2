package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:08
 * description:
 **/

public class RoadAdapter implements ITileAdapter {
    private MapTile roadTile;

    public RoadAdapter(MapTile roadTile) {
        this.roadTile = roadTile;
    }

    @Override
    public TileType getType() {
        return TileType.ROAD;
    }

    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.ROAD;
    }
}
