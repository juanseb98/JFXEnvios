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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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
    @FXML
    private TableColumn<Paquete, Boolean> tcEntregado;

    private ObservableList<Paquete> data;

    private Reparto rep;

    @FXML
    private RadioButton rbTodos;
    @FXML
    private RadioButton rbNoEntregados;
    @FXML
    private RadioButton rbEntregados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        data = FXCollections.observableArrayList(
                new Paquete(123, "Paquete grande", "Sevilla", false),
                new Paquete(456, "Paquete pequeño", "Malaga", false),
                new Paquete(789, "Paquete mediano", "Alcala", false),
                new Paquete(159, "Paquete diminuto", "Sevilla", false),
                new Paquete(753, "Paquete enorme", "Cadiz", false)
        );

        cargarComboBox();

        cargarTablaConPaquetes();

    }

    @FXML
    private void aniadirPaqueteToReparto(MouseEvent event) {
        Paquete p = tbPaqueteria.getSelectionModel().getSelectedItem();
        System.out.println(p);

        //realizar actualizacion en reparto para añadir paquete
        data.remove(p);
        tbPaqueteria.setItems(data);
        tbPaqueteria.getSelectionModel().selectFirst();
    }

    private void rellenarDatos(String datos) {
        switch (datos) {
            case "Todos":
                //TODO consultar tos los paquetes
                System.out.println("todos");
                break;
            case "No entregados":
                //TODO consultar tos los paquetes where entregado FALSE
                System.out.println("No entregado");
                break;
            case "Entregados":
                //TODO consultar tos los paquetes where entregado TRUE
                System.out.println("Entregados");
                break;
        }
    }

    private void cargarComboBox() {
        ToggleGroup group = new ToggleGroup();
        rbEntregados.setToggleGroup(group);
        rbNoEntregados.setToggleGroup(group);
        rbTodos.setToggleGroup(group);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (group.getSelectedToggle() != null) {
                    RadioButton sel = (RadioButton) group.getSelectedToggle();
                    rellenarDatos(sel.getText());

                }
            }
        });
    }

    private void cargarTablaConPaquetes() {
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

        //futura implementacion
        //setDobleClickFila();
    }

    private void setDobleClickFila() {
        tbPaqueteria.setRowFactory(tv -> {
            TableRow<Paquete> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Paquete rowData = row.getItem();
                    //TODO Mostrar mas informacion de paquete en otra ventana
                    System.out.println("Paquete seleccionado: " + rowData.getDescripcion());
                }
            });
            return row;
        });
    }

}
