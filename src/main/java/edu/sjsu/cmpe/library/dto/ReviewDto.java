package edu.sjsu.cmpe.library.dto;

public class ReviewDto {
    private String id;
    private String review;
   private String rating;

    
	
    public ReviewDto(String id,String rating, String review) {
	super();
	this.id = id;
	this.rating=rating;
	this.review = review;

    }

  
    public String getReview() {
	return review;
    }

    
    public void setReview(String review) {
	this.review = review;
    }
    
    public String getRating() {
    	return rating;
    }

    public void setRating(String rating) {
    	this.rating = rating;
    }


    
    public String getId() {
	return id;
    }

   
    public void setId(String id) {
	this.id = id;
    }

 

}
