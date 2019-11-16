
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
 Universidade Estadual de Feira de Santana (UEFS)
 CURSO: Engenharia de Computação
 DISCIPLINA: EXA869 - MI - Processadores de Linguagem de Programação
 TUTOR: Alberlan

 PROBLEMA 01 - ANALISADOR LÉXICO
 */
/**
 *
 * @author Emanuel Santana e Johnny Santana
 */
public class LerArquivos {

    private ArrayList<String> linhas;
    private FileReader arquivo;
    private BufferedReader leitor;
    private int quantLinha = 0;

    public LerArquivos() {
        this.linhas = new ArrayList();

    }

    public ArrayList<String> abrirArquivo(File nome) throws IOException {
        
        this.arquivo = new FileReader(nome);
        this.leitor = new BufferedReader(arquivo);

        while (leitor.ready()) {
            String linhaAtual = leitor.readLine();
            linhas.add(linhaAtual);
            quantLinha++;
        }
        arquivo.close();
        leitor.close();

        return linhas;
    }

    public void escreveArq(ArrayList<String> lis, String nome) throws IOException {
        File ar;
        FileWriter escArq;
        PrintWriter escrita;

        ar = new File(nome + ".txt");
        escArq = new FileWriter(ar);
        escrita = new PrintWriter(escArq);

        for (int i = 0; i < lis.size(); i++) {
            escrita.println("Linha - " + lis.get(i) + " ");
        }
        escrita.flush();

    }

    public void imprimeConteudo() {
        for (int i = 0; i < this.linhas.size(); i++) {
            System.out.println(linhas.get(i));
        }
    }

    public int qtdLinhas() {
        return quantLinha;
    }

}
