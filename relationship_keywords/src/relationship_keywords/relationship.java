package relationship_keywords;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import DrawingGraph.DrawingGraph;


@SuppressWarnings("serial")
public class relationship extends JFrame{
	JTextField KeyField;
	static HashMap<String, Integer> keywords = new HashMap<String, Integer>();
	static String current_dir;
	static String[] tmp_key_list; 
	static int list_size;	
	static Map<String, Long> map;
	
	relationship()
	{
		setTitle("Relationship between Keywords");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.WHITE);
		setLayout(new BorderLayout(0,0));
		JButton OkButton = new JButton("OK");
		KeyField = new JTextField("Write your Keywords");
		
		//add("hello",BorderLayout.NORTH);
		add(KeyField, BorderLayout.CENTER);
		add(OkButton, BorderLayout.SOUTH);
		
		OkButton.addActionListener(new Listener(this));
		
		setSize(300, 100);
		setResizable(false);
		setVisible(true);
	}
	
	private static void key_search(String pdf_str)
	{
		String first_word = "";
		String second_word = "";
		String bigram = "";
		String tmp_result = "";
		String tmp_key = "";
		ArrayList<String> bigram_list = new ArrayList<String>();

		Stream<String> stream = Stream.of(pdf_str.split("\\W+")).parallel();
		map = stream.collect(Collectors.groupingBy(String::toString,Collectors.counting()));
		
		Stream<String> bi_stream = Stream.of(pdf_str.split("\\W+")).parallel();
		List<String> bi_list = bi_stream.collect(Collectors.toList());
		
		for (int i = 0; i + 1 < bi_list.size(); i++)
		{
			first_word = bi_list.get(i);
			second_word = bi_list.get(i+1);
			if (first_word.equals(second_word))
				continue;
			bigram = first_word + " " + second_word;
			bigram_list.add(bigram);			
		}

		Set<String> tmp_set = new HashSet<String> (bigram_list);
		for (int i = 0; i < list_size; i++)
		{	
			if (tmp_set.contains(tmp_key_list[i].trim()))
			{
				tmp_result = tmp_key_list[i].trim() + "-";
				int j = i + 1;
				while (j < list_size)
				{
					if(tmp_set.contains(tmp_key_list[j].trim()))
					{
						tmp_key = tmp_result+tmp_key_list[j].trim();
						Integer count = keywords.get(tmp_key);
						keywords.put(tmp_key, (count == null) ? 1 : count + 1);
					}
					else if(map.get(tmp_key_list[j].trim()) == null)
					{
						tmp_key = tmp_result+tmp_key_list[j].trim();
						Integer count = keywords.get(tmp_key);
						keywords.put(tmp_key, (count == null) ? count : count);
					}
					else
					{
						tmp_key = tmp_result+tmp_key_list[j].trim();
						Integer count = keywords.get(tmp_key);
						keywords.put(tmp_key, (count == null) ? 1 : count + 1);						
					}
					j++;
				}				
			}
			else if (map.get(tmp_key_list[i].trim()) == null)
			{
				tmp_result = tmp_key_list[i].trim() + "-";
				int j = i + 1;
				while (j < list_size)
				{
					if(tmp_set.contains(tmp_key_list[j].trim()))
					{
						tmp_key = tmp_result+tmp_key_list[j].trim();
						Integer count = keywords.get(tmp_key);
						keywords.put(tmp_key, (count == null) ? count : count);
					}
					else if(map.get(tmp_key_list[j].trim()) == null)
					{
						tmp_key = tmp_result+tmp_key_list[j].trim();
						Integer count = keywords.get(tmp_key);
						keywords.put(tmp_key, (count == null) ? count : count);
					}
					else
					{
						tmp_key = tmp_result+tmp_key_list[j].trim();
						Integer count = keywords.get(tmp_key);
						keywords.put(tmp_key, (count == null) ? count : count);						
					}
					j++;
				}
			}
			else if(map.get(tmp_key_list[i].trim()) > Long.valueOf(0))
			{
				tmp_result = tmp_key_list[i].trim() + "-";
				int j = i + 1;
				while (j < list_size)
				{
					if(tmp_set.contains(tmp_key_list[j].trim()))
					{
						tmp_key = tmp_result+tmp_key_list[j].trim();
						Integer count = keywords.get(tmp_key);
						keywords.put(tmp_key, (count == null) ? 1 : count + 1);
					}
					else if(map.get(tmp_key_list[j].trim()) == null)
					{
						tmp_key = tmp_result+tmp_key_list[j].trim();
						Integer count = keywords.get(tmp_key);
						keywords.put(tmp_key, (count == null) ? count : count);
					}
					else
					{
						tmp_key = tmp_result+tmp_key_list[j].trim();
						Integer count = keywords.get(tmp_key);
						keywords.put(tmp_key, (count == null) ? 1 : count + 1);						
					}
					j++;
				}
			}
		}
	}
	
	private static void pdf_manager(String current_dir) throws IOException
	{
		list_size = tmp_key_list.length;
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
			String tmp_str = pdfManager.ToText().toLowerCase().split("introduction")[0].replaceAll("\r\n", " ");
			
			key_search(tmp_str);	
		}
		
		DrawingGraph hello = new DrawingGraph();
		hello.main(keywords, tmp_key_list);
	}
	
	private static void MakeHashMap(String keys) throws IOException
	{
		tmp_key_list = keys.toLowerCase().split(",");
		int list_size = tmp_key_list.length;
				
		for (int i = 0; i < list_size; i++)
		{
			int j = i + 1;
			while (j < list_size)
			{
				keywords.put(tmp_key_list[i].trim() + "-" + tmp_key_list[j].trim(), null);
				j++;
			}
		}
		pdf_manager(current_dir);
		keywords.clear();
	}

	class Listener implements ActionListener
	{
		JFrame frame;
		
		public Listener(JFrame f)
		{
			frame = f;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				MakeHashMap(KeyField.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) throws IOException 
	{
		current_dir = System.getProperty("user.dir");	//Get the current Directory
		@SuppressWarnings("unused")
		relationship mf = new relationship();
    }
}
