package home.tm.controllers;

import home.tm.TimeKeeper.api.controllers.TransactionsApi;
import home.tm.TimeKeeper.api.models.TransactionDto;
import home.tm.TimeKeeper.api.models.TransactionPaginatedListDto;
import home.tm.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class TransactionController implements TransactionsApi {

    private final TransactionService transactionService;

    @Override
    public ResponseEntity<TransactionDto> createTransaction(TransactionDto dto) {
        return new ResponseEntity<>(transactionService.createTransaction(dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteTransaction(Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TransactionDto> getTransaction(Long id) {
        return new ResponseEntity<>(transactionService.getTransaction(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionPaginatedListDto> getTransactions(Pageable pageable, String search) {
        return new ResponseEntity<>(transactionService.getTransactions(pageable, search), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDto> updateTransaction(Long id, TransactionDto dto) {
        return new ResponseEntity<>(transactionService.updateTransaction(id, dto), HttpStatus.OK);
    }
}
