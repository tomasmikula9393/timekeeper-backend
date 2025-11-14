package home.tm.controllers;

import home.tm.TimeKeeper.api.controllers.ItemsApi;
import home.tm.TimeKeeper.api.models.ItemDto;
import home.tm.TimeKeeper.api.models.ItemsPaginatedListDto;
import home.tm.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws/rest")
@RequiredArgsConstructor
public class ItemController implements ItemsApi {

    private final ItemService itemService;

    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<ItemsPaginatedListDto> getItems(Pageable pageable, String search) {
        return new ResponseEntity<>(itemService.getItems(pageable, search), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<ItemDto> getItem(Long id) {
        return new ResponseEntity<>(itemService.getItem(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<ItemDto> createItem(ItemDto itemDto) {
        return new ResponseEntity<>(itemService.createItem(itemDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<Void> deleteItem(Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<ItemDto> updateItem(Long id, ItemDto itemDto) {
        return new ResponseEntity<>(itemService.updateItem(id, itemDto), HttpStatus.OK);
    }
}
