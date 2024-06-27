package giovannighirardelli.u5w2d4.servicies;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import giovannighirardelli.u5w2d4.entities.Autore;
import giovannighirardelli.u5w2d4.entities.BlogPost;
import giovannighirardelli.u5w2d4.exceptions.NotFoundException;
import giovannighirardelli.u5w2d4.payloads.NewBlogPostDTO;
import giovannighirardelli.u5w2d4.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;
    @Autowired
    private AutoreService autoreService;
    @Autowired
    private Cloudinary cloudinary;


    public Page<BlogPost> getBlogPosts(int pageNumber, int pageSize, String sortBy){
        if (pageSize > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return blogPostRepository.findAll(pageable);
    }

    public BlogPost saveBlogPost(NewBlogPostDTO body){
        Autore autore = autoreService.findById(body.autoreId());

        BlogPost blogPost = new BlogPost(body.categoria(),body.contenuto(), body.tempoLettura(), autore);

        return this.blogPostRepository.save(blogPost);
    }

    public BlogPost findById(int id){

     return this.blogPostRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

    }

    public BlogPost findByIdAndUpdate(int id, BlogPost updatedBlogPost){
      BlogPost found = this.blogPostRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
            found.setCategoria(updatedBlogPost.getCategoria());
            found.setContenuto(updatedBlogPost.getContenuto());
            found.setCover(updatedBlogPost.getCover());
            found.setTempoLettura(updatedBlogPost.getTempoLettura());

       return this.blogPostRepository.save(found);

    }
    public void findByIdAndDelete(int id){

        BlogPost found = this.findById(id);
        this.blogPostRepository.delete(found);
        }


        public String uploadImage(int id, MultipartFile file) throws IOException {
            BlogPost found = this.blogPostRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
            String img = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            found.setCover(img);
            this.blogPostRepository.save(found);
            return img;

        }
}
