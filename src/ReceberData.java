// T1 REDES
// Grupo 02
// OPÇÃO 4 – Mensagem Instantânea
//Caio Fernando Peres 769298
//Eduardo Ravagnani de Melo 771004
//Leonardo Valerio Morales 771030
//Luís Felipe Dobner Henriques 771036


import javafx.application.Platform;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ReceberData implements Runnable {

	// Socket servidor eh utilizado para manter o socket de conexao estabelecido.
    Socket servidor;
    MainController ctrl;
    boolean executar = true;

    //construtor
    public ReceberData(Socket servidor, MainController ctrl) {
        this.servidor = servidor;
        this.ctrl = ctrl;
    }

    //método para parar essa thread de outra thread
    public void parar(){
        executar = false;
    }


    //o java exige o uso da keyword synchronized para interação entre threads
    //não tem espera ocupada nessa thread, pois o método read() bloqueia a thread caso não tenha dados para ler
    @Override
    public synchronized void run(){

        InputStream in = null;

        //estabelece a stream in para receber dados
        try {
            in = servidor.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        while (executar) {
            try {

                //le a mensagem recebida para um buffer de bytes
                byte[] msg = new byte[500];
                in.read(msg);

                //convertendo mensagem para string
                String s = new String(msg, StandardCharsets.UTF_8);
				
				// Mandamos a string pra UI.
                ctrl.atribuirUI(s);
            } catch (Exception e) {
                ctrl.alertError("Conexão Encerrada!");
                this.parar();
            }
        }

    }

}