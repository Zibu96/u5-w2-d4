package giovannighirardelli.u5w2d4.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewBlogPostDTO(
        @NotEmpty(message = "La categoria del blogpost è obbligatoria")
        String categoria,
        @NotEmpty(message = "Il contenuto del blogpost è obbligatorio")
        @Size(max = 1200, message = "Il blogpost non può superare i 1200 caratteri")
        String contenuto,
        @NotNull(message = "Il blogpost deve avere un tempo di lettura indicativo")
        int tempoLettura,
        @NotNull(message = "Il blogpost deve avere un autore")
        int autoreId
) {
}
