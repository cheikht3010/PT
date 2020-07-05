/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.io.FileInputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;

public class JavaFXApplication1 extends Application {

Stage window;
Scene scene1,scene2;
public static void main(String[] args) {
    launch(args);
}

@Override
public void start(Stage primaryStage) throws Exception{
    window = primaryStage;
     Label PrenomLabel = new Label("Prenom :");
     Label NomLabel = new Label("Nom :");
     Label CreerLabel = new Label("tu dois parle dans 3 minutes");
     TextField Prenom = new TextField(); 
     TextField Nom = new TextField();
     Prenom.setLayoutX(500);
     Prenom.setLayoutY(300);
     Nom.setLayoutX(500);
     Nom.setLayoutY(350);
     PrenomLabel.setLayoutX(500);
     PrenomLabel.setLayoutY(275);
     NomLabel.setLayoutX(500);
     NomLabel.setLayoutY(330);
     PrenomLabel.setFont(Font.font(STYLESHEET_CASPIAN, 15));
     NomLabel.setFont(Font.font(STYLESHEET_CASPIAN, 15));
     CreerLabel.setLayoutX(800);
     CreerLabel.setLayoutY(420);
     CreerLabel.setFont(Font.font(STYLESHEET_CASPIAN, 20));
    //titre de l'application
    primaryStage.setTitle("speaker recognition");
    //label sur l'application "label"
    Label label = new Label("SPEAKER RECOGNITION");
    //label du test "label1"
    Label label1 = new Label();
    label1.setLayoutX(750);
    label1.setLayoutY(165);
    label1.setFont(Font.font(STYLESHEET_CASPIAN, 20));
    label.setLayoutX(20);
    label.setLayoutY(20);
    label.setFont(Font.font(STYLESHEET_CASPIAN, 50));
    Button btn = new Button("CREER MODEL");
    Button btn2 = new Button("TESTER");
    Button btn1 = new Button("CREER MODEL");
    FileInputStream input = new FileInputStream("background .jpg");
    Image image = new Image(input);
    FileInputStream input1 = new FileInputStream("background .jpg");
    Image image1 = new Image(input1);
    //BACKGROUND
    ImageView mv = new ImageView(image);
    ImageView mv1 = new ImageView(image1);
    mv.setFitHeight(700);
    mv.setFitWidth(1100);
    mv1.setFitHeight(700);
    mv1.setFitWidth(1100);
    btn.setLayoutX(30);
    btn.setLayoutY(200);
    btn2.setLayoutX(800);
    btn2.setLayoutY(200);
    
    btn.setOnAction(e->window.setScene(scene2));
    btn1.setOnAction(e->window.setScene(scene1));
    btn1.setLayoutX(800);
    btn1.setLayoutY(450);
    label1.setText("veuillez dire bonjour dans 5 seconde");
     
     
    //btn1.setOnAction(e->window.setScene(scene1));
    btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               
                try {
                    label1.setText("veuillez dire bonjour dans 5 seconde ");
                    System.out.println("veuillez dire bonjour dans 5 seconde ");
                    
                    Date date = new Date();
                    Date date1 = new Date();
                    long y,x;
                    double z;
                    
                    
                    y= System.currentTimeMillis();
                    for (int i = 0; i < 5; i++) {
                        
                         Thread.sleep(1000);
                    }
                   
                    
                    System.out.println("c'est terminer");
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("c'est termine");
                    alert.setContentText("le test est terminer");
                    alert.show();
                    x=System.currentTimeMillis();
                    z= (x-y)/ 1000F;
                    System.out.println(z);
                    /*  for (int i = 0; i < 100000; i++) {
                    date1.getTime();
                    long x= date1.getTime()-y;
                    System.out.println(x);
                    }*/
                } catch (InterruptedException ex) {
                    Logger.getLogger(JavaFXApplication1.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
                }

     
            
        });
    
    Pane root1 = new Pane();
    Pane root = new Pane();
    
    root.getChildren().add(mv);
    root.getChildren().add(label);
    root.getChildren().add(label1);
    root.getChildren().add(btn);
    root.getChildren().add(btn2);
    scene1=new Scene(root,1100,700);
    
    root1.getChildren().add(mv1);
    root1.getChildren().add(btn1);
    root1.getChildren().add(Prenom);
    root1.getChildren().add(Nom);
    root1.getChildren().add(PrenomLabel);
    root1.getChildren().add(NomLabel);
    root1.getChildren().add(CreerLabel);
    
    

    
    scene2= new Scene(root1,1100,700);
    window.setScene(scene1);
    window.show();
}
}