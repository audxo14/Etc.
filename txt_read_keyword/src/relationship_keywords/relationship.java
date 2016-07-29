package relationship_keywords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

import DrawingGraph.DrawingGraph;
import Tfidf.Tfidf;



@SuppressWarnings("serial")
public class relationship extends JFrame{
	static HashMap<String, Integer> keywords = new HashMap<String, Integer>();
	static String current_dir;
	static String[] tmp_key_list; 
	static List<String> docu_list = new ArrayList<String>();
	static int list_size;	
	static Map<String, Long> map;
	static List<HashMap<String, Double>> tfidf_result = new ArrayList<HashMap<String, Double>>();
	static List<List<String>> result_list = new ArrayList<List<String>>();
	
	private static String ReadTxt(String cont_dir) throws IOException
	{
		File txt_file = new File(cont_dir);
		String txt_str = "";
		FileReader txt_Reader = new FileReader(txt_file);
        BufferedReader txt_reader = new BufferedReader(txt_Reader);

        String txt_line = null;
        while ((txt_line = txt_reader.readLine()) != null) {
        	if(txt_line.endsWith("-"))
        		txt_str += txt_line + "";
        	else
        		txt_str += txt_line + ", ";
        }
        txt_str = txt_str.trim();
        txt_reader.close();
        return txt_str.toLowerCase();
	}
	
	private static void SplitDocu(String txt)
	{
		String[] tmp_list = txt.split("\\--+");
		
		for (String docu : tmp_list)
		{
			docu_list.add(docu.trim());
		}
	}

	@SuppressWarnings("unchecked")
	private static List<String> sortByValue(final Map<String, Double> maps){
        List<String> list = new ArrayList<String>();
        list.addAll(maps.keySet());
         
        Collections.sort(list,new Comparator<Object>(){
             
            public int compare(Object o1,Object o2){
                Object v1 = maps.get(o1);
                Object v2 = maps.get(o2);
                 
                return ((Comparable<Object>) v1).compareTo(v2);
            }
             
        });
        Collections.reverse(list);
        return list;
    }
	
	/*
	@SuppressWarnings("unused")
	private static String pdf_manager(String current_dir) throws IOException
	{
		String result = "";
		String pdf_dir = current_dir.concat("\\pdf\\");
		File pdf_folder = new File(pdf_dir);
		PDFManager pdfManager = new PDFManager();
		
		if (!pdf_folder.isDirectory())
		{
			pdf_folder.mkdir();
			System.exit(0);
		}
		
		File[] pdf_list = pdf_folder.listFiles();
		
		for (File pdf_file:pdf_list)
		{
			//System.out.println(pdf_file.getName());
			String pdf_filename = pdf_file.getName();
			int str_len = pdf_filename.length();
			String is_pdf = pdf_filename.substring(str_len-3,str_len);
			if (!is_pdf.equals("pdf"))
				continue;

			pdfManager.setFilePath(pdf_file.getAbsolutePath());
			result += pdfManager.ToText().toLowerCase() + "----------------------";
		}
		
		return result;
	}
	*/
	public static void main(String[] args) throws IOException 
	{
		current_dir = System.getProperty("user.dir");	//Get the current Directory
		String cont_dir = current_dir.concat("\\contents.txt");	
		String stop_dir = current_dir.concat("\\stopword.txt");
		
		String txt_str = ReadTxt(cont_dir);
		String[] stop_list = ReadTxt(stop_dir).split(",");
		//String txt_str = pdf_manager(current_dir);
		SplitDocu(txt_str);
		
		Tfidf tfidf = new Tfidf();
		tfidf_result = tfidf.main(docu_list, stop_list);
		
		for (HashMap<String, Double> tmp_map: tfidf_result)
		{
			List<String> tmp_list = sortByValue(tmp_map);
			//HashMap<String, Double> tmp_val = new HashMap<String, Double>();
			/*
			for (int i = 0; i < 10; i ++)
			{
				String tmp_str = tmp_list.get(i);
				tmp_val.put(tmp_str, tmp_map.get(tmp_str));
			}
			*/
			
			result_list.add(tmp_list.subList(0, 4));
			
			//sort_result.add(tmp_val);			
		}
		
		System.out.println(result_list);
		DrawingGraph hello = new DrawingGraph();
		hello.main(result_list);
		
		//Tfidf tfidf = new Tfidf();
		//tfidf.TfandIdf();
		
    }
}
