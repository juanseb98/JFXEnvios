package Objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Objeto que representa la relacion N:M de la BD
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
@Entity
@Table(name = "reparto")
public class Reparto implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int identificador;

    @ManyToOne
    @JoinColumn(name = "matriculaCamion")
    @Valid
    private Camion camion;

    @ManyToOne
    @JoinColumn(name = "dniCamionero")
    @Valid
    private Camionero camionero;

    @Column(name = "fecha")
    @NotNull
    private Date fecha;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_reparto")
    private List<Paquete> listaPaquetes;

    /**
     * Contructor generico de reparto
     *
     * @param camion Camion que realiza el reparto
     * @param camionero Camioneron que realiza el reparto
     * @param fecha Fecha en la que se realiza dicho partido
     */
    public Reparto(Camion camion, Camionero camionero, Date fecha) {
        this.camion = camion;
        this.camionero = camionero;
        this.fecha = fecha;
        listaPaquetes = new ArrayList<Paquete>();
    }

    /**
     * Constructor vacio
     */
    public Reparto() {
        super();
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

    public List<Paquete> getListaPaquetes() {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reparto{" + "identificador=" + identificador + ", camion=" + camion + ", camionero=" + camionero + ", fecha=" + fecha + "} paquetes: ");
        for (Paquete paq : listaPaquetes) {
            sb.append(paq);
        }
        return sb.toString();
    }

}
