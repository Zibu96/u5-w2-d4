package giovannighirardelli.u5w2d4.controllers;

import giovannighirardelli.u5w2d4.entities.BlogPost;
import giovannighirardelli.u5w2d4.exceptions.BadRequestException;
import giovannighirardelli.u5w2d4.payloads.NewBlogPostDTO;
import giovannighirardelli.u5w2d4.payloads.NewBlogPostResponseDTO;
import giovannighirardelli.u5w2d4.servicies.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/blogpost")
public class BlogPostController {
    @Autowired
    private BlogPostService blogPostService;

    @GetMapping
    private Page<BlogPost> getAllBlogPost(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        return this.blogPostService.getBlogPosts(page, size, sortBy);

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private NewBlogPostResponseDTO saveBlogPost(@RequestBody @Validated NewBlogPostDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewBlogPostResponseDTO(this.blogPostService.saveBlogPost(body).getId());
    }

    @GetMapping("/{blogpostId}")
    private BlogPost findById(@PathVariable int blogpostId){

        return this.blogPostService.findById(blogpostId);
    }

    @PutMapping("/{blogpostId}")
    private BlogPost findByIdAndUpdate(@RequestBody BlogPost body, @PathVariable int blogpostId){

        return this.blogPostService.findByIdAndUpdate(blogpostId, body);
    }

    @DeleteMapping("/{blogpostId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findBlogPostAndDelete(@PathVariable int blogpostId){

        this.blogPostService.findByIdAndDelete(blogpostId);
    }

    @PostMapping("/{blogpostId}/avatar")
    public String uploadAvatar(@RequestParam("avatar")MultipartFile image, @PathVariable int blogpostId) throws IOException {
        return this.blogPostService.uploadImage(blogpostId,image);
    }
}
