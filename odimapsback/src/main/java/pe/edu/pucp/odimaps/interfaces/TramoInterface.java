/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.odimaps.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.pucp.odimaps.models.TramoModel;

/**
 *
 * @author USUARIO
 */
@Repository
public interface TramoInterface extends JpaRepository<TramoModel, Integer> {
    
}
