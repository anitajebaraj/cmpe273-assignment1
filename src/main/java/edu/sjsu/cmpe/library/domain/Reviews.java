package edu.sjsu.cmpe.library.domain;

public class Reviews {
	private long reviewID;
	private long rating;
	private String comment;
	
	public long getReviewID() {
		return reviewID;
	    }
 
	public void setReviewID(long reviewID) {
		this.reviewID = reviewID;
	    }
 
	public long getRating() {
		return rating;
	    }

	public void setRating(long rating) {
		this.rating = rating;
	    }
	
	public String getComment() {
		return comment;
	    }

	public void setComment(String comment) {
		this.comment = comment;
	    }

}
