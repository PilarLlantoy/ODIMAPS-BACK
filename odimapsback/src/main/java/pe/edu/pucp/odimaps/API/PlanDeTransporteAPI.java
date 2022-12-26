package pe.edu.pucp.odimaps.API;

import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;
import pe.edu.pucp.odimaps.interfaces.PlanDeTransporteInterface;
import pe.edu.pucp.odimaps.models.CiudadModel;
import pe.edu.pucp.odimaps.models.PedidoModel;
import pe.edu.pucp.odimaps.models.PlanDeTransporteModel;
import pe.edu.pucp.odimaps.models.VehiculoModel;
import pe.edu.pucp.odimaps.services.CargaService;
import pe.edu.pucp.odimaps.services.CiudadService;
import pe.edu.pucp.odimaps.services.PedidoService;
import pe.edu.pucp.odimaps.services.PlanDeTransporteService;
import pe.edu.pucp.odimaps.services.VehiculoService;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/planDeTransporte")
public class PlanDeTransporteAPI {
    @Autowired
    CargaService cargaService;
    @Autowired
    CiudadService ciudadService;
    @Autowired
    VehiculoService vehiculoService;
    @Autowired
    PlanDeTransporteService planDeTransporteService;



    @GetMapping()
    public ArrayList<PlanDeTransporteModel> obtenerPlanDeTransporte(){
        return planDeTransporteService.obtenerPlanesDeTransportes();
    }

    // @PostMapping("/{almacen}/{vehiculo}/{cargas}")
    // public PlanDeTransporteModel guardarCiudad(@RequestBody PlanDeTransporteModel planDeTransporte,@PathVariable int almacen,@PathVariable int vehiculo,@PathVariable String listaCargas){
    //     planDeTransporte.setFechaPartida(new Date(System.currentTimeMillis()));
    //     CiudadModel almcenD = ciudadService.obtenerCiudadPorId(almacen);
    //     planDeTransporte.setAlmacen(almcenD);
    //     VehiculoModel camion = vehiculoService.obtenerVehiculoPorId(vehiculo);
    //     planDeTransporte.setVehiculo(camion);
    //     String[] cargas2 = listaCargas.split("|");
    //     ArrayList<String> cargas3 = new ArrayList<String>();
    //     for (String carguita : cargas2) {
    //         carg
    //         cargas3.add(carguita);
    //     }
    //     cargas3
    //     return planDeTransporteService.guardarPlanDeTransporte(planDeTransporte);
    // }

}
