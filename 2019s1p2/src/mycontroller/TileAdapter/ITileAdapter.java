package mycontroller.TileAdapter;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:06
 * description:
 **/

public interface ITileAdapter {
    enum TileType{WALL, ROAD, START, FINISH, HEALTH, LAVA, PARCEL, WATER}

    TileType getType();

    boolean isType(TileType tileType);
}
