package com.literalura.literalura.services;

import com.literalura.literalura.models.Autor;
import com.literalura.literalura.repositories.AutorRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutorService {


    @Autowired
    private AutorRepository autorRepository;


    public Autor add(JSONArray obje) {
        List<Autor> autores = new ArrayList<>();

        for (int i = 0; i < obje.length(); i++) {
            JSONObject jsonObject = obje.getJSONObject(i);
            Autor autor = new Autor();
            autor.setName(jsonObject.getString("name"));
            autor.setBirth_year(jsonObject.getInt("birth_year"));
            autor.setDeath_year(jsonObject.getInt("death_year"));

            // Si tienes libros asociados, tendrás que añadir lógica adicional para manejarlos
            if (autorRepository.existsByNameIgnoreCase(jsonObject.getString("name")) == null) {
                autores.add(autor);
            }
            autorRepository.saveAll(autores);
            return autorRepository.existsByNameIgnoreCase(jsonObject.getString("name"));
        }
        return null;
    }


    public void findAll(){

        List<Autor> autores= autorRepository.findAll();
        if (!autores.isEmpty()){
            System.out.println(autores);
        } else {
            System.out.println("No ahí autores registrados en la base de datos");
        }

    }

    public void findByAño(Float añoVivo){
        List<Autor> autors= autorRepository.findByAño(añoVivo);


        if (!autors.isEmpty()){
            System.out.println(autors);
        } else {
            System.out.println("No ahí autores vivos en ese determinado año");
        }
    }
}