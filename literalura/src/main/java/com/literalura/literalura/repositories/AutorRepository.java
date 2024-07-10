package com.literalura.literalura.repositories;

import com.literalura.literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {



    @Query("SELECT a FROM Autor a WHERE LOWER(a.name) = LOWER(:nombreAutor)")
    Autor existsByNameIgnoreCase(@Param("nombreAutor") String nombreAutor);

    @Query("SELECT a FROM Autor a WHERE a.birth_year < :añoVivo and a.death_year > :añoVivo")
    List<Autor> findByAño(@Param("añoVivo") Float añoVivo);
}
