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
	
	public <T>void addContent( AccessorPool<T,BoatAccessor<T,CourseCombo>> accessorPool , CellDecorator<T> cellDecorator,Comparator<T> comparator){
		stringBuffer.append(getAllTable(accessorPool, cellDecorator,comparator));
	}
	
	public <T>String getAllTable( AccessorPool<T,BoatAccessor<T,CourseCombo>> accessorPool , CellDecorator<T> cellDecorator,Comparator<T> comparator){
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
			stringBuilder.append(getTable((BoatAccessor<T,CourseCombo>)accessor,cellDecorator));
            if(i%2==0)
                stringBuilder.append("<div style=\"PAGE-BREAK-AFTER: always\"></div>");
            i++;
		}
		return stringBuilder.toString();
	}
	

	
	public <T>String getTable(BoatAccessor<T,CourseCombo> scheduler, CellDecorator<T> cellDecorator) {
		final String[] weekDays = new String[]{"\u661f\u671f\u4e00","\u661f\u671f\u4e8c","\u661f\u671f\u4e09","\u661f\u671f\u56db","\u661f\u671f\u4e94"};
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<table class=\"scheduleTable\">");
		stringBuilder.append("<thead><tr><th colspan=\"7\" class=\"title\">\u8bfe\u7a0b\u8868</th></tr><tr>");
		String title=scheduler.getIdentifier().toString();
		stringBuilder.append("<th colspan=\"4\" class=\"identifier\">").append(title).append("</th>");
        stringBuilder.append("<th colspan=\"3\" class=\"titletime\">2014\u5e74\u4e0b\u671f</th></tr>");
		stringBuilder.append("</thead><tbody>");
        stringBuilder.append("<tr><td></td><td>\u7b2c1\u8282</td><td>\u7b2c2\u8282</td><td>\u7b2c3\u8282</td><td>\u7b2c4\u8282</td><td>\u7b2c5\u8282</td><td>\u7b2c6\u8282</td></tr>");

		String[][] tableContent = new String[5][6];
		for(CourseCombo course:scheduler.getAll()){
			String rawString =  cellDecorator.decorate(course);
			tableContent[course.getWeekDay().getWeekDayInteger()][course.getTime().getTime()] =rawString.replace("\r\n","<br>").replace("\n","<br>");
		}
		for(int i=0;i<tableContent.length;i++){
			stringBuilder.append("<tr>").append("<td>").append(weekDays[i]).append("</td>");
			for(int j=0;j<tableContent[i].length;j++){
				if(tableContent[i][j]==null){
					stringBuilder.append("<td></td>");				
				}else{
					stringBuilder.append("<td>").append(tableContent[i][j]).append("</td>");					
				}
			}
			stringBuilder.append("</tr>");	
		}
		stringBuilder.append("</tbody></table>");
		return stringBuilder.toString();
	}

}


