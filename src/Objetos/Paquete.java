package Objetos;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

/**
 * Objeto RPaquete
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
@Entity
@Table(name = "paquete")
public class Paquete implements Serializable {

    @Id
    @Column(name = "codigo")
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codigo;

    @Column(name = "descripcion")
    @Max(40)
    private String descripcion;

    @Column(name = "destino")
    @NotNull
    private String destino;

    @Column(name = "entregado")
    @Type(type = "boolean")
    @AssertFalse
    private Boolean entregado;

    @ManyToOne
    @JoinColumn(name = "id_reparto")
    @Valid
    private Reparto reparto;

    /**
     * Contructor generico del objeto
     *
     * @param descripcion Cadena de texto con la descripcion del paquete
     * @param destino Cadena de texto con el destino
     * @param entregado
     */
    public Paquete(String descripcion, String destino, Boolean entregado) {
        this.descripcion = descripcion;
        this.destino = destino;
        this.entregado = entregado;
        this.reparto = null;
    }

    /**
     * Constructor vacio
     */
    public Paquete() {
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDestino() {
        return destino;
    }

    public Reparto getReparto() {
        return reparto;
    }

    public boolean isEntregado() {
        return this.entregado;
    }

    public void setEntregado(Boolean b) {
        this.entregado = b;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setReparto(Reparto reparto) {
        this.reparto = reparto;
    }

    @Override
    public String toString() {
        return "Paquete{" + "codigo=" + codigo + ", descripcion=" + descripcion + ", destino=" + destino + ", entregado=" + entregado + '}';
    }

}
