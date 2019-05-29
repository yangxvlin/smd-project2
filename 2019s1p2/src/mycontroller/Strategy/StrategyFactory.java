package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Dijkstra;
import mycontroller.GraphAlgorithm.ISearchAlgorithm;
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
    /** The instance for factory. */
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
     * @param strategyType : drive strategy type
     * @param comparator : conserve comparator
     * @param searchAlgorithm : graph search algorithm
     * @return : created drive strategy
     */
    private IStrategy createStrategy(IStrategy.StrategyType strategyType,
                                     Comparator<Node> comparator,
                                     ISearchAlgorithm searchAlgorithm) {
        switch (strategyType) {
            case HEAL:
                IStrategy healStrategy = new HealStrategy(comparator);
                healStrategy.registerISearchAlgorithm(searchAlgorithm);
                return healStrategy;
            case EXPLORE:
                IStrategy exploreStrategy = new ExploreStrategy(comparator);
                exploreStrategy.registerISearchAlgorithm(searchAlgorithm);
                return exploreStrategy;
            case EXIT:
                IStrategy exitStrategy = new ExitStrategy(comparator);
                exitStrategy.registerISearchAlgorithm(searchAlgorithm);
                return exitStrategy;
            case PICKUP:
                IStrategy pickupStrategy = new ParcelPickupStrategy(comparator);
                pickupStrategy.registerISearchAlgorithm(searchAlgorithm);
                return pickupStrategy;
        }

        /* for completeness */
        return null;
    }

    /**
     * This method is responsible for creating IStrategy depends on given conserve mode.
     *
     * @param mode : The specified conserve mode.
     * @return : The IStrategy object specified by the given mode.
     */
    public IStrategy createConserveStrategy(Simulation.StrategyMode mode) {
        ISearchAlgorithm searchAlgorithm = new Dijkstra();

        switch (mode) {
            case HEALTH:
                /* If it is health conserve mode, initialize a health conserve strategy */
                HealthConserveStrategy healthConserveStrategy = new HealthConserveStrategy();
                /* register strategy into the health conserve strategy */
                Comparator<Node> healthComparator = new HealthConserveStrategy.HealthComparator();
                Comparator<Node> healComparator   = new HealthConserveStrategy.HealComparator();

                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.PICKUP,  createStrategy(IStrategy.StrategyType.PICKUP,  healthComparator, searchAlgorithm));
                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXIT,    createStrategy(IStrategy.StrategyType.EXIT,    healthComparator, searchAlgorithm));
                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXPLORE, createStrategy(IStrategy.StrategyType.EXPLORE, healthComparator, searchAlgorithm));
                healthConserveStrategy.registerIStrategy(IStrategy.StrategyType.HEAL,    createStrategy(IStrategy.StrategyType.HEAL,    healComparator,   searchAlgorithm));

                return healthConserveStrategy;
            case FUEL:
                /* If it is fuel conserve mode, initialize a fuel conserve strategy */
                FuelConserveStrategy fuelConserveStrategy = new FuelConserveStrategy();
                /* register strategy into the fuel conserve strategy */
                Comparator<Node> fuelComparator = new FuelConserveStrategy.FuelComparator();

                fuelConserveStrategy.registerIStrategy(IStrategy.StrategyType.PICKUP,  createStrategy(IStrategy.StrategyType.PICKUP, fuelComparator,  searchAlgorithm));
                fuelConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXIT,    createStrategy(IStrategy.StrategyType.EXIT,   fuelComparator,  searchAlgorithm));
                fuelConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXPLORE, createStrategy(IStrategy.StrategyType.EXPLORE, fuelComparator, searchAlgorithm));

                return fuelConserveStrategy;
        }

        return null;
    }
}
