/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.AlgoritmoACO.FloydWarshall;


import java.util.ArrayList;
import pe.edu.pucp.odimaps.models.CiudadModel;

/**
 *
 * @author USUARIO
 */
public class Encapsulado {
    public ArrayList<CiudadModel> ciudades = new ArrayList<CiudadModel>();
    public double horas=0.0;
    public void copiar(Encapsulado e){
        this.ciudades=e.ciudades;
        this.horas=e.horas;
    }

    double mas(Encapsulado e) {
        return this.horas+e.horas;
    }

    Encapsulado incluir(Encapsulado e, CiudadModel c) {
        Encapsulado en = new Encapsulado();
        en.horas=this.horas+e.horas;
        en.ciudades.addAll(this.ciudades);
        en.ciudades.add(c);
        en.ciudades.addAll(e.ciudades);
        return en;
    }
}
