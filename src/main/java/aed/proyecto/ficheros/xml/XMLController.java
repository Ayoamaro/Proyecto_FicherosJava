package aed.proyecto.ficheros.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * @author Ayoze Amaro
 *
 */
public class XMLController implements Initializable {

	// VIEW
	@FXML
    private GridPane view;
	@FXML
	private Button contentfileBTN, contractBTN, modifytrophiesBTN, deleteteamBTN, writefileBTN;
	@FXML
	private TextField routeTXT, nameplayerTXT, startdateTXT, enddateTXT, nameteamTXT, trophiesTXT;
	@FXML
	private TextArea contentfileTXT;
	
	// CONSTRUCTOR
	public XMLController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/XMLView.fxml"));
    	loader.setController(this);
    	loader.load();
    }
	
	// INITIALIZE
	@Override
	public void initialize(URL location, ResourceBundle resources) { }
    
	/*
	 * --- VER CONTENIDO DEL FICHERO ---
	 * Permite visualizar el contenido de un fichero .XML
	 */
	@FXML
	void onShowContentFileAction(ActionEvent event) throws FileNotFoundException, JDOMException, IOException { 
		
		SAXBuilder builder = new SAXBuilder();
		Document documentJDOM = builder.build(new FileInputStream("Equipos.XML"));
		String content = "";
		Element root = documentJDOM.getRootElement();
		List<Element> childrenRoot = root.getChildren();

		for (Element firstChildren : childrenRoot) {
			String name = firstChildren.getName();
			content += ("\nEtiqueta: " + name);
			String id = firstChildren.getAttributeValue("nomEquipo");
			
			if (id != null) { content += ("\nNombre Equipo: " + id); }

			id = firstChildren.getAttributeValue("copasGanadas");
			if (id != null) { content += ("\nCopas Ganadas: " + id); }
	
			List<Element> childrenThreat = firstChildren.getChildren();
			for (Element secondChildren : childrenThreat) {
				String secondName = secondChildren.getName();
				String secondText = secondChildren.getValue();

				if (secondName == "Contratos") {
					content += ("\nEtiqueta: " + secondName);
					List<Element> thirtChildrens = secondChildren.getChildren();

					for (Element thirtChildren : thirtChildrens) {
						String thirtName = thirtChildren.getName();
						String thirtText = thirtChildren.getValue();
						content += ("\n	Etiqueta: " + thirtName + ". Texto: " + thirtText);
						id = thirtChildren.getAttributeValue("fechaInicio");
						if (id != null) { content += ("\tFecha Inicio: " + id); }
						id = thirtChildren.getAttributeValue("fechaFin");
						if (id != null) { content += ("\tFecha Fin: " + id); }
					}
				} else { content += ("\nEtiqueta: " + secondName + ". Texto: " + secondText); }	
			}
		}
		contentfileTXT.setText(content);
	}
	
	/*
	 * --- INSERTAR UN CONTRATO ---
	 * Insercción de un nuevo contrato en el fichero .XML
	 */
	@FXML
	void onInsertContractAction(ActionEvent event) throws FileNotFoundException, JDOMException, IOException { 
		
		SAXBuilder builder = new SAXBuilder();
		Document documentJDOM = builder.build(new FileInputStream("Equipos.XML"));
		Element root = documentJDOM.getRootElement();
		List<Element> childrenRoot = root.getChildren();

		for (Element children : childrenRoot) {
			String name = children.getAttributeValue("nomEquipo");
			if (name != null) {
				if (name.equals(nameteamTXT.textProperty().get())) {
					Element newElement = new Element("Futbolista");

					newElement.setAttribute("fechaInicio", startdateTXT.textProperty().get());
					newElement.setAttribute("fechaFin", enddateTXT.textProperty().get());
					newElement.setText(nameplayerTXT.textProperty().get());

					Element elementCreated = (Element) children.getChild("Contratos");
					elementCreated.addContent(newElement);
				}
			}
		}
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream file = new FileOutputStream("Equipos.XML");
		out.output(documentJDOM, file);
		onShowContentFileAction(event);
	}
	
	/*
	 * --- MODIFICAR LAS COPAS GANADAS ---
	 * Permite modificar las copas ganadas de los equipos del fichero .XML
	 */
	@FXML
	void onModifyTrophiesAction(ActionEvent event) throws FileNotFoundException, JDOMException, IOException { 
		
		SAXBuilder builder = new SAXBuilder();
		Document documentJDOM = builder.build(new FileInputStream("Equipos.XML"));
		Element root = documentJDOM.getRootElement();
		List<Element> childrenRoot = root.getChildren();

		for (Element children : childrenRoot) {
			String name = children.getAttributeValue("nomEquipo");
			if (name != null) {
				if (name.equals(nameteamTXT.textProperty().get()))
					children.setAttribute("copasGanadas", trophiesTXT.textProperty().get());
			}
		}
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream file = new FileOutputStream("Equipos.XML");
		out.output(documentJDOM, file);
		onShowContentFileAction(event);
	}
	
	/*
	 * --- ELIMINAR EQUIPO POR NOMBRE ---
	 * Permite borrar un equipo del fichero .XML escribiendo su nombre
	 */
	@FXML
	void onDeleteTeamByName(ActionEvent event) throws FileNotFoundException, JDOMException, IOException { 
		
		SAXBuilder builder = new SAXBuilder();
		Document documentJDOM = builder.build(new FileInputStream("Equipos.XML"));
		Element root = documentJDOM.getRootElement();
		List<Element> childrenRoot = root.getChildren();

		Element childrenDelete = null;

		for (Element children : childrenRoot) {
			String name = children.getAttributeValue("nomEquipo");
			if (name != null) {
				if (name.equals(nameteamTXT.textProperty().get()))
					childrenDelete = children;
			}
		}
		childrenRoot.remove(childrenDelete);
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream file = new FileOutputStream("Equipos.XML");
		out.output(documentJDOM, file);
		onShowContentFileAction(event);
	}
	
	/*
	 * --- CREACIÓN DE UN NUEVO FICHERO ---
	 * Creación de un nuevo fichero .XML
	 */
	@FXML
	void onWriteAnotherFileAction(ActionEvent event) throws FileNotFoundException, JDOMException, IOException { 
		
		SAXBuilder builder = new SAXBuilder();
		Document documentJDOM = builder.build(new FileInputStream("Equipos.XML"));
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream file = new FileOutputStream(routeTXT.textProperty().get());
		out.output(documentJDOM, file);
	}
	
	// SHOW VIEW
    public GridPane getView() {
    	return view;
    }
}
