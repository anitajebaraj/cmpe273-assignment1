package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;




public class Book {
    private long isbn;

    private String title;
    @JsonProperty("num-pages")
	private String numOfPages;
    private String status;
    @JsonProperty("publication-date")
    private String publicationDate;
   
    private String language;
    private ArrayList <Author> authors;

    public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}

    // add more fields here

    /**
     * @return the isbn
     */
    public long getIsbn() {
	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }
    
    
    public String getNumOfPages() {
    	return numOfPages;
        }
    public void setNumOfPages(String numOfPages) {
    	this.numOfPages = numOfPages;
        }
    public String getStatus() {
    	return status;
        }
    public void setStatus(String status) {
    	this.status = status;
        } 
    public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
    

