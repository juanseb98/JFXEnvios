/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxenvios;

import Objetos.TipoCamion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author sastian
 */
public class InsertarCamionController implements Initializable {

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
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    txPotencia.setText(oldValue);
                }
            }
        });
    }

    @FXML
    private void insertarCamion(MouseEvent event) {
        //TODO Realizar insert en Camion hibernate
    }

}
