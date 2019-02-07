/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxenvios;

import Objetos.Paquete;
import Objetos.Reparto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author DAM-2
 */
public class PaquetesController implements Initializable {

    @FXML
    private Button btPaqueteToReparto;
    @FXML
    private TableView<Paquete> tbPaqueteria;
    @FXML
    private TableColumn<Paquete, String> tcId;
    @FXML
    private TableColumn<Paquete, String> tcdescripcion;
    @FXML
    private TableColumn<Paquete, String> tcDestino;

    private final ObservableList<Paquete> data
            = FXCollections.observableArrayList(
                    new Paquete(123, "Paquete grande", "Sevilla"),
                    new Paquete(456, "Paquete pequeño", "Malaga"),
                    new Paquete(789, "Paquete mediano", "Alcala"),
                    new Paquete(159, "Paquete diminuto", "Sevilla"),
                    new Paquete(753, "Paquete enorme", "Cadiz")
            );

    private Reparto rep;
    @FXML
    private TableColumn<Paquete, Boolean> tcEntregado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcId.setCellValueFactory(
                new PropertyValueFactory<Paquete, String>("codigo"));
        tcdescripcion.setCellValueFactory(
                new PropertyValueFactory<Paquete, String>("descripcion"));
        tcDestino.setCellValueFactory(
                new PropertyValueFactory<Paquete, String>("destino"));
        tcEntregado.setCellValueFactory(
                new PropertyValueFactory<Paquete, Boolean>("entregado"));

        tbPaqueteria.setItems(data);
        tbPaqueteria.getSelectionModel().selectFirst();
    }

    @FXML
    private void aniadirPaqueteToReparto(MouseEvent event) {
        Paquete p = tbPaqueteria.getSelectionModel().getSelectedItem();
        System.out.println(p);

        //realizar actualizacion en reparto para añadir paquete
    }

}
