package jfxenvios;

import Objetos.Camionero;
import ajustesHibernate.HibernateUtil;
import dao.GenericDAO;
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
import org.hibernate.Session;

/**
 * Controlador encargado de realizar todas las consultas y acciones de la
 * ventana de Principal en la aplicacion
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
public class FXMLDocumentController implements Initializable {

    private static Session session;
    private static GenericDAO genericDAO = new GenericDAO<>();

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button btTrabajando;
    @FXML
    private Button btCamion;
    @FXML
    private Button btPaquete;
    @FXML
    private Button btEntrega;

    /**
     * Metodo encargado de realizar los siguientes pasos al inicializar
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSesion();
        comprobarLogueado();
    }

    /**
     * Metodo encargado de cerrar la aplicacion
     *
     * @param event
     */
    @FXML
    private void Close(MouseEvent event) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

    }

    /**
     * Metodo que se realiza a la hora de dar al boton de logueo de la ventana
     * principal, que carga en el panel centro la ventana de logueo
     *
     * @param event
     */
    @FXML
    private void login(MouseEvent event) {
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            root = fxmlLoader.load();
            LoginController ctr = fxmlLoader.getController();
            ctr.setCtrPrincipal(this);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPane.setCenter(root);
    }

    /**
     * Metodo que se realiza a la hora de dar al boton de logueo de la ventana
     * principal, que carga en el panel centro la ventana de camiones
     *
     * @param event
     */
    @FXML
    private void Camiones(MouseEvent event) {
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Camiones.fxml"));
            root = fxmlLoader.load();
            CamionesController ctr = fxmlLoader.getController();
            ctr.setCtrPrincipal(this);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPane.setCenter(root);
    }

    /**
     * Metodo que se realiza a la hora de dar al boton de logueo de la ventana
     * principal, que carga en el panel centro la ventana de Paquetes
     *
     * @param event
     */
    @FXML
    private void Paquetes(MouseEvent event) {
        loadPantalla("Paquetes");

    }

    /**
     * Metodo que se realiza a la hora de dar al boton de logueo de la ventana
     * principal, que carga en el panel centro la ventana de Entrega
     *
     * @param event
     */
    @FXML
    private void Entrega(MouseEvent event) {
        loadPantalla("Entrega");
    }

    /**
     * Metodo que se realiza a la hora de dar al boton de logueo de la ventana
     * principal, que carga en el panel centro la ventana de paqueteria
     *
     * @param event
     */
    @FXML
    private void trabajadoresActuales(MouseEvent event) {
        loadPantalla("VerListadoCompleto");
    }

    /**
     * Metodo encargado de preparar y colocar la nueva pantalla en el centro del
     * panel principal
     *
     * @param pantalla nombre de la pantalla a cargar
     */
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

    /**
     * Metodo que en funcion de si el usuario esta logueado o no
     * activa/desactiva los botones correspondientes
     *
     * @param ac accion de activar(True) o desactivar(False)
     */
    public void activarLogin(Boolean ac) {
        btCamion.setVisible(ac);

        btEntrega.setVisible(ac);
        btPaquete.setVisible(ac);
        btTrabajando.setVisible(ac);
    }

    /*

    public void activarLogin(Boolean ac, Camionero ca) {
        btCamion.setVisible(ac);

        Date fecha = new Date();
        String fech = new SimpleDateFormat("yyyy-MM-dd").format(fecha);

        Query query = session.createQuery("SELECT c FROM Camionero c WHERE c IN(SELECT r.camionero FROM Reparto r WHERE r.fecha='" + fech + "')");
        List<Camionero> camioneros = query.list();
        for (Camionero camionero : camioneros) {
            if (camionero.getDni().equals(ca.getDni())) {
                btEntrega.setVisible(ac);
                btPaquete.setVisible(ac);
                btTrabajando.setVisible(ac);
            }
        }

    }

     */
    /**
     * Metodo que comprueba en la base de datos si hay algun camionero y en caso
     * negativa desactiva los botones
     */
    public void comprobarLogueado() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Camionero cam = (Camionero) session.createQuery("SELECT c FROM Camionero c WHERE logueado= 1").uniqueResult();
        if (cam == null) {
            activarLogin(false);
        }
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
