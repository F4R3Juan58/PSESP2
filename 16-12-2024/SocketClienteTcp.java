public class SocketTCPClient {

    private String serverIP;
    private int serverPort;
    private Socket socket;
    private OutputStream os;

    // Constructor donde se inicializan la IP y el puerto del servidor
    public SocketTCPClient(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    // Método que establece la conexión y envía el mensaje al servidor
    public void start(String message) throws UnknownHostException, IOException {
        System.out.println("(Cliente) Estableciendo conexión...");
        socket = new Socket(serverIP, serverPort);  // Establece la conexión con el servidor
        os = socket.getOutputStream();
        System.out.println("(Cliente) Conexión establecida.");
// Enviar el mensaje recibido como argumento (cadena de palabras)
        os.write(message.getBytes());  // Convertimos el mensaje a bytes y lo enviamos al servidor
        os.flush();  // Aseguramos que los datos se envíen inmediatamente
    }

    // Método para cerrar la conexión
    public void stop() throws IOException {
        System.out.println("(Cliente) Cerrando conexiones...");
        os.close();  // Cerramos el OutputStream
        socket.close();  // Cerramos el Socket
        System.out.println("(Cliente) Conexiones cerradas.");
    }

    public static void main(String[] args) {
        // Eliminar la necesidad de parámetros y colocar las palabras directamente
        String message = "palabra3 palabra1 palabra2";  // Cadena de palabras predefinida
        SocketTCPClient cliente = new SocketTCPClient("172.20.10.10", 49177);
        try {
            cliente.start(message);  // Iniciamos la conexión y enviamos el mensaje

            // Recibimos la respuesta del servidor
            byte[] buffer = new byte[1024];
            int bytesRead = cliente.socket.getInputStream().read(buffer);
            String response = new String(buffer, 0, bytesRead);  // Convertimos los bytes recibidos a cadena

            // Mostramos el mensaje enviado y la respuesta recibida
            System.out.println("Mensaje enviado: " + message);
            System.out.println("Mensaje recibido: " + response);

            cliente.stop();  // Cerramos la conexión

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
