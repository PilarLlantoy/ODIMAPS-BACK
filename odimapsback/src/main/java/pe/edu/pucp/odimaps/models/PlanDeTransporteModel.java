package pe.edu.pucp.odimaps.models;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "PlanDeTransporte")
public class PlanDeTransporteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column
    private File planTransporte = null;

    @Column
    private Date fechaPartida;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private CiudadModel almacen;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private VehiculoModel vehiculo;

    @ManyToMany
    @JoinTable(
      name = "Carga_PlanDeTransporte",
      joinColumns = @JoinColumn(name = "planDeTransporte", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "carga", referencedColumnName = "id")
    )
    private List<CargaModel> cargas;


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
     * @return the planTransporte
     */
    public File getPlanTransporte() {
        return planTransporte;
    }

    /**
     * @param planTransporte the planTransporte to set
     */
    public void setPlanTransporte(File planTransporte) {
        this.planTransporte = planTransporte;
    }

    /**
     * @return the fechaPartida
     */
    public Date getFechaPartida() {
        return fechaPartida;
    }

    /**
     * @param fechaPartida the fechaPartida to set
     */
    public void setFechaPartida(Date fechaPartida) {
        this.fechaPartida = fechaPartida;
    }

    /**
     * @return the almacen
     */
    public CiudadModel getAlmacen() {
        return almacen;
    }

    /**
     * @param almacen the almacen to set
     */
    public void setAlmacen(CiudadModel almacen) {
        this.almacen = almacen;
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

    /**
     * @return the cargas
     */
    public List<CargaModel> getCargas() {
        return cargas;
    }

    /**
     * @param cargas the cargas to set
     */
    public void setCargas(List<CargaModel> cargas) {
        this.cargas = cargas;
    }

    
}
