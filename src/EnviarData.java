// T1 REDES
// Grupo 02
// OPÇÃO 4 – Mensagem Instantânea
//Caio Fernando Peres 769298
//Eduardo Ravagnani de Melo 771004
//Leonardo Valerio Morales 771030
//Luís Felipe Dobner Henriques 771036

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class EnviarData implements Runnable {

	// Socket servidor eh utilizado para manter o socket de conexao estabelecido.
    Socket servidor;
	
	// Uma string que sera recebida da UI quando o usuario enviar uma mensagem.
    String str_mensagem;
	
	// Nome do Usuario
    String nome;
	
    boolean executar = true;
    MainController ctrl;

    //costrutor
    public EnviarData(Socket servidor, String nome, MainController ctrl) {
        this.servidor = servidor;
        this.nome = nome;
        this.ctrl = ctrl;
    }

    //o java exige o uso da keyword synchronized para interação entre threads
    public synchronized void enviarDado(String dado){
		// Quando a UI manda uma string pro processo de envio de dados setamos a str_mensagem como esse dado.
        this.str_mensagem = dado;
        this.notify();
    }

    //método para parar essa thread de outra thread
    public void parar(){
        executar = false;
    }


    //o java exige o uso da keyword synchronized para interação entre threads
    @Override
    public synchronized void run(){

        OutputStream saida;

        try {
            saida = servidor.getOutputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        while (executar){
            try {

                //método para não utilizar espera-ocupada (necessita synchronized)
                this.wait();
				
				// Apos ser notificado que existe uma nova string no buffer.

                //escreve os bytes da mensagem no buffer da saída juntamente com o nome do usuario concatenado.
                byte mensagem[] = new String(nome + ": " + str_mensagem).getBytes(StandardCharsets.UTF_8);
                saida.write(mensagem, 0, mensagem.length);

                // Flush para enviar (certifica-se de que enviou tudo).
                saida.flush();

                ctrl.atribuirUI(new String(mensagem));

            } catch (Exception e) {
                ctrl.alertError("Conexão Encerrada!");
            }

        }

    }
};