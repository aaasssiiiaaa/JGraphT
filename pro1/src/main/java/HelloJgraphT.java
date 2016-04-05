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
 * 需求：根据自定义数据来构建grapht图，并算路
 * 思路：1.读取.txt文件，读入节点和链路信息；
 *       2.循环创建节点和边；
 *       3.D算法算最短路径。
 */
public class HelloJgraphT extends JApplet{
    //主函数，继承了JApplet类（为了jgraph画图用），用约定俗成的init
    public void init(){
        String result="";
        int a=0;
        int b=0;
        int c=0;
        try {
            //读入txt中节点和链路数据
            String s=null;
            //写成文件相对路径（报错找不到路径）
            FileReader word = new FileReader("D:/Maven/maven practice/pro1/src/main/java/graph.txt");
            BufferedReader br =new BufferedReader(word);
            //创建有向图g(jgrapht)，并初始化
            DefaultDirectedGraph<Integer,DefaultEdge> g = new DefaultDirectedGraph<Integer, DefaultEdge>(DefaultEdge.class);
            //将数据读入Map中（因为txt数据格式读入会重复，而Map集合不会重，会一一覆盖）
            Map<Integer,String> map = new HashMap<>(); //Map是个接口，常用实现类是HashMap（key放整型a/b，value没有）
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
                map.put(a, null);//存入map中，但该方法返回的是value（自定义是String类型）
                map.put(b,null);
                result=null+"\n";//多了一行判断，待解决
            }
            br.close();

            //创建有向图g对应的jgraph图
            JGraphModelAdapter jma = new JGraphModelAdapter(g);
            JGraph jgraph = new JGraph(jma);
            getContentPane().add(jgraph);//是JApplet类里边的方法
            defaultPositions(map,jma);

            //对有向图jgrapht进行算路()
            DijkstraShortestPath dsp = new DijkstraShortestPath(g,a,b);
            int x=0;        //给定初始节点
            int y=3;        //给定目的节点
            System.out.println(x+"---->"+y+" ShortestPath:");
            System.out.println(dsp.findPathBetween(g, x, y));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    //功能函数1：对jgraph画的图进行位置分配（存放节点的Map，传入adapter）
    private void defaultPositions(Map<Integer,String> zz,JGraphModelAdapter ad) {
        int nodeNum = 4;           //节点数
        int colNum = 2;            //指定列数
        int rowNum = nodeNum / colNum;
        int x = 0, y = 0;
        for (Map.Entry<Integer,String > entry :zz.entrySet()) {  //将存放的Map a中德节点遍历一遍（Map通用的方法）
            if (x == 2) {
                x = 0;
                y++;
            }
            positionVertexAt(entry.getKey(),ad,x * 200, y * 70);//调用功能函数2
            x++;
        }

    }
    //功能函数2：对节点（长方形）给定位置（Map的key所存节点，传入adapter，长方形长和宽）
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
