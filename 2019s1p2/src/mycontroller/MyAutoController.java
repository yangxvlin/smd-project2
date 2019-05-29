package mycontroller;

import controller.CarController;
import mycontroller.Strategy.IStrategy;
import mycontroller.Strategy.StrategyFactory;
import mycontroller.TileAdapter.ITileAdapter;
import swen30006.driving.Simulation;
import utilities.Coordinate;
import world.Car;
import world.WorldSpatial;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-21 20:33
 * description: our ai car controller
 **/

public class MyAutoController extends CarController {
    /**
     * customized map information holder and updater
     */
    private MapRecorder mapRecorder;

    /**
     * drive strategy to search next location to go to
     */
    private IStrategy driveStrategy;

    /**
     * car's location at previous time frame
     */
    private Coordinate previousPosition;

    /**
     * the health that the car has used in the history
     */
    private float healthUsage;

    /**
     * when search started car has no fuel usage
     */
    private static final int INITIAL_FUEL_COST = 0;

    /**
     * car is stationary when speed = 0
     */
    private static final int STATIONARY_SPEED = 0;

	/**
     * initialize our car drive controller object
	 * @param car : car object
	 */
	public MyAutoController(Car car) {
	    super(car);

	    this.mapRecorder = new MapRecorder();
	    this.mapRecorder.updateInitialMap(super.getMap());

        driveStrategy = StrategyFactory.getInstance()
                                        .createConserveStrategy(Simulation.toConserve());

        healthUsage = getHealth();

        previousPosition = new Coordinate(getPosition());
	}

    /**
     * update the car status to the controller and decide car's next coordinate
     * to drive to
     */
	@Override
	public void update() {
        updateHealthUsage();

	    mapRecorder.updateMapRecorder(super.getView());

        Coordinate carPosition = new Coordinate(getPosition());

        Coordinate next = driveStrategy.getNextPath(mapRecorder,
                                                    carPosition,
                                                    healthUsage,
                                                    getHealth(),
                                                    INITIAL_FUEL_COST,
                                                    getSpeed(),
                                                    getMovingDirection(carPosition),
                                                    isEnoughParcel());

		previousPosition = carPosition;
		makeMove(carPosition, next);
	}

	/* ************************* helper methods below *********************** */
    /**
     * update car's total health usage
     */
	private void updateHealthUsage() {
        if (getHealth() > healthUsage) {
            healthUsage = getHealth();
        }
    }

    /**
     * @return true if the car has found enough parcels
     */
    private boolean isEnoughParcel() {
	    return numParcels() <= numParcelsFound();
    }

    /**
     * @return True if the car is moving
     */
    private boolean isEngineStarted() {
        return !(getSpeed() == STATIONARY_SPEED);
    }

    /**
     * This methods is responsible for determining the current moving direction of the car,
     * by calculating the relative direction of the current position to the previous position.
     * @param carPosition : car's current's position
     * @return the car's current direction moving toward
     * */
    private WorldSpatial.Direction getMovingDirection(Coordinate carPosition){
        WorldSpatial.Direction movingDirection = null;
	    if (carPosition.x == previousPosition.x && carPosition.y == previousPosition.y - 1) {
            movingDirection =  WorldSpatial.Direction.SOUTH;
        } else if (carPosition.x == previousPosition.x && carPosition.y == previousPosition.y + 1) {
            movingDirection =  WorldSpatial.Direction.NORTH;
        } else if (carPosition.y == previousPosition.y && carPosition.x == previousPosition.x - 1) {
            movingDirection =  WorldSpatial.Direction.WEST;
        } else if (carPosition.y == previousPosition.y && carPosition.x == previousPosition.x + 1) {
            movingDirection =  WorldSpatial.Direction.EAST;
        } else if (isEngineStarted()) {
	        movingDirection = getOrientation();
        }
	    return movingDirection;
    }

    /**
     * @param carPosition : car's current coordinate location
     * @return the coordinate forward the current car position based on the car's
     * orientation
     */
    private Coordinate getForward(Coordinate carPosition) {
        switch (getOrientation()) {
            case NORTH:
                return new Coordinate(carPosition.x, carPosition.y + 1);
            case SOUTH:
                return new Coordinate(carPosition.x, carPosition.y - 1);
            case EAST:
                return new Coordinate(carPosition.x + 1, carPosition.y);
            case WEST:
                return new Coordinate(carPosition.x - 1, carPosition.y);
        }

        /* for completeness */
        return null;
    }

    /**
     * based on the current location and next location to go, apply the car's
     * reaction to the next location to go to
     * @param from : current location
     * @param to   : next location to go to
     */
	private void makeMove(Coordinate from, Coordinate to) {
        if (from.equals(to)) {
            applyBrake();
            return;
        } else {
            if (!isEngineStarted()) {
                if (mapRecorder.getTileAdapter(getForward(from)).isType(ITileAdapter.TileType.WALL)) {
                    applyReverseAcceleration();
                } else {
                    applyForwardAcceleration();
                }
            }

            if (from.x < to.x) {
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
                        applyReverseAcceleration();
                        return;
                }

            } else if (from.x > to.x) {
                switch (getOrientation()) {
                    case NORTH:
                        turnLeft();
                        return;
                    case SOUTH:
                        turnRight();
                        return;
                    case EAST:
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
        }

        /* for completeness */
        System.out.println("Invalid");
        System.exit(1);
    }
}
