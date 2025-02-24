import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import javax.net.ssl.HttpsURLConnection;

public class GetContenidoPost {
    // Método que hace una solicitud POST a la URL proporcionada y obtiene la respuesta
    public StringBuilder getContenidoMetodoPost(String direccion, String parametros) throws Exception {
        StringBuilder respuesta = new StringBuilder(); // Usamos un StringBuilder para almacenar la respuesta
        URL url = new URL(direccion); // Creamos el objeto URL con la dirección proporcionada
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection(); // Abrimos la conexión HTTP

        conexion.setRequestMethod("POST"); // Establecemos el método HTTP (POST en este caso)
        conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // Definimos el tipo de contenido
        conexion.setRequestProperty("charset", "utf-8"); // Definimos la codificación de caracteres
        conexion.setRequestProperty("User-Agent", "Mozilla/5.0"); // Definimos el User-Agent para que sea tratado como un navegador web
        conexion.setDoOutput(true); // Indicamos que vamos a enviar datos en el cuerpo de la solicitud

        // Enviar los parámetros en el cuerpo de la solicitud
        try (OutputStream os = conexion.getOutputStream()) {
            byte[] input = parametros.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int estado = conexion.getResponseCode(); // Obtenemos el código de respuesta HTTP de la conexión

        // Si la respuesta es exitosa (código 200 OK), leemos la respuesta del servidor
        if (estado == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream(), StandardCharsets.UTF_8))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    respuesta.append(linea).append("\n");
                }
            }
        } else {
            // Si la respuesta no es exitosa, lanzamos una excepción
            throw new Exception("Error HTTP estado: " + estado);
        }
        
        conexion.disconnect(); // Cerramos la conexión
        return respuesta; // Devolvemos la respuesta obtenida
    }

    // Método para guardar el contenido en un archivo
    public static void writeFile(String strPath, String contenido) throws IOException {
        Path path = Paths.get(strPath); // Creamos el objeto Path con la ruta proporcionada
        byte[] strToBytes = contenido.getBytes(StandardCharsets.UTF_8); // Convertimos el contenido a bytes
        Files.write(path, strToBytes); // Escribimos el contenido en el archivo
    }

    public static void main(String[] args) {
        try {
            String esquema = "https://"; // El esquema de la URL (https)
            String servidor = "dle.rae.es"; // El servidor (sitio de la RAE)
            String recurso = "/"; // La raíz del recurso (corregido)
            String palabra = "Almendra";
            String parametros = "palabra=" + URLEncoder.encode(palabra, StandardCharsets.UTF_8.name()); // Codificamos el parámetro

            // Concatenamos la URL completa sin parámetros en la URL
            String direccion = esquema + servidor + recurso;

            // Creamos una instancia de la clase GetContenidoPost
            GetContenidoPost gp = new GetContenidoPost();
            // Obtenemos el contenido de la URL utilizando el método POST
            StringBuilder resultado = gp.getContenidoMetodoPost(direccion, parametros);
            // Guardamos el resultado en un archivo HTML
            writeFile("almendra.html", resultado.toString());

            // Imprimimos un mensaje indicando que la descarga ha terminado
            System.out.println("Descarga Finalizada");
        } catch (Exception e) {
            // Si ocurre algún error, lo mostramos
            System.err.println("Error: " + e.getMessage());
        }
    }
}


