package com.aoneposapp.utils;

import android.util.Log;



public class Inventory {
	String departmentAdd="",itemNameAdd="", itemNoAdd="",secondDescription="" , avgCost="",priceYouChange="", priceTax="", inStock,dscpt="",vndr="", 
	       quantity="", inventoryTaxOne="", inventoryTaxTwo="", inventoryTaxThree="", inventoryTaxFour="", inventoryTaxTotal="";

	public String getInventoryTaxOne() {
		return inventoryTaxOne;
	}

	public void setInventoryTaxOne(String inventoryTaxOne) {
		this.inventoryTaxOne = inventoryTaxOne;
	}
	public String getInventoryTaxTotal() {
		return inventoryTaxTotal;
	}

	public void setInventoryTaxTotal(String inventoryTaxTotal) {
		this.inventoryTaxTotal = inventoryTaxTotal;
		try{
			Double.valueOf(inventoryTaxTotal);
			this.inventoryTaxTotal = inventoryTaxTotal;
	} catch (NumberFormatException e) {
		  e.getLocalizedMessage();
		  this.inventoryTaxTotal ="0";
		} catch (Exception e1) {
			  e1.getLocalizedMessage();
			}
	}
	public String getDepartmentAdd() {
		return departmentAdd;
	}

	public void setDepartmentAdd(String departmentAdd) {
		this.departmentAdd = departmentAdd;
	}

	public String getItemNameAdd() {
		return itemNameAdd;
	}

	public void setItemNameAdd(String itemNameAdd) {
		this.itemNameAdd = itemNameAdd;
	}

	public String getItemNoAdd() {
		return itemNoAdd;
	}

	public void setItemNoAdd(String itemNoAdd) {
		this.itemNoAdd = itemNoAdd;
	}

	public String getSecondDescription() {
		return secondDescription;
	}

	public void setSecondDescription(String secondDescription) {
		this.secondDescription = secondDescription;
	}

	public String getAvgCost() {
		return avgCost;
	}

	public void setAvgCost(String avgCost) {
		this.avgCost = avgCost;
		try{
			Double.valueOf(avgCost);
			this.avgCost = avgCost;
	} catch (NumberFormatException e) {
		  e.getLocalizedMessage();
		  this.avgCost ="0";
		} catch (Exception e1) {
			  e1.getLocalizedMessage();
			}
	}

	public String getPriceYouChange() {
		return priceYouChange;
		
	}

	public void setPriceYouChange(String priceYouChange) {
		try{
			Double.valueOf(priceYouChange);
			this.priceYouChange = priceYouChange;
	} catch (NumberFormatException e) {
		  e.getLocalizedMessage();
		  this.priceYouChange ="0";
		} catch (Exception e1) {
			  e1.getLocalizedMessage();
			}
	}

	public String getPriceTax() {
		return priceTax;
	}

	public void setPriceTax(String priceTax) {
		this.priceTax = priceTax;
	}

	public String getInStock() {
		return inStock;
	}

	public void setInStock(String inStock) {
		
		try{
			Double.valueOf(inStock);
			this.inStock = inStock;
	} catch (NumberFormatException e) {
		  e.getLocalizedMessage();
		  this.inStock ="0";
		} catch (Exception e1) {
			  e1.getLocalizedMessage();
			}
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		try{
			Double.valueOf(quantity);
		this.quantity =quantity;
	} catch (NumberFormatException e) {
		  e.getLocalizedMessage();
		  this.quantity ="0";
		} catch (Exception e1) {
			  e1.getLocalizedMessage();
			}
	}
	

	public String getInventoryVndr() {
		return vndr;
	}

	public void setInventoryVndr(String vndr) {
		this.vndr = vndr;
	}
	
}
