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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.hibernate.Session;

/**
 * Controlador encargado de realizar todas las consultas y acciones de la
 * ventana de de Login en la aplicacion
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
public class LoginController implements Initializable {

	private static Session session;
	private static GenericDAO genericDAO = new GenericDAO<>();
	FXMLDocumentController ctrPrincipal;

	@FXML
	private Button btLogin;
	@FXML
	private TextField tfDni;
	@FXML
	private TextField tfPsw;
	@FXML
	private Button btDesloguear;

	/**
	 * Metodo encargado de realizar los siguientes pasos al inicializar
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		configurarSesion();
		comprobarLogueo();
	}

	/**
	 * Metodo que se activa al pulsar el boton de loguear que se encarga de
	 * comprobar si los datos del camionero introducido son correctos en el caso de
	 * ser correctos se procede a loguear al camionero (almacenando en la BD que
	 * esta logueado) y activando los botones.
	 *
	 * En caso de no ser correctos se mostrara la informacion correspondiente
	 *
	 * @param event
	 */
	@FXML
	private void registrarCamionero(MouseEvent event) {
		try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegistrarCamionero.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            RegistrarCamioneroController camioneroController = fxmlLoader.getController();
            camioneroController.setStage(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(CamionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	@FXML
	private void loguear(MouseEvent event) {
		Camionero camionero = (Camionero) session
				.createQuery("SELECT c FROM Camionero c WHERE dni= '" + tfDni.getText() + "'").uniqueResult();
		if (camionero == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No se ha encontrado camionero");
			alert.showAndWait();
		} else {
			if (camionero.getPass().equals(tfPsw.getText())) {
				camionero.setLogueado();

				genericDAO.guardarActualizar(camionero);

				tfDni.setEditable(false);
				tfPsw.setEditable(false);
				btLogin.setVisible(false);
				btDesloguear.setVisible(true);
				ctrPrincipal.activarLogin(true);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("La contraseña no parece correcta");
				alert.showAndWait();
			}
		}

	}

	/**
	 * Metodo que se realizara al pulsar el boton de desloguear que se encargara de
	 * actualizar el estado de logueo del camionero en la BD y de desactivar los
	 * botones
	 *
	 * @param event
	 */
	@FXML
	private void desloguear(MouseEvent event) {
		Camionero cam = (Camionero) session
				.createQuery("SELECT c FROM Camionero c WHERE dni= '" + tfDni.getText() + "'").uniqueResult();
		cam.setDeslogueado();
		genericDAO.guardarActualizar(cam);

		tfDni.setEditable(true);
		tfPsw.setEditable(true);
		btLogin.setVisible(true);
		btDesloguear.setVisible(false);
		ctrPrincipal.activarLogin(false);

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
	 * Metodo que comprueba en la base de datos si hay algun camionero y en caso
	 * negativa desactiva los botones
	 */
	private void comprobarLogueo() {
		Camionero cam = (Camionero) session.createQuery("SELECT c FROM Camionero c WHERE logueado= 1").uniqueResult();
		if (cam != null) {
			tfDni.setText(cam.getDni());
			tfPsw.setText(cam.getPass());

			tfDni.setEditable(false);
			tfPsw.setEditable(false);
			btLogin.setVisible(false);
		} else {
			btDesloguear.setVisible(false);
		}
	}

	void setCtrPrincipal(FXMLDocumentController aThis) {
		ctrPrincipal = aThis;
	}

}
