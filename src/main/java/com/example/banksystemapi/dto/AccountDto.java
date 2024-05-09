package com.example.banksystemapi.dto;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Integer id;
    private String name;
    @Positive(message = "Amount should be positive number.")
    private Double amount;
}
