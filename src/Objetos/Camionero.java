package Objetos;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.*;

/**
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
@Entity
@Table(name = "camionero")
public class Camionero implements Serializable {

    @Id
    @Column(name = "dni")
    private String dni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "poblacion")
    private String poblacion;

    @Column(name = "telefono")
    private int tlfn;

    @Column(name = "salario")
    private Double salario;

    @OneToMany(mappedBy = "camionero", cascade = CascadeType.ALL)
    private ArrayList<Reparto> repartos;

    public Camionero(String dni, String nombre, String poblacion, int tlfn, Double salario) throws Exception {
        setDni(dni);
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.tlfn = tlfn;
        setSalario(salario);
        repartos = new ArrayList<Reparto>();
    }

    //Getters
    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public int getTlfn() {
        return tlfn;
    }

    public Double getSalario() {
        return salario;
    }

    //Setters
    public void setDni(String dni) throws Exception {
        if (dni.matches("\\d{8}\\D")) {
            this.dni = dni;
        } else {
            throw new Exception("Dni " + dni + " no ocrrecto");
        }
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public void setTlfn(int tlfn) {
        this.tlfn = tlfn;
    }

    public void setSalario(Double salario) throws Exception {
        if (salario > 0) {
            this.salario = salario;
        } else {
            throw new Exception("Salario no puede ser engativo");
        }
    }

}
