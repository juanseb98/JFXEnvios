
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

public class Main {

    private static Scanner teclado = new Scanner(System.in);
    private static Session session;
    private static GenericDAO genericDAO = new GenericDAO<>();

    public static void main(String[] args) {
        configurarSesion();
        try {
            Camionero cam = new Camionero("20503879T", "password", "Juan", "Sevilla", 697366754, 1400.0);
            Camion cami = new Camion("123jdk", "Mercedes", 25.0, TipoCamion.Diesel);

            Paquete a = new Paquete(123, "Paquete grande", "Sevilla", false);
            Paquete b = new Paquete(456, "Paquete pequeño", "Malaga", false);
            Paquete c = new Paquete(789, "Paquete mediano", "Alcala", false);
            Paquete d = new Paquete(159, "Paquete diminuto", "Sevilla", false);
            Paquete e = new Paquete(753, "Paquete enorme", "Cadiz", false);

            Date fecha = new Date();
            Reparto rep = new Reparto(cami, cam, fecha);

            System.out.println("Que hacer?");
            int opc = teclado.nextInt();
            if (opc == 1) {

                genericDAO.guardar(a);
                genericDAO.guardar(b);
                genericDAO.guardar(c);
                genericDAO.guardar(d);
                genericDAO.guardar(e);

                genericDAO.guardar(cami);
                genericDAO.guardar(cam);
                genericDAO.guardar(rep);

            } else {
                Query query = session.createQuery("SELECT c FROM Reparto c");
                List<Reparto> paquetes = query.list();
                for (Reparto paquete : paquetes) {
                    System.out.println(paquete);
                }
            }

            System.out.println(" se guardo reparto");
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
