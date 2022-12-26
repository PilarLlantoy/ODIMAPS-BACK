package pe.edu.pucp.odimaps.models;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author USUARIO
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "Vehiculo")
public class VehiculoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    
    @Column(nullable = false)
    private String codigo = "";

    @Column(nullable = false)
    private int estado = 0;

    @Column(nullable = false)
    private String tipo = "";

    @Column(nullable = false)
    private int capacidad = 0;    
    
    private ArrayList<CargaModel> entregas = new ArrayList<CargaModel>();
    private int cargaAcumulada=0;
    
    public ArrayList<CargaModel> getEntregas() {return entregas;}
    
    public void agregarCarga(CargaModel carga){
        this.cargaAcumulada+=carga.getNumPaquetes();
        this.entregas.add(carga);
    }
    public int getCargaAcumulada() {return cargaAcumulada;}

    public VehiculoModel(String cod, int cap, String type){
        this.codigo = cod;
        this.capacidad = cap;
        this.tipo = type;
    }

    public VehiculoModel(String cod, int cap, char type){
        this.codigo = cod;
        this.capacidad = cap;
        this.tipo = Character.toString(type);
    }


    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
}
