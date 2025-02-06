package home.tm.model.enums;

import lombok.Getter;

@Getter
public enum TrainingType {
    AEROBIC, // Běh, kolo, plavání
    ANAEROBIC; // Silový trénink

    public static TrainingType getType(String type) {
        if (type.equalsIgnoreCase("aerobic")) {
            return TrainingType.AEROBIC;
        }
        return TrainingType.ANAEROBIC;
    }
}