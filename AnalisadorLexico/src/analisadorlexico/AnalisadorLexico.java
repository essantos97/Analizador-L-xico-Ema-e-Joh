package analisadorlexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.LerArquivos;

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
public class AnalisadorLexico {

    FileReader arquivoEntrada;
    BufferedReader lerArquivo;
    static LerArquivos ler = new LerArquivos();
    static ArrayList<String> lErro = new ArrayList<>();
    static ArrayList<String> linhas = new ArrayList<>();
    static ArrayList<String> lexemas = new ArrayList<>();
    static ArrayList<Tokens> tokens = new ArrayList<>();
    
   


    /*Imprimir todas as linhas*/
    public void imprimeLinhas() {
        for (int i = 0; i < linhas.size(); i++) {
            System.out.println("Linha " + (i + 1) + ": " + linhas.get(i));
        }
        System.out.println("\n");
    }

    /*Imprimir total de linhas*/
    public void imprimeTotalLinhas() {
        System.out.println("Total de linhas: " + linhas.size() + "\n");
    }

    /*Imprimir ArrayList das linhas*/
    public void imprimeArrayLinhas() {
        System.out.println(linhas);
    }

    /*Imprimir caractere por caractere*/
    public void imprimeCaracteres() {
        for (int i = 0; i < linhas.size(); i++) {
            for (int j = 0; j < linhas.get(i).length(); j++) {
                System.out.println(linhas.get(i).charAt(j));
            }
        }
    }

    /*Se o c encontrado for um delimitador, determina o que deve ser inserido
     e gera um ArrayList de Tokens Separados*/
    public void separarTokens() {

        //Percorre cada linha, analisando caractere por caractere
        for (int i = 0; i < linhas.size(); i++) {
            
            int count = 0;
            int conta_aspas = 0;
            int conta_aspas_simples = 0;
            String temp = "";
            String delim = "";
            boolean erro = false;

            for (int j = 0; j < linhas.get(i).length(); j++) {

                //Pega caractere por caractere pelo index
                int c = linhas.get(i).charAt(j);

                //Se o caractere for um delimitador
                //ASCII 9 = TAB //ASCII 32 = ESPAÇO //ASCII 39 = ' //ASCII 40 = (
                //ASCII 42 = * //ASCII 43 = + //ASCII 44 = , //ASCII 45 = - //ASCII 47 = /
                //ASCII 59 = ; //ASCII 60 = < //ASCII 61 = = //ASCII 62 = > //ASCII 95 = _ //ASCII 123 = {
                if (c == 9 || c == 32 || c == 33  || c == 35 ||c == 36 ||c == 37 ||c == 38 || c == 39 || c == 40 || c == 41 || c == 42
                        || c == 43 || c == 44 || c == 45 || c == 47 || c == 59
                        || c == 60 || c == 61 || c == 62 || c == 63 || c == 64 || c == 94 || c == 95 
                        || c == 96 || c == 123|| c == 124|| c == 125|| c == 126  ) {
                    //Posição do delimitador
                    //delimitador = linhas.get(i).charAt(j);

                    //Se antes do delimitador houver caracteres acumulados, significa que há um token a ser adicionado
                    if (count > 0) {
                        Tokens t = new Tokens(i, temp, "TOKEN");
                        tokens.add(t);
                        count = 0;
                        temp = "";
                        System.out.println("Linha " + (i + 1));
                        System.out.println(">>TOKEN adicionado com sucesso!");
                    }
                    //Se o caractere for diferente de TAB e ESPAÇO
                    if (c != 9 && c != 32) {
                        //Se for ultimo caractere da linha
                        if (j == linhas.get(i).length() - 1) {
                            if (c == 34) {//Se for aspas
                                System.out.println("Linha " + (i + 1) + " ERRO");
                                System.out.println(">>Cadeia mal formada");
                            } else if (c == 39) {//Se for aspas_simples
                                System.out.println("Linha " + (i + 1) + " ERRO");
                                System.out.println(">>Caractere mal formado");
                            }
                            temp = temp + linhas.get(i).charAt(j);
                            Tokens t = new Tokens(i, temp, "Delimitador");
                            tokens.add(t);
                            System.out.println("Linha " + (i + 1));
                            System.out.println(">>Delimitador adicionado com sucesso!");
                        } //Se for aspas
                        else if (c == 34) {
                            System.out.println("QUE FULERAGEm");
                            conta_aspas++;
                            temp = temp + linhas.get(i).charAt(j);

                            //Se o primeiro caractere após o abre aspas for letra
                            if (conta_aspas % 2 != 0 && Character.isLetter(linhas.get(i).charAt(j + 1))) {
                                //Anda uma linha
                                System.out.println("TESTANDO SE FUNCIONA E PQ NÃO FUNCION");
                                //Enquanto não achar o fecha aspas e não finalizar a linha                               
                                while (j < linhas.get(i).length() - 1 && linhas.get(i).charAt(j) != 34) {
                                    temp = temp + linhas.get(i).charAt(j);
                                    j++;
                                }
                                //Se achar o fecha aspas
                                if (linhas.get(i).charAt(j) == 34) {
                                    conta_aspas++;
                                    temp = temp + linhas.get(i).charAt(j);
                                    Tokens t = new Tokens(i, temp, "Cadeia de Caracteres");
                                    tokens.add(t);
                                    temp = "";
                                    System.out.println("Linha " + (i + 1));
                                    System.out.println(">>Cadeia de Caracteres adicionada com sucesso!");
                                }//Se não achar o fecha aspas
                                else {
                                    erro = true;
                                    System.out.println("Linha " + (i + 1) + " ERRO");
                                    System.out.println(">>Cadeia mal formada");
                                    temp = temp + linhas.get(i).charAt(j);
                                    Tokens t = new Tokens(i, erro, temp, "Cadeia mal formada");
                                    tokens.add(t);
                                    temp = "";
                                }
                            }//Se o primeiro caractere após o abre aspas não for uma letra
                            else {
                                
                                System.out.println("NÂO ENTROU NO IF CERTO DEU BARRIL");
                                erro = true;
                                System.out.println("Linha " + (i + 1) + " ERRO");
                                System.out.println(">>Cadeia mal formada");
                                //Anda uma linha
                                j++;
                                //Enquanto não achar o fecha aspas e não finalizar a linha                               
                                while (j < linhas.get(i).length() - 1 && linhas.get(i).charAt(j) != 34) {
                                    temp = temp + linhas.get(i).charAt(j);
                                    j++;
                                }
                                //Se achar o fecha aspas
                                if (linhas.get(i).charAt(j) == 34) {
                                    conta_aspas++;
                                    temp = temp + linhas.get(i).charAt(j);
                                    Tokens t = new Tokens(i, erro, temp, "Cadeia mal formada");
                                    tokens.add(t);
                                    temp = "";
                                }//Se não achar o fecha aspas
                                else {
                                    temp = temp + linhas.get(i).charAt(j);
                                    Tokens t = new Tokens(i, erro, temp, "Cadeia mal formada");
                                    tokens.add(t);
                                    temp = "";
                                }
                            }

                        }//Se for aspas_simples
                         else {
                            delim = delim + linhas.get(i).charAt(j);
                            Tokens t = new Tokens(i, delim, "Delimitador");
                            tokens.add(t);
                            System.out.println("Linha " + (i + 1));
                            System.out.println(">>Delimitador adicionado com sucesso!");
                            delim = "";
                        }
                    }//Se for espaço e estiver dentro de aspas
                    else if (c == 32 && conta_aspas % 2 != 0) {
                        //Se for o primeiro caractere após o abre aspas
                        if (linhas.get(i).charAt(j - 1) == 34) {
                            temp = temp + linhas.get(i).charAt(j);
                        } else {
                            temp = temp + linhas.get(i).charAt(j);
                        }
                    }
                } //Se caractere não for delimitador e pertencer a gramática
                //ASCII 46 = . //ASCII 125 = }
                else if ((Character.isLetter(c) || Character.isDigit(c)) || c == 46 || c == 125 ||c == 34) {

                    //Se não for o fim da linha
                    if (j < linhas.get(i).length() - 1) {
                        temp = temp + linhas.get(i).charAt(j);
                        count++;
                    } //Se for o fim da linha
                    else if (j == linhas.get(i).length() - 1) {
                        temp = temp + linhas.get(i).charAt(j);
                        Tokens t = new Tokens(i, temp, "TOKEN");
                        tokens.add(t);
                        temp = "";
                        System.out.println("Linha " + (i + 1));
                        System.out.println(">>TOKEN adicionado com sucesso!");
                    }
                } //Se o caractere não pertencer a gramática
                else {
                    erro = true;
                    System.out.println("Linha " + (i + 1) + " ERRO");
                    System.out.println(">>Caractere ASCII " + c + " (" + linhas.get(i).charAt(j) + ")" + " não pertence a gramática");
                    //Se não for o fim da linha
                    if (j < linhas.get(i).length() - 1) {
                        temp = temp + linhas.get(i).charAt(j);
                        Tokens t = new Tokens(i, erro, temp, "Caractere desconhecido");
                        tokens.add(t);
                        temp = "";
                    } //Se for o fim da linha
                    else if (j == linhas.get(i).length() - 1) {
                        temp = temp + linhas.get(i).charAt(j);
                        Tokens t = new Tokens(i, erro, temp, "Caractere desconhecido");
                        tokens.add(t);
                        temp = "";
                    }
                }

            }
        }
        System.out.println("\nLista de Tokens:\n");
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(tokens.get(i).getToken());
        }
    }

    public void verificarTokens() {

        for (int i = 0; i < tokens.size(); i++) {
            int c = tokens.get(i).getToken().charAt(0);
            if (tokens.get(i).getToken().matches("^([a-zA-Z])\\w*$")) {//IDENTIFICADOR     
                if (tokens.get(i).getToken().matches("var|const|struct|extends|procedure|function|start"
                        + "return|if|else|then|while|read|print|int|real|boolean|string|true"
                        + "false|global|local")) {//PALAVRAS RESERVADAS
                    System.out.println("PALAVRA RESERVADA");
                    lexemas.add(tokens.get(i).getLinha() + " PALAVRA RESERVADA " + tokens.get(i).getToken());
                } else {
                    System.out.println("IDENTIFICADOR");
                    lexemas.add(tokens.get(i).getLinha() + " IDENTIFICADOR " + tokens.get(i).getToken());
                }
            } 
             else if (tokens.get(i).getToken().matches("\\|")){
                if(i<tokens.size()-1){
                if(tokens.get(i+1).getToken().matches("\\|")){
                    String temp = tokens.get(i).getToken() + tokens.get(i+1).getToken();                    
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR LÓGICO " + temp);
                    i++;
                }else{
                    lexemas.add(tokens.get(i).getLinha() + " SÍMBOLO " + tokens.get(i).getToken());
                }}else{
                    lexemas.add(tokens.get(i).getLinha() + " SÍMBOLO " + tokens.get(i).getToken());
                }
            }
            
            else if (tokens.get(i).getToken().matches("&")){
                if(i<tokens.size()-1){
                if(tokens.get(i+1).getToken().matches("&")){
                    String temp = tokens.get(i).getToken() + tokens.get(i+1).getToken();                    
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR LÓGICO " + temp);
                    i++;
                }else{
                    lexemas.add(tokens.get(i).getLinha() + " SÍMBOLO " + tokens.get(i).getToken());
                }}else{
                    lexemas.add(tokens.get(i).getLinha() + " SÍMBOLO " + tokens.get(i).getToken());
                }
            }
            
            else if (tokens.get(i).getToken().matches("!")){
                if(i<tokens.size()-1){
                if(tokens.get(i+1).getToken().matches("=")){
                    String temp = tokens.get(i).getToken() + tokens.get(i+1).getToken();                    
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR RELACIONAL " + temp);
                    i++;
                }else{
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR LÓGICO " + tokens.get(i).getToken());
                }}else{
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR LÓGICO " + tokens.get(i).getToken());
                }
            }
            
            
            
            else if (tokens.get(i).getToken().matches("[0-9]")) {//NÚMERO
                System.out.println("DÍGITO");
                lexemas.add(tokens.get(i).getLinha() + " DÍGITO " + tokens.get(i).getToken());
            } else if (tokens.get(i).getToken().matches("^([-])?([0-9]+\\.)?\\d+")) {//NÚMERO
                System.out.println("NÚMERO");
                lexemas.add(tokens.get(i).getLinha() + " NÚMERO " + tokens.get(i).getToken());
            } 
            
            
            else if (tokens.get(i).getToken().matches("-")){
                if(i<tokens.size()-1){
                if(tokens.get(i+1).getToken().matches("-")){
                    String temp = tokens.get(i).getToken() + tokens.get(i+1).getToken();
                    
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR ARITMÉTICO " + temp);
                    i++;
                }else{
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR ARITMÉTICO " + tokens.get(i).getToken());
                }}else{
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR ARITMÉTICO " + tokens.get(i).getToken());
                }
            }
            
            else if (tokens.get(i).getToken().matches("\\+")){
                if(i<tokens.size()-1){
                if(tokens.get(i+1).getToken().matches("\\+")){
                    String temp = tokens.get(i).getToken() + tokens.get(i+1).getToken();
                    
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR ARITMÉTICO " + temp);
                    i++;
                }else{
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR ARITMÉTICO " + tokens.get(i).getToken());
                }}else{
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR ARITMÉTICO " + tokens.get(i).getToken());
                }
            }
            else if (tokens.get(i).getToken().matches("/")){
                if(i<tokens.size()-1){
                if(tokens.get(i+1).getToken().matches("/")){
                    String temp = tokens.get(i).getToken() + tokens.get(i+1).getToken();
                    
                    lexemas.add(tokens.get(i).getLinha() + " COMENTÁRIO DE LINHA " + temp);
                    i++;
                    while(tokens.get(i).getLinha() == tokens.get(i-2).getLinha()){                    
                    i++;
                    }
                } else if(tokens.get(i+1).getToken().matches("\\*")){
                    
                    String temp = tokens.get(i).getToken() + tokens.get(i+1).getToken();                                        
                    i = i+2;
                    
                    while((!tokens.get(i).getToken().matches("\\*"))&&(i!=tokens.size()-1)){                    
                    i++;
                    }
                    if(tokens.get(i).getToken().matches("\\*")&&tokens.get(i+1).getToken().matches("/")){
                        
                       temp = temp + (tokens.get(i).getToken() + tokens.get(i+1).getToken());
                    lexemas.add(tokens.get(i).getLinha() + " COMENTÁRIO DE BLOCO " + temp);
                    
                    i = i+2;
                        
                    }else{
                        i = tokens.size()-1;
                    System.out.println("ERRO");
                lErro.add(tokens.get(i).getLinha() + " ERRO COMENTÁRIO MAL FORMADO " + tokens.get(i).getToken());
                    }
                }else{
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR ARITMÉTICO " + tokens.get(i).getToken());
                }}else{
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR ARITMÉTICO " + tokens.get(i).getToken());
                }
                 
            }
            
            else if (tokens.get(i).getToken().matches("[\\*]")) {//OPERADOR ARITMÉTICO
                System.out.println("OPERADOR ARITMÉTICO");
                lexemas.add(tokens.get(i).getLinha() + " OPERADOR ARITMÉTICO " + tokens.get(i).getToken());
            } 
            
            else if (tokens.get(i).getToken().matches("<")|tokens.get(i).getToken().matches(">")){
                if(i<tokens.size()-1){
                if(tokens.get(i+1).getToken().matches("=")){
                    String temp = tokens.get(i).getToken() + tokens.get(i+1).getToken();                    
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR RELACIONAL " + temp);
                    i++;
                }else{
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR RELACIONAL " + tokens.get(i).getToken());
                }}else{
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR RELACIONAL " + tokens.get(i).getToken());
                }
            }   
             else if (tokens.get(i).getToken().matches("=")){
                
                 if(i<tokens.size()-1){
                    if(tokens.get(i+1).getToken().matches("=")){
                        String temp = tokens.get(i).getToken() + tokens.get(i+1).getToken();
                        
                        lexemas.add(tokens.get(i).getLinha() + " OPERADOR RELACIONAL " + temp);
                        i++;
                    }else{
                        lexemas.add(tokens.get(i).getLinha() + " OPERADOR RELACIONAL " + tokens.get(i).getToken());
                    }
                 }else{
                    lexemas.add(tokens.get(i).getLinha() + " OPERADOR RELACIONAL " + tokens.get(i).getToken());
                }
               
            }   
            
            
            else if (tokens.get(i).getToken().matches("[\\;\\,\\.\\(\\)\\[\\]\\{\\}]")) {
                System.out.println("DELIMITADOR");
                lexemas.add(tokens.get(i).getLinha() + " DELIMITADOR " + tokens.get(i).getToken());

            } else if (tokens.get(i).getToken().matches("^\"[a-zA-Z][\\d|[a-zA-Z]|\\s]*\"$")) {//CADEIA DE CARACTERES
                System.out.println("CADEIA");
                lexemas.add(tokens.get(i).getLinha() + " CADEIA " + tokens.get(i).getToken());
            } else if (tokens.get(i).getToken().matches("^\\'([a-zA-Z]|\\d)\\'$")) {//CARACTERES 
                System.out.println("CARACTERE");
                lexemas.add(tokens.get(i).getLinha() + " CARACTERE " + tokens.get(i).getToken());
            }            
            else if (c == 35 ||c == 36 ||c == 37 ||c == 38 || c == 39||c == 58 || c == 63|| c == 64|| c == 94 || c == 95 
                        || c == 96 || c == 124|| c == 126) {//CARACTERES 
                System.out.println("SÍMBOLO");
                lexemas.add(tokens.get(i).getLinha() + " SÍMBOLO " + tokens.get(i).getToken());
            } 
            else {//ERRO
                System.out.println("ERRO");
                lErro.add(tokens.get(i).getLinha() + " ERRO " + tokens.get(i).getToken());
            }
        }

    }

  
      
    static File[] arqs;
    static File file = new File("input\\");
     
      /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        AnalisadorLexico analisador = new AnalisadorLexico();
        
        
        arqs = file.listFiles();
        for (int i = 0; i < arqs.length; i++) {
            System.out.println(arqs[i]);
            LerArquivos ler = new LerArquivos();
            linhas = ler.abrirArquivo(arqs[i]);
analisador.separarTokens();
        analisador.verificarTokens();
            for (int l = 0; l < lErro.size(); l++) {
            lexemas.add(lErro.get(l));
        }

            if(lErro.size()==0){
            lexemas.add("\n\nSUCESSO NÃO HAVIA ERROS");
            }
            ler.escreveArq(lexemas, "output\\saida" + (i + 1) + ".txt");
            
            lexemas.clear();
            tokens.clear();
            lErro.clear();
            linhas.clear();
        }

    }

   

}
