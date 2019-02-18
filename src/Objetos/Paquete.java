package Objetos;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

/**
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
@Entity
@Table(name = "paquete")
public class Paquete implements Serializable {

    @Id
    @Column(name = "codigo")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codigo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "destino")
    private String destino;

    @Column(name = "entregado")
    @Type(type = "boolean")
    private Boolean entregado;

    @ManyToOne
    @JoinColumn(name = "id_reparto")
    private Reparto reparto;

    public Paquete(String descripcion, String destino, Boolean entregado) {
        this.descripcion = descripcion;
        this.destino = destino;
        this.entregado = entregado;
        this.reparto = null;
    }

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
