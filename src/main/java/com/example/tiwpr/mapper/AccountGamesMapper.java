package com.example.tiwpr.mapper;

import com.example.tiwpr.dto.GameDto;
import com.example.tiwpr.entity.Game;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountGamesMapper {

    @Mapping(target = "ownerId", expression = "java(game.getOwner().getId())")
    GameDto mapGameToGameDto(Game game);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Game mapGameDtoToGame(GameDto gameDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Game mergeGameDtoWithGame(@MappingTarget Game currentAccount, GameDto newAccount);

}
