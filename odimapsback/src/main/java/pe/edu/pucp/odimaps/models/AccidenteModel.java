package pe.edu.pucp.odimaps.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Accidente")
public class AccidenteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    
    @Column(nullable = false)
    private Date fechaAccidente;

    @Column(nullable = false)
    private int tipo = 0;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private TramoModel tramo;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private VehiculoModel vehiculo;


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
     * @return the fechaAccidente
     */
    public Date getFechaAccidente() {
        return fechaAccidente;
    }

    /**
     * @param fechaAccidente the fechaAccidente to set
     */
    public void setFechaAccidente(Date fechaAccidente) {
        this.fechaAccidente = fechaAccidente;
    }

    /**
     * @return the codigo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the carretera
     */
    public TramoModel getTramo() {
        return tramo;
    }

    /**
     * @param carretera the carretera to set
     */
    public void setTramo(TramoModel tramo) {
        this.tramo = tramo;
    }

    /**
     * @return the vehiculo
     */
    public VehiculoModel getVehiculo() {
        return vehiculo;
    }

    /**
     * @param vehiculo the vehiculo to set
     */
    public void setVehiculo(VehiculoModel vehiculo) {
        this.vehiculo = vehiculo;
    }
}
