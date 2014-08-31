package liyuan.wu.classschedulor.view;

import java.util.LinkedList;
import java.util.List;

public class QueryResult {

	private final List<SingleQueryResult> singleQueryResults;
	
	public QueryResult(){
		this.singleQueryResults = new LinkedList<SingleQueryResult>();
	}
	
	public void addQueryResult(SingleQueryResult queryResult){
		this.singleQueryResults.add(queryResult);
	}
	
	public SingleQueryResult[] getSingleQueryResults(){
		return singleQueryResults.toArray(new SingleQueryResult[singleQueryResults.size()]);
	}
}
