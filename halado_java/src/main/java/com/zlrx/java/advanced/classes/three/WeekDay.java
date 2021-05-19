package com.zlrx.java.advanced.classes.three;

import java.util.List;
import java.util.Map;

public enum WeekDay {
    MON(
            Map.of(
                    "hu", "Hétfő",
                    "en", "Monday"
            )
    ),

    TUE(
            Map.of(
                    "hu", "Kedd",
                    "en", "Tuesday"
            )
    ),

    WED(
            Map.of(
                    "hu", "Szerda",
                    "en", "Wednesday"
            )
    ),

    THU(
            Map.of(
                    "hu", "Csütörtök",
                    "en", "Thursday"
            )
    ),

    FRI(
            Map.of(
                    "hu", "Péntek",
                    "en", "Friday"
            )
    ),

    SAT(
            Map.of(
                    "hu", "Szombat",
                    "en", "Saturday"
            )
    ),

    SUN(
            Map.of(
                    "hu", "Vasárnap",
                    "en", "Sunday"
            )
    );


    static final List<String> languages = List.of("hu", "en");

    private Map<String, String> names;

    WeekDay(Map<String, String> names) {
        this.names = names;
    }

    public WeekDay nextDay() {
        int index = ordinal() + 1;
        return WeekDay.values()[index % 7];
    }

    public WeekDay nextDay(int step) {
        if (step < 0) {
            step = 7 - (-1 * (step % 7));
        }
        int index = (ordinal() + step) % 7;
        return WeekDay.values()[index];
    }

    public String get(String lang) {
        return names.getOrDefault(lang, "?");
    }

}
