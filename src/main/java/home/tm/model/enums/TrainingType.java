package home.tm.model.enums;

import lombok.Getter;

@Getter
public enum TrainingType {
    AEROBIC("Kardio"), // Běh, kolo, plavání
    ANAEROBIC("Silový"); // Silový trénink s činkami

    private final String name;

    TrainingType(String name) {
        this.name = name;
    }

    public static TrainingType getType(String type) {
        for (TrainingType trainingType : TrainingType.values()) {
            if (trainingType.name().equalsIgnoreCase(type)) {
                return trainingType;
            }
        }
        throw new IllegalArgumentException("Neplatný typ tréninku: " + type);
    }
}
