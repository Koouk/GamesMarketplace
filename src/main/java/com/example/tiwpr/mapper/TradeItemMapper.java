package com.example.tiwpr.mapper;

import com.example.tiwpr.dto.TradeItemDto;
import com.example.tiwpr.entity.TradeItem;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TradeItemMapper {


    TradeItemDto mapTradeItemToTradeItemDto(TradeItem TradeItem);

    TradeItem mapTradeItemDtoToTradeItem(TradeItemDto TradeItemDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TradeItem mergeTradeItemDtoWithTradeItem(@MappingTarget TradeItem currentTradeItem, TradeItemDto newTradeItem);

}
