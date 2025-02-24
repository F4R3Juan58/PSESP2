public class TcpServer {
	
	private ServerSocket serverSocket;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;

	public TcpServer (int puerto) throws IOException {
	serverSocket=new ServerSocket (puerto);
	}}

	public void start() throws IOException {
	System.out.println(" (Servidor) Esperando conexiones...");
	socket=serverSocket.accept();
	is = socket.getInputStream();
	os = socket.getOutputStream();
	socket.getOutputStream();
	System.out.println(" (Servidor) Conexión establecida.");
	}

	public void stop() throws IOException {
	System.out.println("(Servidor) Cerrando conexiones...");
	is.close();
	os.close();
	socket.close();
	serverSocket.close();
	System.out.println("(Servidor) Conexiones cerradas.");
  }
public void abrirCanalesDeTexto () {
	System.out.println("(Servidor) Abriendo canales de texto...");
	//Canales de lectura
	isr=new InputStreamReader(is);
	br=new BufferedReader(isr);
	//Canales de escritura
	pw= new PrintWriter(os, true);
	System.out.println(" (Servidor) Canales de texto abiertos.");
	}

	public void cerrarCanalesDeTexto() throws IOException {
	System.out.println("(Servidor) Cerrando canales de texto.");
	//Canales de lectura
	br.close();
	//Canal de escritura
	isr.close();
	pw.close();
	}

	public String leerMensajeTexto () throws IOException {
	System.out.println("(Servidor) Canales de texto cerrados.");
	System.out.println("(Servidor) Leyendo mensaje...");
	String mensaje = br.readLine();
	System.out.println("(Servidor) Mensaje leido.");
	return mensaje;
	}

	public void enviarMensajeTexto (String mensaje) {
	System.out.println("(Servidor) Enviando mensaje..."); 
	pw.println(mensaje);
	System.out.println("(Servidor) Mensaje enviado.");
	}

	public static void main(String[] args) {
	try {
	TcpServer servidor = new TcpServer (49171);
	servidor.start();
	servidor.abrirCanalesDeTexto();
	//Recepción del mensaje del cliente
	String mensajeRecibido=servidor.leerMensajeTexto(); 
	System.out.println("(Servidor) Mensaje recibido:"
	+ mensajeRecibido); 
	//Envio del mensaje al cliente");
	servidor.enviarMensajeTexto ("Mensaje enviado desde el servidor");
	servidor.cerrarCanalesDeTexto ();
	servidor.stop();
	} catch (IOException ioe) {
		ioe.printStackTrace();
	}
	}
}
