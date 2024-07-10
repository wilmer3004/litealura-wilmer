package com.literalura.literalura.services;


import com.literalura.literalura.models.Autor;
import com.literalura.literalura.models.Libro;
import com.literalura.literalura.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.json.JSONArray;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.json.JSONObject;


@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorService autorService1 = new AutorService();

    public void findAPI(String name) {
        String API_URL = "http://gutendex.com/books/";


        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray result = jsonObject.getJSONArray("results");


                // Iterar sobre los elementos del array
                for (int i = 0; i < result.length(); i++) {
                    JSONObject resultObject = result.getJSONObject(i);

                    // Obtener el título del objeto actual
                    String title = resultObject.getString("title");


                    // Verificar si el título contiene el término de búsqueda (ignorando mayúsculas y minúsculas)
                    if (title.equalsIgnoreCase(name)) {
                        // Si encuentra una coincidencia, hacer un scream
                        System.out.println("¡Encontré una coincidencia en el título!");

                        // Puedes imprimir el título si lo deseas
                        System.out.println("Titulo: "+resultObject.getString("title") + "\n" +
                                "Id: "+ resultObject.getNumber("id")+ "\n" +
                                "authors: "+ resultObject.getJSONArray("authors")+ "\n" +
                                "languages: "+ resultObject.getJSONArray("languages")+ "\n" +
                                "download_count: "+ resultObject.getLong("download_count")+ "\n");

                        if (!libroRepository.existsByNameIgnoreCase(resultObject.getString("title"))){
                            Autor autor = autorService1.add(resultObject.getJSONArray("authors"));

                            Libro libro = new Libro();
                            libro.setTitle(resultObject.getString("title"));
                            libro.setLanguages(String.valueOf(resultObject.getJSONArray("languages")));
                            libro.setDownload_count(resultObject.getLong("download_count"));
                            libro.setAutor(autor);

                            libroRepository.save(libro);
                            System.out.println("Se registro correctamente el libro y sus autores");
                            return;
                        } else {
                            System.out.println("Este libro ya se encuentra registrado");
                            return;
                        }


                    }
                }
                System.out.println("No se encontro ningun libro con tal nombre");

            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al procesar la solicitud HTTP", e);
        }
    }

    public void findAll(){

        List<Libro> libros= libroRepository.findAll();
        if (!libros.isEmpty()){
            System.out.println(libros);
        } else {
            System.out.println("No ahi libros registrados en la base de datos");
        }

    }

    public void findByLenguage(String lenguage){
        List<Libro> libros= libroRepository.findByLenguage(lenguage);
        if (!libros.isEmpty()){
            System.out.println(libros);
        } else {
            System.out.println("No ahi libros registrados con ese idioma");
        }
    }
}