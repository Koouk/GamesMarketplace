package com.example.tiwpr.mapper;

import com.example.tiwpr.dto.SaleItemDto;
import com.example.tiwpr.entity.SaleItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SaleItemMapper {

    @Mapping(target = "gameId", expression = "java(saleItem.getGame().getId())")
    SaleItemDto mapSaleItemToSaleItemDto(SaleItem saleItem);

    @Mapping(target = "id", ignore = true)
   // @Mapping(target = "game", source = "gameId", qualifiedByName="test") todo fix later mayby
    SaleItem mapSaleItemDtoToSaleItem(SaleItemDto saleItemDto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "game", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SaleItem mergeSaleItemDtoWithSaleItem(@MappingTarget SaleItem currentSaleItem, SaleItemDto newSaleItem);

}
