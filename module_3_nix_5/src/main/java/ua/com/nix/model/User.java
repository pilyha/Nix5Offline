package ua.com.nix.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "first_name")
    private String firstName;

    @JoinColumn(name = "last_name")
    private String lastName;

    private String email;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    public User() {
    }

    public User(String firstName, String lastName, String email, List<Account> accounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accounts = accounts;
    }
}
