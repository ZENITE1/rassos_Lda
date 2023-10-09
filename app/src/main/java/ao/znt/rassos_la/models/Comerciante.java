package ao.znt.rassos_la.models;

import java.util.List;

public class Comerciante extends Usuario{
    private int[] imagens;
    private String profissao;
    private String endereco;
    private int avaliacaoPositiva;
    private int avaliacaoNegativa;

    public Comerciante(int[] imgs,String nome,String profissao, String endereco, int avaliacaoPositiva, int avaliacaoNegativa) {
        this.profissao = profissao;
        this.endereco = endereco;
        this.avaliacaoPositiva = avaliacaoPositiva;
        this.avaliacaoNegativa = avaliacaoNegativa;
        this.imagens = imgs;
        this.setName(nome);
    }

    public String getProfissao() {
        return profissao;
    }

    public int[] getImagens() {
        return imagens;
    }

    public void setImagens(int[] imagens) {
        this.imagens = imagens;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getAvaliacaoPositiva() {
        return avaliacaoPositiva;
    }

    public void setAvaliacaoPositiva(int avaliacaoPositiva) {
        this.avaliacaoPositiva = avaliacaoPositiva;
    }

    public int getAvaliacaoNegativa() {
        return avaliacaoNegativa;
    }

    public void setAvaliacaoNegativa(int avaliacaoNegativa) {
        this.avaliacaoNegativa = avaliacaoNegativa;
    }
}
