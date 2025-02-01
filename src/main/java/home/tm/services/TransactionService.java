package home.tm.services;

import home.tm.TimeKeeper.api.models.TransactionDto;
import home.tm.TimeKeeper.api.models.TransactionPaginatedListDto;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    TransactionDto createTransaction(TransactionDto dto);

    void deleteTransaction(Long id);

    TransactionDto getTransaction(Long id);

    TransactionPaginatedListDto getTransactions(Pageable pageable, String search);

    TransactionDto updateTransaction(Long id, TransactionDto dto);
}
