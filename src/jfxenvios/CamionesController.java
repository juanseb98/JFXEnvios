/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxenvios;

import Objetos.Camion;
import Objetos.Camionero;
import Objetos.Reparto;
import Objetos.TipoCamion;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DAM-2
 */
public class CamionesController implements Initializable {

    @FXML
    private Button btSeleccionar;

    @FXML
    private TableView<Camion> tablaCamiones = new TableView<Camion>();

    //TODO Realizar consulta de camiones que no esten dentro de la tabla reparto
    private ObservableList<Camion> data;
    @FXML
    private TableColumn<Camion, String> tcMatricula;
    @FXML
    private TableColumn<Camion, String> tcModelo;
    @FXML
    private TableColumn<Camion, String> tcTipo;
    @FXML
    private TableColumn<Camion, String> tcPotencia;

    private double x, y;
    @FXML
    private Button btInsertar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList(
                new Camion("123hjk", "Mercedes", 20.5, TipoCamion.Diesel),
                new Camion("798asd", "BMW", 15.5, TipoCamion.Gasolina),
                new Camion("156dsa", "Renault", 30.0, TipoCamion.Diesel),
                new Camion("156njk", "Mercedes", 60.5, TipoCamion.Gasolina),
                new Camion("365phb", "Seat", 26.3, TipoCamion.Diesel)
        );

        tcMatricula.setCellValueFactory(
                new PropertyValueFactory<Camion, String>("matricula"));
        tcModelo.setCellValueFactory(
                new PropertyValueFactory<Camion, String>("Modelo"));
        tcTipo.setCellValueFactory(
                new PropertyValueFactory<Camion, String>("tipo"));
        tcPotencia.setCellValueFactory(
                new PropertyValueFactory<Camion, String>("potencia"));

        tablaCamiones.setItems(data);
        tablaCamiones.getSelectionModel().selectFirst();
    }

    @FXML
    private void seleccionarCamion(MouseEvent event) {
        Camion cam = tablaCamiones.getSelectionModel().getSelectedItem();
        Date fecha = new Date();
        Camionero cami = new Camionero();
        Reparto rep = new Reparto(cam, cami, fecha);
        // TODO Realizar insert en la tabla Reparto con un camionero y cam
        System.out.println(cam.toString());
    }

    //Futuro actualizar tabla
    public void insertarCamion(Camion c) {
        data.add(c);
        tablaCamiones.setItems(data);
        tablaCamiones.getSelectionModel().selectFirst();

        tablaCamiones.setItems(data);
        tablaCamiones.getSelectionModel().selectFirst();
    }

    @FXML
    private void insertarNuevoCamion(MouseEvent event) {

        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InsertarCamion.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);

            InsertarCamionController camionctr = fxmlLoader.getController();
            camionctr.setCamionesController(this);
            camionctr.setStage(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(CamionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //TODO obtener camiones de nuevo
    }

}
