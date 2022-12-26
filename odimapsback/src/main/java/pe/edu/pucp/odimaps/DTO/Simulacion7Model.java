/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.DTO;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.pucp.odimaps.models.BloqueoModel;
import pe.edu.pucp.odimaps.models.MantenimientoModel;
import pe.edu.pucp.odimaps.models.PedidoModel;

/**
 *
 * @author USUARIO
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "Simulacion7")
public class Simulacion7Model {
    private ArrayList<String> bloqueos;
    private ArrayList<String> mantenimientos;
    private ArrayList<String> pedidos;
    //private Date hoy;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    
    /**
     * @return the bloqueos
     */
    public ArrayList<String> getBloqueos() {
        return bloqueos;
    }

    /**
     * @param bloqueos the bloqueos to set
     */
    public void setBloqueos(ArrayList<String> bloqueos) {
        this.bloqueos = bloqueos;
    }

    /**
     * @return the mantenimientos
     */
    public ArrayList<String> getMantenimientos() {
        return mantenimientos;
    }

    /**
     * @param mantenimientos the mantenimientos to set
     */
    public void setMantenimientos(ArrayList<String> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    /**
     * @return the pedidos
     */
    public ArrayList<String> getPedidos() {
        return pedidos;
    }

    /**
     * @param pedidos the pedidos to set
     */
    public void setPedidos(ArrayList<String> pedidos) {
        this.pedidos = pedidos;
    }
}
