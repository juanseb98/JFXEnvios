/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxenvios;

import Objetos.Paquete;
import ajustesHibernate.HibernateUtil;
import dao.GenericDAO;
import java.net.URL;
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
 * FXML Controller class
 *
 * @author sastian
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
     * Initializes the controller class.
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

    @FXML
    private void entregarPaquete(MouseEvent event) {
        Paquete p = tbPaqueteria.getSelectionModel().getSelectedItem();
        p.setEntregado(true);
        genericDAO.guardar(p);
        cargarDatosDeBD();
    }

    private void cargarDatosDeBD() {
        //select * from paquete p where p.id_reparto=(select id from reparto where dniCamionero = (Select dni from camionero where nombre ="juan")) and p.entregado = 0;
        Query query = session.createQuery("SELECT c FROM Paquete c");
        data = FXCollections.observableArrayList();

        List<Paquete> paquetes = query.list();
        for (Paquete paquete : paquetes) {
            data.add(paquete);
        }

        //TODO select * from paquete where entregado = 0;
        //select * from paquete p where p.id_reparto=(select id from reparto where dniCamionero = (Select dni from camionero where nombre ="juan")) and p.entregado = 0;
        cargarDatosTabla();
    }

    private void cargarDatosTabla() {
        tbPaqueteria.setItems(data);
        tbPaqueteria.getSelectionModel().selectFirst();
    }

    private static void configurarSesion() {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSessionAndBindToThread();
        session = HibernateUtil.getSessionFactory().getCurrentSession();

    }

}
