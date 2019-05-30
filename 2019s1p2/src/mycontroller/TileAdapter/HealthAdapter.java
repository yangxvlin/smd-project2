package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Team number: W12-3
 * Team member: XuLin Yang(904904), Zhuoqun Huang(908525), Renjie Meng(877396)
 *
 * @create 2019-05-22 13:13
 * description: Health Tile adapter
 **/

public class HealthAdapter implements ITileAdapter {
    /**
     * mapTile adaptee
     */
    private MapTile healthTile;

    /**
     * @param healthTile : mapTile with health type
     */
    public HealthAdapter(MapTile healthTile) {
        this.healthTile = healthTile;
    }

    /**
     * @return : HEALTH tile type
     */
    @Override
    public TileType getType() {
        return TileType.HEALTH;
    }

    /**
     * @param tileType : TileType
     * @return : true if input TileType is HEALTH otherwise false
     */
    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.HEALTH;
    }
}
