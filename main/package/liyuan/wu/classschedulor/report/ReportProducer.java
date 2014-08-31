package liyuan.wu.classschedulor.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.LinkedList;

import org.apache.commons.io.IOUtils;

import liyuan.wu.classschedulor.accessor.AccessorPool;
import liyuan.wu.classschedulor.accessor.BoatAccessor;
import liyuan.wu.classschedulor.beans.CourseCombo;
import liyuan.wu.classschedulor.view.CellDecorator;

public class ReportProducer {
	
	private static final String REPORTTEMPLATEPATH = "reportTemplate.html";
	StringBuffer stringBuffer = new StringBuffer();
	
	public String getReport(){
		StringBuilder stringBuilder = new StringBuilder();
		InputStream in = null;
		 try {
			in = new FileInputStream(new File(REPORTTEMPLATEPATH));
			String s = IOUtils.toString( in );
		   if(s.contains("${content}")){
				stringBuilder.append(s.replace("${content}", stringBuffer.toString())).append("\n");
			}else{
				stringBuilder.append(s).append("\n");
			}
		   return stringBuilder.toString();
		 } catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
		   IOUtils.closeQuietly(in);
		 }
		
	}
	
	public <T>void addContent( AccessorPool<T,BoatAccessor<T,CourseCombo>> accessorPool ,
			CellDecorator<T> cellDecorator,Comparator<T> comparator){
		stringBuffer.append(getAllTable(accessorPool, cellDecorator,comparator,""));
	}
	
	public <T>void addContent( AccessorPool<T,BoatAccessor<T,CourseCombo>> accessorPool ,
			CellDecorator<T> cellDecorator,Comparator<T> comparator,String specialClass){
		stringBuffer.append(getAllTable(accessorPool, cellDecorator,comparator,specialClass));
	}
	
	public <T>String getAllTable( AccessorPool<T,BoatAccessor<T,CourseCombo>> accessorPool ,
			CellDecorator<T> cellDecorator,Comparator<T> comparator,String specialClass){
		StringBuilder stringBuilder = new StringBuilder();
		LinkedList<BoatAccessor<T, ?>> tmpList = new LinkedList<>();
		for(BoatAccessor<T, ?> accessor:accessorPool.getAllBoats()){
			boolean hasAdd = false;
			for(int i=0;i<tmpList.size();i++){
				if(comparator.compare(tmpList.get(i).getIdentifier(),accessor.getIdentifier())>0){
					tmpList.add(i,accessor);
					hasAdd=true;
					break;
				}
			}
			if(!hasAdd){
				tmpList.add(accessor);
			}
		}
		int i=1;
		for(BoatAccessor<T, ?> accessor:tmpList){
			stringBuilder.append(getTable((BoatAccessor<T,CourseCombo>)accessor,cellDecorator,specialClass));
			if(i%2==0)
				stringBuilder.append("<div style=\"page-break-after :always\"></div>");
			i++;
		}
		return stringBuilder.toString();
	}
	

	
	public <T>String getTable(BoatAccessor<T,CourseCombo> scheduler, CellDecorator<T> cellDecorator,String specialClass) {
		final String[] weekDays = new String[]{"\u661f\u671f\u4e00","\u661f\u671f\u4e8c","\u661f\u671f\u4e09","\u661f\u671f\u56db","\u661f\u671f\u4e94"};
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<table class=\"scheduleTable\">");
		stringBuilder.append("<thead><tr>");
		String title=scheduler.getIdentifier().toString();
		stringBuilder.append("<th colspan=\"7\" class=\"title\">课程表</th></tr>");
		stringBuilder.append("<tr><th colspan=\"4\" class=\"subtitle\">").append(title).append("</th>");
		stringBuilder.append("<th colspan=\"4\" class=\"titletime\">2014年下期</th>");
		stringBuilder.append("</tr>");
		stringBuilder.append("</tdead><tbody><tr><td></td><td>\u7b2c1\u8282</td><td>\u7b2c2\u8282</td><td>\u7b2c3\u8282</td><td class=\"split\">\u7b2c4\u8282</td><td>\u7b2c5\u8282</td><td>\u7b2c6\u8282</td></tr>");

		String[][] tableContent = new String[5][6];
		for(CourseCombo course:scheduler.getAll()){
			String rawString =  cellDecorator.decorate(course);
			tableContent[course.getWeekDay().getWeekDayInteger()][course.getTime().getTime()] =rawString.replace("\r\n","<br>").replace("\n","<br>");
		}
		for(int i=0;i<tableContent.length;i++){
			stringBuilder.append("<tr>").append("<td>").append(weekDays[i]).append("</td>");
			for(int j=0;j<tableContent[i].length;j++){
				String prefix="<td class=\""+specialClass+"\">";
				if(j==3){
					prefix="<td class=\""+specialClass+" split\">";
				}
				if(tableContent[i][j]==null){
					stringBuilder.append(prefix+"</td>");				
				}else{
					stringBuilder.append(prefix).append(tableContent[i][j]).append("</td>");					
				}
			}
			stringBuilder.append("</tr>");	
		}
		stringBuilder.append("</tbody></table>");
		return stringBuilder.toString();
	}

}


