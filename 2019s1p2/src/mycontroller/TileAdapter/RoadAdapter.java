package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Team number: W12-3
 * Team member: XuLin Yang(904904), Zhuoqun Huang(908525), Renjie Meng(877396)
 *
 * @create 2019-05-22 13:08
 * description: Road Tile adapter
 **/

public class RoadAdapter implements ITileAdapter {
    /**
     * mapTile adaptee
     */
    private MapTile roadTile;

    /**
     * @param roadTile : mapTile with road type
     */
    public RoadAdapter(MapTile roadTile) {
        this.roadTile = roadTile;
    }

    /**
     * @return : ROAD tile type
     */
    @Override
    public TileType getType() {
        return TileType.ROAD;
    }

    /**
     * @param tileType : TileType
     * @return : true if input TileType is ROAD otherwise false
     */
    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.ROAD;
    }
}
