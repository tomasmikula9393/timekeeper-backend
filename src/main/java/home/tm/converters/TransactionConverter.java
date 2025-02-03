package home.tm.converters;

import home.tm.TimeKeeper.api.models.TransactionDto;
import home.tm.model.Transaction;
import home.tm.model.enums.TransactionType;
import home.tm.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionConverter {

    private final UserConverter userConverter;
    private final SecurityService securityService;

    public TransactionDto toDto(Transaction entity) {
        return toDto(entity, new TransactionDto());
    }

    public TransactionDto toDto(Transaction entity, TransactionDto transactionDto) {
        transactionDto.setId(entity.getId());
        transactionDto.setAmount(String.valueOf(entity.getAmount()));
        transactionDto.setCategory(entity.getCategory());
        transactionDto.setType(entity.getType());
        transactionDto.setDescription(entity.getDescription());
        transactionDto.setTransactionDate(entity.getTransactionDate());
        transactionDto.setUser(userConverter.toDto(entity.getUser()));
        return transactionDto;
    }

    public List<TransactionDto> toListDto(List<Transaction> data) {
        return data.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Transaction toEntity(TransactionDto dto) {
        return toEntity(dto, new Transaction());
    }

    public Transaction toEntity(TransactionDto dto, Transaction entity) {
        entity.setCategory(dto.getCategory());
        entity.setDescription(dto.getDescription());
        entity.setType(getType(dto.getType()));
        entity.setTransactionDate(dto.getTransactionDate());
        entity.setAmount(new BigDecimal(dto.getAmount()));
        entity.setUser(securityService.getCurrentUser());
        return entity;
    }

    private String getType(String type) {
        return Arrays.stream(TransactionType.values())
                .map(Enum::name)
                .filter(t -> t.equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }
}
