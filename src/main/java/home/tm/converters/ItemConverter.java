package home.tm.converters;

import home.tm.TimeKeeper.api.models.ItemDto;
import home.tm.model.Item;
import home.tm.model.enums.Stav;
import home.tm.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemConverter {

    private final SecurityService securityService;

    public Item toEntity(ItemDto dto) {
        Item item = toEntity(new Item(), dto);
        item.setUser(securityService.getCurrentUser());
        item.setStav(Stav.VALID.name());
        return item;
    }

    public List<ItemDto> toListDto(List<Item> data) {
        return data.stream().map(item -> toDto(item, new ItemDto())).toList();
    }

    public ItemDto toDto(Item entity, ItemDto dto) {
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());
        dto.setValidityFrom(entity.getValidityFrom());
        dto.setValidityTo(entity.getValidityTo());
        return dto;
    }

    public ItemDto toDto(Item item) {
        return toDto(item, new ItemDto());
    }

    public Item toEntity(Item item, ItemDto dto) {
        item.setId(dto.getId() == null ? null : dto.getId());
        item.setTitle(dto.getTitle());
        item.setDescription(dto.getDescription());
        item.setValidityTo(dto.getValidityTo());
        item.setValidityFrom(dto.getValidityFrom());
        return item;
    }
}
