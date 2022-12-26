/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.AlgoritmoACO.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import pe.edu.pucp.odimaps.models.CiudadModel;

/**
 *
 * @author USUARIO
 */
public class Hormiga implements Callable<Hormiga>{
    
    //public static final double c = 1.0;
    public static final double alpha = 0.8;
    public static final double beta = 12;
    public static final double evaporation = 0.7;
    public static final double Q = 500;
    //public static final double antFactor = 0.8;
    //public static final double randomFactor = 0.01;
    
    private ACO aco;
    private int nroHormiga;
    private double espera=1.0; 
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    private int indiceOrigenDeCiudad; //Acá se carga el (indice) almacen
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    private Ruta ruta = null;
    static int indiceInvalidoDeCiudad = -1;
    
    private ArrayList<CiudadModel> rutaInicialCiudades;
    private ArrayList<Integer> rutaEntregas;
    private ArrayList<Integer> ciudadesEntrega;
    static int nroDeCiudades;
    static int nroEntregas;
    
    private Date inicio;
    
    public Hormiga(ACO aco, int nroHormiga, int indiceOrigenDeCiudad,
            ArrayList<CiudadModel> rutaInicialCiudades, ArrayList<Integer> rutaEntregas,
            ArrayList<Integer> ciudadesEntrega, Date inicio){
        this.indiceOrigenDeCiudad = indiceOrigenDeCiudad;
        this.rutaInicialCiudades = rutaInicialCiudades;
        this.rutaEntregas = rutaEntregas;
        this.ciudadesEntrega = ciudadesEntrega;
        this.nroDeCiudades = rutaInicialCiudades.size();
        this.nroEntregas = rutaEntregas.size();
        this.aco = aco;
        this.nroHormiga = nroHormiga;
        this.inicio = inicio;
    }
    
    private int getY(int x, HashMap<String, Boolean> ciudadesVisitadas){
        int returnY = indiceInvalidoDeCiudad;
        double random = ThreadLocalRandom.current().nextDouble();
        ArrayList<Double> probabilidadDeTransicion = getProbabilidadDeTransicion(x, ciudadesVisitadas);
        for (int y = 0; y < nroDeCiudades; y++){
            if( probabilidadDeTransicion.get(y) > random){
                returnY = y;
                break;
            }
            else random -= probabilidadDeTransicion.get(y);
        }
        
        return returnY;
    }
    private void ajustarNivelDeFeromonas(int x, int y, double distancia){
        boolean flag = false;
        while(!flag){
            double nivelDeFeromonaActual = aco.getMatrizNivelFeromonas()[x][y].doubleValue();
            double nivelDeFeromonaActualizado = (1-evaporation)*nivelDeFeromonaActual + Q/distancia;
            if (nivelDeFeromonaActualizado < 0.00) flag = aco.getMatrizNivelFeromonas()[x][y].compareAndSet(0.5);
            else flag = aco.getMatrizNivelFeromonas()[x][y].compareAndSet(nivelDeFeromonaActualizado);
              
        }
    }
    private ArrayList<Double> getProbabilidadDeTransicion(int x, HashMap<String, Boolean> ciudadesVisitadas){
        ArrayList<Double> probabilidadDeTransicion = new ArrayList<Double>(nroDeCiudades);
        for( int i = 0; i < nroDeCiudades; i++){
            probabilidadDeTransicion.add(0.0);
        }
        double denominador = getDenominadorPT(probabilidadDeTransicion, x, ciudadesVisitadas);
        for( int y = 0; y < nroDeCiudades; y++){
            probabilidadDeTransicion.set(y, probabilidadDeTransicion.get(y)/denominador);
        }
        
        return probabilidadDeTransicion;
    }
    private double getDenominadorPT(ArrayList<Double> probabilidadDeTransicion, int x, HashMap<String, Boolean> ciudadesVisitadas){
        double denominador = 0.0;
        for (int y = 0; y < nroDeCiudades; y++){
            if(!ciudadesVisitadas.get(rutaInicialCiudades.get(y).getNombre()) && esEntrega(y)){
                if(x==y) probabilidadDeTransicion.set(y, 0.0);
                else probabilidadDeTransicion.set(y, getNumeradorPT(x,y));
                denominador += probabilidadDeTransicion.get(y);
            }
        }
        
        return denominador;
    }
    private double getNumeradorPT(int x, int y){
        double numerador = 0.0;
        double nivelDeFeromona = aco.getMatrizNivelFeromonas()[x][y].doubleValue();
        if(nivelDeFeromona != 0.0) numerador = Math.pow(nivelDeFeromona, alpha) * Math.pow(1/aco.getMatrizHoras()[x][y].horas, beta);
        
        return numerador;
    }
    
    
    public Ruta getRuta() {return ruta;}
    
    @Override
    public Hormiga call() throws Exception{
        ArrayList<CiudadModel> rutaCiudades = new ArrayList<CiudadModel>(nroDeCiudades);
        ArrayList<Date> llegadas = new ArrayList<Date>(nroDeCiudades);
        
        HashMap<String, Boolean> ciudadesVisitadas = new HashMap<String, Boolean>(nroDeCiudades);
        
        for(int i = 0; i < nroDeCiudades; i++) {
            ciudadesVisitadas.put(rutaInicialCiudades.get(i).getNombre(), false);
        }
        //Adaptación
        //-----------------------------------------------
        int nroCiudadesVisitadas = 0;
        //Adaptación
        int nroCiudadesEntregas = 0;
        ciudadesVisitadas.put(rutaInicialCiudades.get(indiceOrigenDeCiudad).getNombre(), true);
        rutaCiudades.add(nroCiudadesVisitadas++, rutaInicialCiudades.get(indiceOrigenDeCiudad));
        Date tiempo = new Date(inicio.getTime()+(long)(0*60*60*1000)); 
        llegadas.add(tiempo);// Primera llegada
        //Adaptación
        if(ciudadesEntrega.get(indiceOrigenDeCiudad) == 2){ //{Códgido numerico, 0:inicial 2:entrega}
            nroCiudadesEntregas++;
        }
        //----------------------------------------------
        //------------------------------------------------
        double horasRuta = 0.0;
        int x = indiceOrigenDeCiudad;
        int y = indiceInvalidoDeCiudad;
        int cont = 0;
        int ultima = -1;
        if (nroCiudadesVisitadas != nroDeCiudades && nroCiudadesEntregas != nroEntregas) y = getY(x, ciudadesVisitadas);
        while(y != indiceInvalidoDeCiudad){
            horasRuta += aco.getMatrizHoras()[x][y].horas;
            
            for(int i = 0; i < nroEntregas; i++){
                if(rutaInicialCiudades.get(y).getId() == rutaEntregas.get(i).intValue()){
                    //System.out.println(rutaEntregas);
                    ajustarNivelDeFeromonas(x, y, horasRuta);
                }
            }
            if(cont == 0)ajustarNivelDeFeromonas(x, y, horasRuta);
            cont = 0;
            
            ciudadesVisitadas.put(rutaInicialCiudades.get(y).getNombre(), true);
            //Adaptación
            if(ciudadesEntrega.get(y) == 2){ //{Códgido numerico, 0:inicial 2:entrega}
                nroCiudadesEntregas++;
            }
            //----------------------------------------------
            tiempo = new Date(tiempo.getTime()+(long)(aco.getMatrizHoras()[x][y].horas*60*60*1000)-(long)(espera*60*60*1000));
            llegadas.add(tiempo);
            tiempo = new Date(tiempo.getTime()+(long)(espera*60*60*1000));
            x = y;
            ultima = y;
            rutaCiudades.add(nroCiudadesVisitadas++, rutaInicialCiudades.get(x));
            // Acá se modificaria para validar que se hicieron las entregas
            if (nroCiudadesVisitadas != nroDeCiudades && nroCiudadesEntregas != nroEntregas ) y = getY(x,ciudadesVisitadas);
            //-----------------------------------------------------------------------------
            else{
                //System.out.println(nroCiudadesVisitadas+" "+nroCiudadesEntregas);
                y = indiceInvalidoDeCiudad;
            }
        }
        
        //distanciaRuta += aco.getMatrizDistancias()[x][indiceOrigenDeCiudad];
        if(horasRuta == 0){
            ruta = new Ruta(rutaCiudades, horasRuta, llegadas, ultima);
        }else ruta = new Ruta(rutaCiudades, horasRuta-espera, llegadas, ultima);
        //long end2 = System.currentTimeMillis();
        //System.out.println("Elapsed Time in milli seconds: "+ (end2-start2));
        return this;
    }
    
    public int getNroHormiga() {return this.nroHormiga;}

    private boolean esEntrega(int y) {
        for(int i = 0; i <rutaEntregas.size();i++)
            if(rutaEntregas.get(i)==y)return true;
        return false;
    }
}
