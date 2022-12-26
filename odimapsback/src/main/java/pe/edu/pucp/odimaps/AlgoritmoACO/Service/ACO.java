/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.AlgoritmoACO.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;
import pe.edu.pucp.odimaps.AlgoritmoACO.FloydWarshall.Encapsulado;
import pe.edu.pucp.odimaps.AlgoritmoACO.util.AtomicDouble;
import pe.edu.pucp.odimaps.models.CiudadModel;

/**
 *
 * @author USUARIO
 */
public class ACO {
    
    private AtomicDouble[][] matrizNivelFeromonas = null;
    private Encapsulado[][] matrizHoras = null;
    private ArrayList<CiudadModel> ciudades;
    private int tamCiudades;
    
    public void inicializarNivelesFeromonas() {
       matrizNivelFeromonas = new AtomicDouble[tamCiudades][tamCiudades];
       Random random = new Random();
       IntStream.range(0, tamCiudades).forEach(x -> {
           IntStream.range(0, tamCiudades).forEach(y -> matrizNivelFeromonas[x][y] = new AtomicDouble(random.nextDouble()));
       });
    }
    
    public ACO(ArrayList<CiudadModel> ciudades, Encapsulado[][] matrizHoras) throws IOException {
        this.ciudades = ciudades;
        this.tamCiudades = this.ciudades.size();
        this.matrizHoras = matrizHoras;
        //inicializarHoras();
        inicializarNivelesFeromonas();
    }
    public AtomicDouble[][] getMatrizNivelFeromonas() {return matrizNivelFeromonas;}
    public Encapsulado[][] getMatrizHoras() {return matrizHoras;}
    
}
