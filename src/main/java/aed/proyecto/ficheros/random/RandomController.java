package aed.proyecto.ficheros.random;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

/**
 * @author Ayoze Amaro
 *
 */
public class RandomController implements Initializable {

	@FXML
    private GridPane view;
	
	public RandomController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RandomView.fxml"));
    	loader.setController(this);
    	loader.load();
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) { 
		
	}
    
    public GridPane getView() {
    	return view;
    }
}
