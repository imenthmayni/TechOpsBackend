package tn.esprit.se.pispring.Service.Review;

import javax.transaction.Transactional;

public interface IReviewServices {
    @Transactional
    void userLikesProduct(Long productId, Long userId);

 //   @Transactional
    //void userDislikesProduct(Long productId, Long userId);

    @Transactional
    void userDislikesProduct(Long productId, Long userId);

    int numberOfLikes(Long productId);

    int numberOfDisikes(Long productId);
}
