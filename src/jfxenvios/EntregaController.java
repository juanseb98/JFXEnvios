package jfxenvios;

import Objetos.Paquete;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Controlador encargado de realizar todas las consultas y acciones de la
 * ventana de Entrega en la aplicacion
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
public class EntregaController implements Initializable {

    private static Session session;
    private static GenericDAO genericDAO = new GenericDAO<>();

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
     * Metodo encargado de realizar los siguientes pasos al inicializar
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSesion();
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

    /**
     * Metodo encargado de actualizar el estado de entregado a true del paquete
     * seleccionado
     *
     * @param event
     */
    @FXML
    private void entregarPaquete(MouseEvent event) {
        Paquete p = tbPaqueteria.getSelectionModel().getSelectedItem();
        if (p != null) {
            p.setEntregado(true);
            genericDAO.guardarActualizar(p);
            cargarDatosDeBD();
        }

    }

    /**
     * Metodo encargado de cargar los datos de los paquetes de la base de datos
     * que pertenecen al reparto del camionero actual y que no esten entregados
     * en la tabla
     */
    private void cargarDatosDeBD() {
        Date fecha = new Date();
        String fech = new SimpleDateFormat("yyyy-MM-dd").format(fecha);
        Query query = session.createQuery("SELECT p FROM Paquete p WHERE p.reparto=(SELECT r FROM Reparto r WHERE r.camionero = (SELECT c FROM Camionero c WHERE c.logueado=1) AND r.fecha='" + fech + "') AND p.entregado=0");
        data = FXCollections.observableArrayList();

        List<Paquete> paquetes = query.list();
        for (Paquete paquete : paquetes) {
            data.add(paquete);
        }
        cargarDatosTabla();
    }

    /**
     * Metodo encargado de rellenar los datos obtenidos de la base de datos y
     * mostrarlos en la tabla
     */
    private void cargarDatosTabla() {
        tbPaqueteria.setItems(data);
        tbPaqueteria.getSelectionModel().selectFirst();
    }

    /**
     * Configuracion de la coneccion con la base de datos
     */
    private static void configurarSesion() {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSessionAndBindToThread();
        session = HibernateUtil.getSessionFactory().getCurrentSession();

    }

}
