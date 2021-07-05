package com.mission2.racingcar;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CarRacingService {
    public static final int MAX_CAR_NAME_COUNT = 5;
    public static final int MAX_GAME_COUNT = 10;
    public static final int INIT_SCORE = 1;
    public static final String CAR_NAME_DELIMITER = ",";

    public void game(List<String> carNames, int gameCount) {
        System.out.println("실행결과");

        carNames.stream()
                .map(name -> new Car(name, INIT_SCORE))
                .forEach(System.out::println);

        Race race = initRace(gameCount, carNames);
        proceedGame(race);
        printWinner(race.getWinners());
    }

    public boolean checkCarNamesNotOverMaxCarCount(List<String> carNames) {
        long count = carNames.stream().filter(car -> car.length() > MAX_CAR_NAME_COUNT).count();

        return count <= 0;
    }

    public List<String> splitCarNamesByComma(String input) {
        return Arrays.stream(input.split(CAR_NAME_DELIMITER))
                .map(String::trim)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean checkGameCountNotOverMaxGameCount(int input) {
        return input <= MAX_GAME_COUNT;
    }

    public int getGameCount(String input) {
        if (!Pattern.matches("^[0-9]*$", input)) {
            throw new RuntimeException("잘못된 입력입니다.");
        }
        return Integer.parseInt(input);
    }

    public Race initRace(int gameCount, List<String> carNames) {
        List<Car> carList = carNames.stream()
                .map(carName -> new Car(carName, INIT_SCORE))
                .collect(Collectors.toList());

        return new Race
                .Builder(carList)
                .gameCount(gameCount)
                .build();
    }

    public void proceedGame(Race race) {
        for (int i = 0; i < race.getGameCount(); i++) {
            System.out.println();
            racing(race);
        }
    }

    private void racing(Race race) {
        for (Car car : race.getCars()) {
            Random random = new Random();
            car.raceByRandomNumber(random.nextInt(10));
            System.out.println(car);
        }
    }

    public void printWinner(String[] winners) {
        String winner = String.join(",", winners);
        System.out.println("\n" + winner + "가 최종 우승했습니다.");
    }
}
