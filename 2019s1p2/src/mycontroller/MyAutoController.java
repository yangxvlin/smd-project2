package mycontroller;

import controller.CarController;
import mycontroller.Strategy.IStrategy;
import swen30006.driving.Simulation;
import tiles.MapTile;
import utilities.Coordinate;
import world.Car;

/**
 *
 */
public class MyAutoController extends CarController {

    private MapRecorder mapRecorder;

    private IStrategy driveStrategy;

	/**
	 * @param car
	 */
	public MyAutoController(Car car) {
	    super(car);

	    this.mapRecorder = new MapRecorder();
	    this.mapRecorder.updateInitialMap(super.getMap());

//        driveStrategy = StrategyFactory.getInstance()
//                                        .createConserveStrategy(Simulation.toConserve());
	}

    /**
     * update the car status to the controller and decide car's next state
     */
	@Override
	public void update() {
	    mapRecorder.updateMapRecorder(super.getView());
//	    mapRecorder.print();

        Coordinate carPosition = new Coordinate(getPosition());


	}
}
