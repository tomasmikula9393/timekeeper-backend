package home.tm.controllers;

import home.tm.TimeKeeper.api.controllers.BodyMeasurementsApi;
import home.tm.TimeKeeper.api.models.BodyMeasurementDto;
import home.tm.services.BodyMeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ws/rest")
@RequiredArgsConstructor
public class BodyMeasurementController implements BodyMeasurementsApi {

    private final BodyMeasurementService bodyMeasurementService;

    @Override
    public ResponseEntity<BodyMeasurementDto> createBodyMeasurement(Long id, BodyMeasurementDto dto) {
        return new ResponseEntity<>(bodyMeasurementService.createBodyMeasurement(id, dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteBodyMeasurement(Long diaryId, Long id) {
        bodyMeasurementService.deleteBodyMeasurement(diaryId, id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<BodyMeasurementDto> getBodyMeasurement(Long diaryId, Long id) {
        return new ResponseEntity<>(bodyMeasurementService.getBodyMeasurement(diaryId, id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BodyMeasurementDto>> getBodyMeasurements(Long id, Pageable pageable, String search) {
        return new ResponseEntity<>(bodyMeasurementService.getBodyMeasurements(id, pageable, search), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BodyMeasurementDto> updateBodyMeasurement(Long diaryId, Long id, BodyMeasurementDto dto) {
        return new ResponseEntity<>(bodyMeasurementService.updateBodyMeasurement(diaryId, id, dto), HttpStatus.OK);
    }
}
