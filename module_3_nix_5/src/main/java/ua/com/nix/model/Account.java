package ua.com.nix.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer balance;

    @OneToMany(mappedBy = "account")
    private List<Operation> operations;

    public Account() {
    }

    public Account(Integer id, User user, Integer balance) {
        this.id = id;
        this.user = user;
        this.balance = balance;
    }


}
