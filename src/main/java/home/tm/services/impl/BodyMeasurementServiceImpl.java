package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.BodyMeasurementDto;
import home.tm.converters.BodyMeasurementConverter;
import home.tm.exceptions.NotFoundException;
import home.tm.model.BodyMeasurement;
import home.tm.model.Training;
import home.tm.model.TrainingDiary;
import home.tm.repositories.BodyMeasurementRepository;
import home.tm.services.BodyMeasurementService;
import home.tm.services.TrainingDiaryService;
import home.tm.utils.ParamsParser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static home.tm.exceptions.ExceptionMessage.BODY_MEASUREMENT_WAS_NOT_FOUND;
import static home.tm.exceptions.ExceptionMessage.TRAINING_DIARY_WAS_NOT_FOUND;
import static home.tm.exceptions.ExceptionMessage.TRAINING_WAS_NOT_FOUND;
import static home.tm.exceptions.ExceptionMessage.USER_IS_NOT_AUTHORIZED;
import static home.tm.exceptions.ExceptionType.NEBYLO_NALEZENO;
import static home.tm.exceptions.SeverityEnum.ERROR;

@Service
@RequiredArgsConstructor
public class BodyMeasurementServiceImpl implements BodyMeasurementService {

    private final BodyMeasurementRepository bodyMeasurementRepository;
    private final BodyMeasurementConverter bodyMeasurementConverter;
    private final TrainingDiaryService trainingDiaryService;

    @Override
    public BodyMeasurementDto createBodyMeasurement(Long id, BodyMeasurementDto dto) {
        BodyMeasurement bodyMeasurement = bodyMeasurementRepository.save(bodyMeasurementConverter.toEntity(dto, trainingDiaryService.getTrainingDiaryById(id)));
        return bodyMeasurementConverter.toDto(bodyMeasurement);
    }

    @Override
    public BodyMeasurementDto getBodyMeasurement(Long diaryId, Long id) {
        trainingDiaryService.getTrainingDiaryById(diaryId);
        return bodyMeasurementConverter.toDto(getBodyMeasurementById(id));
    }

    @Override
    public List<BodyMeasurementDto> getBodyMeasurements(Long id, Pageable pageable, String search) {
        TrainingDiary trainingDiary = trainingDiaryService.getTrainingDiaryById(id);
        Map<String, String> filters = ParamsParser.parseSearchQuery(search);

        List<BodyMeasurement> bodyMeasurements = bodyMeasurementRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filters.containsKey("week")) {
                predicates.add(criteriaBuilder.equal(root.get("week"), Integer.parseInt(filters.get("week"))));
            }
            predicates.add(criteriaBuilder.equal(root.get("trainingDiary"), trainingDiary.getId()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        return bodyMeasurementConverter.toListDto(bodyMeasurements);
    }

    @Override
    public BodyMeasurementDto updateBodyMeasurement(Long diaryId, Long id, BodyMeasurementDto dto) {
        BodyMeasurement bodyMeasurement = bodyMeasurementRepository.save(
                bodyMeasurementConverter.toEntity(dto, getBodyMeasurementById(id), trainingDiaryService.getTrainingDiaryById(diaryId))
        );
        return bodyMeasurementConverter.toDto(bodyMeasurement);
    }

    @Override
    public void deleteBodyMeasurement(Long diaryId, Long id) {
        trainingDiaryService.getTrainingDiaryById(diaryId);
        bodyMeasurementRepository.deleteById(id);
    }

    private BodyMeasurement getBodyMeasurementById(Long id) {
        return bodyMeasurementRepository.findById(id).orElseThrow(
                () -> new NotFoundException(NEBYLO_NALEZENO, String.format(BODY_MEASUREMENT_WAS_NOT_FOUND.getMessage(), id), ERROR)
        );
    }
}
