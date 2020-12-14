package gestion.views;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import gestion.entities.Personne;

public class LazySorter implements Comparator<Personne> {


	private String sortField;

	private SortOrder sortOrder;

	public LazySorter(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}
	@Override
	public int compare(Personne p1, Personne p2) {

		try {
			// TODO Auto-generated method stub
			Object value1 = Personne.class.getField(this.sortField).get(p1);
			Object value2 = Personne.class.getField(this.sortField).get(p2);

			int value = ((Comparable)value1).compareTo(value2);

			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		}
		catch(Exception e) {
			throw new RuntimeException();
		}
	}

}
