package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private int age;

    public Student(String name, int age, State state) {
        this.name = name;
        this.age = age;
        this.state = state;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private State state;
}
