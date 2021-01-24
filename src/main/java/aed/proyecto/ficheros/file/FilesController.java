package aed.proyecto.ficheros.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import aed.proyecto.ficheros.app.App;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * @author Ayoze Amaro
 *
 */
public class FilesController implements Initializable {

	// MODEL
	private ListProperty<String> fileLIST = new SimpleListProperty<String>(FXCollections.observableArrayList());
	private StringProperty contentFile = new SimpleStringProperty();
	
	// VIEW
	@FXML
	private GridPane view;
	@FXML
	private Button crearBTN, deleteBTN, moveBTN, filesfolderBTN, showfileBTN, modifyfileBTN;
	@FXML
	private CheckBox folderCHK, filesCHK;
	@FXML
	private TextField routeTXT, filesfolderTXT;
	@FXML
	private ListView<String> filesfolderLIST;
	@FXML
	private TextArea contentfileTXT;
	@FXML
	private Text changeTXT;
	
	// CONSTRUCTOR
	public FilesController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FilesView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	// INITIALIZE
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		filesCHK.disableProperty().bind(folderCHK.selectedProperty().asObject());
		folderCHK.disableProperty().bind(filesCHK.selectedProperty().asObject());

		filesfolderLIST.itemsProperty().bind(fileLIST);
		contentfileTXT.textProperty().bind(contentFile);
	}
	
	/*
	 * --- CREAR FICHERO ---
	 * Crear un fichero en el directorio actual
	 */
	@FXML
	void onCreateAction(ActionEvent event) { 
		
		try {
			File selectedfile = new File(routeTXT.textProperty().get() + "\\" + routeTXT.textProperty().get());
			if (filesCHK.isSelected()) {
				if (selectedfile.createNewFile()) { changeTXT.setText("No existe - creado"); }
				else { changeTXT.setText("Ya existe"); }	
			}	
			if (folderCHK.isSelected()) {
				if (selectedfile.mkdir()) { changeTXT.setText("No existe - creado"); }
				else { changeTXT.setText("Ya existe"); }
			}
		} catch (Exception e) { System.err.println(e); }
	}
	
	/*
	 * --- ELIMINAR FICHERO/DIRECTORIO ---
	 * Eliminar un fichero o carpeta del directorio actual
	 */
	@FXML
	void onDeleteAction(ActionEvent event) { 
		
		File selectedFile = new File(routeTXT.textProperty().get() + "\\" + filesfolderTXT.textProperty().get());
		if (selectedFile.isDirectory()) {
			try {
				Utils.deleteDirectory(selectedFile);
				if (selectedFile.delete()) { changeTXT.setText("Borrado"); }
				else { changeTXT.setText("No borrado"); }
			} catch (Exception e) { System.err.println(e); }
		}
		if (selectedFile.isFile()) {
			try {
				if (selectedFile.delete()) { changeTXT.setText("Borrado");}
				else { changeTXT.setText("No borrado"); }
			} catch (Exception e) { System.err.println(e); }
		}
	}
	
	/*
	 * --- MOVER/RENOMBRAR UN FICHERO/DIRECTORIO ---
	 * Mover o renombrar un fichero o carpeta del directorio actual
	 */
	@FXML
	void onMoveAction(ActionEvent event) { 
		
		try {
			File selectedFile1 = new File(routeTXT.textProperty().get() + "\\" + filesfolderTXT.textProperty().get());
			String selectedRoute2 = "";

			TextInputDialog dialog = new TextInputDialog();
			dialog.initOwner(App.getPrimaryStage());
			dialog.setTitle("Mover fichero");
			dialog.setHeaderText("Nombre anterior: " + filesfolderTXT.textProperty().get());
			dialog.setContentText("Nuevo nombre:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				selectedRoute2 = result.get();
			}
			
			File selectedFile2 = new File(routeTXT.textProperty().get() + "\\" + selectedRoute2);
			if (selectedFile2 != null) {
				if (selectedFile1.renameTo(selectedFile2)) { changeTXT.setText("Renombrado: "); }
				else { changeTXT.setText("No Renombrado: "); }
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	/*
	 * --- MOSTRAR FICHEROS/DIRECTORIOS ---
	 * Visualiza todos aquellos ficheros/carpetas del directorio actual
	 */
	@FXML
	void onShowFilesFolderAction(ActionEvent event) { 
		
		if (!fileLIST.isEmpty())
			fileLIST.clear();
		try {
			File f = new File(routeTXT.textProperty().get());
			String[] nombres = f.list();
			if (f.exists()) { fileLIST.addAll(nombres); } 
			else { changeTXT.setText("No existe /n No es directorio"); }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * --- MOSTRAR CONTENIDO DE UN FICHERO ---
	 * Muestra el contenido de un fichero del directorio actual
	 */
	@FXML
	void onShowContentFileAction(ActionEvent event) { 
		
		File selectedFile = new File(routeTXT.textProperty().get() + "\\" + filesfolderTXT.textProperty().get());
		if (selectedFile.isDirectory())
			changeTXT.setText("Es directorio");
		else {
			if (selectedFile.canRead()) {
				String str;
				FileReader fileRD = null;
				try { 
					fileRD = new FileReader(routeTXT.textProperty().get() + "\\" + filesfolderTXT.textProperty().get()); 
				} catch (FileNotFoundException e) { 
					e.printStackTrace(); 
				}
				
				BufferedReader br = new BufferedReader(fileRD);
				try {
					while ((str = br.readLine()) != null) { contentFile.set(str); }
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * --- MODIFICAR CONTENIDO DE UN FICHERO ---
	 * Modifica el contenido de un fichero del directorio actual
	 */
	@FXML
	void onModifyFilesAction(ActionEvent event) throws IOException { 
		
		File selectedFile = new File(routeTXT.textProperty().get() + "\\" + filesfolderTXT.textProperty().get());
		if (selectedFile.isDirectory()) { changeTXT.setText("Es directorio"); }	
		else {
			FileWriter fileWR = new FileWriter(selectedFile);
			String txt = "";

			TextInputDialog dialog = new TextInputDialog();
			dialog.initOwner(App.getPrimaryStage());
			dialog.setTitle("Modificar fichero");
			dialog.setHeaderText("Introduce el nuevo texto");
			dialog.setContentText("Texto:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) { txt = result.get(); }

			for (int i = 0; i < txt.length(); i++) {
				fileWR.write(txt.charAt(i));
			}
		fileWR.close();
		}
	}
	
	// SHOW VIEW
	public GridPane getView() {
		return view;
	}
}
