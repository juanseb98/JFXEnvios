/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxenvios;

import Objetos.Camionero;
import ajustesHibernate.HibernateUtil;
import dao.GenericDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author DAM-2
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSesion();
        comprobarLogueo();
    }

    @FXML
    private void loguear(MouseEvent event) {
        Camionero camionero = (Camionero) session.createQuery("SELECT c FROM Camionero c WHERE dni= '" + tfDni.getText() + "'").uniqueResult();
        if (camionero == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha encontrado camionero");
            alert.showAndWait();
        } else {
            if (camionero.getPass().equals(tfPsw.getText())) {
                camionero.setLogueado();

                genericDAO.guardar(camionero);

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

    @FXML
    private void desloguear(MouseEvent event) {
        Camionero cam = (Camionero) session.createQuery("SELECT c FROM Camionero c WHERE dni= '" + tfDni.getText() + "'").uniqueResult();
        cam.setDeslogueado();
        genericDAO.guardar(cam);

        tfDni.setEditable(true);
        tfPsw.setEditable(true);
        btLogin.setVisible(true);
        btDesloguear.setVisible(false);
        ctrPrincipal.activarLogin(false);

    }

    private static void configurarSesion() {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSessionAndBindToThread();
        session = HibernateUtil.getSessionFactory().getCurrentSession();

    }

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
