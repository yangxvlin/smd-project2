package mycontroller;

import controller.CarController;
import tiles.MapTile;
import world.Car;

/**
 *
 */
public class MyAutoController extends CarController{

    private MapRecorder mapRecorder;

	/**
	 * @param car
	 */
	public MyAutoController(Car car) {
	    super(car);

	    this.mapRecorder = new MapRecorder();
	    this.mapRecorder.updateInitialMap(super.getMap(), MapTile.Type.ROAD);

	}

    /**
     * update the car status to the controller and decide car's next state
     */
	@Override
	public void update() {
	    mapRecorder.updateMapRecorder(super.getView());
	}
}
