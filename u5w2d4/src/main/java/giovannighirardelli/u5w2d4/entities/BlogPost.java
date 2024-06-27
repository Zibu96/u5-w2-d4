package giovannighirardelli.u5w2d4.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blogpost")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BlogPost {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private int id;
    private String categoria;
    private String cover;
    private String contenuto;
    @Column(name = "tempo_lettura")
    private int tempoLettura;
    @ManyToOne
    @JoinColumn(name = "autore")
    private Autore autore;

    public BlogPost(String categoria, String contenuto, int tempoLettura, Autore autore) {
        this.categoria = categoria;

        this.contenuto = contenuto;
        this.tempoLettura = tempoLettura;
        this.autore = autore;
    }
}
