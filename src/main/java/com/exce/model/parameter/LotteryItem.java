package com.exce.model.parameter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum LotteryItem implements Serializable {
    ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"),
    ELEVEN("11"), TWELVE("12"), THIRTEEN("13"), FOURTEEN("14"), FIFTEEN("15"), SIXTEEN("16"), SEVENTEEN("17"), EIGHTEEN("18"), NINETEEN("19"),
    A("A"), B("B"), C("C"), ODD("ODD"), EVEN("EVEN"), BIG("BIG"), SMALL("SMALL"), DRAGON("DRAGON"), TIGER("TIGER");

    private final String value;

    LotteryItem(String value) {
        this.value = value;
    }

    private static final Map<String, LotteryItem> map;

    static {
        map = Arrays.stream(values())
                .collect(Collectors.toMap(e -> e.value, e -> e));
    }

    public static Optional<LotteryItem> fromValue(String value) {
        return Optional.ofNullable(map.get(value));
    }
}