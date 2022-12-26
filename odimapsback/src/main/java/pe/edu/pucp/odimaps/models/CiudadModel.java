/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.models;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author USUARIO
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "Ciudad")
public class CiudadModel {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    
    @Column(unique = true, nullable = false)
    private String ubigeo = "";
    
    @Column(nullable = false)
    private double  latitud = 0;
    
    @Column(nullable = false)
    private double  longitud = 0;
    
    @Column(nullable = false)
    private String nombre = "";
    
    @Column(nullable = false)
    private String region = "";
    
    @Column(nullable = false)
    private int numCamiones = 0;
    
    private String[] inicios = new String[6];
    private String[] destinos = new String[6];
    private double[] velocidades = new double[6];
    private double espera = 1.0;

    
    public CiudadModel(String nombre, double latitud, double longitud, String ubigeo, String region, int indice){
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ubigeo = ubigeo;
        this.region = region;
        inicios[0] = "COSTA";
        inicios[1] = "COSTA";
        inicios[2] = "SIERRA";
        inicios[3] = "SIERRA";
        inicios[4] = "SELVA";
        inicios[5] = "COSTA";
        destinos[0] = "COSTA";
        destinos[1] = "SIERRA";
        destinos[2] = "SIERRA";
        destinos[3] = "SELVA";
        destinos[4] = "SELVA";
        destinos[5] = "SELVA";
        velocidades[0] = 70.0;
        velocidades[1] = 50.0;
        velocidades[2] = 60.0;
        velocidades[3] = 55.0;
        velocidades[4] = 65.0;
        velocidades[5] = 60.0;
        this.id = indice;
    }
    public String toString() {return this.ubigeo;}
    
    /**
     * @return the indice
     */
    public int getId() {
        return id;
    }

    /**
     * @param indice the indice to set
     */
    public void setId(int indice) {
        this.id = indice;
    }



    /**
     * @return the ubigeo
     */
    public String getUbigeo() {
        return ubigeo;
    }

    /**
     * @param ubigeo the ubigeo to set
     */
    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    /**
     * @return the latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the numCamiones
     */
    public int getNumCamiones() {
        return numCamiones;
    }

    /**
     * @param numCamiones the numCamiones to set
     */
    public void setNumCamiones(int numCamiones) {
        this.numCamiones = numCamiones;
    }

    public double medirHoras(CiudadModel ciudad, ArrayList<TramoModel> tramos, Date hoy, ArrayList<BloqueoModel> bloqueos){
        boolean conex = false;
        for(int i = 0; i <tramos.size(); i++)
            if(tramos.get(i).getOrigen().getId()==this.id){
                if(tramos.get(i).getDestino().getId()==ciudad.getId()){
                    conex = true;
                    for(int j = 0; j < bloqueos.size(); j++){
                        if(bloqueos.get(j).getTramo().getId() == tramos.get(i).getId()){
                            if(bloqueos.get(j).getFechaInicio().before(hoy) && bloqueos.get(j).getFechaFin().after(hoy)){
                                conex = false;
                                break;
                            }
                        }
                    }
                    break;
                }
                
            }
            else if(tramos.get(i).getDestino().getId()==this.id){
                if(tramos.get(i).getOrigen().getId()==ciudad.getId()){
                    conex = true;
                    for(int j = 0; j < bloqueos.size(); j++){
                        if(bloqueos.get(j).getTramo().getId() == tramos.get(i).getId()){
                            if(bloqueos.get(j).getFechaInicio().before(hoy) && bloqueos.get(j).getFechaFin().after(hoy)){
                                conex = false;
                                break;
                            }
                        }
                    }
                    break;
                }
                
            }
        if (conex){
            double radioTierra = 6371;//en kilómetros
            double deltaLat = Math.toRadians(ciudad.getLatitud() - this.getLatitud());
            double deltaLong = Math.toRadians(ciudad.getLongitud() - this.getLongitud());
            double sindLat = Math.sin(deltaLat / 2);
            double sindLng = Math.sin(deltaLong / 2);
            double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(this.getLatitud())) * Math.cos(Math.toRadians(ciudad.getLatitud()));
            double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
            double a = radioTierra * va2;
            double horas;
            for(int i = 0; i<5; i++){
                if((this.inicios[i].contains(this.region) && this.destinos[i].contains(ciudad.region)) 
                    || (this.inicios[i].contains(ciudad.region) && this.destinos[i].contains(this.region))){
                    horas = a/this.velocidades[i] + espera;
                    //if((this.ubigeo.contains("150101")))System.out.println("Distancia LIMA-"+ciudad.nombre+":"+horas+" "+ this.region +" - "+ ciudad.region);
                    return horas;
                }
            }
            return a;
        }
        return 99999999;
    }
}
