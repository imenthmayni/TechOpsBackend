package tn.esprit.se.pispring.Service.Review;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.LikeDislikeRepository;
import tn.esprit.se.pispring.Repository.ProductRepository;
import tn.esprit.se.pispring.Repository.ReviewRepository;
import tn.esprit.se.pispring.Repository.UserRepository;
import tn.esprit.se.pispring.entities.Product;
import tn.esprit.se.pispring.entities.Rating.LikeDislike;
import tn.esprit.se.pispring.entities.Rating.ProductRating;
import tn.esprit.se.pispring.entities.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@AllArgsConstructor
public class ReviewServices implements IReviewServices{
    ProductRepository productRepository;
    ReviewRepository reviewRepository;
    LikeDislikeRepository likeDislikeRepository;
    UserRepository userRepository;
    @Transactional
    public void userLikesProduct (Long productId, Long userId){
        User user=userRepository.findById(userId).get();
        Product product=productRepository.findById(productId).get();
        LikeDislike previousLike = (LikeDislike) likeDislikeRepository.findByProductAndUser(product, user);
        if (previousLike != null) {
            // Delete the previous like
            product.getLikeDislikeProducts().remove(previousLike);
            user.getLikeDislikeProductList().remove(previousLike);
            likeDislikeRepository.delete(previousLike);
        }
        LikeDislike likeDislikeProduct=new LikeDislike();
        likeDislikeProduct.setProductRating(ProductRating.LIKE);
        likeDislikeProduct.setProduct(product);
        likeDislikeProduct.setUser(user);
        likeDislikeRepository.save(likeDislikeProduct);
        productRepository.save(product);
    }
    @Transactional
    public void userDislikesProduct (Long productId, Long userId){
        User user=userRepository.findById(userId).get();
        Product product=productRepository.findById(productId).get();
        LikeDislike previousLike = (LikeDislike) likeDislikeRepository.findByProductAndUser(product, user);
        if (previousLike != null) {
            // Delete the previous like
            product.getLikeDislikeProducts().remove(previousLike);
            user.getLikeDislikeProductList().remove(previousLike);
            likeDislikeRepository.delete(previousLike);
        }
        LikeDislike likeDislikeProduct=new LikeDislike();
        likeDislikeProduct.setProductRating(ProductRating.DISLIKE);
        likeDislikeProduct.setProduct(product);
        likeDislikeProduct.setUser(user);
        likeDislikeRepository.save(likeDislikeProduct);
        productRepository.save(product);
    }

    @Override
    public int numberOfLikes(Long productId){
        Product product=productRepository.findById(productId).get();
        int likes=0;
        for (LikeDislike e:product.getLikeDislikeProducts()){
            if(e.getProductRating().equals(ProductRating.LIKE)){
                likes++;
            }
        }
        return  likes;
    }
    @Override
    public int numberOfDisikes(Long productId){
        Product product=productRepository.findById(productId).get();
        int dislikes=0;
        for (LikeDislike e:product.getLikeDislikeProducts()){
            if(e.getProductRating().equals(ProductRating.DISLIKE)){
                dislikes++;
            }
        }
        return  dislikes;
    }
}
