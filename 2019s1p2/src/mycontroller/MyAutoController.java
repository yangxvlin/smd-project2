package mycontroller;

import controller.CarController;
import mycontroller.Strategy.IStrategy;
import mycontroller.Strategy.StrategyFactory;
import mycontroller.TileAdapter.ITileAdapter;
import swen30006.driving.Simulation;
import utilities.Coordinate;
import world.Car;
import world.WorldSpatial;

import java.util.Stack;

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
     * car's remaining fuel
     */
//    private float carFuel;

    private Coordinate previousPosition;

    private float maxHealth;

    private Stack<Coordinate> path;

	/**
	 * @param car : car object
	 */
	public MyAutoController(Car car) {
	    super(car);

	    this.mapRecorder = new MapRecorder();
	    this.mapRecorder.updateInitialMap(super.getMap());

        driveStrategy = StrategyFactory.getInstance()
                                        .createConserveStrategy(
                                                Simulation.toConserve());

//        carFuel = car.getFuel();

        maxHealth = getHealth();

        previousPosition = new Coordinate(getPosition());

        path = new Stack<>();
	}

    /**
     * update the car status to the controller and decide car's next state
     */
	@Override
	public void update() {
	    if (getHealth() > maxHealth) {
	        maxHealth = getHealth();
        }

	    mapRecorder.updateMapRecorder(super.getView());
        System.out.println();
//	    mapRecorder.print();

        System.out.println("previous: " + previousPosition + " " + Integer.toString(numParcelsFound()));
        Coordinate carPosition = new Coordinate(getPosition());

        if ((path.size() == 1) || path.empty()) {
            path = driveStrategy.getNextPath(mapRecorder,
                                                              carPosition,
                                                              maxHealth,
                                                              getHealth(),
                                                              0,
                                                              getSpeed(),
                                                              getMovingDirection(carPosition),
                                                              numParcels() <= numParcelsFound());

        }
		previousPosition = carPosition;
		makeMove(carPosition);
	}

    /**
     * This methods is responsible for determining the current moving direction of the car,
     * by calculating the relative direction of the current position to the previous position.
     *
     * */
    private WorldSpatial.Direction getMovingDirection(Coordinate currentPosition){
        WorldSpatial.Direction movingDirection = null;
	    if (currentPosition.x == previousPosition.x && currentPosition.y == previousPosition.y -1) {
            movingDirection =  WorldSpatial.Direction.SOUTH;
        } else if (currentPosition.x == previousPosition.x && currentPosition.y == previousPosition.y + 1) {
            movingDirection =  WorldSpatial.Direction.NORTH;
        } else if (currentPosition.y == previousPosition.y && currentPosition.x == previousPosition.x - 1) {
            movingDirection =  WorldSpatial.Direction.WEST;
        } else if (currentPosition.y == previousPosition.y && currentPosition.x == previousPosition.x + 1) {
            movingDirection =  WorldSpatial.Direction.EAST;
        } else if (getSpeed() == 0) {
	        movingDirection = getOrientation();
        }
	    return movingDirection;
    }

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
     */
	private void makeMove(Coordinate from) {
	    Coordinate to = path.peek();
	    assert(to != null);

	    if (to.equals(from)) {
            path.pop();

            if (!path.empty()) {
                to = path.peek();
            }
        }

        System.out.println(from.toString() + " -> " + to.toString() +
                "(" + mapRecorder.getTileAdapter(to).getType().toString() + ") " +
                getOrientation() + " " + getMovingDirection(from));

        /* spend 1 fuel for an attempt move */
        // TODO a constant 1?
//        carFuel -= 1;

        if (from.equals(to)) {
            applyBrake();
            /* brake has no move attempt thus no fuel consumption */
            // TODO a constant 1?
//            carFuel += 1;
            return;
        } else {
            if (getSpeed() == 0) {
                if (mapRecorder.getTileAdapter(getForward(from)).isType(ITileAdapter.TileType.WALL)) {
                    applyReverseAcceleration();
                } else {
                    applyForwardAcceleration();
                }
            }

            if (from.x < to.x) {
                switch (getOrientation()) {
                    case NORTH:
                        //                    applyForwardAcceleration();
                        turnRight();
                        return;

                    case SOUTH:
                        //                    applyForwardAcceleration();
                        turnLeft();
                        return;

                    case EAST:
                        applyForwardAcceleration();
                        return;

                    case WEST:
                        //                    applyBrake();
                        //                    applyReverseAcceleration();
                        applyReverseAcceleration();
                        return;
                }

            } else if (from.x > to.x) {
                switch (getOrientation()) {
                    case NORTH:
                        //                    applyForwardAcceleration();
                        turnLeft();
                        return;

                    case SOUTH:
                        //                    applyForwardAcceleration();
                        turnRight();
                        return;

                    case EAST:
                        //                    applyBrake();
                        //                    applyReverseAcceleration();
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
                        //                    applyBrake();
                        //                    applyReverseAcceleration();
                        applyReverseAcceleration();
                        return;

                    case EAST:
                        //                    applyForwardAcceleration();
                        turnLeft();
                        return;

                    case WEST:
                        //                    applyForwardAcceleration();
                        turnRight();
                        return;
                }

            } else if (from.y > to.y) {
                switch (getOrientation()) {

                    case NORTH:
                        //                    applyBrake();
                        //                    applyReverseAcceleration();
                        applyReverseAcceleration();
                        return;

                    case SOUTH:
                        applyForwardAcceleration();
                        return;

                    case EAST:
                        //                    applyForwardAcceleration();
                        turnRight();
                        return;

                    case WEST:
                        //                    applyForwardAcceleration();
                        turnLeft();
                        return;
                }
            }
        }

        /* debug */
        System.out.println("Invalid");
        System.exit(1);
    }
}
