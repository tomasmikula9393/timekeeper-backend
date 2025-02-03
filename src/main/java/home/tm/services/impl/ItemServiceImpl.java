package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.ItemDto;
import home.tm.TimeKeeper.api.models.ItemsPaginatedListDto;
import home.tm.converters.ItemConverter;
import home.tm.exceptions.NotFoundException;
import home.tm.model.Item;
import home.tm.repositories.ItemRepository;
import home.tm.security.service.SecurityService;
import home.tm.services.ItemService;
import home.tm.utils.InputValidator;
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
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;
    private final SecurityService securityService;

    @Override
    public ItemsPaginatedListDto getItems(Pageable pageable, String search) {
        Page<Item> items = itemRepository.findAll(pageable);
        ItemsPaginatedListDto dto = new ItemsPaginatedListDto();
        dto.setList(itemConverter.toListDto(items.getContent()));
        dto.setCount(items.getPageable().getPageSize());
        dto.setPage(items.getPageable().getPageNumber());
        return dto;
    }

    @Override
    public ItemDto createItem(ItemDto itemDto) {
        InputValidator.validateItem(itemDto);
        Item item = itemRepository.save(itemConverter.toEntity(itemDto));
        return itemConverter.toDto(item);
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.delete(getItemById(id));
    }

    @Override
    public ItemDto updateItem(Long id, ItemDto itemDto) {
        Item item = itemRepository.save(itemConverter.toEntity(getItemById(id), itemDto));
        return itemConverter.toDto(item);
    }

    @Override
    public ItemDto getItem(Long id) {
        return itemConverter.toDto(getItemById(id));
    }

    private Item getItemById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new NotFoundException(NEBYLO_NALEZENO, String.format(ITEM_WAS_NOT_FOUND.getMessage(), id), ERROR)
        );
        Long userId = securityService.getCurrentUser().getId();
        if (!item.getUser().getId().equals(userId)) {
            throw new NotFoundException(NEBYLO_NALEZENO, String.format(USER_IS_NOT_AUTHORIZED.getMessage(), userId), ERROR);
        }
        return item;
    }
}
