package ao.znt.rassos_la.models;

public class Cartao {
    public final static String UNITEL = "unitel";
    public final static String AFRICEL = "africel";
    public final static String MOVICEL = "movicel";
    public final static String QUALQUER = "qualquer";
    public final static String E_KWANZA = "E-Kwanza";
    public final static String MONAY = "Monay";
    public final static String ATLANTICO = "Atlantico";
    private int telefone;
    private String operadora;
    private String cartera;
    private int senha;

    public Cartao(int telefone, String operadora,String cartera, int senha) {
        this.telefone = telefone;
        this.operadora = operadora;
        this.cartera = cartera;
        this.senha = senha;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    public String getCartera() {
        return cartera;
    }

    public void setCartera(String cartera) {
        this.cartera = cartera;
    }
}
