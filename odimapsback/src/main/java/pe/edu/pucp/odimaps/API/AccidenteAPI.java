package pe.edu.pucp.odimaps.API;

import java.util.Date;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.odimaps.models.AccidenteModel;
import pe.edu.pucp.odimaps.models.TramoModel;
import pe.edu.pucp.odimaps.models.VehiculoModel;
import pe.edu.pucp.odimaps.services.AccidenteService;
import pe.edu.pucp.odimaps.services.TramoService;
import pe.edu.pucp.odimaps.services.VehiculoService;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/accidente")
public class AccidenteAPI {
    @Autowired
    AccidenteService accidenteService;
    @Autowired
    VehiculoService vehiculoService;
    @Autowired
    TramoService tramoService;
    
    @GetMapping()
    public ArrayList<AccidenteModel> obtenerAccidentes(){
        return accidenteService.obtenerAccidentes();
    }

    @PostMapping("/{tramo}/{vehiculo}")
    public AccidenteModel guardarAccidente(@RequestBody AccidenteModel accidente,@PathVariable int tramo,@PathVariable int vehiculo){
        accidente.setFechaAccidente(new Date(System.currentTimeMillis()));
        
        TramoModel carretera = tramoService.obtenerTramoPorId(tramo);
        accidente.setTramo(carretera);
        VehiculoModel camion = vehiculoService.obtenerVehiculoPorId(vehiculo);
        accidente.setVehiculo(camion);
        return accidenteService.guardarAccidente(accidente);
    }
}
