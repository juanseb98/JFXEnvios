
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
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.GenericDAO;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {

    private static Scanner teclado = new Scanner(System.in);
    private static Session session;
    private static GenericDAO genericDAO = new GenericDAO<>();

    public static void main(String[] args) {
        configurarSesion();
        try {
            Camionero cam1 = new Camionero("20503879T", "password", "Juan", "Sevilla", 697366754, 1400.0);
            Camionero cam2 = new Camionero("12345678A", "password", "Pedro", "Sevilla", 685965365, 1500.0);
            Camionero cam3 = new Camionero("78945612A", "password", "Antonio", "Malaga", 632587693, 1632.0);

            Camion cami1 = new Camion("1234jdk", "Mercedes", 25.0, TipoCamion.Diesel);
            Camion cami2 = new Camion("2356dse", "Cadilac", 30.0, TipoCamion.Gasolina);
            Camion cami3 = new Camion("1568red", "Toledo", 50.0, TipoCamion.Electrico);

            Paquete a = new Paquete("Paquete grande", "Sevilla", false);
            Paquete b = new Paquete("Paquete pequeño", "Malaga", false);
            Paquete c = new Paquete("Paquete mediano", "Alcala", false);
            Paquete d = new Paquete("Paquete diminuto", "Sevilla", false);
            Paquete e = new Paquete("Paquete enorme", "Cadiz", false);
            Paquete f = new Paquete("Spice", "Koralle", false);
            Paquete g = new Paquete("Artichokes", "Terri", false);
            Paquete h = new Paquete("Stock", "Johanna", false);
            Paquete i = new Paquete("Wine La Vielle", "Rafaelita", false);
            Paquete j = new Paquete("Parsley Italian", "Tabbie", false);
            Paquete k = new Paquete("Ginger", "Kirstin", false);
            Paquete l = new Paquete("Chips", "Brien", false);
            Paquete m = new Paquete("Salt And Pepper Mix", "Darsie", false);
            Paquete n = new Paquete("Lettuce", "Lorraine", false);
            Paquete o = new Paquete("Flour", "Bamby", false);
            Paquete p = new Paquete("Nantucket", "Barbabas", false);
            Paquete q = new Paquete("Easy Off Oven Cleaner", "Cordula", false);
            Paquete r = new Paquete("Bread", "Ludwig", false);
            Paquete s = new Paquete("Pops", "Dorrie", false);
            Paquete t = new Paquete("Kolrabi", "Casie", false);
            Paquete u = new Paquete("Apple", "Jilleen", false);
            Paquete v = new Paquete("Pear", "Horatia", false);
            Paquete x = new Paquete("Wine", "Rozanne", false);
            Paquete y = new Paquete("Soup", "Jenda", false);
            Paquete z = new Paquete("Coffee", "Barny", false);

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
                genericDAO.guardar(f);
                genericDAO.guardar(g);
                genericDAO.guardar(h);
                genericDAO.guardar(i);
                genericDAO.guardar(j);
                genericDAO.guardar(k);
                genericDAO.guardar(l);
                genericDAO.guardar(m);
                genericDAO.guardar(n);
                genericDAO.guardar(o);
                genericDAO.guardar(p);
                genericDAO.guardar(q);
                genericDAO.guardar(r);
                genericDAO.guardar(s);
                genericDAO.guardar(t);
                genericDAO.guardar(u);
                genericDAO.guardar(v);
                genericDAO.guardar(x);
                genericDAO.guardar(y);
                genericDAO.guardar(z);
                System.err.println("paquetes entregados");

                genericDAO.guardar(cami1);
                genericDAO.guardar(cami2);
                genericDAO.guardar(cami3);
                System.err.println("camion entregados");

                genericDAO.guardar(cam1);
                genericDAO.guardar(cam2);
                genericDAO.guardar(cam3);

                System.err.println("camionero entregados");

                genericDAO.guardar(rep);
                System.err.println("reparto entregados");

                System.out.println("todo guardado");

            } else if (opc == 2) {
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

            } else {

                genericDAO.guardar(cam3);
                System.err.println("camion entregados");
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
