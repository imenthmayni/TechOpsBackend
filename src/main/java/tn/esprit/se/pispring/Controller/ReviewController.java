package tn.esprit.se.pispring.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.se.pispring.Repository.ProductRepository;
import tn.esprit.se.pispring.Repository.ReviewRepository;
import tn.esprit.se.pispring.Service.Review.ReviewServices;

@RestController
@AllArgsConstructor
@RequestMapping("/review")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {
    ReviewServices reviewServices;
    ProductRepository productRepository;
    ReviewRepository reviewRepository;
    @PostMapping("/like/{userId}/{idprod}")
    public void likeProduct (@PathVariable("idprod") Long productId, @PathVariable("userId") Long userId){
        reviewServices.userLikesProduct(productId,userId);
    }
    @PostMapping("/dislike/{userId}/{idprod}")
    public void dislikeProduct (@PathVariable("idprod") Long productId,@PathVariable("userId") Long userId){
        reviewServices.userDislikesProduct(productId,userId);
    }
    @GetMapping("/nblike/{idprod}")
    public int numberOfLikes(@PathVariable("idprod") Long productId){
        return reviewServices.numberOfLikes(productId);
    }
    @GetMapping("/nbdislike/{idprod}")
    public int numberOfDislikes(@PathVariable("idprod") Long productId){
        return reviewServices.numberOfDisikes(productId);
    }

}
