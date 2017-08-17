package mx.edu.uaz.utils;

import java.util.Random;

/**
 * Created by jaetmar on 4/3/17.
 */
public class CadenaAleatoria {

    public static String getCadenaAleatoria (int longitud){
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);

        int i = 0;
        while ( i < longitud){
            char c = (char)r.nextInt(255);
            if ( (c >= '0' && c <=9) || (c >='A' && c <='Z') ){
                cadenaAleatoria += c;
                i ++;
            }
        }
        return cadenaAleatoria;
    }
}