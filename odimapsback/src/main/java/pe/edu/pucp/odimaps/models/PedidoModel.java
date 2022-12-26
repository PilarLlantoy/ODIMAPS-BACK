/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.pucp.odimaps.services.ClienteService;

/**
 *
 * @author USUARIO
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "Pedido")
public class PedidoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    
    @Column(unique = true, nullable = false)
    private String codigo = "";
    
    @Column(nullable = false)
    private Date fechaRegistro;
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private CiudadModel destino;
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private CiudadModel almacen;
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private ClienteModel cliente;
    
    @Column(nullable = false)
    private int numPaquetes = 0;
    
    @Column
    private int estado = 0;
    
    @Column
    private Date fechaEntregaMax;
    
    @Column
    private Date fechaEntrega;
    
    @Column(nullable = false)
    private int tipo = 0; //0 = Directo, 1 = parcial
    
    public PedidoModel(int dia, int hora, int min, CiudadModel origen, CiudadModel destino, int cant,ClienteModel client){
        this.almacen = origen;
        this.destino = destino;
        this.numPaquetes = cant;
        this.cliente = client;
    }
    
    private int sinAsignar;
    public void setSinAsignar(int v) {this.sinAsignar=v;}
    public int getCantidadSinAsignar() {return sinAsignar;}
    public void disminuirSinAsignar(int cantidad){
        //System.out.println("sin: "+sinAsignar);
        //System.out.println("cant: "+cantidad);
        this.sinAsignar-=cantidad;
        
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
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the destino
     */
    public CiudadModel getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(CiudadModel destino) {
        this.destino = destino;
    }

    /**
     * @return the numPaquetes
     */
    public int getNumPaquetes() {
        return numPaquetes;
    }

    /**
     * @param numPaquetes the numPaquetes to set
     */
    public void setNumPaquetes(int numPaquetes) {
        this.numPaquetes = numPaquetes;
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
     * @return the fechaEntregaMax
     */
    public Date getFechaEntregaMax() {
        return fechaEntregaMax;
    }

    /**
     * @param fechaEntregaMax the fechaEntregaMax to set
     */
    public void setFechaEntregaMax(Date fechaEntregaMax) {
        this.fechaEntregaMax = fechaEntregaMax;
    }

    /**
     * @return the fechaEntrega
     */
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * @param fechaEntrega the fechaEntrega to set
     */
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
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
     * @return the cliente
     */
    public ClienteModel getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }
    
    
}
