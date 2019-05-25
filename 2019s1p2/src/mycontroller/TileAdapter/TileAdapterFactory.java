package mycontroller.TileAdapter;

import tiles.MapTile;
import tiles.TrapTile;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 13:37
 * description:
 **/

public class TileAdapterFactory {
    /**
     *
     */
    private static TileAdapterFactory tileAdapterFactory = null;

    /**
     *
     */
    private TileAdapterFactory() {}

    /**
     * @return
     */
    public static TileAdapterFactory getInstance() {
        if (tileAdapterFactory == null) {
            tileAdapterFactory = new TileAdapterFactory();
        }

        return tileAdapterFactory;
    }

    /**
     * @param mapTile
     * @return
     */
    public ITileAdapter createTileAdapter(MapTile mapTile) {
        switch (mapTile.getType()) {
            case START:
                return new StartAdapter(mapTile);
            case FINISH:
                return new FinishAdapter(mapTile);
            case ROAD:
                return new RoadAdapter(mapTile);
            case WALL:
                return new WallAdapter(mapTile);
            case TRAP:
                switch (((TrapTile)mapTile).getTrap()) {
                    case "health":
                        return new HealthAdapter(mapTile);
                    case "lava":
                        return new LavaAdapter(mapTile);
                    case "parcel":
                        return new ParcelAdapter(mapTile);
                    case "water":
                        return new WaterAdapter(mapTile);
                }
        }

        /* for completeness */
        System.out.println("Unsupported tile type!");
        System.exit(1);
        return null;
    }

}
