package ua.com.nix.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "operations")
@Getter
@Setter
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "date")
    private Instant date;

    private Integer result;

    @OneToMany(mappedBy = "operation")
    private List<OperationCategory> operationCategories;

    @Transient
    private OperationType type;

    public Operation() {

    }

    public Operation(Account account, Integer result, Instant date) {
        this.account = account;
        this.result = result;
        this.date = date;
    }

    public Operation(Integer id, Account account, Instant date, Integer result) {
        this.id = id;
        this.account = account;
        this.date = date;
        this.result = result;
    }
}
