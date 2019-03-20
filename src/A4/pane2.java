package A4;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class pane2 extends Application{
    int[] num = new int[4];
    List<Integer> numTemp;
    List<String> result;
    int count = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        //Top
        StackPane stackPane = new StackPane();
        HBox top = new HBox();
        TextField textField1 = new TextField();
        Button Find = new Button("Find a Solution");
        Button Refresh = new Button("Refresh");
        top.getChildren().addAll(Find,textField1,Refresh);
        top.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(top);
        borderPane.setTop(stackPane);

        //Center
        HBox center = new HBox();
        numTemp = getCards(center);
        borderPane.setCenter(center);

        //Refresh界面
        Refresh.setOnAction(e->{
            RefreshStage(primaryStage);
        });


        //Find a Solution
        Find.setOnAction(e->{

            for(int i=0;i<4;i++){
                num[i] = numTemp.get(i);
            }

            if(result == null){
                PointGameOf24 judge = new PointGameOf24();
                try {
                    result = judge.getResult(num);
                } catch (ScriptException e1) {
                    e1.printStackTrace();
                }
            }
            else{
                if(result.size() == 0){
                    textField1.setText("No Solution!");
                }
                else{
                    textField1.setText(result.get(count++));
                    if(count >= result.size()){
                        count = 0;
                    }
                }
            }

        });

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("PointGameOf24");
        primaryStage.show();
    }

    //刷新Stage
    private void RefreshStage(Stage primaryStage){
        pane2 temp =new pane2();
        try {
            temp.start(primaryStage);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    //获取四张牌
    private List<Integer> getCards(HBox hBox){
        List<Integer> num = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

        for(int i=0;i<4;i++){
            int index = (int)(Math.random()*52+1);
            //不能出相同的牌
            if(temp.contains(index)){
                i--;
                continue;
            }
            temp.add(index);
            num.add(index % 13 == 0 ? 13: index % 13);
            ImageView imageView = new ImageView(new Image("A4/cards/" +index+".png"));
            hBox.getChildren().add(imageView);
        }
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);
        return num;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
