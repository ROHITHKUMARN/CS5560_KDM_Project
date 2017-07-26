package pubmedretriever.pubmed;
  
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import pubmedretriever.utils.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
  

/**
 * @author EG
 * This class is responsible from the connection to PubMed and several functions over the REST API
 * */
public class PubMedConnector { 
  
    HttpClient client;
    HttpPost post;
        
    private void connect(String searchType){
    	
    	client  = new DefaultHttpClient(); 
    	post    = new HttpPost(StringUtils.pubMedConnectionUrl + searchType); 
        
    }
    
    /**
     * This method searches the keyword in PUBMED and retrieves the related IDs of Pubmed Publications
     * @param  //String keyword : pattern to be searched in PUBMED
     * @return List<String> : List of Pubmed Publication IDs
     * */
    public List<String> search(String keyword){ 
          
    	connect(StringUtils.SEARCH_URL);
        List<NameValuePair> parameters    = new ArrayList<NameValuePair>(3); 
        parameters.add(new BasicNameValuePair(StringUtils.DB			, StringUtils.PUBMED)); 
        parameters.add(new BasicNameValuePair(StringUtils.TERM			, keyword)); 
        parameters.add(new BasicNameValuePair(StringUtils.RETURN_MODE	, StringUtils.JSON)); 
        parameters.add(new BasicNameValuePair(StringUtils.USEHISTORY	, "y")); 
        parameters.add(new BasicNameValuePair(StringUtils.RETURN_MAX	, "1000"));
        parameters.add(new BasicNameValuePair(StringUtils.WEB_ENV		, "gkdnz"));
        
        HttpEntity entity = getHttpResponse(parameters);

        List<String> pubMedIds = new ArrayList<String>();
        String json;
		try {
			json = EntityUtils.toString(entity);  
			System.out.println(json);
			JSONParser parser = new JSONParser();
	        JSONObject obj = (JSONObject)parser.parse(json);
	        JSONObject searchResult = (JSONObject) obj.get("esearchresult");
	        JSONArray ids = (JSONArray)searchResult.get("idlist");
	        for (int i = 0; i < ids.size(); i++)
	            pubMedIds.add((String)ids.get(i));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
      

        return pubMedIds;
        		
    }    
    
    /**
     * This method retrieves the summary of publications for the given id or id list separated with comma 
     * @param  //String id : PubMed ID
     * @return String : Summary Text
     * */
    public String getSummary(String id){ 
        
    	connect(StringUtils.SUMMARY_URL);
        List<NameValuePair> parameters    = new ArrayList<NameValuePair>(4); 
        parameters.add(new BasicNameValuePair(StringUtils.DB			, StringUtils.PUBMED)); 
        parameters.add(new BasicNameValuePair(StringUtils.ID			, id)); 
        parameters.add(new BasicNameValuePair(StringUtils.VERSION		, StringUtils.VERSION_NUM));
        parameters.add(new BasicNameValuePair(StringUtils.RETURN_MODE	, StringUtils.JSON));
        parameters.add(new BasicNameValuePair(StringUtils.RETURN_MAX	, "1000"));
        parameters.add(new BasicNameValuePair(StringUtils.WEB_ENV		, "gkdnz"));
        
        HttpEntity entity = getHttpResponse(parameters);
        
        return entity.toString();
        		
    } 
    /**
     * This method retrieves the abstracts of publications for the given id or id list (separated with comma) 
     * @param  //String id : PubMed ID
     * @return String : Abstract Text
     * */
    public String fetch(String id) { 
        
    	String abstractText = null;
    	
    	try{
	    	connect(StringUtils.FETCH_URL);
	        List<NameValuePair> parameters    = new ArrayList<NameValuePair>(4); 
	        parameters.add(new BasicNameValuePair(StringUtils.DB			, StringUtils.PUBMED)); 
	        parameters.add(new BasicNameValuePair(StringUtils.ID			, id)); 
	        parameters.add(new BasicNameValuePair(StringUtils.VERSION		, StringUtils.VERSION_NUM));
	        parameters.add(new BasicNameValuePair(StringUtils.RETURN_TYPE	, StringUtils.TEXT));
	        parameters.add(new BasicNameValuePair(StringUtils.RETURN_MAX	, "1000"));
	        parameters.add(new BasicNameValuePair(StringUtils.RETURN_MODE	, StringUtils.ABSTRACT));
	        parameters.add(new BasicNameValuePair(StringUtils.WEB_ENV		, "gkdnz"));
	        
	        HttpEntity entity = getHttpResponse(parameters);
	        
	        abstractText = EntityUtils.toString(entity);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
        
        return abstractText; 
        		
    } 
    
    /**
     * This method returns the regular HTTP Response
     * */
    private HttpEntity getHttpResponse(List<NameValuePair> parameters){
    	
    	HttpEntity entity = null;
        
    	try { 
            post.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8")); 
            HttpResponse resp = client.execute(post); 
            entity = resp.getEntity();
              
        } catch (UnsupportedEncodingException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        
        return entity;
    	
    }
    
    
}