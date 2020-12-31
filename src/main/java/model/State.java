package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    public State(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @OneToMany(
            mappedBy = "state",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Student> students = new ArrayList<Student>();
}
