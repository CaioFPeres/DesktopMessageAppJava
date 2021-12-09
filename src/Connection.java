// T1 REDES
// Grupo 02
// OPÇÃO 4 – Mensagem Instantânea
//Caio Fernando Peres 769298
//Eduardo Ravagnani de Melo 771004
//Leonardo Valerio Morales 771030
//Luís Felipe Dobner Henriques 771036

import java.io.*;
import java.net.*;


public class Connection {

		// Ip, Socket geral e ServerSocket que sera atribuido ao Socket geral caso seja host.
        private InetAddress IP;
        private Socket servidor = null;
        private ServerSocket localServer;
		
		// Threads que serao executados
        private Thread t1;
        private Thread t2;
        private EnviarData r2;
        private ReceberData r3;
		
		// Nome do usuario
        private String nome;
		
        private MainController ctrl;

        //define parametros de conexão nos construtores

        //se receber o ip, então é conexão do cliente
        Connection(String nome, MainController ctrl, InetAddress ip){
            this.nome = nome;
            this.ctrl = ctrl;
            this.IP = ip;

            try {
                cliente();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }

        //se não receber, é servidor
        Connection(String nome, MainController ctrl){
            this.nome = nome;
            this.ctrl = ctrl;

            try {
                servidor();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }


        public void cliente() throws IOException{

            //Como cliente, criamos o socket, atribuimos o ip e so depois damos connect para poder tratar o timeout.
            servidor = new Socket();
            SocketAddress sock = new InetSocketAddress(IP, 7777);

            try {
                servidor.connect(sock, 10000);
            }
            catch(Exception e){
                ctrl.alertError("Timeout na espera de um host.");
            }

			// Apos conectar o socket iniciamos a thread de envio e a thread de recepcao de dados.
            r2 = new EnviarData(servidor, nome, ctrl);
            t1 = new Thread(r2);
            t1.start();

            r3 = new ReceberData(servidor, ctrl);
            t2 = new Thread(r3);
            t2.start();


        }

        public void servidor() throws IOException{

			// Como servidor, criamos um ServerSocket na porta 7777, colocamos o timeout em 10 segundos para tratar o timeout e tentamos aceitar conexao.

            localServer = new ServerSocket(7777);
            //para um IP especifico
            /*
            IP = InetAddress.getByName("25.109.23.100");
            localServer = new ServerSocket(7777, 0, IP);
             */


            localServer.setSoTimeout(10000);

            try{
                servidor = localServer.accept();
            }
            catch (Exception e){
                ctrl.alertError("Timeout na espera da conexão.");
            }

			
			// Apos conectar o socket iniciamos a thread de envio e a thread de recepcao de dados.
            r2 = new EnviarData(servidor, nome, ctrl);
            t1 = new Thread(r2);
            t1.start();

            r3 = new ReceberData(servidor, ctrl);
            t2 = new Thread(r3);
            t2.start();

        }


        //notificar thread
        public void notificar(String dado){
            r2.enviarDado(dado);
        }

        //parar as threads
        public void pararThreads(){
            r2.parar();
            r3.parar();
        }

}