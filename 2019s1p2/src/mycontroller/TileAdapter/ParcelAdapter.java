package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:14
 * description: Parcel Tile adapter
 **/

public class ParcelAdapter implements ITileAdapter {
    /**
     * mapTile adaptee
     */
    private MapTile parcelTile;

    /**
     * @param parcelTile : mapTile with parcel type
     */
    public ParcelAdapter(MapTile parcelTile) {
        this.parcelTile = parcelTile;
    }

    /**
     * @return : PARCEL tile type
     */
    @Override
    public TileType getType() {
        return TileType.PARCEL;
    }

    /**
     * @param tileType : TileType
     * @return : true if input TileType is PARCEL otherwise false
     */
    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.PARCEL;
    }
}
