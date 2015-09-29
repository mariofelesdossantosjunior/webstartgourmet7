/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startgourmet7;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author mario
 */
public class ManipulaZip {
    public static void zip(File file) {
        byte[] buffer = new byte[1024];

        try {
            // Stream de saida
            FileOutputStream fos = new FileOutputStream(file);

            // Zip de saida
            ZipOutputStream zos = new ZipOutputStream(fos);

            // Arquivo a ser zipdo
            ZipEntry ze = new ZipEntry(file.getName());
            System.out.println("zipando...: " + file.getName());
            System.out.println("em: " + file.getAbsolutePath());
            // Adciona arquivo no Zip de saida
            zos.putNextEntry(ze);

            // Ler o Arquivo que sera Zipado
            FileInputStream in = new FileInputStream(file.getName());

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            // Fecha Arquivos
            in.close();

            // Fecha Zip e entrada
            zos.closeEntry();
            zos.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void unZip(File file) {

        byte[] buffer = new byte[1024];

        try {

            // Cria o input do arquivo ZIP
            ZipInputStream zinstream = new ZipInputStream(new FileInputStream(file));

            // Pega a proxima entrada do arquivo
            ZipEntry zentry = zinstream.getNextEntry();
            System.out.println("Estraindo...: " + file.getName());
            System.out.println("em: " + file.getAbsolutePath());
            // Enquanto existir entradas no ZIP
            while (zentry != null) {
                // Pega o nome da entrada
                String entryName = zentry.getName();
                System.out.println("File: " + entryName);
                // Cria o output do arquivo , Sera extraido onde esta rodando a classe
                FileOutputStream outstream = new FileOutputStream(new File(file.getAbsolutePath().replace(file.getName(), entryName)));
                int n;
                // Escreve no arquivo
                while ((n = zinstream.read(buffer)) > -1) {
                    outstream.write(buffer, 0, n);
                }
                // Fecha arquivo
                outstream.close();

                // Fecha entrada e tenta pegar a proxima
                zinstream.closeEntry();
                zentry = zinstream.getNextEntry();
            }

            // Fecha o zip como um todo
            zinstream.close();

            System.out.println("Done");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
