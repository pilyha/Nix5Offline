package ua.com.nix.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "expense_categories")
@Getter
@Setter
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "name_category")
    private String name;

    public ExpenseCategory() {
    }

    public ExpenseCategory(String name) {
        this.name = name;
    }
}