package Objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Juan Sebastian Gonzalez Sanchez
 */
@Entity
@Table(name = "camionero")
public class Camionero implements Serializable {

    @Id
    @Column(name = "dni")
    @NotNull
    private String dni;

    @Column(name = "nombre")
    @NotBlank
    @Size(min = 3, max = 17)
    private String nombre;

    @Column(name = "pwd")
    @NotNull
    @Size(min = 5, max = 25)
    private String pass;

    @Column(name = "poblacion")
    @NotNull
    private String poblacion;

    @Column(name = "telefono", unique = true)
    @NotNull
    private int tlfn;

    @Column(name = "salario")
    @NotNull
    private Double salario;

    @Column(name = "logueado")
    @Type(type = "boolean")
    @NotNull
    private Boolean logueado;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dniCamionero")
    //@IndexColumn(name = "idx2")
    private List<Reparto> repartos;

    public Camionero(String dni, String pwd, String nombre, String poblacion, int tlfn, Double salario) throws Exception {
        setDni(dni);
        this.pass = pwd;
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.tlfn = tlfn;
        this.logueado = false;
        setSalario(salario);
        repartos = new ArrayList<Reparto>();
    }

    public Camionero() {
        super();
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

    public String getPass() {
        return pass;
    }

    public Boolean getLogueado() {
        return logueado;
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

    public void setLogueado() {
        this.logueado = true;
    }

    public void setDeslogueado() {
        this.logueado = false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.tlfn;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Camionero other = (Camionero) obj;
        if (this.tlfn != other.tlfn) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Camionero{" + "dni=" + dni + ", nombre=" + nombre + ", pass=" + pass + ", poblacion=" + poblacion + ", tlfn=" + tlfn + ", salario=" + salario + '}';
    }

}
