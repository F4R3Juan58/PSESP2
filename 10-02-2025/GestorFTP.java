public class GestorFTP {
	private FTPClient clienteFTP;
	private static final String SERVIDOR = "localhost";
	private static final int PUERTO = 21;
	private static final String USUARIO = "alberto";
	private static final String PASSWORD = "alberto";

	public GestorFTP() {
		clienteFTP = new FTPClient();
	}

// Método para conectar al servidor FTP
	private void conectar() throws SocketException, IOException {
		System.out.println("Intentando conectar al servidor FTP...");
		clienteFTP.connect(SERVIDOR, PUERTO);
		int respuesta = clienteFTP.getReplyCode();
		System.out.println("Código de respuesta: " + respuesta); // Agregar esta línea para depuración
		if (!FTPReply.isPositiveCompletion(respuesta)) {
			clienteFTP.disconnect();
			throw new IOException("Error al conectar con el servidor FTP");
		}
		System.out.println("Conexión exitosa");
		boolean credencialesOK = clienteFTP.login(USUARIO, PASSWORD);
		if (!credencialesOK) {
			throw new IOException("Error al conectar con el servidor FTP. Credenciales incorrectas.");
		}
		clienteFTP.setFileType(FTP.BINARY_FILE_TYPE);
	}

// Método para desconectar del servidor FTP
	private void desconectar() throws IOException {
		clienteFTP.disconnect();
	}

//Método para subir un fichero al servidor FTP
	private boolean subirFichero(String path) throws IOException {
		File ficheroLocal = new File(path);
		InputStream is = new FileInputStream(ficheroLocal);
		boolean enviado = clienteFTP.storeFile(ficheroLocal.getName(), is);
		is.close();
		return enviado;
	}

//Método para descargar un fichero desde el servidor FTP
	private boolean descargarFichero(String ficheroRemoto, String pathLocal) throws IOException {
		OutputStream os = new BufferedOutputStream(new FileOutputStream(pathLocal));
		boolean recibido = clienteFTP.retrieveFile(ficheroRemoto, os);
		os.close();
		return recibido;
	}

//Método principal
	public static void main(String[] args) {
		GestorFTP gestorFTP = new GestorFTP(); // Corregido: se añadió el "new"
		try {
			gestorFTP.conectar();
			System.out.println("Conectado");
//Intentar subir el fichero
			boolean subido = gestorFTP.subirFichero("C:/prueba.rar");
			if (subido) {
				System.out.println("Fichero subido correctamente");
			} else {
				System.err.println("Ha ocurrido un error al intentar subir el fichero");
			}
//Intentar descargar el fichero
			boolean descargado = gestorFTP.descargarFichero("alumno.txt", "C:/alumno.txt");
			if (descargado) {
				System.out.println("Fichero descargado correctamente");
			} else {
				System.err.println("Ha ocurrido un error al intentar descargar el fichero");
			}
//Desconectar del servidor FTP
			gestorFTP.desconectar();
			System.out.println("Desconectado");
		} catch (Exception e) {
//Manejo de errores
			System.err.println("Ha ocurrido un error: " + e.getMessage());
		}
	}
}
