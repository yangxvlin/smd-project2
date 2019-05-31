package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Team number: W12-3
 * Team member: XuLin Yang(904904), Zhuoqun Huang(908525), Renjie Meng(877396)
 *
 * @create 2019-05-22 13:14
 * description: Lava Tile adapter
 **/

public class LavaAdapter implements ITileAdapter {
    /**
     * mapTile adaptee
     */
    private MapTile lavaTile;

    /**
     * @param lavaTile : mapTile with lava type
     */
    public LavaAdapter(MapTile lavaTile) {
        this.lavaTile = lavaTile;
    }

    /**
     * @return : LAVA tile type
     */
    @Override
    public TileType getType() {
        return TileType.LAVA;
    }

    /**
     * @param tileType : TileType
     * @return : true if input TileType is LAVA otherwise false
     */
    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.LAVA;
    }
}
