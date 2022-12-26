package pe.edu.pucp.odimaps.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.odimaps.interfaces.AccidenteInterface;
import pe.edu.pucp.odimaps.models.AccidenteModel;

/**
 *
 * @author USUARIO
 */
@Service
public class AccidenteService {
    @Autowired
    AccidenteInterface accidenteInterface;

    public AccidenteModel guardarAccidente(AccidenteModel accidente){
        return accidenteInterface.save(accidente);
    }

    public AccidenteModel obtenerTramoPorId(int id)
    {
        AccidenteModel tramo = accidenteInterface.findById(id).get();
        return tramo;
    }

    public ArrayList<AccidenteModel> obtenerAccidentes(){
        return (ArrayList<AccidenteModel>)accidenteInterface.findAll();
    }
}
