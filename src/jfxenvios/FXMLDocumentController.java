/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxenvios;

import java.awt.Container;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author sastian
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button btTrabajando;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Close(MouseEvent event) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

    }

    //Futuro boton de loguear y desloguear
    @FXML
    private void Clear(MouseEvent event) {
        borderPane.setCenter(null);
    }

    @FXML
    private void Camiones(MouseEvent event) {
        loadPantalla("Camiones");
    }

    @FXML
    private void Paquetes(MouseEvent event) {
        loadPantalla("Paquetes");

    }

    @FXML
    private void Entrega(MouseEvent event) {
        loadPantalla("Entrega");
    }

    @FXML
    private void trabajadoresActuales(MouseEvent event) {
        loadPantalla("VerListadoCompleto");
    }

    private void loadPantalla(String pantalla) {
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pantalla + ".fxml"));
            fxmlLoader.getController();
            root = fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPane.setCenter(root);
    }

}
