package com.example.banksystemapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "bank")
    private List<Account> accounts;

    private double transactionPercentFeeValue;

}
