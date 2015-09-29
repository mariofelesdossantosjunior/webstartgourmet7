/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startgourmet7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author Vinicius
 */
public class Database {

    private static final String DRIVER_NAME = "org.postgresql.Driver";

    static {
        try {
            Class.forName(DRIVER_NAME).newInstance();
            System.out.println("*** Driver loaded");
        } catch (Exception e) {
            System.out.println("*** Error : " + e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            e.printStackTrace();
        }

    }

    private static String INSTRUCTIONS = new String();

    public static Connection getConnection(String URL, String USER, String PASSWORD) throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://192.168.0.187:5432/" + URL, USER, PASSWORD);
    }

    public static void create(String url, String user, String password) throws SQLException {
        String s = new String();
        StringBuffer sb = new StringBuffer();
        String caminhoSqlUpdate = System.getProperty("user.home")+"/gourmet7/update.sql";

        try {
            FileReader fr = new FileReader(new File(caminhoSqlUpdate));
            // be sure to not have line starting with "--" or "/*" or any other non aplhabetical character  

            BufferedReader br = new BufferedReader(fr);

            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            br.close();

            // here is our splitter ! We use ";" as a delimiter for each request  
            // then we are sure to have well formed statements  
            String[] inst = sb.toString().split(";");

            Connection c = Database.getConnection(url, user, password);

            Statement st = c.createStatement();

            for (int i = 0; i < inst.length; i++) {
                // we ensure that there is no spaces before or after the request string  
                // in order to not execute empty statements  
                if (!inst[i].trim().equals("")) {
                    st.executeUpdate(inst[i]);
                    System.out.println(">>" + inst[i]);
                }
            }
            c.close();
        } catch (Exception e) {
            System.out.println("*** Error : " + e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            e.printStackTrace();
            System.out.println("################################################");
            System.out.println(sb.toString());
        }
    }
}
