package com.whijj.xmlpull;

import android.os.Handler;
import android.os.Message;
import android.service.carrier.CarrierMessagingService;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG="ssssssssssssssss";
    private static final int SHOW_XML=1;
    private Button bt;
    private ListView listView;
    private List<Information> list;
    private InfoAdapter infoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt= (Button) findViewById(R.id.xml_bt);
        listView= (ListView) findViewById(R.id.listView);
        list= new ArrayList<Information>();
        infoAdapter=new InfoAdapter(this,list);
        listView.setAdapter(infoAdapter);
        listView.setOnItemClickListener(this);
        bt.setOnClickListener(this);
    }
    public Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_XML:
                    Bundle bundle= (Bundle) msg.obj;
                    String id=bundle.getString("id");
                    String name=bundle.getString("name");
                    String version=bundle.getString("version");
                    list.add(new Information(id,name,version));
                    infoAdapter.notifyDataSetChanged();
                    break;

                default:
                    break;

            }
        }
    };
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.xml_bt:
               SendRequestHttp();
               break;
           default:
               break;
       }
    }
    private void SendRequestHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection=null;
                try {
                    URL url=new URL("http://www.hijj.wang/jiexi.xml");
                    httpURLConnection= (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    BufferedReader reader=new BufferedReader(new InputStreamReader(
                            httpURLConnection.getInputStream()
                    ));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append(line);
                    }

                    Log.d("sssssssssss",response.toString());
                    parseXML(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(httpURLConnection!=null){
                        httpURLConnection.disconnect();
                    }
                }
            }
        }).start();
    }
    private void parseXML(String XmlData){
        try{
            XmlPullParserFactory xmlPullParserFactory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(XmlData));
            String id="";
            String name="";
            String version="";
            int evenType=xmlPullParser.getEventType();
            while(evenType!=XmlPullParser.END_DOCUMENT){
                String nodeName=xmlPullParser.getName();
                switch (evenType){
                    case XmlPullParser.START_TAG:{
                        if("id".equals(nodeName)){
                            id=xmlPullParser.nextText();
                        }
                        else if("name".equals(nodeName)){
                            name=xmlPullParser.nextText();
                        }
                        else if("version".equals(nodeName)){
                            version=xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        if("app".equals(nodeName)){
                            Message msg=new Message();
                            Bundle bundle=new Bundle();
                            bundle.putString("id","id: "+id);
                            bundle.putString("name","name: "+name);
                            bundle.putString("version","version: "+version);
                            msg.obj=bundle;
                            msg.what=SHOW_XML;
                            handler.sendMessage(msg);
                            Log.d(TAG,"id: "+id);
                            Log.d(TAG,"name: "+name);
                            Log.d(TAG,"version: "+version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                evenType=xmlPullParser.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Information info=list.get(position);
        Toast.makeText(MainActivity.this,info.getId(),Toast.LENGTH_LONG).show();
    }
}
