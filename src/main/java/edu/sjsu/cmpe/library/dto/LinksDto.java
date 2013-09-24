package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;
import java.util.List;

public class LinksDto {
	private List<ReviewDto> reviews = new ArrayList<ReviewDto>();
    private List<LinkDto> links = new ArrayList<LinkDto>();
    private List<AuthorDto> authors = new ArrayList<AuthorDto>();
    
    public void addLink(LinkDto link) {
	links.add(link);
    }
    
    public void addReviewLink(ReviewDto review) {
    	reviews.add(review);
    }
    public void addAuthorLink(AuthorDto author) {
    	authors.add(author);
    }

    /**
     * @return the links
     */
    public List<LinkDto> getLinks() {
	return links;
    }
    public List<ReviewDto> getReviewLinks() {
    	return reviews;
        }
    public List<AuthorDto> getAuthorLinks() {
    	return authors;
        }
    /**
     * @param links
     *            the links to set
     */
    public void setLinks(List<LinkDto> links) {
	this.links = links;
    }
    public void setReviewLinks(List<ReviewDto> reviews) {
    	this.reviews = reviews;
        }
    
    public void setAuthorLinks(List<AuthorDto> authors) {
    	this.authors = authors;
        }
}
