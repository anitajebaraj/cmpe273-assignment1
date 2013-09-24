package edu.sjsu.cmpe.library.domain;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookDetails {
	
	static HashMap<Integer,ArrayList> bookHashMap=new HashMap<Integer,ArrayList>();
	static HashMap<Integer,ArrayList> reviewRatingHashMap=new HashMap<Integer,ArrayList>();
	static HashMap<Integer,ArrayList> reviewHashMap=new HashMap<Integer,ArrayList>();
	static HashMap<Integer,ArrayList> authorHashMap=new HashMap<Integer,ArrayList>();
	static long newIsbn=1;
	
	
	    public static long getNewIsbn() {
		return newIsbn++;
	}
	    
	
		public HashMap<Integer,ArrayList> createBookMap(HashMap<Integer,ArrayList> bookMap,int i,String bookTitle,String pubDate,String Language,String numOfPages,String status,String rating)
	    {
	    	System.out.print("testinggg i"+i);
	    	ArrayList<String> bookList=new ArrayList<String>();
	    	List<String> book1=new ArrayList<String>();
	    	book1.add(bookTitle);
	    	book1.add(pubDate);
	    	book1.add(Language);
	    	book1.add(numOfPages);
	    	book1.add(status);
	    	book1.add(rating);
	    	bookList.addAll(book1);
	    	bookMap.put(i,(ArrayList<String>) book1);
	    	return bookMap;
	    }
	
	    public HashMap<Integer,ArrayList> getBookHashMap()
	    { 	
	   			//HashMap<Integer,ArrayList> bookHashMap=new HashMap<Integer,ArrayList>();
	    		
	   			//bookHashMap=bookResourceDetails();
	    		return bookHashMap;
	    }
	    public void setBookHashMap(HashMap<Integer,ArrayList> map)
	    {
	    	this.bookHashMap=map;
	    }
	   
	    
	    public HashMap<Integer,ArrayList> createBookReviewResource(HashMap <Integer,ArrayList> reviewMap,int i,String[] reviews)
	    {
	    	ArrayList<String> bookReviewsList=new ArrayList<String>();
	    	List<String> reviewList=new ArrayList<String>();
	    	System.out.print("in createBookReviewResource\n"+reviews);
	    	for(int count=0;count<reviews.length;count++)
	    	reviewList.add(reviews[count]);
	    	bookReviewsList.addAll(reviewList);
	    	reviewMap.put(i, (ArrayList<String>)reviewList);
	    	return reviewMap;
	    }
	    public HashMap<Integer,ArrayList> createBookReviewRatingResource(HashMap <Integer,ArrayList> reviewMap,int i,String[] reviewRatings)
	    {
	    	System.out.print("in createBookReviewRatingResource\n");
	    	ArrayList<String> bookReviewRatingList=new ArrayList<String>();
	    	List<String> reviewRatingList=new ArrayList<String>();
	    	System.out.print("in createBookReviewResource\n"+reviewRatings);
	    	for(int count=0;count<reviewRatings.length;count++)
	    	reviewRatingList.add(reviewRatings[count]);
	    	bookReviewRatingList.addAll(reviewRatingList);
	    	reviewMap.put(i, (ArrayList<String>)reviewRatingList);
	    	return reviewMap;
	    }
	  /*  public HashMap<Integer,ArrayList> createReviewHashMap()
	    {
	    	//HashMap<Integer,ArrayList> bookHashMap=new HashMap<Integer,ArrayList>();
	    	reviewHashMap=bookReviewResourceDetails();
	    	return reviewHashMap;
	    }
	    public HashMap<Integer,ArrayList> createReviewRatingHashMap()
	    {
	    	//HashMap<Integer,ArrayList> bookHashMap=new HashMap<Integer,ArrayList>();
	    	reviewRatingHashMap=bookReviewRatingDetails();
	    	return reviewRatingHashMap;
	    }*/
	    
	    public HashMap<Integer,ArrayList> getReviewHashMap()
	    {
	    	//HashMap<Integer,ArrayList> bookHashMap=new HashMap<Integer,ArrayList>();
	    	return reviewHashMap;
	    }
	    
	    public void setReviewHashMap(HashMap<Integer,ArrayList> map)
	    {
	    	this.reviewHashMap=map;
	    }
	    
	    public HashMap<Integer,ArrayList> getReviewRatingHashMap()
	    {
	    	//HashMap<Integer,ArrayList> bookHashMap=new HashMap<Integer,ArrayList>();
	    	return reviewRatingHashMap;
	    }
	    
	    public void setReviewRatingHashMap(HashMap<Integer,ArrayList> map)
	    {
	    	this.reviewRatingHashMap=map;
	    }
	    public void addCommentToReviewHashMap(HashMap<Integer,ArrayList> reviewHashMap,int isbn,String comment)
	    {
	    	ArrayList reviewList=new ArrayList();
	    	reviewList=reviewHashMap.get(isbn);
	    	System.out.print("\n reviewList in addCommentToReviewHashMap"+reviewList);
	    	int listSize=reviewList.size();
	    	reviewList.add(comment);
	    	System.out.print("\n reviewList after adding"+reviewList);
	    	reviewHashMap.put(isbn, reviewList);
	    	System.out.print("\n reviewHashMap after adding"+reviewHashMap);
	    	setReviewHashMap(reviewHashMap);
	    }
	    public void addRatingToReviewHashMap(HashMap<Integer,ArrayList> reviewHashMap,int isbn,String rating)
	    {
	    	ArrayList reviewRatingList=new ArrayList();
	    	System.out.print("\nreviewRatingHashMap====="+reviewRatingHashMap);
	    	reviewRatingList=reviewRatingHashMap.get(isbn);
	    	int listSize=reviewRatingList.size();
	    	reviewRatingList.add(rating);
	    	System.out.print("\n reviewRatingList after adding"+reviewRatingList);
	    	reviewRatingHashMap.put(isbn, reviewRatingList);
	    	System.out.print("\n reviewRatingHashMap after adding"+reviewRatingHashMap);
	    	setReviewRatingHashMap(reviewRatingHashMap);
	    }

	    public HashMap<Integer,ArrayList> createAuthorResource(HashMap <Integer,ArrayList> reviewMap,int i,String[] authors)
	    {
	    	ArrayList<String> bookReviewsList=new ArrayList<String>();
	    	List<String> authorList=new ArrayList<String>();
	    	for(int count=0;count<authors.length;count++)
	    	authorList.add(authors[count]);
	    	bookReviewsList.addAll(authorList);
	    	reviewMap.put(i, (ArrayList<String>)authorList);
	    	return reviewMap;
	    }

	    public HashMap<Integer,ArrayList> getAuthorHashMap()
	    {
	    	//HashMap<Integer,ArrayList> bookHashMap=new HashMap<Integer,ArrayList>();
	    	return authorHashMap;
	    }
	    public void setAuthorHashMap(HashMap<Integer,ArrayList> map)
	    {
	    	this.authorHashMap=map;
	    }
	   
	    
	    
}
