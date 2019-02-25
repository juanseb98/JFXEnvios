/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxenvios;

import Objetos.Camion;
import Objetos.TipoCamion;
import dao.GenericDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Controlador encargado de realizar todas las consultas y acciones de la
 * ventana de de Insertar Nuevo camion en la aplicacion
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
public class InsertarCamionController implements Initializable {

    private static GenericDAO genericDAO = new GenericDAO<>();

    @FXML
    private Button btInsertar;
    @FXML
    private TextField txMatricula;
    @FXML
    private TextField txModelo;
    @FXML
    private TextField txPotencia;
    @FXML
    private ComboBox<TipoCamion> cbTipo;

    private CamionesController ctr;
    private Stage stage;

    /**
     * Metodo encargado de realizar los siguientes pasos al inicializar
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTipo.setItems(FXCollections.observableArrayList(TipoCamion.values()));

        //El text field de potencia aceptara unicamente numeros decimales
        txPotencia.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,2}([\\.]\\d{0,2})?")) {
                    txPotencia.setText(oldValue);
                }
            }
        });

        txMatricula.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,4}\\D{0,3}")) {
                    txMatricula.setText(oldValue);
                }
            }
        });
    }

    /**
     * Metodo que se activa al pulsar el boton de insertar camion y se encarga
     * de comprobar los datos insertados del nuevo camion y si estan correctos
     * inserta el nuevo camion en la base de datos
     *
     * @param event
     */
    @FXML
    private void insertarCamion(MouseEvent event) {
        String matricula, modelo, tipo;
        double potencia;

        try {
            matricula = txMatricula.getText();
            modelo = txModelo.getText();
            tipo = cbTipo.getValue().toString();
            potencia = Double.parseDouble(txPotencia.getText());

            if (matricula.equals("") || modelo.equals("")) {
                throw new RuntimeException("datos vacios");
            }
            Camion c = new Camion(matricula, modelo, potencia, TipoCamion.valueOf(tipo));
            try {
                genericDAO.guardarActualizar(c);

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Insertado con exito");
                alert.setHeaderText(null);
                alert.setContentText("Camion con matricula " + matricula + " insertado con exito");
                alert.showAndWait();
                stage.close();

            } catch (ConstraintViolationException e) {
                StringBuilder str = new StringBuilder();
                for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
                    str.append("En el campo '" + constraintViolation.getPropertyPath() + "':" + constraintViolation.getMessage());
                }
                mostrarError(str.toString());
            }

        } catch (RuntimeException e) {
            mostrarError("Deben estar todos los datos rellenos \n y el camion no ha de estar ya registrado");
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

    /**
     * Metodo que se utilizara para cargar el controlador de la ventana camiones
     *
     * @param ctr Controlador de la ventana camiones
     */
    public void setCamionesController(CamionesController ctr) {
        this.ctr = ctr;
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

}
