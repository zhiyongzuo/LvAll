package com.epro.web_socket;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private WebSocketClient mWebSocketClient;
    //注意更改成为你的服务器地址，格式："ws://ip:port/项目名字/websocket入口地址
//    private String address = "ws://10.1.40.159:58080/gzrq/websocket/socketServer";
    private String address = "ws://192.168.102.164:8080/gzrq/websocket/socketServer";
    private TextView tv;
    private TextView tvDisconnect;
    private TextView tvSendMsg;
    private EditText etMsg;
    private TextView tvStatus;
    private WebSocket mWebSocket;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == 0x111){
                String news = "";
                news = msg.getData().getString("news");
                Toast.makeText(MainActivity.this, "收到消息:"+news, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tvDisconnect = findViewById(R.id.tv_disconnect);
        etMsg = findViewById(R.id.edit_msg);
        tvSendMsg = findViewById(R.id.tv_send_msg);
        tvStatus = findViewById(R.id.tv_status);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    initSocketClient();
//                    mWebSocketClient.connect();
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }

//                try {
//                    MyWebSocketClient client = new MyWebSocketClient( new URI(address), new Draft_10() );
//                    boolean f = client.connectBlocking();
//                    System.out.println("connectBlocking: "+ f);
//                    //判断连接状态,
//                    if (client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
//                        System.out.println("成功链接!");
//                        client.send("hello, is TestSocketClient...");
//                    }
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                setLongConnect();
            }
        });

        tvDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeConnect();
            }
        });
        tvSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMsg.setText("");
                sendMsg(etMsg.getText().toString().trim());
            }
        });

    }

    private void initSocketClient() throws URISyntaxException {
        if(mWebSocketClient == null) {
            mWebSocketClient = new WebSocketClient(new URI(address)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    //连接成功
                    Log.i("LOG","opened connection");
                }
                @Override
                public void onMessage(String s) {
                    //服务端消息来了
                    Log.i("LOG","received:" + s);
                    Message msg = Message.obtain();
                    msg.what = 0x111;
                    Bundle bundle = new Bundle();
                    bundle.putString("news",s);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }

                @Override
                public void onClose(int i, String s, boolean remote) {
                    //连接断开，remote判定是客户端断开还是服务端断开
                    Log.i("LOG","Connection closed by " + ( remote ? "remote peer" : "us" ) + ", info=" + s);
                    //
                    closeConnect();
                }

                @Override
                public void onError(Exception e) {
                    Log.i("LOG","error:" + e);
                }
            };
        }
    }

    //断开连接
    private void closeConnect() {
        try {
            mWebSocketClient.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            mWebSocketClient = null;
        }
    }
    //向服务器发送消息的方法
    private void sendMsg(String msg) {
//        mWebSocketClient.send(msg);
        mWebSocket.send(msg);
    }





    final class EchoWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            String openid = "1";
            //连接成功后，发送登录信息
            String message = "{\"type\":\"login\",\"user_id\":\""+openid+"\"}";
//            mSocket.send(message);
            output("连接成功！");
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            output("receive bytes:" + bytes.hex());
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            output("receive text:" + text);
////            //收到服务器端发送来的信息后，每隔25秒发送一次心跳包
//            final String message = "{\"type\":\"heartbeat\",\"user_id\":\"heartbeat\"}";
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    mSocket.send(message);
//                }
//            },25000);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            output("closed:" + reason);
            setLongConnect();
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            output("closing:" + reason);
            setLongConnect();
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            output("failure:" + t.getMessage());
            setLongConnect();
        }
    }

    private void setLongConnect() {
        //长连接
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3000, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        Request request = new Request.Builder().url(address).build();
        EchoWebSocketListener socketListener = new EchoWebSocketListener();
        mWebSocket = mOkHttpClient.newWebSocket(request, socketListener);
        mOkHttpClient.dispatcher().executorService().shutdown();
    }

    private void output(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("xiao---login-llong", text);
                tvStatus.setText("状态：" + text);
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
