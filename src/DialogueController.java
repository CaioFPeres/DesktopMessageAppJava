// T1 REDES
// Grupo 02
// OPÇÃO 4 – Mensagem Instantânea
//Caio Fernando Peres 769298
//Eduardo Ravagnani de Melo 771004
//Leonardo Valerio Morales 771030
//Luís Felipe Dobner Henriques 771036

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.net.InetAddress;

public class DialogueController {

    //uso da annotation @FXML para que o fxml encontre a relação do design em fxml com o código

    @FXML
    private Button ButtonConectar;
    @FXML
    private Button ButtonHospedar ;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField IPField;

    private String nome;
    private InetAddress IP;
    private MainController ctrl;
    private Stage dialogueStage;
    private Stage secondStage;

    public void initialize(){
        ButtonConectar.setOnMouseClicked(buttonHandler1);
        ButtonHospedar.setOnMouseClicked(buttonHandler2);
    }

    //event handlers (interação assíncrona do usuario com o programa/UI)

    EventHandler<MouseEvent> buttonHandler1 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent){
            handlerConectar();
        }
    };

    EventHandler<MouseEvent> buttonHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            handlerHospedar();
        }
    };

    EventHandler<MouseEvent> closeHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            secondStage.close();
        }
    };


    //cria nova janela a partir do fxml existente
    private void newWindow(){

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("mainPane.fxml"));

        try {
            Parent root = mainLoader.load();
            ctrl = mainLoader.getController();
            ctrl.setNome(nome);
            if(IP != null){
                ctrl.setIP(IP);
            }
            ctrl.conectar();

            secondStage = new Stage();
            Scene scene = new Scene(root, 600, 400);
            secondStage.setScene(scene);
            secondStage.setTitle("Chat TCP");
            secondStage.setResizable(false);
            secondStage.show();

            //manda a janela criada para o controlador de UI
            ctrl.setStage(secondStage);

        }
        catch(Exception e) {
            //nada
        }

        //fecha a janela atual
        dialogueStage.close();

        //precisa criar o listener depois de fechar o dialogue, se não ele fecha os 2.
        ctrl.createCloseListener();

    }

    // cria janela de alerta programaticamente (sem usar um fxml já definido) sobre input invalido.
    private void alert(String msg){

        secondStage = new Stage();
        Pane root = new Pane();
        Scene scene = new Scene(root);
        secondStage.setHeight(200);
        secondStage.setWidth(300);
        secondStage.setScene(scene);
        secondStage.setTitle("Erro!");

        Label label = new Label(msg);
        Button botao = new Button("Ok");


        botao.setTextAlignment(TextAlignment.CENTER);
        botao.setMinWidth(50);
        botao.setLayoutX(120);
        botao.setLayoutY(90);

        label.setPadding(new Insets(50, 30, 10, 80));

        root.getChildren().add(label);
        root.getChildren().add(botao);

        secondStage.setTitle("Chat TCP");
        secondStage.setResizable(false);
        secondStage.setAlwaysOnTop(true);
        secondStage.show();

        botao.setOnMouseClicked(closeHandler);

    }

    //pega o stage (janela) da classe principal main
    public void setStage(Stage dialogueStage){
        this.dialogueStage = dialogueStage;
    }

    //verifica qual foi a escolha do usuario e manda para o controlador da nova janela
    private void handlerConectar(){

        nome = nomeField.getText();

        if(nome.length() == 0){
            alert("Utilize nome e/ou IP válidos!");
            return;
        }

        if(IPField.getText().length() == 0){
            alert("Utilize nome e/ou IP válidos!");
            return;
        }

        try {
            IP = InetAddress.getByName(IPField.getText());
        }
        catch(Exception e){
            alert("Utilize nome e/ou IP válidos!");
            return;
        }

        newWindow();

    }

    private void handlerHospedar(){
        nome = nomeField.getText();

        if(nome.length() == 0){
            alert("Utilize nome e/ou IP válidos!");
            return;
        }

        newWindow();
    }


}
