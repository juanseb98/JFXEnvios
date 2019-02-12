/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author sastian
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSesion();
        comprobarLogueado();
    }

    @FXML
    private void Close(MouseEvent event) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

    }

    //Futuro boton de loguear y desloguear
    @FXML
    private void Clear(MouseEvent event) {
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

    @FXML
    private void Camiones(MouseEvent event) {
        loadPantalla("Camiones");
    }

    @FXML
    private void Paquetes(MouseEvent event) {
        loadPantalla("Paquetes");

    }

    @FXML
    private void Entrega(MouseEvent event) {
        loadPantalla("Entrega");
    }

    @FXML
    private void trabajadoresActuales(MouseEvent event) {
        loadPantalla("VerListadoCompleto");
    }

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

    public void activar(Boolean ac) {
        btCamion.setVisible(ac);
        btEntrega.setVisible(ac);
        btPaquete.setVisible(ac);
        btTrabajando.setVisible(ac);
    }

    public void comprobarLogueado() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Camionero cam = (Camionero) session.createQuery("SELECT c FROM Camionero c WHERE logueado= 1").uniqueResult();
        if (cam == null) {
            activar(false);
        }
    }

    private static void configurarSesion() {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSessionAndBindToThread();
        session = HibernateUtil.getSessionFactory().getCurrentSession();

    }

}
