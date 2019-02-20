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

/**
 * FXML Controller class
 *
 * @author sastian
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
     * Initializes the controller class.
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
            System.out.println("matricula " + matricula + " modelo " + modelo + " tipo " + tipo + " potencia " + potencia);

            Camion c = new Camion(matricula, modelo, potencia, TipoCamion.valueOf(tipo));
            try{
            genericDAO.guardar(c);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Insertado con exito");
            alert.setHeaderText(null);
            alert.setContentText("Camion con matricula " + matricula + " insertado con exito");
            alert.showAndWait();
            stage.close();
            
            }catch(javax.validation.ConstraintViolationException e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al insertar");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            }
            
        } catch (RuntimeException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al insertar");
            alert.setContentText("Deben estar todos los datos rellenos \n y el camion no ha de estar ya registrado");
            alert.showAndWait();
        }

    }

    public void setCamionesController(CamionesController ctr) {
        this.ctr = ctr;
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

}
