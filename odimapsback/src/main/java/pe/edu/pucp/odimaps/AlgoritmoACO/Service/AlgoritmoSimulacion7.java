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
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import pe.edu.pucp.odimaps.AlgoritmoACO.FloydWarshall.Encapsulado;
import pe.edu.pucp.odimaps.AlgoritmoACO.FloydWarshall.FloyWarshall;
import pe.edu.pucp.odimaps.models.BloqueoModel;

import pe.edu.pucp.odimaps.services.ClienteService;
import pe.edu.pucp.odimaps.models.CiudadModel;
import pe.edu.pucp.odimaps.models.ClienteModel;
import pe.edu.pucp.odimaps.models.MantenimientoModel;
import pe.edu.pucp.odimaps.models.PedidoModel;
import pe.edu.pucp.odimaps.models.TramoModel;
import pe.edu.pucp.odimaps.models.VehiculoModel;

/**
 *
 * @author USUARIO
 */
public class AlgoritmoSimulacion7 {
    private ArrayList<Integer> rutaEntregas = new ArrayList<Integer>();
    private ArrayList<Date> maxEntregas= new ArrayList<Date>();
    private ArrayList<CiudadModel> rutaInicial;

    @Autowired
    ClienteService clienteService;
    
    public ArrayList<Ruta> ejecutar(Date inicio, ArrayList<PedidoModel> listaPedidos, ArrayList<BloqueoModel> listaBloqueos,ArrayList<MantenimientoModel> listaMantenimientos, ArrayList<CiudadModel> ciudades,ArrayList<TramoModel> tramos,ArrayList<Almacen> almacenes) throws IOException{
        this.rutaInicial = ciudades;
        ArrayList<Ruta> rutas = new ArrayList<>();
        //ASIGNACION DE PEDIDOS
        Ejecucion exe = new Ejecucion();
        ////System.out.println(pedidos);
        for(int i=0; i<listaPedidos.size(); i++) rutaEntregas.add((int)(listaPedidos.get(i).getDestino().getId()));
        for(int i=0; i<listaPedidos.size(); i++) maxEntregas.add((listaPedidos.get(i).getFechaEntregaMax()));
        ////System.out.println(rutaEntregas);

        //asignacion de pedidos al almacen

        for(int i=0; i<listaPedidos.size(); i++){
            int indiceAlmacen = listaPedidos.get(i).getAlmacen().getId();
            //System.out.println(indiceAlmacen+" "+ pedidos.get(i));
            for(int j = 0; j <almacenes.size(); j++){
                if(almacenes.get(j).getCiudad().getId() == indiceAlmacen){
                    almacenes.get(j).agregarPedido(listaPedidos.get(i));
                    break;
                }
            }
        }
        //agrupacion de pedidos por camiones
        boolean asignacionPedidos, asignacionCamiones;

        //Considerar Bloqueo
        //---------------------------------------------------------------------------------
        Encapsulado[][] matrizHoras;
        FloyWarshall fw = new FloyWarshall();
        //Matriz de horas==========================================================
        matrizHoras = fw.ejecutar(rutaInicial, tramos, listaBloqueos, inicio);
        while(true){
            exe.asignacionPedidosxCamion(almacenes);
            asignacionPedidos = true;
            asignacionCamiones = true;
            for(int numAlmacen=0; numAlmacen<almacenes.size(); numAlmacen++){
                ////System.out.println("pedidos");
                ////System.out.println(almacenes.get(numAlmacen).getPedidos());

                for(int numCamion=0; numCamion<almacenes.get(numAlmacen).getCamiones().size(); numCamion++){
                    
                    ArrayList<Date> fechasLimite = exe.obtenerFechas(almacenes.get(numAlmacen).getCamiones().get(numCamion));
                    ArrayList<Integer> indicesDestinos = exe.obtenerDestinos(almacenes.get(numAlmacen).getCamiones().get(numCamion));
                    int puntoOrigen = (int)almacenes.get(numAlmacen).getCiudad().getId();
                    if(indicesDestinos.size() == 0) asignacionCamiones = false;
                    if(indicesDestinos.size()>0){
                        Ejecucion exe1 = new Ejecucion();
                        double total = 0;
                        if(indicesDestinos.size()>0);
                        total+=matrizHoras[puntoOrigen][indicesDestinos.get(0)].horas;
                        for(int i =0; i<indicesDestinos.size()-1;i++){
                            total+=matrizHoras[indicesDestinos.get(i)][indicesDestinos.get(i+1)].horas;
                        }
                        //System.out.println(total);
                        Ruta rutaMasCorta = null;
                        while(rutaMasCorta==null){
                            rutaMasCorta = exe1.ejecutar(fechasLimite,indicesDestinos,puntoOrigen, rutaInicial,matrizHoras, inicio); ////Faltaría completar las fechas de llegada
                            if(rutaMasCorta==null){
                                for(int i = 0; i < almacenes.get(numAlmacen).getPedidos().size();i++){
                                    if(almacenes.get(numAlmacen).getPedidos().get(i).getId() == almacenes.get(numAlmacen).getCamiones().get(numCamion).getEntregas().get(indicesDestinos.size()-1).getPedido().getId()){
                                        almacenes.get(numAlmacen).getPedidos().get(i).setSinAsignar(almacenes.get(numAlmacen).getCamiones().get(numCamion).getEntregas().get(indicesDestinos.size()-1).getNumPaquetes());
                                        indicesDestinos.remove(indicesDestinos.size()-1);
                                        //System.out.println("Reasignando pedido");
                                        asignacionPedidos = false;
                                    }
                                }
                            }
                        }
                        //Calculo de retorno
                        rutaMasCorta.setHoras(rutaMasCorta.getHoras()+1);
                        rutaEntregas = new ArrayList<Integer>(Arrays.asList(puntoOrigen));
                        maxEntregas= new ArrayList<Date>(Arrays.asList(
                            new Date(rutaMasCorta.getLlegada().get(rutaMasCorta.getLlegada().size()-1).getTime()+3*24*60*60*1000)
                        ));
                        Ejecucion exe2 = new Ejecucion();
                        //Ruta retorno = exe2.ejecutar(maxEntregas,rutaEntregas,rutaMasCorta.getUltima(), rutaInicial,matrizHoras,rutaMasCorta.getLlegada().get(rutaMasCorta.getCiudades().size()-1));
                        Ruta retorno = exe2.ejecutar(maxEntregas,rutaEntregas,(int)rutaMasCorta.getUltima(), rutaInicial,matrizHoras,rutaMasCorta.getLlegada().get(0));//No valida horas
                        rutaMasCorta.addAllCiudades(retorno.getCiudades());
                        rutaMasCorta.addAllLlegada(retorno.getLlegada());
                        /*Invocación ACO---------------------------------------------------------------------
                        Invocación ACO---------------------------------------------------------------------*/
                        System.out.println("\nRuta Óptima          : "+rutaMasCorta.getCiudades());
                        System.out.println("w/ Tiempo   : "+String.format("%.4f", rutaMasCorta.getHoras())+" horas");
                        rutas.add(rutaMasCorta);
                    }

                }
            }
            if(asignacionPedidos || asignacionCamiones) break;
        }
        return rutas;
    }
    
    public void leerFlotaxAlmacen(ArrayList<Almacen> almacenes, ArrayList<CiudadModel> todasLasCiudades){
        //Leer archivo
        File flota = new File("flota.txt");
        try{
            String buffer = ""; 
            String lugar = ""; 
            String almac = ""; 
            String[] tokens = null;
            String[] camionesA = null;
            String[] camionesB = null;
            String[] camionesC = null;
            Scanner obflo = new Scanner(flota);  
            
            while(obflo.hasNextLine()){
                
                lugar = obflo.nextLine();
                CiudadModel ciudad = buscarCiudadxNombre(lugar, todasLasCiudades);
                //System.out.println(ciudad);
                Almacen almacen = new Almacen(ciudad);
                
                almac = obflo.nextLine();
                camionesA = almac.split(", ");
                for(int i=0; i<camionesA.length; i++){
                    VehiculoModel camionA = new VehiculoModel(camionesA[i], 90, 'A');
                    almacen.agregarCamion(camionA);
                }
                
                almac = obflo.nextLine();
                camionesB = almac.split(", ");
                for(int i=0; i<camionesB.length; i++){
                    VehiculoModel camionB = new VehiculoModel(camionesB[i], 90, 'B');
                    almacen.agregarCamion(camionB);
                }
                
                almac = obflo.nextLine();
                camionesC = almac.split(", ");
                for(int i=0; i<camionesC.length; i++){
                    VehiculoModel camionC = new VehiculoModel(camionesC[i], 90, 'C');
                    almacen.agregarCamion(camionC);
                }
                
                //almacen.imprimirCamiones(); 
                almacenes.add(almacen); 
            } 
        }catch(FileNotFoundException ff){System.out.println("Exception " + ff.toString());exit(1);} 
        //------------------------------------------------------------ 
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
    
    private CiudadModel buscarCiudadxNombre (String nombre, ArrayList<CiudadModel> todasLasCiudades){
        for(int i=0; i<todasLasCiudades.size(); i++){
            //System.out.println(todasLasCiudades.get(i).getUbigeo()+"-"+ ubigeo);
            if(todasLasCiudades.get(i).getNombre().equals(nombre)){
                //System.out.println("MATCH");
                return todasLasCiudades.get(i);
            }
        }
        return null;
    }
}
