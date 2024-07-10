package com.literalura.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Libro")
public class Libro {

    @JsonAlias("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    private Long download_count;

    private String languages;


    @ManyToOne
    private Autor autor;

    @Override
    public String toString() {
        return "Libro{\n" +
                "\t" + "id=" + id + "\n" +
                "\t" + "title='" + title + '\'' + "\n" +
                "\t" + "download_count=" + download_count + "\n" +
                "\t" + "languages='" + languages + '\'' + "\n" +
                "\t" + "autor=" + autor.getName() + '\n';
    }



}
