public class programa2 {
	// Método que hace una solicitud GET a la URL proporcionada y obtiene la
	// respuesta
	public StringBuilder getContenidoMetodoGet(String direccion) throws Exception {
	 StringBuilder respuesta = new StringBuilder(); // Usamos un StringBuilder para 
                                                almacenar la respuesta
	 URL url = new URL(direccion); // Creamos el objeto URL con la dirección 
                                proporcionada
	 HttpURLConnection conexion = (HttpURLConnection) url.openConnection(); // Abrimos 
                                                                 la conexión HTTP
	 conexion.setRequestMethod("GET"); // Establecemos el método HTTP 
                                   (GET en este caso)
	 conexion.setRequestProperty("Content-Type", "text/plain"); // Definimos el tipo 
                                                            de contenido
	 conexion.setRequestProperty("charset", "utf-8"); // Definimos la codificación de 
                                                  caracteres
	 conexion.setRequestProperty("User-Agent", "Mozilla/5.0"); // Definimos el 
                            User-Agent para que sea tratado como un navegador web


	 int estado = conexion.getResponseCode(); // Obtenemos el código de respuesta HTTP 
                                         de la conexión
	 Reader streamReader = null;

	 // Si la respuesta es exitosa (código 200 OK), leemos la respuesta del servidor
	 if (estado == HttpURLConnection.HTTP_OK) {
	 streamReader = new InputStreamReader(conexion.getInputStream()); // Leemos el 
                                                   flujo de entrada del servidor
	 int caracter;

	 // Leemos cada carácter de la respuesta
	 while ((caracter = streamReader.read()) != -1) {
	 respuesta.append((char) caracter); // Añadimos cada carácter leído al 
                                    StringBuilder
	 }
	 } else {
	 // Si la respuesta no es exitosa, lanzamos una excepción
	 throw new Exception("Error HTTP estado");
	 }
	 conexion.disconnect(); // Cerramos la conexión
	 return respuesta; // Devolvemos la respuesta obtenida
	 }

	// Método para guardar el contenido en un archivo
	public static void writeFile(String strPath, String contenido) throws IOException {
		Path path = Paths.get(strPath); // Creamos el objeto Path con la ruta pro
                                       porcionada
		byte[] strToBytes = contenido.getBytes(); // Convertimos el contenido 
                                                a bytes
		Files.write(path, strToBytes); // Escribimos el contenido en el archivo
	}

	public static void main(String[] args) {
	 try {
	 // Definimos la URL que vamos a consultar
	 String esquema = "https://"; // El esquema de la URL (https)
	 String servidor = "dle.rae.es/"; // El servidor (en este caso, el sitio de la 
                                 Real Academia Española)
	 String recurso = URLEncoder.encode("Almendra", StandardCharsets.UTF_8.name()); //
	Codificamos la palabra "Almendra" para que sea parte de la URL
	 // Creamos una instancia de la clase programa2
	 programa2 gp = new programa2();
	 // Concatenamos la URL completa
	 String direccion = esquema + servidor + recurso;
	 // Obtenemos el contenido de la URL utilizando el método GET
	 StringBuilder resultado = gp.getContenidoMetodoGet(direccion);
	 // Guardamos el resultado en un archivo HTML
	 programa2.writeFile("c:almendra.html", resultado.toString());
	 // Imprimimos un mensaje indicando que la descarga ha terminado
	 System.out.println("Descarga Finalizada");
} catch (Exception e) {
	 // Si ocurre algún error, lo mostramos
	 System.err.println(e.getMessage());
	 }
    }
}
