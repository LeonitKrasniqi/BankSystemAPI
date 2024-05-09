package com.example.banksystemapi.model;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double amount;
    private String description;

    @ManyToOne
    private Account originatingAccount;

    @ManyToOne
    private Account resultingAccount;
}
