public class GestorProcesos extends Thread {
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    // Constructor donde se pasa el socket para manejar la conexión
    public GestorProcesos(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            realizarProceso();  // Llamamos al método que maneja el proceso
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método que maneja la recepción, procesamiento y envío de la respuesta
    public void realizarProceso() throws IOException {
        is = socket.getInputStream();  // Obtenemos el InputStream para recibir datos del cliente
        os = socket.getOutputStream();  // Obtenemos el OutputStream para enviar datos al cliente

        // Leer el mensaje enviado por el cliente (cadena de palabras)
        byte[] buffer = new byte[1024];
        int bytesRead = is.read(buffer);  // Leemos los datos del InputStream
        String message = new String(buffer, 0, bytesRead);  // Convertimos los bytes a cadena
        System.out.println("(Servidor) Mensaje recibido: " + message);

        // Separar las palabras y ordenarlas alfabéticamente
        String[] palabras = message.split(" ");  // Dividimos la cadena en un array de palabras
        Arrays.sort(palabras);  // Ordenamos las palabras alfabéticamente

        // Unimos las palabras ordenadas de nuevo en una cadena
        String respuesta = String.join(" ", palabras);

        // Enviar la respuesta al cliente
        os.write(respuesta.getBytes());  // Convertimos la cadena ordenada a bytes y la enviamos
        os.flush();  // Aseguramos que los datos se envíen inmediatamente
    }
}
