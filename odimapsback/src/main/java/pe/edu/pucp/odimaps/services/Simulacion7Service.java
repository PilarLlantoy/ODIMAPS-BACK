/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.odimaps.AlgoritmoACO.Service.AlgoritmoSimulacion7;
import pe.edu.pucp.odimaps.AlgoritmoACO.Service.Almacen;
import pe.edu.pucp.odimaps.AlgoritmoACO.Service.Ruta;
import pe.edu.pucp.odimaps.DTO.Simulacion7Model;
import pe.edu.pucp.odimaps.interfaces.Simulacion7Interface;
import pe.edu.pucp.odimaps.models.BloqueoModel;
import pe.edu.pucp.odimaps.models.CiudadModel;
import pe.edu.pucp.odimaps.models.MantenimientoModel;
import pe.edu.pucp.odimaps.models.PedidoModel;
import pe.edu.pucp.odimaps.models.TramoModel;
import pe.edu.pucp.odimaps.models.VehiculoModel;

/**
 *
 * @author USUARIO
 */
@Service
public class Simulacion7Service {
    
    @Autowired
    Simulacion7Interface simulacion7Interface;
    
    public ArrayList<Ruta> ejecutarAlgoritmo(Simulacion7Model modelo, Date hoy) throws IOException{
        AlgoritmoSimulacion7 algo = new AlgoritmoSimulacion7();
        
        ArrayList<Ruta> rutas;
        
        String ciudades = "inf226.oficinas.txt";
        String carreteras = "inf226.tramos.v.2.0.txt";
        ArrayList<CiudadModel> rutaInicial = leerRutas(ciudades);
        ArrayList<TramoModel> tramos = leerTramos(carreteras, rutaInicial);
        ArrayList<Almacen> almacenes = new ArrayList<>();
        ArrayList<VehiculoModel> vehiculos = new ArrayList<>();
        leerFlotaxAlmacen(almacenes, rutaInicial, vehiculos);
        //Obtener lista de objetos
        ArrayList<BloqueoModel> listaBloqueo = new ArrayList<>();
        ArrayList<MantenimientoModel> listaMantenimiento = new ArrayList<>();
        ArrayList<PedidoModel> listaPedido = new ArrayList<>();
        
        listaBloqueo = sacarBloqueos(modelo.getBloqueos(), tramos);
        listaMantenimiento = sacarMantenimiento(modelo.getMantenimientos(), vehiculos);
        listaPedido = sacarPedidos(modelo.getPedidos(), rutaInicial);
        
        //System.out.println(listaBloqueo);
        //System.out.println(listaMantenimiento);
        //System.out.println(listaPedido);
        
        rutas = algo.ejecutar(hoy, listaPedido, listaBloqueo, listaMantenimiento, rutaInicial,tramos, almacenes);
        return rutas;
    }
    
    private ArrayList<BloqueoModel> sacarBloqueos(ArrayList<String> bloqueos, ArrayList<TramoModel> listaTramos) {
        ArrayList<BloqueoModel> bloq = new ArrayList<>();
        String[] traFec = new String[2];
        String[] tram = new String[2];
        String[] iniFin = new String[2];
        String[] inicio = new String[2];
        String[] fin = new String[2];
        Date ini;
        Date fi;
        String buff = "";
        for(int k = 0; k < bloqueos.size();k++){
                if(bloqueos.get(k).isBlank()) continue;
                BloqueoModel bl = new BloqueoModel();
                traFec = bloqueos.get(k).split(";");
                tram = traFec[0].split(" => ");
                iniFin = traFec[1].split("==");
                //Encontra Tramo
                for(int i = 0; i < listaTramos.size(); i++){
                    if(listaTramos.get(i).getOrigen().getUbigeo().contains(tram[0]) && listaTramos.get(i).getDestino().getUbigeo().contains(tram[1])){
                        bl.setTramo(listaTramos.get(i));
                        break;
                    }
                    if(listaTramos.get(i).getOrigen().getUbigeo().contains(tram[1]) && listaTramos.get(i).getDestino().getUbigeo().contains(tram[0])){
                        bl.setTramo(listaTramos.get(i));
                        break;
                    }
                }
                //Sacar Fechas
                inicio = iniFin[0].split(",");
                System.out.println("Anio modificar(no hardcodear) en Simulacion7Service.java linea 93 y 97 (referencia)");
                ini = new Date(122,Integer.parseInt(inicio[0].substring(0, 2))-1,Integer.parseInt(inicio[0].substring(2, 4)),Integer.parseInt(inicio[1].split(":")[0]),Integer.parseInt(inicio[1].split(":")[1]),0);
                buff = "";
                //SacarFechas
                fin = iniFin[1].split(",");
                fi = new Date(122,Integer.parseInt(fin[0].substring(0, 2))-1,Integer.parseInt(fin[0].substring(2, 4)),Integer.parseInt(fin[1].split(":")[0]),Integer.parseInt(fin[1].split(":")[1]),0);
                buff = "";
                
                bl.setId(k);
                bl.setFechaInicio(ini);
                bl.setFechaFin(fi);
                bloq.add(bl);
            }
        return bloq;
    }

    private ArrayList<MantenimientoModel> sacarMantenimiento(ArrayList<String> mantenimientos, ArrayList<VehiculoModel> vehiculos) {
        ArrayList<MantenimientoModel> mantenimiento = new ArrayList<>();
        String[] fecVeh = new String[2];
        String[] anio = new String[2];
        String[] mesDia = new String[2];
        String fecha = "";
        String vehiculo = "";
        Date fec;
        for(int k = 0; k < mantenimientos.size();k++){
            if(mantenimientos.get(k).isBlank()) continue;
            MantenimientoModel man = new MantenimientoModel();
            fecVeh = mantenimientos.get(k).split(":");
            fecha = fecVeh[0];
            vehiculo = fecVeh[1];
            //Encontra Vehiculo
            for(int i = 0; i < vehiculos.size(); i++){
                if(vehiculos.get(i).getCodigo().contains(vehiculo))
                    man.setVehiculo(vehiculos.get(i));
            }
            //Sacar Fecha
            anio[0] = fecha.substring(0, 4);
            anio[1] = fecha.substring(4, 8);
            mesDia[0] = anio[1].substring(0, 2);
            mesDia[1] = anio[1].substring(2, 4);
            fecha = "";
            fec = new Date(Integer.parseInt(anio[0])-1900,Integer.parseInt(mesDia[0])-1,Integer.parseInt(mesDia[1]),0,0,0);
            man.setInicio(fec);
            fec = new Date(Integer.parseInt(anio[0])-1900,Integer.parseInt(mesDia[0])-1,Integer.parseInt(mesDia[1])+1,0,0,1);
            man.setFin(fec);



            man.setId(k);
            mantenimiento.add(man);
            fecha = "";
            vehiculo = "";
        }
        return mantenimiento;
    }

    private ArrayList<PedidoModel> sacarPedidos(ArrayList<String> pedidos, ArrayList<CiudadModel> ciudades) {
        ArrayList<PedidoModel> pedido = new ArrayList<>();
        String fecha = "";
        String ciudad = "";
        String cantidad = "";
        String cliente = "";
        String alma = "";
        String dest = "";
        
        for(int k = 0; k < pedidos.size(); k++){
            if(pedidos.get(k).isBlank()) continue;
            PedidoModel ped = new PedidoModel();
            fecha = pedidos.get(k).split(", ")[0];
            fecha = fecha.replaceAll(" ", "");
            ciudad = pedidos.get(k).split(", ")[1];
            ciudad = ciudad.replaceAll(" ", "");
            cantidad = pedidos.get(k).split(", ")[2];
            cantidad = cantidad.replaceAll(" ", "");
            alma = ciudad.split("=>")[0];
            dest = ciudad.split("=>")[1];
            //Buscar ciudades
            for(int i = 0; i < ciudades.size(); i++){
                if(alma.contains(ciudades.get(i).getUbigeo())) ped.setAlmacen(ciudades.get(i));
                if(dest.contains(ciudades.get(i).getUbigeo())) ped.setDestino(ciudades.get(i));
            }
            
            ped.setId(k);
            System.out.println("Anio y mes modificar(no hardcodear) en Simulacion7Service.java linea 175, 176, 168 (referencia)");
            ped.setFechaRegistro(new Date(122, 0, Integer.parseInt(fecha.substring(0, 2)), Integer.parseInt(fecha.substring(2, 4)),Integer.parseInt(fecha.substring(5, 7)), 0));
            if(ped.getDestino().getRegion().contains("COSTA"))ped.setFechaEntregaMax(new Date(ped.getFechaRegistro().getTime()+1*24*60*60*1000));
            if(ped.getDestino().getRegion().contains("SIERRA"))ped.setFechaEntregaMax(new Date(ped.getFechaRegistro().getTime()+2*24*60*60*1000));
            if(ped.getDestino().getRegion().contains("SELVA"))ped.setFechaEntregaMax(new Date(ped.getFechaRegistro().getTime()+3*24*60*60*1000));
            
            ped.setNumPaquetes(Integer.parseInt(cantidad));
            ped.setSinAsignar(Integer.parseInt(cantidad));
            pedido.add(ped);
        }
        
        return pedido;
    }
    
    private ArrayList<TramoModel> leerTramos(String archCarreteras, ArrayList<CiudadModel> ciudades){
        File rutas = new File(archCarreteras);
        ArrayList<TramoModel> tramos = new ArrayList<>();
        try{
            Scanner objRu = new Scanner(rutas);
            String buffer = "";
            int cont = 0;
            while(objRu.hasNextLine()){
                buffer = objRu.nextLine();
                String[] con = new String[2];
                con = buffer.split(" => ");
                con[0]=con[0].replaceAll(" ", "");
                con[1]=con[1].replaceAll(" ", "");
                CiudadModel destino = new CiudadModel();
                CiudadModel origen = new CiudadModel();
                for(int i = 0; i < ciudades.size(); i++){
                    if(ciudades.get(i).getUbigeo().contains(con[0])) origen = ciudades.get(i);
                    if(ciudades.get(i).getUbigeo().contains(con[1])) destino = ciudades.get(i);
                }
                tramos.add(new TramoModel(cont, origen, destino));
                cont++;
            }
        }catch(FileNotFoundException ff){System.out.println("Exception " + ff.toString());exit(1);}
        return tramos;
    }
    
    private ArrayList<CiudadModel> leerRutas(String archCiudades){
        ArrayList<CiudadModel> rutaInicial = new ArrayList<CiudadModel>();
        //Leer archivo
        File oficinas = new File(archCiudades);
        
        try{
            String[] tokens = null;
            Scanner objOfi = new Scanner(oficinas);
            String buffer = "";
            String ubigeo = "";
            String departamento = "";
            String provincia = "";
            double latitud = 0.0;
            double longitud = 0.0;
            String region = "";
            String conex = "";
            int contar = 0;
            while(objOfi.hasNextLine()){
                conex = "";
                buffer = objOfi.nextLine();
                tokens = buffer.split(",");
                ubigeo = tokens[0]; //Leer UBIGEO
                departamento=tokens[1]; //Leer Departamento
                provincia=tokens[2]; //Leer Provincia
                latitud=Double.parseDouble(tokens[3]); //Leer Latitud (eje X)
                longitud=Double.parseDouble(tokens[4]); //Leer Longitud (eje Y)
                region=tokens[5]; //Leer Region
                CiudadModel ciu = new CiudadModel(provincia,latitud,longitud,ubigeo, region, contar);
                rutaInicial.add(ciu);
                contar++;
            }
        }catch(FileNotFoundException ff){System.out.println("Exception " + ff.toString());exit(1);}
        
        //------------------------------------------------------------
        return rutaInicial;
    }

    public void leerFlotaxAlmacen(ArrayList<Almacen> almacenes, ArrayList<CiudadModel> todasLasCiudades, ArrayList<VehiculoModel> vehiculos){
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
                    vehiculos.add(camionA);
                }
                
                almac = obflo.nextLine();
                camionesB = almac.split(", ");
                for(int i=0; i<camionesB.length; i++){
                    VehiculoModel camionB = new VehiculoModel(camionesB[i], 45, 'B');
                    almacen.agregarCamion(camionB);
                    vehiculos.add(camionB);
                }
                
                almac = obflo.nextLine();
                camionesC = almac.split(", ");
                for(int i=0; i<camionesC.length; i++){
                    VehiculoModel camionC = new VehiculoModel(camionesC[i], 30, 'C');
                    almacen.agregarCamion(camionC);
                    vehiculos.add(camionC);
                }
                
                //almacen.imprimirCamiones(); 
                almacenes.add(almacen);
            } 
        }catch(FileNotFoundException ff){System.out.println("Exception " + ff.toString());exit(1);} 
        //------------------------------------------------------------ 
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
