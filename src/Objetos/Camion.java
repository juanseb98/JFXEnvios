package Objetos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
@Entity
@Table(name = "camion")
public class Camion {

    @Id
    @Column(name = "matricula")
    private String matricula;

    @Column(name = "modelo")
    private String Modelo;

    @Column(name = "potencia")
    private double potencia;

    @Enumerated(EnumType.STRING)
    private TipoCamion tipo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "matriculaCamion")
    //@IndexColumn(name = "idx1")
    private List<Reparto> repartos;

    public Camion(String matricula, String Modelo, double potencia, TipoCamion tipo) {
        this.matricula = matricula;
        this.Modelo = Modelo;
        this.potencia = potencia;
        this.tipo = tipo;
        repartos = new ArrayList<Reparto>();
    }

    public Camion() {
        super();
    }

    //Getters
    public String getMatricula() {
        return matricula;
    }

    public String getModelo() {
        return Modelo;
    }

    public double getPotencia() {
        return potencia;
    }

    public TipoCamion getTipo() {
        return tipo;
    }

    //Setters
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setModelo(String Modelo) {
        this.Modelo = Modelo;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    public void setTipo(TipoCamion tipo) {
        this.tipo = tipo;
    }

    public Reparto mostrarRepartos() {
        return repartos.get(0);
    }

    @Override
    public String toString() {
        return "Camion{" + "matricula=" + matricula + ", Modelo=" + Modelo + ", potencia=" + potencia + ", tipo=" + tipo + '}';
    }

}
