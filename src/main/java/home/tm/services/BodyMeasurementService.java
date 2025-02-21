package home.tm.services;

import home.tm.TimeKeeper.api.models.BodyMeasurementDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BodyMeasurementService {

    BodyMeasurementDto createBodyMeasurement(Long id, BodyMeasurementDto dto);

    BodyMeasurementDto getBodyMeasurement(Long diaryId, Long id);

    List<BodyMeasurementDto> getBodyMeasurements(Long id, Pageable pageable, String search);

    BodyMeasurementDto updateBodyMeasurement(Long diaryId, Long id, BodyMeasurementDto dto);

    void deleteBodyMeasurement(Long diaryId, Long id);
}
