package com.example.tiwpr.mapper;

import com.example.tiwpr.dto.SaleItemDto;
import com.example.tiwpr.entity.SaleItem;
import com.example.tiwpr.repository.GameRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SaleItemMapper {

    @Autowired
    GameRepository gameRepository;

    @Mapping(target = "gameId", expression = "java(saleItem.getGame().getId())")
    public abstract SaleItemDto mapSaleItemToSaleItemDto(SaleItem saleItem);

    @Mapping(target = "id", ignore = true)
   // @Mapping(target = "game", source = "gameId", qualifiedByName="test") todo fix later mayby
    public abstract SaleItem mapSaleItemDtoToSaleItem(SaleItemDto saleItemDto);


    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract SaleItem mergeSaleItemDtoWithSaleItem(@MappingTarget SaleItem currentSaleItem, SaleItemDto newSaleItem);

}
