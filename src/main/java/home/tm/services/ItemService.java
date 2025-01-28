package home.tm.services;

import home.tm.TimeKeeper.api.models.ItemDto;
import home.tm.TimeKeeper.api.models.ItemsPaginatedListDto;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    ItemsPaginatedListDto getItems(Pageable pageable, String search);

    ItemDto createItem(ItemDto itemDto);

    void deleteItem(Long id);

    ItemDto updateItem(Long id, ItemDto itemDto);

    ItemDto getItem(Long id);
}
