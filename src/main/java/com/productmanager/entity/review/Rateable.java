package com.productmanager.entity.review;

@FunctionalInterface
public interface Rateable <T> {
    public static final Rating DEFAULT_RATING = Rating.NOT_RATED;

    T applyNewRating(Rating rating);

    public default T applyNewRating(int rating){
        return applyNewRating(Rateable.convert(rating));
    }

    public default Rating getRating(){
        return DEFAULT_RATING;
    }

    public static Rating convert(int stars){
        return (stars >= 0 && stars <=5) ? Rating.values()[stars] : DEFAULT_RATING;
    }
}
