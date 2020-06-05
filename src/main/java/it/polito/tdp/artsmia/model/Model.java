package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private Graph<ArtObject, DefaultWeightedEdge> graph;
	private Map<Integer, ArtObject> idMap; 
	
	public Model() {
		idMap = new HashMap<Integer, ArtObject>(); 
		
	}
	
	public void CreaGrafo() {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
		ArtsmiaDAO dao = new ArtsmiaDAO(); 
		
		dao.listObjects(idMap);
		
		
		Graphs.addAllVertices(graph, idMap.values()); 
		
		//metodo con doppio ciclo for( non sempre funziona), in questo caso troppo lento
		
		/*for(ArtObject a1: this.graph.vertexSet()) {
			for(ArtObject a2: this.graph.vertexSet()) {
				
				
				
				int peso = dao.getPeso(a1, a2); 
				
				if (peso >0 ) {
					if(!this.graph.containsEdge(a1, a2));
					Graphs.addEdge(this.graph, a1, a2, peso);
				}
			}
		}*/
		
		
		//Approccio 2 funziona con qualsiasi numero di righe perche molto più veloce a computare 
		for(Adiacenza a : dao.getAdiacenze()) {
			if(a.getPeso()>0) {
				Graphs.addEdge(this.graph, idMap.get(a.getObj1()), idMap.get(a.getObj2()), a.getPeso()); 
			}
		}
		
		
		
		
	}
	
	public int nVertici () {
		return this.graph.vertexSet().size(); 
	}
	
	public int nArchi () {
		return this.graph.edgeSet().size(); 
	}
	

}
