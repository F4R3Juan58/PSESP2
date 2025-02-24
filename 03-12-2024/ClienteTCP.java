public class TcpClient {
	
	private String serverIP;
	private int serverPort;
	private Socket socket;
	private InputStream is;
	private OutputStream os;

	//Objetos especificos para el envío y recepción de cadenas de caracteres
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;

	public TcpClient (String serverIP, int serverPort) {
	this.serverIP=serverIP;
	this.serverPort=serverPort;
	}

	public void start() throws UnknownHostException, IOException { 
		System.out.println("(Cliente) Estableciendo conexión...");
		socket = new Socket (serverIP, serverPort);
		os=  socket.getOutputStream();
		is = socket.getInputStream();
		System.out.println(" (Cliente) Conexión establecida.");
	}

	public void stop() throws IOException {
	System.out.println(" (Cliente) Cerrando conexiones.");
	is.close();
	os.close();
	socket.close();
	System.out.println("(Cliente) Conexiones cerradas.");
	}

	public void abrirCanalesDeTexto() {
	System.out.println("(Cliente) Abriendo canales de texto...");
	//Canales de lectura
	isr = new InputStreamReader(is);
	br = new BufferedReader (isr );
	//Canales de escritura
	pw = new PrintWriter(os, true);
	System.out.println("(Cliente) Canales de texto abiertos.");
	}
public void cerrarCanalesDeTexto() throws IOException { 
		System.out.println(" (Cliente) Cerrando canales de texto");
		//Canales de lectura
		br.close();
		isr.close();
		//Canal de escritura
		pw.close();
		System.out.println("(Cliente) Canales de texto cerrados.");
	}

	public String leerMensajeTexto() throws IOException { 
		System.out.println(" (Cliente) Leyendo mensaje...");
		String mensaje = br.readLine(); 
		System.out.println(" (Cliente) Mensaje leido.");
		return mensaje;
	}

	public void enviarMensajeTexto (String mensaje) {
	System.out.println(" (Cliente) Enviando mensaje..."); 
	pw.println(mensaje);
	System.out.println(" (Cliente) Mensaje enviado.");
	}

	public static void main(String[] args) {
	TcpClient cliente = new TcpClient("localhost",49171);
	try {
	cliente.start();
	cliente.abrirCanalesDeTexto();
	//Envio del mensaje al servidor
	cliente.enviarMensajeTexto("Mensaje enviado desde el cliente");
	//Recepción del mensaje del servidor 
	String mensajeRecibido=cliente.leerMensajeTexto ();
	System.out.println("(Cliente) Mensaje recibido:" + mensajeRecibido);
	cliente.cerrarCanalesDeTexto();
	cliente.stop(); 
	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
}
