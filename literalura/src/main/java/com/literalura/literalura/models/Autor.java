package com.literalura.literalura.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Autor")
public class Autor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;


    private Integer birth_year;


    private Integer death_year;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id + ',' + '\n' +
                "name='" + name + ',' + '\n' +
                "birth_year=" + birth_year + ',' + '\n'  +
                "death_year=" + death_year + ',' + '\n' +
                '}';
    }


}