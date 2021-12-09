// T1 REDES
// Grupo 02
// OPÇÃO 4 – Mensagem Instantânea
//Caio Fernando Peres 769298
//Eduardo Ravagnani de Melo 771004
//Leonardo Valerio Morales 771030
//Luís Felipe Dobner Henriques 771036


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.InetAddress;


public class MainController {

    //uso da annotation @FXML para que o fxml encontre a relação do design em fxml com o código

    @FXML
    private TextArea TextArea;
    @FXML
    private TextField TextInput ;
    @FXML
    private Button ButtonEnviar;

    private String nome;
    private Connection c;
    private MainController ctrl;
    private InetAddress IP;
    private Stage secondStage;
    private Stage thisStage;


    @FXML
    public void initialize() {
        ctrl = this;
        ButtonEnviar.setOnMouseClicked(buttonHandler);

    }

    //event handlers (interação assíncrona do usuario com o programa/UI)

    EventHandler<WindowEvent> windowEvent = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent we) {
            finalizar();
        }
    };

    EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            c.notificar(TextInput.getText());
        }
    };

    EventHandler<MouseEvent> fechaButton = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            secondStage.close();
        }
    };


    //método necessário para que o listener de fechamento da janela atual seja criado
    public void createCloseListener(){
        thisStage.setOnCloseRequest(windowEvent);
    }

    //método para atribuir mensagem ao chat da janela principal por outras classes
    public void atribuirUI(String str_mensagem){

        //javafx exige o uso do método Platform.runLater para interagir com a UI a partir de outra thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                TextArea.setText(TextArea.getText() + "\n" + str_mensagem);
            }
        });
    }

    //metodos set
    public void setNome(String nome){
        this.nome = nome;
    }

    public void setIP(InetAddress ip){
        this.IP = ip;
    }

    public void setStage(Stage stage){
        this.thisStage = stage;
    }

    //método para criar conexão
    public void conectar(){
        if(IP != null){
            c = new Connection(nome, ctrl, IP);
        }
        else {
            c = new Connection(nome, ctrl);
        }

    }

    // esse alert é em caso de erros e já fecha o programa. Cria uma nova janela programaticamente (sem usar um fxml já definido)
    public void alertError(String msg){

        //Platform.runLater para criar janela a partir de outra thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

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

                //cria click listener no botao
                botao.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        finalizar();
                    }
                });

                //coloca o listener de janela já existente na janela de alerta para o fechamento
                secondStage.setOnCloseRequest(windowEvent);

            }
        });

    }

    public void alert(String msg){

        //Platform.runLater para criar janela a partir de outra thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

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


                //cria click listener no botao
                botao.setOnMouseClicked(fechaButton);
            }
        });

    }

    //método chamado para finalizar o programa
    public void finalizar(){
        if(c != null)
            c.pararThreads();
        if(secondStage != null)
            secondStage.close();
        thisStage.close();
        Platform.exit();
        System.exit(0);
    }


}




