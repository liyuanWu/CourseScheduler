package liyuan.wu.classschedulor.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import liyuan.wu.classschedulor.beans.ArrangePool;
import liyuan.wu.classschedulor.beans.Boat;


public class Pool<T,S extends Boat<T,?>> {

	private final Map<T,S> poolMap;
	
	public Pool(){
		this.poolMap = new HashMap<T, S>();
	}
	
	public void addBoat(S s){
		this.poolMap.put(s.getIdentifier(), s);
	}
	
	public void removeBoat(ArrangePool<T> arrangePool){
		this.poolMap.remove(arrangePool.getIdentifier());
	}
	
	public S getBoat(T identifier){
		return this.poolMap.get(identifier);
	}
	
	public Boat<T,?>[] getAllBoats(){
		Set<Boat<T,?>> returnSet = new HashSet<Boat<T,?>>();
		for(Entry<T, S> entry:poolMap.entrySet()){
			returnSet.add(entry.getValue());
		}
		return returnSet.toArray(new Boat[returnSet.size()]);
	}
}
