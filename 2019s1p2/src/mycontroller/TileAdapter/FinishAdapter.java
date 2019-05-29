package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:08
 * description: Finish Tile adapter
 **/

public class FinishAdapter implements ITileAdapter {
    /**
     * mapTile adaptee
     */
    private MapTile finishTile;

    /**
     * @param finishTile : mapTile with finish type
     */
    public FinishAdapter(MapTile finishTile) {
        this.finishTile = finishTile;
    }

    /**
     * @return : FINISH tile type
     */
    @Override
    public TileType getType() {
        return TileType.FINISH;
    }

    /**
     * @param tileType : TileType
     * @return : true if input TileType is FINISH otherwise false
     */
    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.FINISH;
    }
}
