/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.AlgoritmoACO.util;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author USUARIO
 */
public class AtomicDouble extends Number implements Comparable<AtomicDouble>{
    
    private static final long serialVersionUID = 1L;
    private AtomicReference<Double> atomicReference;
    
    public AtomicDouble(Double doubleValue) {atomicReference = new AtomicReference<Double>(doubleValue);}
    @Override
    public int intValue() {return atomicReference.get().intValue();}

    @Override
    public long longValue() {return atomicReference.get().longValue();}

    @Override
    public float floatValue() {return atomicReference.get().floatValue();}

    @Override
    public double doubleValue() {return atomicReference.get().doubleValue();}

    @Override
    public int compareTo(AtomicDouble o) {
        return Double.compare(this.doubleValue(), o.doubleValue());
    }
    public boolean compareAndSet(double updatedValue){
        boolean returnFlag = false;
        if (atomicReference.compareAndSet(atomicReference.get(), updatedValue)) returnFlag = true;
        return returnFlag;
    }
    
    
    
}
