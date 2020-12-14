package gestion.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import gestion.entities.Personne;
import gestion.services.GuestService;

public class LazyPersonneDataModel extends LazyDataModel<Personne>{
	
	/**
	 * 
	 */
	GuestService guestService;
	
	
	private static final long serialVersionUID = -4059712193758273525L;
	private List<Personne> data;
	private int rowsByReq=1000;
	int rangeBegin,rangeEnd;
	private HashMap<String, String> otherFilters;
	private HashMap<String, String> personneFilters;
	private boolean filtersHaveChanged ;
	
	  public LazyPersonneDataModel(GuestService guestService) {
		  this.guestService = guestService;
		  otherFilters= new HashMap<String, String>();
		  personneFilters= new HashMap<String, String>();
		  
		// TODO Auto-generated constructor stub
	}


	@Override
	   public Personne getRowData(String rowKey) {
	        for (Personne p : data) {
	            if (p.getIdPerson().equals(rowKey)) {
	                return p;
	            }
	        }
	 
	        return null;
	   }
	  
	  @Override
      public Object getRowKey(Personne p) {
	        return p.getIdPerson();
	    }
	 
	  
	  
	  @Override
	    public List<Personne> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
	        System.out.println("Page a afficher"+first);
	        if(first>=this.rangeBegin && first+pageSize<rangeEnd && !filtersHaveChanged){
	        	System.out.println("Pas besoin de charger ");
	            try {	
	                return data.subList(first, first + pageSize);
	            }
	            catch (IndexOutOfBoundsException e) {
	                return data.subList(first, first + (data.size() % pageSize));
	            }
	        }
	        rangeBegin = first;
	        rangeEnd = first+ pageSize*5;
		  	data = new ArrayList<>();
		  	//filter
		  	for(String key:otherFilters.keySet()) {
		  		System.out.println(key+"==="+otherFilters.get(key));
		  	}
	        data = guestService.filterPersonnes(personneFilters,otherFilters,first,pageSize);
	        
	        if (sortMeta != null && !sortMeta.isEmpty()) {
	            for (SortMeta meta : sortMeta.values()) {
	            	 Collections.sort(data, new LazySorter(meta.getSortField(), meta.getSortOrder()));	            }
	        }
	 
	       
	        //rowCount
	        int dataSize = guestService.countAllPersonne(personneFilters,otherFilters);
	      //  int dataSize = data.size();
	        this.setRowCount(dataSize);
//	        this.setRowCount(dataSize);
	        filtersHaveChanged = false;
	        //paginate
	        if (dataSize > pageSize) {
	            try {
	                return data.subList(first, first + pageSize);
	            }
	            catch (IndexOutOfBoundsException e) {
	                return data.subList(first, first + (dataSize % pageSize));
	            }
	        }
	        else {
	            return data;
	        }
	    }
	 public void setFilter(String column,String filterValue) {
		 System.out.println("Adding "+column+"=="+filterValue);
		 this.otherFilters.put(column,filterValue);
		 
	 }
	 public void removeFilter(String column) {
		 try {
			 this.otherFilters.remove(column);
		 }catch(Exception e) {
			 System.out.println("Filter already removed ");
		 }
		 
	 }

    public void setPersonneFilter(String column,String filterValue) {
    	System.out.println("Adding "+column+"=="+filterValue);
    	this.personneFilters.put(column,filterValue);
    }
	public boolean isFiltersHaveChanged() {
		return filtersHaveChanged;
	}


	public void setFiltersHaveChanged(boolean filtersHaveChanged) {
		this.filtersHaveChanged = filtersHaveChanged;
	}


	public void removePersonneFilter(String column) {
		// TODO Auto-generated method stub
		 try {
			 this.personneFilters.remove(column);
		 }catch(Exception e) {
			 System.out.println("Filter already removed ");
		 }
	}

}

