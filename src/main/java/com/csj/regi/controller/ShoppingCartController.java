package com.csj.regi.controller;

import com.csj.regi.common.Result;
import com.csj.regi.domain.ShoppingCart;
import com.csj.regi.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:csj
 * @version:1.0
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

   @PostMapping("/add")
    public Result<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
       return shoppingCartService.addToShoppingCart(shoppingCart);
   }
   @GetMapping("/list")
    public Result<List<ShoppingCart>> list(){
       return shoppingCartService.listCart();
   }
   @DeleteMapping("/clean")
    public Result<String> clean(){
       return shoppingCartService.cleanCart();
   }

    @PostMapping("/sub")
    public Result<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
       return shoppingCartService.sub(shoppingCart);
    }
}
