package mycontroller.TileAdapter;

import tiles.MapTile;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:14
 * description:
 **/

public class ParcelAdapter implements ITileAdapter {
    private MapTile parcelTile;

    public ParcelAdapter(MapTile parcelTile) {
        this.parcelTile = parcelTile;
    }

    @Override
    public TileType getType() {
        return TileType.PARCEL;
    }

    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.PARCEL;
    }
}
