package A8;

import A7.WeightedEdge;
import A7.WeightedGraph;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class pane1 extends Application{

    private WeightedGraph<String> graph;
    private List<WeightedEdge> edges = new ArrayList<>();
    private List<String> vertices = new ArrayList<>();
    private List<double[]> verXY = new ArrayList<>();
    private Circle[] V;
    private Text[] Vname;
    private Text[] WeightText;
    private Line[] edgesLines;
    private Pane center = new Pane();
    private Text tips = new Text();

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Text AddVertex = new Text("Add a new Vertex");
        Text VertexName = new Text("Vertex Name");
        TextField vertexNameField = new TextField();
        HBox hBox1 = new HBox(VertexName,vertexNameField);
        Text XOfVertex = new Text("X-Coordinate");
        TextField XInput = new TextField();
        HBox hBox2 = new HBox(XOfVertex,XInput);
        Text YOfVertex = new Text("Y-Coordinate");
        TextField YInput = new TextField();
        HBox hBox3 = new HBox(YOfVertex,YInput);
        Button addV = new Button("Add Vertex");
        VBox footLeft = new VBox(AddVertex,hBox1,hBox2,hBox3,addV);
        footLeft.setSpacing(5);
        footLeft.setAlignment(Pos.CENTER);
        Text AddEdge = new Text("Add a new Edge");
        Text vertexU = new Text("Vertex U (index)");
        TextField UInput = new TextField();
        HBox hBox4 = new HBox(vertexU,UInput);
        Text vertexV = new Text("Vertex V (index)");
        TextField VInput = new TextField();
        HBox hBox5 = new HBox(vertexV,VInput);
        Text WeightTips = new Text("    Weight  (int) ");
        TextField Weight = new TextField();
        HBox hBox6 = new HBox(WeightTips,Weight);
        Button addE = new Button("Add Edge");
        VBox footRight = new VBox(AddEdge,hBox4,hBox5,hBox6,addE);
        footRight.setSpacing(5);
        footRight.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(footLeft,footRight);
        hBox.setSpacing(250);
        hBox.setAlignment(Pos.CENTER);
        Button bestLocation = new Button("查看最佳选址地点");

        Button initGraph = new Button("使用默认地图");
        Button StartOver = new Button("重新开始");
        HBox footButton = new HBox(bestLocation,initGraph,StartOver);//
        footButton.setSpacing(50);
        footButton.setAlignment(Pos.CENTER);
        VBox buttom = new VBox(hBox,footButton);
        buttom.setAlignment(Pos.CENTER);
        initGraph.setOnAction(e->{
            initGraph();
            ShowGraph(-1);
        });
        if(vertices.size()!=0||edges.size()!=0){
            ShowGraph(-1);
        }
        StartOver.setOnAction(e->{
            tips.setText("此操作将会清除所有内容,请确认");
            vertices.clear();
            edges.clear();
            verXY.clear();
            tipsPage(primaryStage);
        });


        addV.setOnAction(e-> {

            try {
                if(vertexNameField.getText().length()==0||XInput.getText().length()==0||
                        YInput.getText().length()==0)
                    throw new NumberFormatException();
                center.getChildren().removeAll(center.getChildren());
                vertices.add(vertexNameField.getText());
                double[] temp = {Double.parseDouble(XInput.getText()), Double.parseDouble(YInput.getText())};
                verXY.add(temp);
                ShowGraph(-1);
            }catch (NumberFormatException ex){
                tips.setText("请输入有效数据！");
                tipsPage(primaryStage);
            }
        });

        addE.setOnAction(e->{
            try {
                center.getChildren().removeAll(center.getChildren());
                if (Integer.parseInt(UInput.getText()) < 0 || Integer.parseInt(UInput.getText()) >= vertices.size()
                        || Integer.parseInt(VInput.getText()) < 0 || Integer.parseInt(VInput.getText()) >= vertices.size()) {
                    tips.setText("请输入有效的下标！");
                    tipsPage(primaryStage);
                }
                else{
                    edges.add(new WeightedEdge(Integer.parseInt(UInput.getText()),
                            Integer.parseInt(VInput.getText()), Integer.parseInt(Weight.getText())));
                    edges.add(new WeightedEdge(Integer.parseInt(VInput.getText()),
                            Integer.parseInt(UInput.getText()), Integer.parseInt(Weight.getText())));
                    ShowGraph(-1);
                }
            }
             catch (NumberFormatException ex){
                tips.setText("请输入有效数据！");
                tipsPage(primaryStage);
            }
        });

        bestLocation.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            BestLocation test = new BestLocation();
            graph = new WeightedGraph<>(edges,vertices);
            ShowGraph(test.getBestLocation(graph));
        });

        borderPane.setCenter(center);
        borderPane.setBottom(buttom);
        primaryStage.setScene(new Scene(borderPane,1000,600));
        primaryStage.setTitle("Graph");
        primaryStage.show();
    }

    private void ShowGraph(int index){

        V = new Circle[vertices.size()];
        Vname = new Text[vertices.size()];

        for(int i=0;i<V.length;i++){
            V[i] = new Circle(verXY.get(i)[0],verXY.get(i)[1],5);
            Vname[i] = new Text(verXY.get(i)[0]-10,verXY.get(i)[1]-10,vertices.get(i)+"("+i+")");
            if(index == i){
                V[i].setStroke(Color.RED);
                V[i].setFill(Color.RED);
                Vname[i].setStroke(Color.RED);
            }
            center.getChildren().addAll(V[i],Vname[i]);
        }

        edgesLines = new Line[edges.size()];
        WeightText = new Text[edges.size()];

        for(int i=0;i<edges.size();i++){
            edgesLines[i] = new Line(verXY.get(edges.get(i).u)[0],verXY.get(edges.get(i).u)[1],
                    verXY.get(edges.get(i).v)[0],verXY.get(edges.get(i).v)[1]);
            //System.out.println("i="+i+"edges.get(i).u="+edges.get(i).u+"  edges.get(i).v="+edges.get(i).v);
            //显示权重
            WeightText[i] = new Text((verXY.get(edges.get(i).u)[0]+verXY.get(edges.get(i).v)[0])/2,
                    (verXY.get(edges.get(i).u)[1]+verXY.get(edges.get(i).v)[1])/2,edges.get(i).weight+"");

            center.getChildren().addAll(edgesLines[i],WeightText[i]);

            drawArrowLine(edgesLines[i].getStartX(),edgesLines[i].getStartY(),
                    edgesLines[i].getEndX(),edgesLines[i].getEndY());


        }
    }

    //提示界面
    public void tipsPage(Stage primaryStage){
        Button button = new Button("确定");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tips,button);
        vBox.setAlignment(Pos.CENTER);

        button.setOnAction(e->{
            pane1 temp = new pane1();
            temp.vertices = new ArrayList<>(vertices);
            temp.edges = new ArrayList<>(edges);
            temp.verXY = new ArrayList<>(verXY);
            try {
                temp.start(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        Scene scene = new Scene(vBox,300,100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //初始化一张图
    private void initGraph(){

        String[] StrTemp = {"Seattle","Denver",
                         "Chicago","Boston",};

        double[][] XYTemp = {{150, 50}, {600, 160}, {200, 400},
                {700, 300}};

        int[][] edgesTemp = {
                {0, 2,3},
                {1, 0,2},
                {2, 1,7}, {2, 3,1},
                {3, 0,6}};

        for(int i=0;i<StrTemp.length;i++)
            vertices.add(StrTemp[i]);

        for(int i=0;i<XYTemp.length;i++)
            verXY.add(XYTemp[i]);

        for(int i=0;i<edgesTemp.length;i++)
            edges.add(new WeightedEdge(edgesTemp[i][0],edgesTemp[i][1],edgesTemp[i][2]));

    }
    //画箭头
    public void drawArrowLine(double x1, double y1,double x2, double y2) {

        // find slope of this line
        double slope = (y1 - y2) / (x1 - x2);
        double arctan = Math.atan(slope);

        // This will flip the arrow 45 off of a
        // perpendicular line at pt x2
        double set45 = 1.57 / 4;

        // arrows should always point towards i, not i+1
        if (x1 < x2) {
            // add 90 degrees to arrow lines
            set45 = -1.57 * 1.75;
        }

        // set length of arrows
        int arrlen = 20;

        // draw arrows on line
        Line[] lines = new Line[2];
        lines[0] = new Line(x2, y2, (int) ((x2 + (Math.cos(arctan + set45) * arrlen))),
                (int) (((y2)) + (Math.sin(arctan + set45) * arrlen)));
        lines[1] = new Line(x2, y2, (int) ((x2 + (Math.cos(arctan - set45) * arrlen))),
                (int) (((y2)) + (Math.sin(arctan - set45) * arrlen)));
        lines[0].setStroke(Color.RED);
        lines[1].setStroke(Color.RED);
        center.getChildren().addAll(lines[0],lines[1]);
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
