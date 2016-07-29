package DrawingGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.util.List;

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
	public void main(List<List<String>> key_list) {
		
		Graph<String, String> g = new SparseMultigraph<String, String>();
		
		for (List<String> tmp_list: key_list)
		{
			for (String tmp_str: tmp_list)
			{
				g.addVertex(tmp_str);
			}
		}
		
		for (List<String> tmp_list: key_list)
		{
			for(int i = 0; i < tmp_list.size(); i++)
			{
				for (int j = i + 1; j < tmp_list.size(); j++)
					g.addEdge(tmp_list.get(i)+"-"+tmp_list.get(j), tmp_list.get(i), tmp_list.get(j));
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		}
}