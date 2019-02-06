package Objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
@Entity
@Table(name = "reparto")
public class Reparto implements Serializable {

    @Id
    @Column(name = "id")
    private int identificador;

    @ManyToOne
    @JoinColumn(name = "matriculaCamion")
    private Camion camion;

    @ManyToOne
    @JoinColumn(name = "dniCamionero")
    private Camionero camionero;

    @Column(name = "fecha")
    private Date fecha;

    @OneToMany(mappedBy = "reparto", cascade = CascadeType.ALL)
    private ArrayList<Paquete> listaPaquetes;

    public Reparto(int identificador, Camion camion, Camionero camionero, Date fecha) {
        this.identificador = identificador;
        this.camion = camion;
        this.camionero = camionero;
        this.fecha = fecha;
        listaPaquetes = new ArrayList<Paquete>();
    }

    //Getters
    public int getIdentificador() {
        return identificador;
    }

    public Camion getCamion() {
        return camion;
    }

    public Camionero getCamionero() {
        return camionero;
    }

    public Date getFecha() {
        return fecha;
    }

    public ArrayList<Paquete> getListaPaquetes() {
        return listaPaquetes;
    }

    //Setters
    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public void setCamionero(Camionero camionero) {
        this.camionero = camionero;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void aniadirPaquete(Paquete p) {
        listaPaquetes.add(p);
    }

}
