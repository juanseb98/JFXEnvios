/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxenvios;

import Objetos.Camion;
import Objetos.Camionero;
import Objetos.Paquete;
import Objetos.Reparto;
import ajustesHibernate.HibernateUtil;
import dao.GenericDAO;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author DAM-2
 */
public class PaquetesController implements Initializable {

    private static Session session;
    private static GenericDAO genericDAO = new GenericDAO<>();

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
        configurarSesion();
        data = FXCollections.observableArrayList();
        cargarComboBox();
        cargarDeDB();

    }

    @FXML
    private void aniadirPaqueteToReparto(MouseEvent event) {
        Paquete p = tbPaqueteria.getSelectionModel().getSelectedItem();
        Date fecha = new Date();
        String fech = new SimpleDateFormat("yyyy-MM-dd").format(fecha);
        Query query = session.createQuery("SELECT r FROM Reparto r WHERE r.camionero = "
                + "(SELECT c FROM Camionero c WHERE logueado= 1) AND r.fecha='" + fech + "'");
        List<Reparto> repartos = query.list();
        if (repartos.size() != 1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Hoy no has seleccionado transporte");
            alert.setContentText("Antes de escoger los paquetes a repartir debes \nseleccionar que transporte quieres usar");
            alert.showAndWait();
        } else {
            Reparto reparto = repartos.get(0);
            reparto.aniadirPaquete(p);
            genericDAO.guardar(reparto);
            //TODO realizar insert realizar actualizacion en reparto para añadir paquete

            tbPaqueteria.setItems(data);
            tbPaqueteria.getSelectionModel().selectFirst();
        }

    }

    private void rellenarDatos(String datos) {
        Query query;
        List<Paquete> paquetes;
        switch (datos) {
            case "Todos":
                query = session.createQuery("SELECT p FROM Paquete p");
                data = FXCollections.observableArrayList();

                paquetes = query.list();
                for (Paquete paquete : paquetes) {
                    data.add(paquete);
                }

                cargarTablaConPaquetes();
                break;
            case "No entregados":
                query = session.createQuery("SELECT p FROM Paquete p WHERE entregado = 0");
                data = FXCollections.observableArrayList();

                paquetes = query.list();
                for (Paquete paquete : paquetes) {
                    data.add(paquete);
                }
                cargarTablaConPaquetes();

                break;
            case "Entregados":
                query = session.createQuery("SELECT p FROM Paquete p WHERE entregado = 1");
                data = FXCollections.observableArrayList();

                paquetes = query.list();
                for (Paquete paquete : paquetes) {
                    data.add(paquete);
                }
                cargarTablaConPaquetes();
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

    private static void configurarSesion() {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSessionAndBindToThread();
        session = HibernateUtil.getSessionFactory().getCurrentSession();

    }

    private void cargarDeDB() {
        //TODO plantear obtener paquetes que no esten en reparto
        Query query = session.createQuery("SELECT p FROM Paquete p WHERE entregado = 0");
        data = FXCollections.observableArrayList();

        List<Paquete> paquetes = query.list();
        for (Paquete paquete : paquetes) {
            data.add(paquete);
        }

        cargarTablaConPaquetes();
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

}
