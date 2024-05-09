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
    private Integer originatingAccountId;
    private Integer resultingAccountId;
    private double amount;
    private String description;
    private Integer bankId;

}
