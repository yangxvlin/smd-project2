package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:15
 * description: Water Tile adapter
 **/

public class WaterAdapter implements ITileAdapter {
    /**
     * mapTile adaptee
     */
    private MapTile waterTile;

    /**
     * @param waterTile : mapTile with water type
     */
    public WaterAdapter(MapTile waterTile) {
        this.waterTile = waterTile;
    }

    /**
     * @return : WATER tile type
     */
    @Override
    public TileType getType() {
        return TileType.WATER;
    }

    /**
     * @param tileType : TileType
     * @return : true if input TileType is WATER otherwise false
     */
    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.WATER;
    }
}
