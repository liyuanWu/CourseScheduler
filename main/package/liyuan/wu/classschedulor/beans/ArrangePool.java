package liyuan.wu.classschedulor.beans;

import java.util.HashMap;
import java.util.Map;

public class ArrangePool<T>  implements Boat<T,UnarrangedCourse>{

	private final T identifier;
	private final Map<UnarrangedCourse,Integer> unarrangedCourseMap;
	
	public ArrangePool(T identifier){
		this.identifier = identifier;
		this.unarrangedCourseMap = new HashMap<UnarrangedCourse, Integer>();
	}
	
	public T getIdentifier() {
		return identifier;
	}

	@Override
	public void add(UnarrangedCourse s) {
		if(unarrangedCourseMap.containsKey(s)){
			unarrangedCourseMap.put(s, unarrangedCourseMap.get(s)+1);
		}else{
			unarrangedCourseMap.put(s, 1);
		}
	}

	@Override
	public void remove(UnarrangedCourse s) {
		if(unarrangedCourseMap.containsKey(s)){
			if(unarrangedCourseMap.get(s)>1){
				unarrangedCourseMap.put(s,unarrangedCourseMap.get(s)-1);
			}else{
				unarrangedCourseMap.remove(s);
			}
		}
	}

	@Override
	public UnarrangedCourse[] getAll() {
		return unarrangedCourseMap.keySet().toArray(new UnarrangedCourse[unarrangedCourseMap.size()]);
	}

	@Override
	public int numberOf(UnarrangedCourse s) {
		Integer number =  this.unarrangedCourseMap.get(s);
		return number==null? 0:number;
	}

	@Override
	public boolean canAdd(WeekDay weekday, Time time) {
		return true;
	}
}
