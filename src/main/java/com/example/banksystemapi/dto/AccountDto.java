package com.example.banksystemapi.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Integer id;
    private String name;
    private Double amount;
}
