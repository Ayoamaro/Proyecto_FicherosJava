package aed.proyecto.ficheros.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import aed.proyecto.ficheros.file.FilesController;
import aed.proyecto.ficheros.random.RandomController;
import aed.proyecto.ficheros.xml.XMLController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

/**
 * @author Ayoze Amaro
 *
 */
public class Controller implements Initializable {

	private FilesController filesController  = new FilesController();
    private RandomController randomController = new RandomController();
    private XMLController xmlController = new XMLController();
    
    @FXML
    private BorderPane view;

    @FXML
    private Tab filesTab, randomTab, xmlTab;
    
    
    public Controller() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
    	loader.setController(this);
    	loader.load();
    }
    
    public BorderPane getView() {
    	return view;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		filesTab.setContent(filesController.getView());
		randomTab.setContent(randomController.getView());
		xmlTab.setContent(xmlController.getView());
	}
}
