/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxenvios;

import Objetos.Paquete;
import Objetos.Reparto;
import ajustesHibernate.HibernateUtil;
import dao.GenericDAO;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;
import org.hibernate.Query;
import org.hibernate.Session;
import reportes.AbrirReportes;

/**
 * Controlador encargado de realizar todas las consultas y acciones de la
 * ventana de de Paqueteria en la aplicacion
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
public class VerListadoCompletoController implements Initializable {

    private static Session session;
    private static GenericDAO genericDAO = new GenericDAO<>();
    private List<Paquete> paquetes;

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
    @FXML
    private Button btReporte;

    /**
     * Metodo encargado de realizar los siguientes pasos al inicializar
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSesion();
        data = FXCollections.observableArrayList();
        prepararRadioButton();
        cargarDeDB();

    }

    /**
     * MEtodo encargado de cargar los paquetes en memoria dependiendo del combo
     * box seleccionado
     *
     * @param datos Parametro de filtro paquetes a mostrar
     */
    private void rellenarDatos(String datos) {
        Query query;

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

    /**
     * Metodo que se encarga de configurar el funcion de los radio buttons
     */
    private void prepararRadioButton() {
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

    /*
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
     */
    /**
     * Configuracion de la coneccion con la base de datos
     */
    private static void configurarSesion() {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSessionAndBindToThread();
        session = HibernateUtil.getSessionFactory().getCurrentSession();

    }

    /**
     * Metodo encargado de cargar los datos de los paquetes de la base de datos
     * que no pertenecen a ningun reparto
     */
    private void cargarDeDB() {
        Query query = session.createQuery("SELECT p FROM Paquete p");
        data = FXCollections.observableArrayList();

        paquetes = query.list();
        for (Paquete paquete : paquetes) {
            data.add(paquete);
        }

        cargarTablaConPaquetes();
    }

    /**
     * Metodo encargado de rellenar los datos obtenidos de la base de datos y
     * mostrarlos en la tabla
     */
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

        //TODO futura implementacion
        //setDobleClickFila();
    }

    @FXML
    private void reporte(MouseEvent event) {
        AbrirReportes abrir = new AbrirReportes();
        try {
            abrir.abrirReporte(paquetes);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

}
