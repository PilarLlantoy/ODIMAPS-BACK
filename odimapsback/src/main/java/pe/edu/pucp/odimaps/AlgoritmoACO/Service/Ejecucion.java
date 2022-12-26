/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.AlgoritmoACO.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;

import pe.edu.pucp.odimaps.AlgoritmoACO.FloydWarshall.Encapsulado;
import pe.edu.pucp.odimaps.models.CargaModel;
import pe.edu.pucp.odimaps.models.CiudadModel;
import pe.edu.pucp.odimaps.models.ClienteModel;
import pe.edu.pucp.odimaps.models.PedidoModel;
import pe.edu.pucp.odimaps.models.VehiculoModel;
import pe.edu.pucp.odimaps.services.ClienteService;
/**
 *
 * @author USUARIO
 */
public class Ejecucion {
    @Autowired
    ClienteService clienteService;
    private int maxContador = 40;
    
    private final int NRO_HORMIGAS = 2000;
    private int indiceOrigenDeCiudad;                                 //#####################################################################
    private ArrayList<CiudadModel> rutaInicial;
    private ArrayList<Integer> rutaEntregas;
    private ArrayList<Date> maxEntregas;
    
    private ArrayList<Integer> ciudadesEntregas;
    
    private ExecutorService executorService;
    private ExecutorCompletionService<Hormiga> executorCompletionService;
    private Ruta rutaMasCorta = null;
    private Ruta retornoRuta = null;
    private int activeAnts = 0;
    public Ruta ejecutar(ArrayList<Date> mE, ArrayList<Integer> rE, int iODC,
            ArrayList<CiudadModel> rutaOriginal, Encapsulado[][] matrizHoras, Date inicio) throws IOException{
        
        indiceOrigenDeCiudad=iODC;
        rutaEntregas = rE;
        maxEntregas=mE;
        //rutaInicial = leerRutas();
        rutaInicial = rutaOriginal;
        ciudadesEntregas = new ArrayList<Integer>(rutaInicial.size());
        obtenerEntregas();
        
        //System.out.println("> "+NRO_HORMIGAS + " Hormigas artificales...");
        ACO aco = new ACO(rutaInicial,matrizHoras);
        //Entregar paquetes
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executorCompletionService = new ExecutorCompletionService<Hormiga>(executorService);
        int contador=0;
        while(rutaMasCorta==null){
            //System.out.println("Iniciar iteracion busqueda");
            IntStream.range(1, NRO_HORMIGAS).forEach(x -> {            
                executorCompletionService.submit(new Hormiga(aco, x, indiceOrigenDeCiudad,
                                                rutaInicial, rutaEntregas, ciudadesEntregas, inicio));
                activeAnts++;
                rutaMasCorta=procesarHormigas(rutaMasCorta);
            });
            contador++;
            if(contador==maxContador) break;
        }
        executorService.shutdownNow();
        if(rutaMasCorta==null) return null;
        return rutaMasCorta.obtenerRutaCompleta(matrizHoras);
    }


    public void leerPedidos(ArrayList<PedidoModel> pedidos, ArrayList<CiudadModel> todasLasCiudades,String archventas){
        //Leer archivo
        File ventas = new File(archventas);
        try{
            String buffer = "";
            String[] times = null;
            String[] tokens = null;
            String[] hhmm = null;
            String[] places = null;
            Scanner obVen = new Scanner(ventas);
            int cont = 0;
            while(obVen.hasNextLine()){
                buffer = obVen.nextLine();
                tokens = buffer.split(", ");
                //tengo bloque de dd hh:mm tokens[0]
                //tengo bloque de origen => destino tokens[1]
                //tengo bloque de cantidad tokens[2]
                //tengo bloque de cliente tokens[3]
                
                times = tokens[0].split(" "); 
                hhmm = times[1].split(":");
                
                int dd = Integer.parseInt(times[0]); 
                int hh = Integer.parseInt(hhmm[0]);
                int mm = Integer.parseInt(hhmm[1]);
                
                places = tokens[1].split(" =>  "); 
                
                String ubigeoOrigen = places[0];
                String ubigeoDestino = places[1];
                
                tokens[2]=tokens[2].replace(" ", "");
                int cantidad = Integer.parseInt(tokens[2]);
                String cliente = tokens[3];
                
                CiudadModel ciudadOrigen=buscarCiudad(ubigeoOrigen, todasLasCiudades); //me falta buscar la ciudad
                CiudadModel ciudadDestino=buscarCiudad(ubigeoDestino, todasLasCiudades); //me falta buscar la ciudad
                //System.out.println(ciudadOrigen.getUbigeo());
                ClienteModel client = clienteService.obtenerClientePorId(Integer.parseInt(cliente));
                PedidoModel ped = new PedidoModel(dd, hh, mm, ciudadOrigen, ciudadDestino, cantidad, client);
                ped.setId(cont);
                pedidos.add(ped);
                
                
                cont++;
            }
            
        }catch(FileNotFoundException ff){System.out.println("Exception " + ff.toString());exit(1);}
        
        
        //------------------------------------------------------------
        
    }
    
    private Ruta procesarHormigas(Ruta rutaMasCorta){
        while(activeAnts > 0) {
            try {
                Hormiga hormiga = executorCompletionService.take().get();
                Ruta rutaActual = hormiga.getRuta();
                if (rutaMasCorta == null || rutaActual.getHoras() < rutaMasCorta.getHoras()){
                    //System.out.println(rutaActual.getHoras());
                    int cumplir = rutaEntregas.size();
                    for(int i = 0; i<rutaEntregas.size();i++){
                        for(int j = 0; j<rutaActual.getCiudades().size();j++){
                            if(rutaActual.getCiudades().get(j).getId() == rutaEntregas.get(i).intValue()){
                                if(maxEntregas.get(i).getTime()>rutaActual.getLlegada().get(j).getTime()){
                                    //System.out.println(rutaActual.getCiudades().get(j).getNombre()+" "+rutaActual.getCiudades().get(j).getIndice());
                                    cumplir--;
                                }
                                //cumplir--;
                            }
                        }
                    }
                    //System.out.println(cumplir);
                    if(cumplir==0){
                        //System.out.println("##################################");
                        rutaMasCorta = rutaActual;
                    }
                }
            } catch (Exception e) { e.printStackTrace();}
            activeAnts--;
        }
        return rutaMasCorta;

    }
    private void obtenerEntregas(){
        for(int l = 0; l<rutaInicial.size(); l++){
            if(rutaEntregas.contains(rutaInicial.get(l).getId())){
                ciudadesEntregas.add(2); //{Códgido numerico, 0:inicial 2:entrega}
            } else ciudadesEntregas.add(0);
        }
        
    }
    
    private CiudadModel buscarCiudad (String ubigeo, ArrayList<CiudadModel> todasLasCiudades){
        for(int i=0; i<todasLasCiudades.size(); i++){
            //System.out.println(todasLasCiudades.get(i).getUbigeo()+"-"+ ubigeo);
            if(todasLasCiudades.get(i).getUbigeo().equals(ubigeo)){
                //System.out.println("MATCH");
                return todasLasCiudades.get(i);
            }
        }
        return null;
    }
    
    void asignacionPedidosxCamion(ArrayList<Almacen> almacenes){ 
        
        for(int numAlmacen=0; numAlmacen<almacenes.size(); numAlmacen++){ // se analizara por cada almacen 
            int numPedido=0;
            for(int numCamion=0; numCamion<almacenes.get(numAlmacen).getCamiones().size(); numCamion++){ //se analizará cada camion A del almacen 
                while(numPedido < almacenes.get(numAlmacen).getPedidos().size()){ //se identificara cada pedido para ver si encaja en el camion A del almacen 
                    if(!quedaEspacioEnCamion(almacenes.get(numAlmacen), numCamion)) almacenes.get(numAlmacen).getCamiones().get(numCamion).setEstado(1);
                    if(almacenes.get(numAlmacen).getCamiones().get(numCamion).getEstado() == 0 && almacenes.get(numAlmacen).getCamiones().get(numCamion).getEntregas().size()<=15){ 
                        if(sePuedeCompleto(almacenes.get(numAlmacen), numCamion, numPedido))
                            asignarCompleto(almacenes.get(numAlmacen), numPedido,  numCamion); 
                        else{
                            asignarParcial(almacenes.get(numAlmacen), numPedido,  numCamion);
                            continue;
                        }
                    }
                    else{
                        break;
                    }
                    numPedido++;
                }
            }
        }
    }
    
    
    boolean quedaEspacioEnCamion(Almacen almacen, int iCamion){
        //la capacidad del camion > carga acumulada (es decir que si fuera igual ya no deberia entrar porque se llenó
        if(almacen.getCamiones().get(iCamion).getCapacidad() 
                > almacen.getCamiones().get(iCamion).getCargaAcumulada())
            return true;
        return false;
    }
    
    boolean sePuedeCompleto(Almacen almacen, int iCamion, int iPedido){
        //cantidadPedido <= capacidadCamion-cantidadEnPedido
        if(almacen.getPedidos().get(iPedido).getCantidadSinAsignar()
                <= 
           ( almacen.getCamiones().get(iCamion).getCapacidad() - almacen.getCamiones().get(iCamion).getCargaAcumulada() )
        )
            return true;
        else
            return false;
    }
    
    void asignarCompleto(Almacen almacen, int iPedido, int iCamion){ 
        String nombreCarga="P"+iPedido+"C"+almacen.getCamiones().get(iCamion).getId();
        CargaModel nuevaCarga; 
        
        //asigno a la carga la cantidad del pedido completa y referencio al pedido
        nuevaCarga = new CargaModel(almacen.getPedidos().get(iPedido).getCantidadSinAsignar(), almacen.getPedidos().get(iPedido), nombreCarga);
        
        //System.out.println("capa: "+almacen.getCamiones().get(iCamion).getCapacidad());
        //System.out.println("acum: "+almacen.getCamiones().get(iCamion).getCargaAcumulada());
        
        //actualizo el sinAsignar del pedido a 0
        almacen.getPedidos().get(iPedido).disminuirSinAsignar(almacen.getPedidos().get(iPedido).getCantidadSinAsignar());
        
        //actualizo la cantidad que lleva el camion y el arreglo de entregas
        almacen.getCamiones().get(iCamion).agregarCarga(nuevaCarga);
        
        //System.out.println(almacen.getPedidos().get(iPedido));
        //System.out.println(nuevaCarga);
    }
    
    void asignarParcial(Almacen almacen, int iPedido, int iCamion){ 
        String nombreCarga="P"+iPedido+"C"+almacen.getCamiones().get(iCamion).getId();
        CargaModel nuevaCarga; 
        
        int cantCarga = almacen.getCamiones().get(iCamion).getCapacidad()-
                        almacen.getCamiones().get(iCamion).getCargaAcumulada();
        
        //System.out.println("capa: "+almacen.getCamiones().get(iCamion).getCapacidad());
        //System.out.println("acum: "+almacen.getCamiones().get(iCamion).getCargaAcumulada());
        
        //asigno a la carga la cantidad del pedido completa y referencio al pedido
        nuevaCarga = new CargaModel(cantCarga, almacen.getPedidos().get(iPedido), nombreCarga);
        
        //actualizo el sinAsignar del pedido con la resta
        almacen.getPedidos().get(iPedido).disminuirSinAsignar(cantCarga);
        
        //actualizo la cantidad que lleva el camion y el arreglo de entregas
        almacen.getCamiones().get(iCamion).agregarCarga(nuevaCarga);
        
        //System.out.println(almacen.getPedidos().get(iPedido));
        //System.out.println(nuevaCarga);
    }
    
    
    ArrayList<Date> obtenerFechas(VehiculoModel camion){
        ArrayList<Date> fechas = new ArrayList<Date>();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for(int k=0; k<camion.getEntregas().size(); k++)
            if(!estaEnDestinos(indices, camion.getEntregas().get(k).getPedido().getDestino().getId())){
                indices.add(camion.getEntregas().get(k).getPedido().getDestino().getId()); 
                fechas.add(camion.getEntregas().get(k).getPedido().getFechaEntregaMax());
            }
            else{
                for(int i = 0; i <fechas.size(); i++){
                    if(camion.getEntregas().get(k).getPedido().getFechaEntregaMax().getTime() < fechas.get(i).getTime()){
                        fechas.set(i,camion.getEntregas().get(k).getPedido().getFechaEntregaMax());
                    }
                }
            }
            
        return fechas;  
    }
    
    ArrayList<Integer> obtenerDestinos(VehiculoModel camion){
        ArrayList<Integer> indices = new ArrayList<Integer>(); 
        for(int k=0; k<camion.getEntregas().size(); k++) 
            if(!estaEnDestinos(indices, camion.getEntregas().get(k).getPedido().getDestino().getId()))
                indices.add(camion.getEntregas().get(k).getPedido().getDestino().getId()); 
        return indices;  
    }
    
    boolean estaEnDestinos(ArrayList<Integer> indices, long indice){
        for(int i=0; i<indices.size(); i++)
            if(indices.get(i)==indice) return true;
        return false;
    }
    
}
