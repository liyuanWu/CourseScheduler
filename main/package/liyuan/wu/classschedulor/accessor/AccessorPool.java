package liyuan.wu.classschedulor.accessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import liyuan.wu.classschedulor.beans.ArrangePool;
import liyuan.wu.classschedulor.beans.Boat;

public class AccessorPool <T,S extends BoatAccessor<T,?>> {

	private final Map<T,S> poolMap;
	
	public AccessorPool(){
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
	
	public BoatAccessor<T,?>[] getAllBoats(){
		Set<BoatAccessor<T,?>> returnSet = new HashSet<BoatAccessor<T,?>>();
		for(Entry<T, S> entry:poolMap.entrySet()){
			returnSet.add(entry.getValue());
		}
		return returnSet.toArray(new BoatAccessor[returnSet.size()]);
	}
}