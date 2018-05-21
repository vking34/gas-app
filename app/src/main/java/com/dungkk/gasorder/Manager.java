package com.dungkk.gasorder;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.view.View;
import com.dungkk.gasorder.extensions.MessageSender;
import com.dungkk.gasorder.extensions.receiveMess;
import com.dungkk.gasorder.passingObjects.Server;

import java.io.IOException;
import java.net.Socket;

public class Manager extends AppCompatActivity {


    private EditText et_mess;
    private Button btn_send;
//    private Socket socket;
//
//    public Socket getSocket() {
//        return socket;
//    }
//
//    public void setSocket(Socket socket) {
//        this.socket = socket;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        et_mess = (EditText) findViewById(R.id.et_mess);

        btn_send = (Button) findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                send(v);
            }
        });


//        try{
//            Log.e("SocketConnection", "Connecting ...");
//            socket = new Socket(Server.getIPaddress(), 6060);
//
//            Intent intent = new Intent(this, receiveMess.class);
//
//            intent.putExtra("socket", (Parcelable) socket);
//
//            startService(intent);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Intent intent = new Intent(this, receiveMess.class);
        startService(intent);
        Log.e("ManagerActivity", "created intent Service");
    }

//    private void send(View view){
//
//        MessageSender messageSender = new MessageSender();
//        messageSender.execute(et_mess.getText().toString());
//    }
}
