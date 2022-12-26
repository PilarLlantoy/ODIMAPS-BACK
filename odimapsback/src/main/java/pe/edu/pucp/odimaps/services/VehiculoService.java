package pe.edu.pucp.odimaps.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.odimaps.interfaces.VehiculoInterface;
import pe.edu.pucp.odimaps.models.VehiculoModel;

/**
 *
 * @author USUARIO
 */
@Service
public class VehiculoService {
    @Autowired
    VehiculoInterface vehiculoInterface;
    
    public ArrayList<VehiculoModel> obtenerVehiculos(){
        return (ArrayList<VehiculoModel>)vehiculoInterface.findAll();
    }
    
    public VehiculoModel guardarVehiculo(VehiculoModel vehiculo){
        return vehiculoInterface.save(vehiculo);
    }

    public VehiculoModel obtenerVehiculoPorId(int id)
    {
        VehiculoModel vehiculo = vehiculoInterface.findById(id).get();
        return vehiculo;
    }
}