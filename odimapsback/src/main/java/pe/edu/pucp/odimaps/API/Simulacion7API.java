/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.API;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.pucp.odimaps.AlgoritmoACO.Service.Almacen;
import pe.edu.pucp.odimaps.AlgoritmoACO.Service.Ruta;
import pe.edu.pucp.odimaps.DTO.Simulacion7Model;
import pe.edu.pucp.odimaps.models.BloqueoModel;
import pe.edu.pucp.odimaps.models.CiudadModel;
import pe.edu.pucp.odimaps.models.MantenimientoModel;
import pe.edu.pucp.odimaps.models.PedidoModel;
import pe.edu.pucp.odimaps.models.TramoModel;
import pe.edu.pucp.odimaps.models.VehiculoModel;
import pe.edu.pucp.odimaps.services.Simulacion7Service;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/simulacion7")
public class Simulacion7API {
    @Autowired
    Simulacion7Service simulacion7Service;
    
    @PostMapping()
    public ArrayList<Ruta> ejecutarAlgoritmo(@RequestBody Simulacion7Model modelo) throws IOException{
        
        return simulacion7Service.ejecutarAlgoritmo(modelo, new Date(122,0,1,4,0,0));        
        
    }
    
}
