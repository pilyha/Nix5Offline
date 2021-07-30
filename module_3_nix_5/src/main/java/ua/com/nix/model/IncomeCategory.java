package ua.com.nix.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "income_categories")
@Getter
@Setter
public class IncomeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public IncomeCategory() {
    }

    public IncomeCategory(String name) {
        this.name = name;
    }
}