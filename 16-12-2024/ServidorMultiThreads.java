public class ServerMultiThreads {
    private ServerSocket serverSocket;

    // Constructor donde se inicializa el ServerSocket y se empieza a aceptar conexiones
    public ServerMultiThreads(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);  // Creamos el servidor en el puerto especificado
        System.out.println("Esperando clientes para establecer conexión");
        while (true) {
            Socket socket = serverSocket.accept();  // Aceptamos la conexión de un cliente
            System.out.println("(Servidor) Conexión establecida");
            new GestorProcesos(socket).start();  // Creamos un nuevo hilo para gestionar la conexión
        }
    }

    // Método para detener el servidor
    public void stop() throws IOException {
        serverSocket.close();  // Cerramos el ServerSocket
    }

    public static void main(String[] args) {
        try {
            // Inicializamos el servidor en el puerto 49177
            new ServerMultiThreads(49177);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
