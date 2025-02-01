package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.ItemsPaginatedListDto;
import home.tm.TimeKeeper.api.models.TransactionDto;
import home.tm.TimeKeeper.api.models.TransactionPaginatedListDto;
import home.tm.converters.TransactionConverter;
import home.tm.exceptions.NotFoundException;
import home.tm.model.Item;
import home.tm.model.Transaction;
import home.tm.repositories.TransactionRepository;
import home.tm.security.service.SecurityService;
import home.tm.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        //todo spring-search
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        TransactionPaginatedListDto dto = new TransactionPaginatedListDto();
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
