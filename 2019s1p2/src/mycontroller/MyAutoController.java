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
        System.out.println();
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
	    assert(to != null);

        System.out.println(from.toString() + " -> " + to.toString() +
                "(" + mapRecorder.getTileAdapter(to).getType().toString() + ")");

        carFuel -= 1;

        if (from.equals(to)) {
            applyBrake();
            /* brake has no fuel consumption */
            carFuel += 1;
            return;
        } else if (from.x < to.x) {
            switch (getOrientation()) {
                case NORTH:
                    applyForwardAcceleration();
                    turnRight();
                    return;

                case SOUTH:
                    applyForwardAcceleration();
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

        } else if (from.x > to.x) {
            switch (getOrientation()) {
                case NORTH:
                    applyForwardAcceleration();
                    turnLeft();
                    return;

                case SOUTH:
                    applyForwardAcceleration();
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
                    applyForwardAcceleration();
                    turnLeft();
                    return;

                case WEST:
                    applyForwardAcceleration();
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
                    applyForwardAcceleration();
                    turnRight();
                    return;

                case WEST:
                    applyForwardAcceleration();
                    turnLeft();
                    return;
            }
        }

        /* debug */
        System.out.println("Invalid");
    }
}
