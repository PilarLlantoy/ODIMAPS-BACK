/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.AlgoritmoACO.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import pe.edu.pucp.odimaps.AlgoritmoACO.FloydWarshall.Encapsulado;
import pe.edu.pucp.odimaps.models.CiudadModel;

/**
 *
 * @author USUARIO
 */
public class Ruta {
    
    private ArrayList<CiudadModel> ciudades;
    private ArrayList<Date> llegada;
    private double distancia;
    private double horas;
    private long ultima;
    
    public Ruta(ArrayList<CiudadModel> ciudades, double horas, ArrayList<Date> llegada,
            long ultima){
        this.ciudades = ciudades;
        this.horas = horas;
        this.llegada = llegada;
        this.ultima = ultima;
    }
    public ArrayList<CiudadModel> getCiudades() {return ciudades;}
    public void addAllCiudades(ArrayList<CiudadModel> otra) {
        otra.remove(0);
        this.ciudades.addAll(otra);}
    public ArrayList<Date> getLlegada() {return llegada;}
    public void addAllLlegada(ArrayList<Date>otra) {
        otra.remove(0);
        this.llegada.addAll(otra);}
    public long getUltima() {return ultima;}
    public double getHoras() {return horas;}
    public void setHoras(double horas) {this.horas = horas;}
    public String toString(){ return Arrays.toString(ciudades.toArray()) + " | "+ horas;}

    Ruta obtenerRutaCompleta(Encapsulado[][] matriz) {
        ArrayList<CiudadModel> nuevo = new ArrayList<CiudadModel>();
        if(ciudades.size()>1){
            for(int i = 0; i<ciudades.size()-1;i++){
                nuevo.add(ciudades.get(i));
                nuevo.addAll(matriz[(int)ciudades.get(i).getId()][(int)ciudades.get(i+1).getId()].ciudades);
            }
            nuevo.add(ciudades.get(ciudades.size()-1));
            this.ciudades = nuevo;
        }
        return this;
    }
    
    
}
