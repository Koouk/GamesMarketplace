package com.example.tiwpr.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequestDto {

    private Long ownerId;

    private Long receiverId;

    private Long[] gamesId;


}
