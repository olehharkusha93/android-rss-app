package com.example.oleh.rssreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Oleh on 2/9/2017.
 */

public class ReadPCWorlRss extends AsyncTask<Void, Void, Void> {
    Context context;

    String address = "http://www.pcworld.com/index.rss";

    ProgressDialog progressDialog;
    ArrayList<Items> feedItems;
    RecyclerView recyclerView;
    URL url;

    public ReadPCWorlRss(Context context, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        MyAdapter adapter=new MyAdapter(context,feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalSpace(50));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessXml(Getdata());

        return null;
    }


    private void ProcessXml(Document data) {
        if (data != null) {
            feedItems=new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node cureentchild = items.item(i);
                if (cureentchild.getNodeName().equalsIgnoreCase("item")) {
                    Items item=new Items();
                    NodeList itemchilds = cureentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node cureent = itemchilds.item(j);
                        if (cureent.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(cureent.getTextContent());
                        }/*else if (cureent.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(cureent.getTextContent());
                        }*/else if (cureent.getNodeName().equalsIgnoreCase("pubDate")){
                            DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm", Locale.ENGLISH);
                            Date date = null;
                            try {
                                date = dateFormat.parse(cureent.getTextContent());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String CurrentString = date.toString();
                            String[] separated = CurrentString.split(" ");
                            String s = separated[0].trim(); // Thu
                            String s1 = separated[1].trim(); //Feb
                            String s2 = separated[2].trim(); //16
                            String s3 = separated[5].trim(); //2017
                            String s4 = separated[3].trim(); //16:00
                            String[] s4sep = s4.split(":");
                            String t = s4sep[0] + ":" + s4sep[1];

                            String full = s1 + ". " + s2 + ", " + s3 +", "+ t;

                            item.setPubDate(full);
                        }else if (cureent.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(cureent.getTextContent());
                        }else if(cureent.getNodeName().equalsIgnoreCase("enclosure")){
                            String url = cureent.getAttributes().item(0).getTextContent();
                            item.setThumbnailUrl(url);
                        }
                    }
                    feedItems.add(item);
                }
            }
        }
    }

    public Document Getdata() {
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
