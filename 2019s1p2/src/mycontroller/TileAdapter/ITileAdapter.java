package mycontroller.TileAdapter;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:06
 * description: adapter for our system used tiles
 **/

public interface ITileAdapter {
    /**
     * types of tiles used in the system
     */
    enum TileType{WALL, ROAD, START, FINISH, HEALTH, LAVA, PARCEL, WATER}

    /**
     * @return TileType of the object
     */
    TileType getType();

    /**
     * @param tileType: TileType
     * @return true if object is input TileType
     */
    boolean isType(TileType tileType);
}
