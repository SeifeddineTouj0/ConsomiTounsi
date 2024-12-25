package tn.fst.igl5.delivery_microservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClaimDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String content;

    private Decision decision;

    private Status status;

}
