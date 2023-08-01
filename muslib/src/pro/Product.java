//A Java class which is a fully encapsulated class.  
//It has a private data member and getter and setter methods.  
package pro;

import pro.Product;

public class Product {
	//private data member  
    private int id;
    private String name;
    private String description;
    private String source;
	public Product(int id, String name, String description, String source) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.source = source;
	}
	//public getter and setter methods  
	//getter method for id
	public int getId() {
		return id;
	}
	//setter method for id
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", source=" + source + "]";
	} 

	// Inheritance method
public class SpecialProduct extends Product {
    private double discount;

    public SpecialProduct(int id, String name, String description, String source, double discount) {
        super(id, name, description, source);
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "SpecialProduct [id=" + getId() + ", name=" + getName() + ", description=" + getDescription() + ", source=" + getSource() + ", discount=" + discount + "]";
    }
}

}


