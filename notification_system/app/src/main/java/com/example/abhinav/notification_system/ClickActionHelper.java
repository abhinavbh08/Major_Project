package com.example.abhinav.notification_system;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by abhinav on 3/4/17.
 */
public class ClickActionHelper {
    public static void startActivity(String className, Bundle extras, Context context){
        Class cls;
        try {
            cls = Class.forName(className);
            Intent i = new Intent(context, cls);
            i.putExtras(extras);
            context.startActivity(i);
        }catch(ClassNotFoundException e){
            Log.d("LOL",className+"hahaha");
            Log.d("LOL","Wrong Class name Input");

        }

    }
}
