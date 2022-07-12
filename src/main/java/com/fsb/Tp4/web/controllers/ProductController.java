package com.fsb.Tp4.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fsb.Tp4.models.Product;
import com.fsb.Tp4.models.requests.ProductForm;

@Controller
public class ProductController {
	private static List<Product> products = new ArrayList<Product>();
	private static int idCount =-1; 
	
	 static {
	 products.add(new Product(++idCount, "SS-S9", "Samsung Galaxy S9",1000D, "samsung-s9.png"));
	 products.add(new Product(++idCount, "NK-5P", "Nokia 5.1 Plus", 600D, ""));
	 }
	 

	 
	 @Value("${welcome.message}")
	 private String message;
	 
	 @Value("${error.message}")
	 private String errorMessage;
	 
	 @Value("${error.message_code}")
	 private String errorMessageCode;
	 
	 @Value("${error.message_name}")
	 private String errorMessageName;
	 
	 @Value("${error.message_price}")
	 private String errorMessagePrice;
	 
	 @RequestMapping(value= {"/","/index"},method = RequestMethod.GET)
	 public String index(Model model) {
		 
		 model.addAttribute("message", message);
		 return "index";
	 }
	
	@RequestMapping(value = {"/productList"},method = RequestMethod.GET)
	public String productList(Model model) {
		
		model.addAttribute("products",products);
		return "productList";
	}
	
	@RequestMapping(value= {"/addProduct"},method = RequestMethod.GET)
	public String showAddProductPage(Model model) {
		ProductForm  productForm = new ProductForm();
		model.addAttribute("productForm", productForm);
		return "addProduct";
	}
	
	@RequestMapping(value= {"/addProduct"},method = RequestMethod.POST)
	public String saveProduct(Model model, @ModelAttribute("productForm") ProductForm productForm) {
		 String code = productForm.getCode();
		 String name = productForm.getName();
		 double price = productForm.getPrice();
		 String image = productForm.getImage();
		 
		 if ( code!=null && code.length()>0 && name!=null && name.length()>0 && price!=0) {
			 Product newProduct = new Product(products.size(), code, name, price,image);
			 products.add(newProduct); 
		 return "redirect:/productList";
		 }
		 if(code==null || code.length()==0) {
			 model.addAttribute("errorMessageCode", errorMessageCode);
		 }
		 if(name==null || name.length()==0) {
			 model.addAttribute("errorMessageName", errorMessageName);
		 }
		 if(price==0) {
			 model.addAttribute("errorMessagePrice", errorMessagePrice);
		 }
		 
		return "addProduct";
	}
	
	@RequestMapping(value= {"/updateProduct"},method = RequestMethod.GET)
	public String updateProductPage(Model model) {
		
		ProductForm  productForm = new ProductForm();
		model.addAttribute("products",products);
		model.addAttribute("productForm", productForm);
		return "updateProduct";
	}
	
	
	@RequestMapping(value= {"/updateProduct"},method = RequestMethod.POST)
	public String updateProduct(Model model, @ModelAttribute("productForm") ProductForm productForm) {
		 int id = productForm.getId();
		 String code = productForm.getCode();
		 String name = productForm.getName();
		 double price = productForm.getPrice();
		 String image = productForm.getImage();
		 
		 for(int i=0;i<products.size();i++) {
			 if(products.get(i).getNumProd()==id) {
				 if(code!=null && code.length()>0) {
					 products.get(i).setCode(code);
				 }
				 if(name!=null && name.length()>0) {
					 products.get(i).setName(name);
				 }
				if(price!=0) {
					products.get(i).setPrice(price);
				}
				if(image!=null && image.length()>0) {
					products.get(i).setImage(image);
				}
			 } 
		 }
		 
		 return "redirect:/productList";
	}
	
	@RequestMapping(value= {"/deleteProduct"},method = RequestMethod.GET)
	public String deleteProductPage(Model model) {
		
		ProductForm  productForm = new ProductForm();
		model.addAttribute("products",products);
		model.addAttribute("productForm", productForm);
		return "deleteProduct";
	}
	
	
	@RequestMapping(value= {"/deleteProduct"},method = RequestMethod.POST)
	public String saveDeleteProduct(Model model, @ModelAttribute("productForm") ProductForm productForm) {
		 int id = productForm.getId();
		 if(id>=products.size() || id<0) {
			 return "redirect:/deleteProduct";
		 }else if(products.size()==1 || id==products.size()-1) {
			 products.remove(id);
		 }else {
			 for(int i=id+1;i<products.size();i++)
				 products.get(i).setNumProd(products.get(i).getNumProd()-1);
			 products.remove(id);
		 }
		 
		 return "redirect:/productList";
	}
	
	
}
