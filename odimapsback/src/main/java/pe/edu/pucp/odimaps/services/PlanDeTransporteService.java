package pe.edu.pucp.odimaps.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.odimaps.interfaces.PlanDeTransporteInterface;
import pe.edu.pucp.odimaps.models.PlanDeTransporteModel;

/**
 *
 * @author USUARIO
 */
@Service
public class PlanDeTransporteService {
    @Autowired
    PlanDeTransporteInterface planDeTransporteInterface;

    public ArrayList<PlanDeTransporteModel> obtenerPlanesDeTransportes(){
        ArrayList<PlanDeTransporteModel> planesDeTransportes = new ArrayList<>();
        planesDeTransportes = (ArrayList<PlanDeTransporteModel>)planDeTransporteInterface.findAll();
        return planesDeTransportes;
    }
    
    public PlanDeTransporteModel guardarPlanDeTransporte(PlanDeTransporteModel planDeTransporte){
        return planDeTransporteInterface.save(planDeTransporte);
    }

    public PlanDeTransporteModel obtenerPlanDeTransportePorId(int id)
    {
        PlanDeTransporteModel planDeTransporte = planDeTransporteInterface.findById(id).get();
        return planDeTransporte;
    }
}
