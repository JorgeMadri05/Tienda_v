package com.tienda.controller;

import com.tienda.domain.Item;
import com.tienda.domain.Producto;
import com.tienda.services.ItemService;
import com.tienda.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarritoController {
    //Para agregarun item al carrito

    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductoService productoService;

    @GetMapping("/carrito/agregar/{idProducto}")
    public ModelAndView agregar(Model model, Item item) {
        //Se valida si ya en el carrito el producto
        Item item2 = itemService.get(item);
        if (item2 == null) {
            //No estaba en el carrito, hay que traerlo
            Producto producto = productoService.getProducto(item);
            item2 = new Item(producto);
        }
        itemService.save(item2);
        var lista = itemService.gets();
        var venta = itemService.getTotal();
        var total = lista.size();
        model.addAttribute("listaItems", lista);
        model.addAttribute("listaTotal", total);
        model.addAttribute("carritoTotal", venta);

        return new ModelAndView("/carrito/fragmentos :: verCarrito");
    }

    @GetMapping("/carrito/listado")
    public String listado(Model model) {
        var items = itemService.gets();
        var total = itemService.getTotal();
        model.addAttribute("items", items);
        model.addAttribute("carritoTotal", total);
        return "/carrito/listado";
    }

    @GetMapping("/carrito/eliminar/{idProducto}")
    public String eliminar(Model model, Item item) {
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }

    @GetMapping("/carrito/modificar/{idProducto}")
    public String modificar(Model model, Item item) {
        itemService.get(item);
        model.addAttribute("item", item);
        return "/carrito/modificar";
    }

    @PostMapping("/carrito/guardar")
    public String guardar(Item item) {
        itemService.save(item);
        return "redirect:/carrito/listado";
    }
    @GetMapping("/facturar/carrito")
    public String facturar() {
        itemService.facturar();
        return "redirect:/";
    }

}
