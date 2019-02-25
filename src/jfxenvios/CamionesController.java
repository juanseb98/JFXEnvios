package jfxenvios;

import Objetos.Camion;
import Objetos.Camionero;
import Objetos.Reparto;
import ajustesHibernate.HibernateUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.GenericDAO;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.validation.ConstraintViolationException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Controlador encargado de realizar todas las consultas y acciones de la
 * ventana de camiones en la aplicacion
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
public class CamionesController implements Initializable {

    private static Session session;
    private static GenericDAO genericDAO = new GenericDAO<>();
    private static FXMLDocumentController ctrPrincipal = new FXMLDocumentController();

    @FXML
    private Button btSeleccionar;

    @FXML
    private TableView<Camion> tablaCamiones = new TableView<Camion>();

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
    @FXML
    private Button btEliminar;

    /**
     * Metodo encargado de realizar los siguientes pasos al inicializar
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSesion();
        cargarDeDB();

    }

    /**
     * Metodo que se realiza al pulsar el boton de seleccionar el cual se
     * encarga de insertar en la tabla reparto los datos correspondiente al
     * camion y al camionero que se han deleccionado
     *
     * @param event
     * @throws MySQLIntegrityConstraintViolationException
     */
    @FXML
    private void seleccionarCamion(MouseEvent event) throws MySQLIntegrityConstraintViolationException {
        Boolean trabajando = false;

        Camionero cami = (Camionero) session.createQuery("SELECT c FROM Camionero c WHERE logueado= 1").uniqueResult();
        Date fecha = new Date();
        String fech = new SimpleDateFormat("yyyy-MM-dd").format(fecha);

        //Obtenemos una lista de camioneros que estan insertados en la tabla reparto con fecha de hoy para comprobar que no ha seleccionado ya camion hoy
        Query query = session.createQuery("SELECT c FROM Camionero c WHERE c IN(SELECT r.camionero FROM Reparto r WHERE r.fecha='" + fech + "')");
        List<Camionero> camioneros = query.list();
        for (Camionero camionero : camioneros) {
            if (camionero.getDni().equals(cami.getDni())) {
                trabajando = true;
            }
        }

        if (!trabajando) {
            Camion cam = tablaCamiones.getSelectionModel().getSelectedItem();

            Reparto rep = new Reparto(cam, cami, fecha);
            try {
                genericDAO.guardarActualizar(rep);
            } catch (ConstraintViolationException e) {
                //no saltara
            }

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Trabajando");
            alert.setHeaderText("Camion " + cam.getMatricula() + " seleccionado");
            alert.setContentText("Vaya a Paquetes para escoger paquetes a repartir");

            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ya se le ha asignado un camion hoy");
            alert.showAndWait();
        }
        cargarDeDB();
    }

    /**
     * Metodo encargado de abrir una nueva ventana en la que se solicitara los
     * datos necesarios para insertar un nuevo camion en la base de datos
     *
     * @param event
     */
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

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(CamionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cargarDeDB();
    }

    /**
     * Configuracion de la coneccion con la base de datos
     */
    private static void configurarSesion() {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSessionAndBindToThread();
        session = HibernateUtil.getSessionFactory().getCurrentSession();

    }

    /**
     * Metodo encargado de cargar los datos de los camiones de la base de datos
     * en la tabla
     */
    private void cargarDeDB() {
        //select * from camion where matricula not in (select matriculaCamion from reparto);
        Date fecha = new Date();
        String fech = new SimpleDateFormat("yyyy-MM-dd").format(fecha);
        //Seleccionamos los camiones que no se encuentren en la lista de reparto en la fecha de hoy
        Query query = session.createQuery("SELECT c FROM Camion c WHERE c NOT IN(SELECT r.camion FROM Reparto r WHERE r.fecha='" + fech + "')");
        data = FXCollections.observableArrayList();

        List<Camion> camiones = query.list();
        for (Camion camion : camiones) {
            data.add(camion);
        }

        cargartabla();
    }

    /**
     * Metodo encargado de rellenar los datos obtenidos de la base de datos y
     * mostrarlos en la tabla
     */
    private void cargartabla() {
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

    /**
     * Metodo para cargar el controlador de la pantalla principal para utilizar
     * metodos necesarios
     *
     * @param aThis
     */
    void setCtrPrincipal(FXMLDocumentController aThis) {
        this.ctrPrincipal = aThis;
    }

    /**
     * Metodo que se ejecuta al dar click en el boton eliminar que se encarga de
     * eliminar un camion de la base de datos y refrescar la tabla
     *
     * @param event
     */
    @FXML
    private void eliminar(MouseEvent event) {
        Camion cam = tablaCamiones.getSelectionModel().getSelectedItem();
        try {
            genericDAO.borrar(cam);
        } catch (Exception ex) {
            Logger.getLogger(CamionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cargarDeDB();
    }

}
