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
                HealthConserveStrategy healthConserveStrategy = new HealthConserveStrategy();

                Comparator<Node> healthComparator = new HealthConserveStrategy.HealthComparator();
                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.PICKUP,  new ParcelPickupStrategy(healthComparator));
                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXIT,    new ExitStrategy(healthComparator));
//                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXPLORE, new ExploreStrategy(healthComparator));
//                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXPLORE, new ExploreStrategy(new HealthConserveStrategy.HealthComparator2()));
                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXPLORE, new ExploreStrategy(new FuelConserveStrategy.FuelComparator()));
                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.HEAL,    new HealStrategy(new HealthConserveStrategy.HealthComparator2()));
//                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.HEAL,    new HealStrategy(new FuelConserveStrategy.FuelComparator()));

                return healthConserveStrategy;
            case FUEL:
                FuelConserveStrategy fuelConserveStrategy = new FuelConserveStrategy();

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
