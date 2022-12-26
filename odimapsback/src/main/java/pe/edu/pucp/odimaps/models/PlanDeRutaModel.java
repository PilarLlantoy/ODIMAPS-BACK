/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.models;

import java.io.File;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PlanDeRuta")
public class PlanDeRutaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    
    @Column
    private File file;
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private PedidoModel pedido;
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private PlanDeTransporteModel planDeTransporte;



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
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the pedido
     */
    public PedidoModel getPedido() {
        return pedido;
    }

    /**
     * @param pedido the pedido to set
     */
    public void setPedido(PedidoModel pedido) {
        this.pedido = pedido;
    }

    /**
     * @return the planDeTransporte
     */
    public PlanDeTransporteModel getPlanDeTransporte() {
        return planDeTransporte;
    }

    /**
     * @param planDeTransporte the planDeTransporte to set
     */
    public void setPlanDeTransporte(PlanDeTransporteModel planDeTransporte) {
        this.planDeTransporte = planDeTransporte;
    }
}
