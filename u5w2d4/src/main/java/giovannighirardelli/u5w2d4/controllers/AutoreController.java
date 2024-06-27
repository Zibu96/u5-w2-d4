package giovannighirardelli.u5w2d4.controllers;

import giovannighirardelli.u5w2d4.entities.Autore;
import giovannighirardelli.u5w2d4.exceptions.BadRequestException;
import giovannighirardelli.u5w2d4.payloads.NewAutoreDTO;
import giovannighirardelli.u5w2d4.payloads.NewAutoreResponseDTO;
import giovannighirardelli.u5w2d4.servicies.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/autore")
public class AutoreController {
    @Autowired
    private AutoreService autoreService;


    @GetMapping
    public Page<Autore> getAutore(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.autoreService.getAutore(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewAutoreResponseDTO saveAutore(@RequestBody @Validated NewAutoreDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()) {
        System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewAutoreResponseDTO(this.autoreService.saveAutore(body).getId());
    }

    @GetMapping("/{autoreId}")
    public Autore findById(@PathVariable int autoreId){
        return this.autoreService.findById(autoreId);
    }

    @PutMapping("/{autoreId}")
    public Autore findByIdAndUpdate(@PathVariable int autoreId, @RequestBody Autore body){
        return this.autoreService.findByIdAndUpdate(autoreId, body);

    }

    @DeleteMapping("/{autoreId}")
    public void findByIdAndDelete(@PathVariable int autoreId){
        this.autoreService.findByIdAndDelete(autoreId);
    }

    @PostMapping("/{autoreId}/avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile image, @PathVariable int autoreId) throws IOException {
        return this.autoreService.uploadImage(autoreId, image);
    }


}
