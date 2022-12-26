/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.odimaps.interfaces.MantenimientoInterface;
import pe.edu.pucp.odimaps.models.MantenimientoModel;

/**
 *
 * @author USUARIO
 */
@Service
public class MantenimientoService {
    @Autowired
    MantenimientoInterface mantenimientoInterface;
    
    public ArrayList<MantenimientoModel> obtenerMantenimiento(){
        ArrayList<MantenimientoModel> bloqueo = new ArrayList<>();
        bloqueo = (ArrayList<MantenimientoModel>)mantenimientoInterface.findAll();
        return bloqueo;
    }
    
    public MantenimientoModel guardarMantenimiento(MantenimientoModel bloqueo){
        return mantenimientoInterface.save(bloqueo);
    }
}
