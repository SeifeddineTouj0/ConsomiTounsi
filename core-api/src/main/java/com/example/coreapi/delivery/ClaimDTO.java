package com.example.coreapi.delivery;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimDTO {
    private String id;
    private String content;
    private Decision decision;
    private Status status;
}
