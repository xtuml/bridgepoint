package org.xtuml.bp.cli;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PlatformUI;

public class LaunchWorkbenchAdvisor extends BPCLIWorkbenchAdvisor {
    
    private static int IDLE = 0;
    private static int INITIALIZED = 1;
    private static int READY = 2;
    private static int RUNNING = 3;
    private static int TERMINATED = 4;
    
    private int server_state = IDLE;
    
    private int init_port;

    protected LaunchWorkbenchAdvisor(BPCLIPreferences prefs) {
        super(prefs);
        try {
            init_port = Integer.parseInt(cmdLine.getStringValue("-port"));
        }
        catch ( NumberFormatException e ) {
            System.out.println("Invalid port number.");
            init_port = -1;
        }
    }

    @Override
    public void postStartup() {
        super.postStartup();
        final LaunchWorkbenchAdvisor advisor = this;
        Thread runner = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // create server
                    System.out.println("Creating server...");
                    ServerSocket server = new ServerSocket();
                    server.bind(new InetSocketAddress(0));
                    server_state = INITIALIZED;

                    // connect to the CLI server and send port
                    Socket socket = new Socket();
                    try {
                        // try to connect for 30 seconds
                        System.out.println("Connecting socket...");
                        int timeout = 30;
                        int sleeptime = 250;
                        int try_num = 0;
                        while ( true ) {
                            try {
                                socket.connect(new InetSocketAddress("localhost", init_port));
                                break;
                            } catch ( ConnectException e ) {
                                if ( try_num > (timeout * ( 1000 / sleeptime )) ) {
                                    System.out.println("Connection timeout.");
                                    break;
                                }
                                else socket = new Socket();
                            }
                            try_num++;
                            try {
                                Thread.sleep(sleeptime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        // write the port
                        if ( socket.isConnected() ) {
                            System.out.println("Sending port number...");
                            OutputStream out = socket.getOutputStream();
                            out.write((Integer.toString(server.getLocalPort())+"\n").getBytes());
                            out.flush();
                            socket.close();
                            server_state = READY;
                        }
                    } catch ( IllegalArgumentException e ) {
                        // do nothing
                    } catch ( IOException e ) {
                        e.printStackTrace();
                    }
                    
                    // start server
                    if ( READY == server_state ) {
                        System.out.println("Server running.");
                        server_state = RUNNING;
                        SocketHandler socketHandler = new SocketHandler(advisor, server);
                        Thread handlerThread = new Thread(socketHandler);
                        handlerThread.start();
                        while ( RUNNING == server_state ) {
                            try {
                                Socket sock = server.accept();
                                socketHandler.appendTaskQueue(sock);
                            } catch ( SocketException e ) {/* do nothing */}
                        }
                    }
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    System.out.println("Launch complete. Exiting.");
                    if (!debug) {
                        PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                PlatformUI.getWorkbench().close();
                            }
                        });
                    }
                }
            }
        });
        runner.setName("BP CLI Launch");
        runner.start();
    }
    
    public void changeServerState( int state ) {
        server_state = state;
    }
    
    private class SocketHandler implements Runnable {
        
        private static final String OK_RESPONSE = "OK";
        private static final String FAILURE_RESPONSE = "FL";

        private ConcurrentLinkedDeque<Command> taskQueue;
        
        private LaunchWorkbenchAdvisor advisor;
        private ServerSocket server;

        public SocketHandler( LaunchWorkbenchAdvisor advisor, ServerSocket server) {
            this.advisor = advisor;
            this.server = server;
            taskQueue = new ConcurrentLinkedDeque<Command>();
        }

        @Override
        public void run() {
            // handle commands
            while ( RUNNING == server_state ) {
                Command command = taskQueue.poll();
                if ( null != command && null != command.getCmd() ) {
                    String response = handleCommand(command.getCmd());
                    command.sendResponse(response);
                }
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        public void appendTaskQueue( Socket socket ) {
            // get command data
            InputStream in;
            ByteArrayOutputStream data = new ByteArrayOutputStream();
            try {
                in = socket.getInputStream();
                int c;
                // read one line from the socket
                while ( -1 != (c=in.read()) && '\n' != c ) {
                    data.write(c);
                }
                taskQueue.add( new Command(data.toString().trim(), socket) );
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        
        private String handleCommand( String cmd ) {
            if ( null == cmd || "".equals(cmd) ) return FAILURE_RESPONSE;

            // print command
            System.out.println("Executing command: " + cmd);
            
            // Check for exit command
            if ( "exit".equals(cmd) ) {
                // terminate the server
                advisor.changeServerState(TERMINATED);
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return OK_RESPONSE;
            }
            
            // execute the command
            Executor executor = null;
            if ( cmd.startsWith("Import") ) {
                String[] context = cmd.replaceFirst("Import", "").trim().split(" ");
                try {
                    BPCLIPreferences cmdLine = new  BPCLIPreferences(context, Import.getCommandLineOptions());
                    executor = new ImportExecutor(cmdLine);
                }
                catch ( BPCLIException e ) {
                    BPCLIPreferences.logError("Error during Launch: " + e.getMessage(), null);
                }
            }
            else if ( cmd.startsWith("Build") ) {
                String[] context = cmd.replaceFirst("Build", "").trim().split(" ");
                try {
                    BPCLIPreferences cmdLine = new  BPCLIPreferences(context, Build.getCommandLineOptions());
                    executor = new BuildExecutor(cmdLine);
                }
                catch ( BPCLIException e ) {
                    BPCLIPreferences.logError("Error during Launch: " + e.getMessage(), null);
                }
            }
            else if ( cmd.startsWith("Merge") ) {
                String[] context = cmd.replaceFirst("Merge", "").trim().split(" ");
                try {
                    BPCLIPreferences cmdLine = new  BPCLIPreferences(context, Merge.getCommandLineOptions());
                    executor = new MergeExecutor(cmdLine);
                }
                catch ( BPCLIException e ) {
                    BPCLIPreferences.logError("Error during Launch: " + e.getMessage(), null);
                }
            }
            else if ( cmd.startsWith("Execute") ) {
                String[] context = cmd.replaceFirst("Execute", "").trim().split(" ");
                try {
                    BPCLIPreferences cmdLine = new  BPCLIPreferences(context, Execute.getCommandLineOptions());
                    try {
                        executor = new ExecuteExecutor(cmdLine, false);
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                }
                catch ( BPCLIException e ) {
                    BPCLIPreferences.logError("Error during Launch: " + e.getMessage(), null);
                }
            }
            else {
                System.out.println("Unrecognized command");
            }
            if ( null != executor ) executor.execute();
            return OK_RESPONSE;
        }
    }
    
    private class Command {

        private String cmd;
        private Socket socket;

        public Command(String cmd, Socket socket) {
            this.cmd = cmd;
            this.socket = socket;
        }
        
        public String getCmd() {
            return cmd;
        }
        
        public void sendResponse( String msg ) {
            try {
                OutputStream out = socket.getOutputStream();
                out.write(msg.getBytes());
                out.flush();
                socket.close();
            } catch (SocketException e) {
                System.out.println("Socket has been closed by client.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
