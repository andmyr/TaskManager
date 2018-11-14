package com.example.homeuser.taskmanager.loaders;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.example.homeuser.taskmanager.enums.MechanizmType;
import com.example.homeuser.taskmanager.factories.MechanizmFactory;
import com.example.homeuser.taskmanager.models.Data;
import com.example.homeuser.taskmanager.models.Mechanizm;
import com.example.homeuser.taskmanager.models.Mechanizms;
import com.example.homeuser.taskmanager.models.MechanizmsItemsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DataLoader extends AsyncTask<Void, Void, Data> {
    private final AssetManager assets;
    private GetJsonFromAssetsCallback callback;

    public DataLoader(AssetManager assets, GetJsonFromAssetsCallback callback) {
        this.assets = assets;
        this.callback = callback;
    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = assets.open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected Data doInBackground(Void... voids) {
        Data data = new Data();
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("mechanizms");
            Mechanizms mechanizms = new Mechanizms();
            data.setMechanizms(mechanizms);
            mechanizms.setMechanizmsItemsLists(new ArrayList<MechanizmsItemsList>());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject joInside = jsonArray.getJSONObject(i);
                String name = joInside.getString("name").toLowerCase();
                JSONArray jsonArrayItems = joInside.getJSONArray("items");
                MechanizmsItemsList mechanizmsItemsList = new MechanizmsItemsList();
                mechanizmsItemsList.setName(name);
                mechanizmsItemsList.setMechanizmList(new ArrayList<Mechanizm>());
                for (int j = 0; j < jsonArrayItems.length(); j++) {
                    JSONObject item = jsonArrayItems.getJSONObject(j);
                    Mechanizm mechanizm = new Mechanizm() {
                        @Override
                        public String getName() {
                            return null;
                        }
                    };
                    try {
                        switch (name) {
                            case "cars":
                                mechanizm = MechanizmFactory.createMechanizm(MechanizmType.CAR, item.getString("name"));
                                break;
                            case "ships":
                                mechanizm = MechanizmFactory.createMechanizm(MechanizmType.SHIP, item.getString("name"));
                                break;
                            case "planes":
                                mechanizm = MechanizmFactory.createMechanizm(MechanizmType.PLANE, item.getString("name"));
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mechanizmsItemsList.getMechanizmList().add(mechanizm);
                }
                mechanizms.getMechanizmsItemsLists().add(mechanizmsItemsList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(Data data) {
        super.onPostExecute(data);
        callback.getResult(data);
    }

    public interface GetJsonFromAssetsCallback {
        void getResult(Data data);
    }
}