package giovannighirardelli.u5w2d4.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record NewAutoreDTO(
        @NotEmpty(message = "L'autore deve avere un nome!")
        @Size(min = 3, max = 16, message = "Il nome deve avere minimo 3 caratteri e massimo 16")
        String nome,
        @NotEmpty(message = "Lautore deve avere un cognome")
        String cognome,
        @NotEmpty(message = "L'autore deve avere una email")
        @Email(message = "Formato inidirizzo mail non valido")
        String email,
        @NotNull(message = "L'autore deve avere una data di nascita")
        LocalDate dataNascita
) {
}
