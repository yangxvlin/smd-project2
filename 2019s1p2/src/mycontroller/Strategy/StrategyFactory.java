package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Node;
import swen30006.driving.Simulation;

import java.util.Comparator;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 15:49
 *
 * description : This class is responsible for creating IStrategy.
 **/

public class StrategyFactory {
    /*The instance for factory.*/
    private static StrategyFactory strategyFactory = null;


    /**
     * The constructor for StrategyFactory.
     */
    private StrategyFactory() {

    }

    /**
     * @return The StrategyFactory instance
     */
    public static StrategyFactory getInstance() {
        if (strategyFactory == null) {
            strategyFactory = new StrategyFactory();
        }

        return strategyFactory;
    }

    /**
     * This method is responsible for creating IStrategy depends on given conserve mode.
     *
     * @param mode : The specified conserve mode.
     * @return: The IStrategy object specified by the given mode.
     */
    public IStrategy createConserveStrategy(Simulation.StrategyMode mode) {
        switch (mode) {
            case HEALTH:
                /*If it is health conserve mode, initialize a health conserve strategy*/
                HealthConserveStrategy healthConserveStrategy = new HealthConserveStrategy();

                /*register strategy into the health conserve strategy*/
                Comparator<Node> healthComparator = new HealthConserveStrategy.HealthComparator();
                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.PICKUP,  new ParcelPickupStrategy(healthComparator));
                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXIT,    new ExitStrategy(healthComparator));
                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXPLORE, new ExploreStrategy(healthComparator));
                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.HEAL,    new HealStrategy(new HealthConserveStrategy.HealthComparator2()));

                return healthConserveStrategy;
            case FUEL:
                /*If it is fuel conserve mode, initialize a fuel conserve strategy*/
                FuelConserveStrategy fuelConserveStrategy = new FuelConserveStrategy();

                /*register strategy into the fuel conserve strategy*/
                Comparator<Node> fuelComparator = new FuelConserveStrategy.FuelComparator();
                fuelConserveStrategy.registerIStrategy(IStrategy.StrategyType.PICKUP,  new ParcelPickupStrategy(fuelComparator));
                fuelConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXIT,    new ExitStrategy(fuelComparator));
                fuelConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXPLORE, new ExploreStrategy(fuelComparator));

                return fuelConserveStrategy;
        }

        System.out.println("Unsupported mode!");
        return null;
    }
}
