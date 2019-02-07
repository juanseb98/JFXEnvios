/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxenvios;

import Objetos.Paquete;
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
 * @author sastian
 */
public class EntregaController implements Initializable {

    @FXML
    private TableView<Paquete> tbPaqueteria;
    @FXML
    private TableColumn<Paquete, String> tcId;
    @FXML
    private TableColumn<Paquete, String> tcdescripcion;
    @FXML
    private TableColumn<Paquete, String> tcDestino;
    @FXML
    private TableColumn<Paquete, Boolean> tcEntregado;

    @FXML
    private Button btEntregar;

    private ObservableList<Paquete> data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosDeBD();

        tcId.setCellValueFactory(
                new PropertyValueFactory<Paquete, String>("codigo"));
        tcdescripcion.setCellValueFactory(
                new PropertyValueFactory<Paquete, String>("descripcion"));
        tcDestino.setCellValueFactory(
                new PropertyValueFactory<Paquete, String>("destino"));
        tcEntregado.setCellValueFactory(
                new PropertyValueFactory<Paquete, Boolean>("entregado"));

        cargarDatosTabla();
    }

    @FXML
    private void entregarPaquete(MouseEvent event) {
        Paquete p = tbPaqueteria.getSelectionModel().getSelectedItem();
        p.setEntregado(true);
        // TODO realizar actualizacion en paquete para marcar como entregado
        cargarDatosDeBD();
    }

    private void cargarDatosDeBD() {
        //TODO select * from paquete where entregado = 0;
        //data.add(p);
        data = FXCollections.observableArrayList(
                new Paquete(123, "Paquete grande", "Sevilla"),
                new Paquete(456, "Paquete pequeño", "Malaga"),
                new Paquete(789, "Paquete mediano", "Alcala"),
                new Paquete(159, "Paquete diminuto", "Sevilla"),
                new Paquete(753, "Paquete enorme", "Cadiz")
        );
        cargarDatosTabla();
    }

    private void cargarDatosTabla() {
        tbPaqueteria.setItems(data);
        tbPaqueteria.getSelectionModel().selectFirst();
    }

}
