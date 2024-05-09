package com.example.banksystemapi.model;


import jakarta.persistence.*;
import lombok.*;



@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

}
