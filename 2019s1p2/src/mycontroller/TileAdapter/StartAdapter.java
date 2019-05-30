package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Team number: W12-3
 * Team member: XuLin Yang(904904), Zhuoqun Huang(908525), Renjie Meng(877396)
 *
 * @create 2019-05-22 13:08
 * description: Start Tile adapter
 **/

public class StartAdapter implements ITileAdapter {
    /**
     * mapTile adaptee
     */
    private MapTile startTile;

    /**
     * @param startTile : mapTile with start type
     */
    public StartAdapter(MapTile startTile) {
        this.startTile = startTile;
    }

    /**
     * @return : START tile type
     */
    @Override
    public TileType getType() {
        return TileType.START;
    }

    /**
     * @param tileType : TileType
     * @return : true if input TileType is START otherwise false
     */
    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.START;
    }
}
