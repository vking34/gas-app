package com.dungkk.gasorder.extensions;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.dungkk.gasorder.Manager;
import com.dungkk.gasorder.passingObjects.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class receiveMess extends IntentService {

    private Socket socket;

    public receiveMess()
    {
        super("receiving Messages");
    }
    public receiveMess(String name, Socket s) {
        super(name);
        this.socket = s;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("receiveMess", "started");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String inString = "";
        Log.e("receiveMess", "init...");
        try {

//            assert intent != null;
            socket = new Socket(Server.getIPaddress(), 6060);

            DataInputStream in = new DataInputStream(socket.getInputStream());

            while (true) {
                inString = in.readUTF();
//                System.out.println("Server says: " + inString);
                Log.e("Manager", "Server says: " + inString);
                if (inString.equals("bye")) {
                    break;
                }
            }
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}