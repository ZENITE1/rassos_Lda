package ao.znt.rassos_la.models;

public class Usuario {
     private String name;
     private String login;
     private int id;
     private int telefone;

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getLogin() {
          return login;
     }

     public void setLogin(String login) {
          this.login = login;
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }
}
