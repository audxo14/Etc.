package DrawingGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
 
public class DrawingGraph{
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void main(HashMap<String, Integer> keywords, String[] key_list) {
		
		Graph<String, String> g = new SparseMultigraph<String, String>();
		Set key = keywords.keySet();
		
		for (int i = 0; i < key_list.length; i++)
		{
			g.addVertex(key_list[i].trim());
		}
		
		for (Iterator iterator = key.iterator(); iterator.hasNext();)
		{
			String keyName = (String) iterator.next();
			String[] key_val = keyName.split("-");
			
			if (keywords.get(keyName) == null)
				continue;
			else
			{
				g.addEdge(keyName, key_val[0], key_val[1]);
			}
		}
		
		Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
			 public Paint transform(String str) {
			 return Color.RED;
			 }
		};

		Layout<String, String> layout = new CircleLayout<String, String>(g);
		layout.setSize(new Dimension(300, 300));
		
		VisualizationViewer<String, String> vv = new VisualizationViewer<String, String>(layout);
		
		DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		gm.setMode(ModalGraphMouse.Mode.PICKING);
		vv.setGraphMouse(gm);
		
		vv.getRenderContext().setEdgeStrokeTransformer(new ConstantTransformer(new BasicStroke(2.0f)));
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
		JFrame frame = new JFrame();
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
		}
}