package com.iesjuanbosco.ejemploweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaCosteMedioDTO {
    private String nombreCategoria;
    private Double costeMedio;
    private Double numeroProductos;
}
