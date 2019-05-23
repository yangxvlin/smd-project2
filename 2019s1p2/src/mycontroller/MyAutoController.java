package mycontroller;

import controller.CarController;
import mycontroller.Strategy.IStrategy;
import mycontroller.Strategy.StrategyFactory;
import swen30006.driving.Simulation;
import utilities.Coordinate;
import world.Car;

/**
 *
 */
public class MyAutoController extends CarController {

    private MapRecorder mapRecorder;

    private IStrategy driveStrategy;

    private float carFuel;

	/**
	 * @param car
	 */
	public MyAutoController(Car car) {
	    super(car);

	    this.mapRecorder = new MapRecorder();
	    this.mapRecorder.updateInitialMap(super.getMap());

        driveStrategy = StrategyFactory.getInstance()
                                        .createConserveStrategy(Simulation.toConserve());

        carFuel = car.getFuel();

//        System.out.println("finished");
	}

    /**
     * update the car status to the controller and decide car's next state
     */
	@Override
	public void update() {
	    mapRecorder.updateMapRecorder(super.getView());
//	    mapRecorder.print();

        Coordinate carPosition = new Coordinate(getPosition());

		Coordinate next = driveStrategy.getNextCoordinate(mapRecorder,
                                                          carPosition,
                                                          getHealth(),
                                                          carFuel,
                                                          numParcels() == numParcelsFound());
		makeMove(carPosition, next);
	}

	private void makeMove(Coordinate from, Coordinate to) {
        carFuel -= 1;

        if (from.equals(to)) {
            applyBrake();
            /* brake has no fuel consumption */
            carFuel += 1;
            return;
        } else if (from.x < to.x) {
            switch (getOrientation()) {
                case NORTH:
                    turnLeft();
                    return;

                case SOUTH:
                    turnRight();
                    return;

                case EAST:
                    applyBrake();
                    applyReverseAcceleration();
                    return;

                case WEST:
                    applyForwardAcceleration();
                    return;
            }

        } else if (from.x > to.x) {
            switch (getOrientation()) {
                case NORTH:
                    turnRight();
                    return;

                case SOUTH:
                    turnLeft();
                    return;

                case EAST:
                    applyForwardAcceleration();
                    return;

                case WEST:
                    applyBrake();
                    applyReverseAcceleration();
                    return;
            }

        } else if (from.y < to.y) {
            switch (getOrientation()) {

                case NORTH:
                    applyForwardAcceleration();
                    return;

                case SOUTH:
                    applyBrake();
                    applyReverseAcceleration();
                    return;

                case EAST:
                    turnLeft();
                    return;

                case WEST:
                    turnRight();
                    return;
            }

        } else if (from.y > to.y) {
            switch (getOrientation()) {

                case NORTH:
                    applyBrake();
                    applyReverseAcceleration();
                    return;

                case SOUTH:
                    applyForwardAcceleration();
                    return;

                case EAST:
                    turnRight();
                    return;

                case WEST:
                    turnLeft();
                    return;
            }
        }

        /* debug */
        System.out.println("Invalid");
    }
}
