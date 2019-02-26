/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxenvios;

import java.net.URL;
import java.util.ResourceBundle;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import Objetos.Camionero;
import dao.GenericDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Controlador encargado de realizar todas las consultas y acciones de la
 * ventana de de Insertar Nuevo camionero en la aplicacion
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
public class RegistrarCamioneroController implements Initializable {

	private static GenericDAO<Object> genericDAO = new GenericDAO<>();

	private Stage stage;

	@FXML
	private PasswordField txtContrasenia;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtDni;
	@FXML
	private TextField txtSalario;
	@FXML
	private TextField txtTelefono;
	@FXML
	private TextField txtPoblacion;
	@FXML
	private Button btRegistrar;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		txtSalario.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,5}([\\.]\\d{0,2})?")) {
					txtSalario.setText(oldValue);
				}
			}

		});
		txtTelefono.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,9}")) {
					txtTelefono.setText(oldValue);
				}
			}

		});
	}

	@FXML
	private void registrar(MouseEvent event) {
		String dni, contrasenia, poblacion, nombre;
		int telefono;
		double salario;

		try {
			nombre = txtNombre.getText();
			dni = txtDni.getText();
			contrasenia = txtContrasenia.getText();
			poblacion = txtPoblacion.getText();
			try {
				telefono = Integer.parseInt(txtTelefono.getText());
				salario = Double.parseDouble(txtSalario.getText());
			} catch (NumberFormatException ex) {
				throw new Exception("Los campos telefono y salario deben ser numericos");
			}
			Camionero camionero = new Camionero(dni, contrasenia, nombre, poblacion, telefono, salario);
			try {
				genericDAO.guardarActualizar(camionero);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Insertado con exito");
				alert.setHeaderText(null);
				alert.setContentText("Camionero insertado con exito");
				alert.showAndWait();
				stage.close();

			} catch (ConstraintViolationException e) {
				StringBuilder str = new StringBuilder();
				for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
					str.append("En el campo '" + constraintViolation.getPropertyPath() + "':"
							+ constraintViolation.getMessage() + "\n");
				}
				mostrarErrorLargo(str.toString());
			}
		} catch (Exception e) {
			mostrarErrorLargo(e.getMessage());
		}

	}

	/**
	 * Metodo que se encarga de mostrar una notificacion de error con el mensaje
	 * pasado por parametro
	 *
	 * @param msg mensaje que aparece en la alerta
	 */
	public void mostrarError(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error al insertar");
		alert.setContentText(msg);
		alert.showAndWait();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Metodo que se encarga de mostrar una notificacion de error con el mensaje
	 * pasado por parametro
	 *
	 * @param msg mensaje que aparece en la alerta
	 */
	private void mostrarErrorLargo(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Ha ocurrido un error mientras se intentaba insertar");

		Label label = new Label("El error fue:");

		TextArea textArea = new TextArea(msg);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}

}
