/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startgourmet7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class ExecutaCmdWindows {

    public synchronized String execCommand(final String commandLine) {

        boolean success = false;
        String result;

        Process p;
        BufferedReader input;
        StringBuffer cmdOut = new StringBuffer();
        String lineOut = null;
        int numberOfOutline = 0;

        try {

            p = Runtime.getRuntime().exec(commandLine);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((lineOut = input.readLine()) != null) {
                if (numberOfOutline > 0) {
                    cmdOut.append("\n");
                }
                cmdOut.append(lineOut);
                numberOfOutline++;
            }

            result = cmdOut.toString();

            success = true;

            input.close();

        } catch (IOException e) {
            result = String.format("Falha ao executar comando %s. Erro: %s", commandLine, e.toString());
        }

        // Se não executou com sucesso, lança a falha  
        if (!success) {
            try {
                throw new IOException(result);
            } catch (IOException ex) {
                Logger.getLogger(WebStartGourmet7.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return result;

    }
}
