package com.example.joao.cafeclientapp.cart;

import android.app.Activity;
import android.util.Log;

import com.example.joao.cafeclientapp.CustomLocalStorage;
import com.example.joao.cafeclientapp.menu.Product;
import com.example.joao.cafeclientapp.menu.ProductsMenu;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andre on 15/10/2016.
 */

public class Cart extends ProductsMenu implements Serializable {
    static private Cart instance;

    static public Cart getInstance(Activity a) {
        if(instance == null) {
            instance = new Cart();
            instance.getSavedCart(a);
        }
        return instance;
    }

    private Cart() {}

    public void addProductToCart(Product p) {
        if(products.get(p.getId()) == null) {
            p.setQuantity(1);
            products.put(p.getId(),p);
        }
        else {
            products.get(p.getId()).setQuantity(products.get(p.getId()).getQuantity()+1);
        }
    }

    public void removeProductFromCart(Product p) {
        if(products.get(p.getId()) == null) return;
        Integer quantity = products.get(p.getId()).getQuantity();
        if(quantity < 2) {
            products.remove(p.getId());
        }
        else if(quantity > 1) {
            products.get(p.getId()).setQuantity(products.get(p.getId()).getQuantity()-1);
        }
    }

    public void getSavedCart(Activity a) {
        try {
            instance = CustomLocalStorage.getCart(a);
        } catch (Exception e) {
            instance = new Cart();
        }
    }

    public void saveCart(Activity a) {
        try {
            CustomLocalStorage.saveCart(a,instance);
        } catch (IOException e) {
            Log.e("cart","couldn't save cart");
        }
    }

    public void resetCart() {
        products.clear();
    }

    public static String printCart(Map<String,Double> c) {
        String ret = "Cart: ";

        for (Map.Entry<String, Double> entry : c.entrySet())
        {
            ret += "\n" + entry.getKey() + "/" + entry.getValue();
        }
        return ret;
    }
}