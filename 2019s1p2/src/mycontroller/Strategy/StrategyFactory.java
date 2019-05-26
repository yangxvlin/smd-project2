package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Node;
import swen30006.driving.Simulation;

import java.util.Comparator;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 15:49
 * description:
 **/

public class StrategyFactory {
    private static StrategyFactory strategyFactory = null;

    private StrategyFactory() {

    }

    public static StrategyFactory getInstance() {
        if (strategyFactory == null) {
            strategyFactory = new StrategyFactory();
        }

        return strategyFactory;
    }

    public IStrategy createConserveStrategy(Simulation.StrategyMode mode) {
        switch (mode) {
            case HEALTH:
                return new HealthConserveStrategy();
            case FUEL:
                Comparator<Node> fuelComparator = new FuelConserveStrategy.FuelComparator();
                FuelConserveStrategy fuelConserveStrategy = new FuelConserveStrategy(fuelComparator);

                fuelConserveStrategy.registerIStrategy(IStrategy.StrategyType.PICKUP,  new ParcelPickupStrategy(fuelComparator));
                fuelConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXIT,    new ExitStrategy(fuelComparator));
                fuelConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXPLORE, new ExploreStrategy(fuelComparator));

                return fuelConserveStrategy;
        }

        System.out.println("Unsupported mode!");
        return null;
    }
}
