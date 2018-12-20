package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 * @author ViNguyen
 */
public class IOFile {
    public static void writeFile(List ob,String s){
        ObjectOutputStream outf;
        try{
            outf = new ObjectOutputStream(new FileOutputStream(s));
            for(Object ol : ob) {
                outf.writeObject(ol);
            }
            outf.close();
        }
        catch(IOException ex) {}
    }
    
    public static void readFile(List ob,String s){
        ob.clear();
        ObjectInputStream inf;
        try {
            inf = new ObjectInputStream(new FileInputStream(s));
            Object obj = null;
            try{
                while((obj = inf.readObject())!= null) {
                    Object ol = (Object)obj;
                    ob.add(ol);
                }
                inf.close();
            }
            catch(ClassNotFoundException ex) {}
        }
        catch(IOException ex) {}
    }
}
