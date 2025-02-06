package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.TransactionDto;
import home.tm.TimeKeeper.api.models.TransactionPaginatedListDto;
import home.tm.converters.TransactionConverter;
import home.tm.exceptions.NotFoundException;
import home.tm.model.Transaction;
import home.tm.repositories.TransactionRepository;
import home.tm.security.service.SecurityService;
import home.tm.services.TransactionService;
import home.tm.utils.ParamsParser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static home.tm.exceptions.ExceptionMessage.ITEM_WAS_NOT_FOUND;
import static home.tm.exceptions.ExceptionMessage.USER_IS_NOT_AUTHORIZED;
import static home.tm.exceptions.ExceptionType.NEBYLO_NALEZENO;
import static home.tm.exceptions.SeverityEnum.ERROR;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;
    private final SecurityService securityService;

    @Override
    public TransactionDto createTransaction(TransactionDto dto) {
        Transaction transaction = transactionRepository.save(transactionConverter.toEntity(dto));
        return transactionConverter.toDto(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        Transaction transaction = getTransactionById(id);
        transactionRepository.delete(transaction);
    }

    @Override
    public TransactionDto getTransaction(Long id) {
        return transactionConverter.toDto(getTransactionById(id));
    }

    @Override
    public TransactionPaginatedListDto getTransactions(Pageable pageable, String search) {
        Map<String, String> filters = ParamsParser.parseSearchQuery(search);
        TransactionPaginatedListDto dto = new TransactionPaginatedListDto();

        Page<Transaction> transactions = transactionRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.containsKey("year")) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.function("YEAR", Integer.class, root.get("transactionDate")),
                        Integer.parseInt(filters.get("year"))
                ));
            }

            if (filters.containsKey("month")) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.function("MONTH", Integer.class, root.get("transactionDate")),
                        Integer.parseInt(filters.get("month"))
                ));
            }

            if (filters.containsKey("type")) {
                predicates.add(criteriaBuilder.equal(root.get("type"), filters.get("type")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "transactionDate")));

        dto.setList(transactionConverter.toListDto(transactions.getContent()));
        dto.setCount(transactions.getPageable().getPageSize());
        dto.setPage(transactions.getPageable().getPageNumber());
        return dto;
    }


    @Override
    public TransactionDto updateTransaction(Long id, TransactionDto dto) {
        Transaction transaction = transactionRepository.save(transactionConverter.toEntity(dto, getTransactionById(id)));
        return transactionConverter.toDto(transaction);
    }

    private Transaction getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(NEBYLO_NALEZENO, String.format(ITEM_WAS_NOT_FOUND.getMessage(), id), ERROR)
        );
        Long userId = securityService.getCurrentUser().getId();
        if (!transaction.getUser().getId().equals(userId)) {
            throw new NotFoundException(NEBYLO_NALEZENO, String.format(USER_IS_NOT_AUTHORIZED.getMessage(), userId), ERROR);
        }
        return transaction;
    }
}
