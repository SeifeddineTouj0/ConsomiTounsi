package com.example.coreapi.produits.queries;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FetchProductByNameOrBarCodeQuery {
    String name;
    String barCode;
}
