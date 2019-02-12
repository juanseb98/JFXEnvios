
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;

import Objetos.Camion;
import Objetos.Camionero;
import Objetos.Paquete;
import Objetos.Reparto;
import Objetos.TipoCamion;
import ajustesHibernate.HibernateUtil;
import dao.GenericDAO;
import java.text.SimpleDateFormat;

public class Main {

    private static Scanner teclado = new Scanner(System.in);
    private static Session session;
    private static GenericDAO genericDAO = new GenericDAO<>();

    public static void main(String[] args) {
        configurarSesion();
        try {
            Camionero cam1 = new Camionero("20503879T", "password", "Juan", "Sevilla", 697366754, 1400.0);
            Camionero cam2 = new Camionero("12345678A", "password", "Pedro", "Sevilla", 685965365, 1500.0);

            Camion cami1 = new Camion("123jdk", "Mercedes", 25.0, TipoCamion.Diesel);
            Camion cami2 = new Camion("235dse", "Cadilac", 30.0, TipoCamion.Gasolina);
            Camion cami3 = new Camion("156red", "Toledo", 50.0, TipoCamion.Electrico);

            Paquete a = new Paquete(123, "Paquete grande", "Sevilla", false);
            Paquete b = new Paquete(456, "Paquete pequeño", "Malaga", false);
            Paquete c = new Paquete(789, "Paquete mediano", "Alcala", false);
            Paquete d = new Paquete(159, "Paquete diminuto", "Sevilla", false);
            Paquete e = new Paquete(753, "Paquete enorme", "Cadiz", false);

            Date fecha1 = new Date();
            Reparto rep = new Reparto(cami1, cam1, fecha1);

            System.out.println("Que hacer?");
            int opc = teclado.nextInt();
            if (opc == 1) {

                genericDAO.guardar(a);
                genericDAO.guardar(b);
                genericDAO.guardar(c);
                genericDAO.guardar(d);
                genericDAO.guardar(e);
                System.err.println("paquetes entregados");

                genericDAO.guardar(cami1);
                genericDAO.guardar(cami2);
                genericDAO.guardar(cami3);
                System.err.println("camion entregados");

                genericDAO.guardar(cam1);
                genericDAO.guardar(cam2);
                System.err.println("camionero entregados");

                genericDAO.guardar(rep);
                System.err.println("reparto entregados");

                System.out.println("todo guardado");

            } else {
                Camionero cami = (Camionero) session.createQuery("SELECT c FROM Camionero c WHERE logueado= 1").uniqueResult();
                System.out.println("###################################");
                System.out.println(cami);
                System.out.println("###################################");

                Date fecha = new Date();
                System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(fecha));
                String fech = new SimpleDateFormat("yyyy-MM-dd").format(fecha);

                Query query = session.createQuery("SELECT c FROM Camionero c WHERE c NOT IN(SELECT r.camionero FROM Reparto r WHERE r.fecha='" + fech + "')");
                List<Camionero> camioneros = query.list();
                for (Camionero camionero : camioneros) {
                    System.out.println(camionero.getDni());
                }

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void cerrarSesion() {
        HibernateUtil.closeSessionFactory();
    }

    private static void configurarSesion() {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSessionAndBindToThread();
        session = HibernateUtil.getSessionFactory().getCurrentSession();

    }
}
