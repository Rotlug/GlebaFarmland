package com.github.rotlug.glebafarmland.util;

import net.minecraft.util.RandomSource;

import java.util.List;
import java.util.Random;

public final class RNG {

    public static boolean hundo(double odds) {
        Random rand = new Random();

        if (odds < 0 || odds > 100) {
            throw new IllegalArgumentException("Odds must be between 0 and 100 inclusive.");
        }
        double roll = rand.nextDouble() * 100;
        return roll < odds;
    }

    public static boolean ihundo(int odds) {
        Random rand = new Random();

        if (odds < 0 || odds > 100) {
            throw new IllegalArgumentException("Odds must be between 0 and 100 inclusive.");
        }
        double roll = rand.nextInt() * 100;
        return roll < odds;
    }

    public static int irandRange(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public static <T> T weightedChoice(List<T> options, List<Integer> weights) {
        Random random = new Random();

        //DewDropDailyWeather.LOGGER.info(String.valueOf(options));

        if (options.size() != weights.size()) {
            throw new IllegalArgumentException("Options and weights must have the same size");
        }

        int totalWeight = 0;
        for (int weight : weights) {
            totalWeight += weight;
        }

        int randomWeight = random.nextInt(totalWeight);

        int currentWeight = 0;
        for (int i = 0; i < options.size(); i++) {
            currentWeight += weights.get(i);
            if (randomWeight < currentWeight) {
                return options.get(i);
            }
        }

        return options.get(0); // should never reach here
    }

    public static boolean mc_ihundo(RandomSource rand, int odds) {

        if (odds < 0 || odds > 100) {
            throw new IllegalArgumentException("Odds must be between 0 and 100 inclusive.");
        }
        return rand.nextInt(100) < odds;
    }

    public static boolean mc_hundo(RandomSource rand, double odds) {

        if (odds < 0 || odds > 1) {
            throw new IllegalArgumentException("Odds must be between 0 and 1 inclusive.");
        }
        return rand.nextDouble() < odds;
    }

}
