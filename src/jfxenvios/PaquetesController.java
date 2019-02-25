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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Controlador encargado de realizar todas las consultas y acciones de la
 * ventana de de Paquetes en la aplicacion
 *
 * @author Juan Sebastian Gonzalez Sanchez
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

    /**
     * Metodo encargado de realizar los siguientes pasos al inicializar
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSesion();
        data = FXCollections.observableArrayList();
        cargarDeDB();

    }

    /**
     * Metodo que se realizara al pulsar el boton de añadir paquete a reparto se
     * encarga de asignarle un reparto al paquete seleccionado y lo almacena en
     * la BD
     *
     * @param event
     */
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
            genericDAO.guardarActualizar(reparto);

            cargarDeDB();
            tbPaqueteria.setItems(data);
            tbPaqueteria.getSelectionModel().selectFirst();
        }

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
        Query query = session.createQuery("SELECT p FROM Paquete p WHERE p.reparto is NULL");
        data = FXCollections.observableArrayList();

        List<Paquete> paquetes = query.list();
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

}
