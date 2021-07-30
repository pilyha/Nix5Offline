package ua.com.nix.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "operation_categories")
@Getter
@Setter
public class OperationCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_id")
    private Operation operation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "income_category_id")
    private IncomeCategory incomeCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consume_category_id")
    private ExpenseCategory expenseCategory;

    public OperationCategory() {
    }

    public OperationCategory(Operation operation, IncomeCategory incomeCategory, ExpenseCategory expenseCategory) {
        this.operation = operation;
        this.incomeCategory = incomeCategory;
        this.expenseCategory = expenseCategory;
    }
}
