package aed.proyecto.ficheros.random;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * @author Ayoze Amaro
 *
 */
public class RandomController implements Initializable {

	// VIEW
	@FXML
    private GridPane view;
	@FXML
	private TextField routeTXT, teamidTXT, nameteamTXT, codleagueTXT, locationTXT, counttrophiesTXT;
	@FXML
	private Button contentfileBTN, registryBTN, modifytrophiesBTN, datateamBTN;
	@FXML
	private CheckBox internationalCHK;
	@FXML
	private TextArea contentfileTXT;
	
	// CONSTRUCTOR
	public RandomController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RandomView.fxml"));
    	loader.setController(this);
    	loader.load();
    }
	
	// INITIALIZE
	@Override
	public void initialize(URL location, ResourceBundle resources) {  }
	
	/*
	 * --- VER CONTENIDO DEL FICHERO ---
	 * Permite visualizar el contenido de un fichero .DAT
	 */
	@FXML
	void onShowContentFileAction(ActionEvent event) throws IOException {  
		
		RandomAccessFile file = new RandomAccessFile(routeTXT.textProperty().get(), "r");
		Charset charset = StandardCharsets.UTF_16;
		String str = "";
		String content = "";

		if (file.length() == 0) {
			contentfileTXT.setText("Nada que visualizar");
		} else {
			while (file.getFilePointer() < file.length()) {
				content += ("ID del equipo: " + file.readInt());
				content += file.readChar();

				byte[] arr1 = new byte[80];
				file.readFully(arr1);
				str = new String(arr1, charset);
				content += ("Nombre del equipo: " + str);

				content += file.readChar();

				byte[] arr2 = new byte[10];
				file.readFully(arr2);
				str = new String(arr2, charset);
				content += ("Código de liga: " + str);

				content += file.readChar();

				byte[] arr3 = new byte[120];
				file.readFully(arr3);
				str = new String(arr3, charset);
				content += ("Localidad: " + str);
				content += file.readChar();
				content += ("Copas ganadas: " + file.readInt());
				content += file.readChar();
				content += ("Internacional: " + file.readBoolean());
				content += file.readChar() + "\n";

			}
		contentfileTXT.setText(content);
		}
	}
	
	/*
	 * --- INSERTAR UN REGISTRO ---
	 * Insercción de un nuevo registro en el fichero .DAT
	 */
	@FXML
	void onInsertRegistryAction(ActionEvent event) throws IOException {  
		
		String[] data = new String[5];
		data[0] = (nameteamTXT.textProperty().get() + "                                                  ").substring(0,40);
		data[1] = (codleagueTXT.textProperty().get() + "     ").substring(0, 5);
		data[2] = (locationTXT.textProperty().get() + "                                                                                     ").substring(0, 60);
		data[3] = counttrophiesTXT.textProperty().get();
		data[4] = internationalCHK.selectedProperty().toString();

		RandomAccessFile fichero = new RandomAccessFile(routeTXT.textProperty().get(), "rw");
		int id = 0;
		char separador = ',';

		if (fichero.length() == 0) {
			id = 1;
		} else {
			fichero.seek(fichero.length() - 231);
			id = fichero.readInt() + 1;
			fichero.seek(fichero.length());
		}

		fichero.writeInt(id);
		fichero.writeChar(separador);
		fichero.writeChars(data[0]);
		fichero.writeChar(separador);
		fichero.writeChars(data[1]);
		fichero.writeChar(separador);
		fichero.writeChars(data[2]);
		fichero.writeChar(separador);
		fichero.writeInt(Integer.parseInt(data[3]));
		fichero.writeChar(separador);
		fichero.writeBoolean(Boolean.parseBoolean(data[4]));
		fichero.writeChar(separador);
		
		onShowContentFileAction(event);
	}
	
	/*
	 * --- MODIFICAR LAS COPAS GANADAS ---
	 * Permite modificar las copas ganadas de los equipos del fichero .DAT
	 */
	@FXML
	void onModifyTrophiesAction(ActionEvent event) throws IOException {  
		
		RandomAccessFile fichero = new RandomAccessFile(routeTXT.textProperty().get(), "rw");
		int id = Integer.parseInt(teamidTXT.textProperty().get());
		int trophies = Integer.parseInt(counttrophiesTXT.textProperty().get());

		if (fichero.length() == 0) {
			contentfileTXT.setText("Nada que modificar");
		} else {
			id = (id - 1) * 231;
			fichero.seek(id + 222);
			fichero.writeInt(trophies);
		}
		onShowContentFileAction(event);
	}
	
	/*
	 * --- MOSTRAR DATOS DE UN EQUIPO POR ID ---
	 * Muestra los datos de un equipo del fichero .DAT escribiendo su ID
	 */
	@FXML
	void onShowDataTeamAction(ActionEvent event) throws IOException { 
		
		int id = Integer.parseInt(teamidTXT.textProperty().get());
		RandomAccessFile file = new RandomAccessFile(routeTXT.textProperty().get(), "r");
		Charset charset = StandardCharsets.UTF_16;
		String s1 = "";
		String content = "";
		
		if (file.length() == 0) {
			contentfileTXT.setText("Ningún dato que visualizar");
		} else {
			id = (id - 1) * 231;
			file.seek(id);
			content += ("ID del equipo: " + file.readInt());
			content += file.readChar();

			byte[] arr1 = new byte[80];
			file.readFully(arr1);
			s1 = new String(arr1, charset);
			content += ("Nombre del equipo: " + s1);
			content += file.readChar();

			byte[] arr2 = new byte[10];
			file.readFully(arr2);
			s1 = new String(arr2, charset);
			content += ("Código de liga: " + s1);
			content += file.readChar();

			byte[] arr3 = new byte[120];
			file.readFully(arr3);
			s1 = new String(arr3, charset);
			content += ("Localidad: " + s1);
			content += file.readChar();
			content += ("Copas ganadas: " + file.readInt());
			content += file.readChar();
			content += ("Internacional: " + file.readBoolean());
			content += file.readChar();
		}
		contentfileTXT.setText(content);
	}
	
	// SHOW VIEW
    public GridPane getView() {
    	return view;
    }
}
