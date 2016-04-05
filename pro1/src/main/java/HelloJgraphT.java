import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asia on 2016/3/30.
 * ���󣺸����Զ�������������graphtͼ������·
 * ˼·��1.��ȡ.txt�ļ�������ڵ����·��Ϣ��
 *       2.ѭ�������ڵ�ͱߣ�
 *       3.D�㷨�����·����
 */
public class HelloJgraphT extends JApplet{
    //���������̳���JApplet�ࣨΪ��jgraph��ͼ�ã�����Լ���׳ɵ�init
    public void init(){
        String result="";
        int a=0;
        int b=0;
        int c=0;
        try {
            //����txt�нڵ����·����
            String s=null;
            //д���ļ����·���������Ҳ���·����
            FileReader word = new FileReader("D:/Maven/maven practice/pro1/src/main/java/graph.txt");
            BufferedReader br =new BufferedReader(word);
            //��������ͼg(jgrapht)������ʼ��
            DefaultDirectedGraph<Integer,DefaultEdge> g = new DefaultDirectedGraph<Integer, DefaultEdge>(DefaultEdge.class);
            //�����ݶ���Map�У���Ϊtxt���ݸ�ʽ������ظ�����Map���ϲ����أ���һһ���ǣ�
            Map<Integer,String> map = new HashMap<>(); //Map�Ǹ��ӿڣ�����ʵ������HashMap��key������a/b��valueû�У�
            while ((s=br.readLine())!=null){
                result=s;
                a = Integer.parseInt(result.substring(1,2));
                b = Integer.parseInt(result.substring(3,4));
                c = Integer.parseInt(result.substring(6,7));
                g.addVertex(a);
                g.addVertex(b);
                if (c==1)
                {
                    g.addEdge(a, b);
                }
                map.put(a, null);//����map�У����÷������ص���value���Զ�����String���ͣ�
                map.put(b,null);
                result=null+"\n";//����һ���жϣ������
            }
            br.close();

            //��������ͼg��Ӧ��jgraphͼ
            JGraphModelAdapter jma = new JGraphModelAdapter(g);
            JGraph jgraph = new JGraph(jma);
            getContentPane().add(jgraph);//��JApplet����ߵķ���
            defaultPositions(map,jma);

            //������ͼjgrapht������·()
            DijkstraShortestPath dsp = new DijkstraShortestPath(g,a,b);
            int x=0;        //������ʼ�ڵ�
            int y=3;        //����Ŀ�Ľڵ�
            System.out.println(x+"---->"+y+" ShortestPath:");
            System.out.println(dsp.findPathBetween(g, x, y));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    //���ܺ���1����jgraph����ͼ����λ�÷��䣨��Žڵ��Map������adapter��
    private void defaultPositions(Map<Integer,String> zz,JGraphModelAdapter ad) {
        int nodeNum = 4;           //�ڵ���
        int colNum = 2;            //ָ������
        int rowNum = nodeNum / colNum;
        int x = 0, y = 0;
        for (Map.Entry<Integer,String > entry :zz.entrySet()) {  //����ŵ�Map a�е½ڵ����һ�飨Mapͨ�õķ�����
            if (x == 2) {
                x = 0;
                y++;
            }
            positionVertexAt(entry.getKey(),ad,x * 200, y * 70);//���ù��ܺ���2
            x++;
        }

    }
    //���ܺ���2���Խڵ㣨�����Σ�����λ�ã�Map��key����ڵ㣬����adapter�������γ��Ϳ�
    private void positionVertexAt(Object vertex,JGraphModelAdapter adapter, int x, int y) {
        DefaultGraphCell cell = adapter.getVertexCell(vertex);
        Map attr = cell.getAttributes();
        Rectangle2D b = GraphConstants.getBounds(attr);
        GraphConstants.setBounds(attr, new Rectangle(x, y, (int) (b.getWidth()/2), (int)(b.getHeight()/1.3)));
        Map cellAttr = new HashMap();
        cellAttr.put(cell, attr);
        adapter.edit(cellAttr, null, null, null);
    }

}
