package ua.com.nix.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Problem {
    private int id;
    private int from_id;
    private int to_id;
}
