package analisadorlexico;

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
public class Tokens {

    private int linha;
    private boolean status;
    private String token;
    private String descricao;

    public Tokens(int newLinha, boolean newStatus, String newToken, String newDescricao) {
        this.linha = newLinha;
        this.status = newStatus;
        this.token = newToken;
        this.descricao = newDescricao;
    }
    public Tokens(int newLinha, String newToken, String newDescricao) {
        this.linha = newLinha;
        this.token = newToken;
        this.descricao = newDescricao;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
