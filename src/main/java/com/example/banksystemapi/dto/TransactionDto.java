package com.example.banksystemapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Long originatingAccountId;
    private Long resultingAccountId;
    private double amount;
    private String description;
}
