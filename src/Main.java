// T1 REDES
// Grupo 02
// OPÇÃO 4 – Mensagem Instantânea
//Caio Fernando Peres 769298
//Eduardo Ravagnani de Melo 771004
//Leonardo Valerio Morales 771030
//Luís Felipe Dobner Henriques 771036

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // metodo start é o primeiro chamado quando extende a Application no javafx
    @Override
    public void start(Stage primaryStage) throws Exception{

        //carrega UI
        FXMLLoader dialogueLoader = new FXMLLoader(getClass().getResource("dialogue.fxml"));

        //pega root pane
        Parent dialogue = dialogueLoader.load();

        //pega controlador
        DialogueController dialCtrl = (DialogueController) dialogueLoader.getController();

        //configura dialogue window
        Scene dialScene = new Scene(dialogue, 390, 190);
        primaryStage.setTitle("Chat TCP");
        primaryStage.setScene(dialScene);
        primaryStage.setResizable(false);
        primaryStage.show();

        //manda esse stage para que ele possa ser fechado na classe da UI
        dialCtrl.setStage(primaryStage);


    }


    public static void main(String[] args) {
        launch(args);
    }
}
