/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.AlgoritmoACO.FloydWarshall;

import java.util.ArrayList;
import java.util.Date;
import pe.edu.pucp.odimaps.models.BloqueoModel;
import pe.edu.pucp.odimaps.models.CiudadModel;
import pe.edu.pucp.odimaps.models.TramoModel;

/**
 *
 * @author USUARIO
 */
public class FloyWarshall {
    double INF = 99999999;
    private int V;
    private Encapsulado[][]matrizHoras;
    ArrayList<CiudadModel>ciudades;
    
    public Encapsulado[][] ejecutar(ArrayList<CiudadModel>ciudades,ArrayList<TramoModel>tramos, ArrayList<BloqueoModel> bloqueos, Date hoy){
        int tamCiudades = ciudades.size();
        matrizHoras = new Encapsulado[tamCiudades][tamCiudades];
        for(int x = 0; x < tamCiudades; x++){
            CiudadModel ciudadY = ciudades.get(x);
            for(int y = 0; y<tamCiudades;y++){
                matrizHoras[x][y] = new Encapsulado();
                matrizHoras[x][y].horas = ciudadY.medirHoras(ciudades.get(y),tramos, hoy, bloqueos);
            }
        }
        V=tamCiudades;
        this.ciudades = ciudades;
        // Print the solution
        return floydWarshall();
    }
    private Encapsulado[][] floydWarshall()
    {
        int tamCiudades = ciudades.size();
        Encapsulado dist[][] = new Encapsulado[V][V];
        for(int x = 0; x < tamCiudades; x++){
            for(int y = 0; y<tamCiudades;y++){
                dist[x][y] = new Encapsulado();
            }
        }
        int i, j, k;
        /* Add all vertices one by one
           to the set of intermediate
           vertices.
          ---> Before start of an iteration,
               we have shortest
               distances between all pairs
               of vertices such that
               the shortest distances consider
               only the vertices in
               set {0, 1, 2, .. k-1} as
               intermediate vertices.
          ----> After the end of an iteration,
                vertex no. k is added
                to the set of intermediate
                vertices and the set
                becomes {0, 1, 2, .. k} */
        for (k = 0; k < V; k++)
        {
            // Pick all vertices as source one by one
            for (i = 0; i < V; i++)
            {
                // Pick all vertices as destination for the
                // above picked source
                for (j = 0; j < V; j++)
                {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
                    if (matrizHoras[i][k].mas(matrizHoras[k][j]) < matrizHoras[i][j].horas)
                        matrizHoras[i][j] = matrizHoras[i][k].incluir(matrizHoras[k][j],ciudades.get(k));
                }
            }
        }
        return matrizHoras;
    }
 
    public void printSolution(int verRutas)
    {
//        System.out.println("ESTA COSA TE SACA EL TIEMPO MAS CORTO DE CIUDAD(i) A CIUDAD(j)");
//        System.out.println("PUEDO MODIFICARLO PARA GUARDAR LAS CIUDADES INTERMEDIAS");
//        System.out.println("CON ESTO FUNCIONARIA TU ABC!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println(V);
//        for (int i=0; i<V; ++i)
//        {
//            for (int j=0; j<V; ++j)
//            {
//                if (matrizHoras[i][j].horas==INF)
//                    System.out.printf("%12.2f",99999999.99);
//                else
//                    System.out.printf("%12.2f",matrizHoras[i][j].horas);
//            }
//            System.out.println();
//        }
        for(int i = 0; i<V; i++){
            System.out.println(ciudades.get(verRutas).getNombre()+" => "+ matrizHoras[verRutas][i].ciudades+" => "+ciudades.get(i).getNombre());
        }
    }
}
