/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.AlgoritmoACO.Service;

import java.util.ArrayList;

import pe.edu.pucp.odimaps.AlgoritmoACO.FloydWarshall.Encapsulado;
import pe.edu.pucp.odimaps.models.CiudadModel;
import pe.edu.pucp.odimaps.models.VehiculoModel;
import pe.edu.pucp.odimaps.models.PedidoModel;

/**
 *
 * @author LENOVO
 */
public class Almacen {
    //private ArrayList<Camion> camionesA = new ArrayList<Camion>();
    //private ArrayList<Camion> camionesB = new ArrayList<Camion>();
    //private ArrayList<Camion> camionesC = new ArrayList<Camion>();
    
    private ArrayList<VehiculoModel> camiones = new ArrayList<VehiculoModel>();
    private ArrayList<PedidoModel> pedidos = new ArrayList<PedidoModel>();
    private CiudadModel ciudad;
    
    //private double  ejeX;
    //private double  ejeY;
    
    /*
    public Almacen(double  ejeX, double  ejeY){
        this.ejeX = ejeX;
        this.ejeY = ejeY;
    }*/
    
    public Almacen(CiudadModel ciudad){
        this.ciudad=ciudad;
    }
    
    /*
    public void agregarCamion(Camion camion, char tipo){
        if(tipo=='A')
            this.camionesA.add(camion);
        if(tipo=='B')
            this.camionesB.add(camion);
        if(tipo=='C')
            this.camionesC.add(camion);
    }*/
    
    public void agregarCamion(VehiculoModel camion){
        this.camiones.add(camion); 
    }
    
    public void agregarPedido(PedidoModel pedido){
        this.pedidos.add(pedido);
    }
    
    public double getEjeX() {return this.ciudad.getLatitud();}
    public double getEjeY() {return this.ciudad.getLongitud();}
    public ArrayList<PedidoModel> getPedidos() {return this.pedidos;}
    
    //public ArrayList<Camion> getCamionesA() {return this.camionesA;}
    //public ArrayList<Camion> getCamionesB() {return this.camionesB;}
    //public ArrayList<Camion> getCamionesC() {return this.camionesC;}
    public ArrayList<VehiculoModel> getCamiones() {return this.camiones;}
    
    /*
    public void imprimirCamionesA(){
        for(int i=0; i<this.camionesA.size(); i++){
            System.out.println(this.camionesA.get(i));
        }
    }
    public void imprimirCamionesB(){
        for(int i=0; i<this.camionesB.size(); i++){
            System.out.println(this.camionesB.get(i));
        }
    }
    public void imprimirCamionesC(){
        for(int i=0; i<this.camionesC.size(); i++){
            System.out.println(this.camionesC.get(i));
        }
    }*/
    
    public void imprimirCamiones(){
        for(int i=0; i<this.camiones.size(); i++){
            System.out.println(this.camiones.get(i)+" "+this.camiones.get(i).getTipo());
        }
    }
    
    public CiudadModel getCiudad(){
        return this.ciudad;
    }

    public double medirDistancia(CiudadModel destino){
        return 10.0;
    }
}
