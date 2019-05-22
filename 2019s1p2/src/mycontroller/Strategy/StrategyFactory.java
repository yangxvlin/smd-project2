package mycontroller.Strategy;

import swen30006.driving.Simulation;

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
                return new FuelConserveStrategy();
        }

        System.out.println("Unsupported mode!");
        System.exit(1);
        return null;
    }
}
