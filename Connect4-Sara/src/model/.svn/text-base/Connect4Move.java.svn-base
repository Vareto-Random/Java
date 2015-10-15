/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package model;

import data.Move;

public class Connect4Move implements Move, Comparable{
	public final int column;
	private double value;
	
	public Connect4Move(int column) {
		this.column = column;
		
	}	
	
	public Connect4Move(int column, double value) {
		this.column = column;
		this.value = value;
	}
	
	@Override
	public boolean equals(Object that){
		if (!(that instanceof Connect4Move)) {
			return false;
		} else {
			return ((Connect4Move)that).column == this.column;
		}
	}
	
	@Override
	public int hashCode(){
		return column;
	}
	
	@Override
	public String toString() {
		return ""+column;
	}

	/**
	 * The compareTo method compares the  receiving object with the specified
	 * object and returns a negative integer, 0, or a positive integer depending on
	 * whether the receiving object is less than, equal to, or greater than the
	 * specified object. If the specified object cannot be compared to the 
	 * receiving object, the method throws a ClassCastException.
	 * 
	 */
	@Override
		public int compareTo(Object o) {
		Connect4Move it = (Connect4Move)o;
		int result;
		if (it.getValue()> this.value){
			result=1;
//			System.out.println("greater than: it.getValue: "+ it.getValue() + " this.Value: "+ this.value + " result: "+ result );			
			return result;	
	
		}else if (it.getValue() < this.value){
			result = -1;
//			System.out.println("less than: it.getValue: "+ it.getValue() + " this.Value: "+ this.value + " result: "+ result);			

			return result;
			
		}else
			result = 0;
//			System.out.println("equal: it.getValue: "+ it.getValue() + " this.Value: "+ this.value + " result: "+ result);		
				return result;	
//		return  it.getValue() - this.value;
//		return  (it.getValue() - this.value)*(-1);
		}

	
	/**
	 * This method returns the value.
	 * @return value
	 */
	public double getValue() {
		return value;
	}
	/**
	 * This method is used to set the value.
	 * @param value
	 */
	public void setValue(double value) {
		this.value = value;
	}
}
