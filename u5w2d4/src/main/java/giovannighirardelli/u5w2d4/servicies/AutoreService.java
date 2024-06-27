package giovannighirardelli.u5w2d4.servicies;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import giovannighirardelli.u5w2d4.entities.Autore;
import giovannighirardelli.u5w2d4.entities.BlogPost;
import giovannighirardelli.u5w2d4.exceptions.BadRequestException;
import giovannighirardelli.u5w2d4.exceptions.NotFoundException;
import giovannighirardelli.u5w2d4.payloads.NewAutoreDTO;
import giovannighirardelli.u5w2d4.repositories.AutoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AutoreService {

    @Autowired
    private AutoreRepository autoreRepository;
    @Autowired
    private Cloudinary cloudinary;



    public Page<Autore> getAutore(int pageNumber, int pageSize, String sortBy){
        if (pageSize > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return autoreRepository.findAll(pageable);
    }

    public Autore saveAutore(NewAutoreDTO body) {
        if (this.autoreRepository.existsByNomeAndCognome(body.nome(), body.cognome())) throw new BadRequestException("L'autore " + body.nome() + " esiste già");

        Autore autore = new Autore(body.nome(), body.cognome(), body.email(), body.dataNascita());


        return this.autoreRepository.save(autore);

    }


    public Autore findById(int id) {
       return this.autoreRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Autore findByIdAndUpdate(int id, Autore updatedAutore) {
        Autore found = this.findById(id);

        found.setNome(updatedAutore.getNome());
        found.setCognome(updatedAutore.getCognome());
        found.setEmail(updatedAutore.getEmail());
        found.setDataNascita(updatedAutore.getDataNascita());
        found.setAvatar("https://ui-avatars.com/api/?name=" + updatedAutore.getNome() + "+" + updatedAutore.getCognome());

        return this.autoreRepository.save(updatedAutore);

    }

    public void findByIdAndDelete(int id) {
       Autore found = this.findById(id);
       this.autoreRepository.delete(found);

    }

    public String uploadImage(int id, MultipartFile file) throws IOException {
        Autore found = this.autoreRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        String img = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(img);
        this.autoreRepository.save(found);
        return img;

    }
}

