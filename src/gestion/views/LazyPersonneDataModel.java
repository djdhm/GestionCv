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
	private List<Personne> listePersonne;
	private int pageSize=1000;
	private int page;
	private int count;

	
	  public LazyPersonneDataModel(GuestService guestService) {
		  this.guestService = guestService;
		// TODO Auto-generated constructor stub
	}


	@Override
	   public Personne getRowData(String rowKey) {
	        for (Personne p : listePersonne) {
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
	        List<Personne> data = new ArrayList<>();
	        HashMap<String,String> filters = new HashMap<String, String>();
	        //filter
	        for(String key:filterMeta.keySet()) {
	        	System.out.println(key);
	        	try {
	        		FilterMeta filter = filterMeta.get(key);
	        		System.out.println(filter);
	        	}catch(Exception e) {
	        		System.out.println("Impossible pour "+key);
	        	}
	        	
	        }
	        data = guestService.filterPersonnes(filters);
	        
	        if (sortMeta != null && !sortMeta.isEmpty()) {
	            for (SortMeta meta : sortMeta.values()) {
	            	 Collections.sort(data, new LazySorter(meta.getSortField(), meta.getSortOrder()));	            }
	        }
	 
	       
	        //rowCount
	        int dataSize = guestService.countAllPersonne();
	        this.setRowCount(dataSize);
//	        this.setRowCount(dataSize);
	 
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
	 

}

