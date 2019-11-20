package com.sda.auction.controller;

import com.sda.auction.dto.ItemDto;
import com.sda.auction.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping("/api/admin/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    public ResponseEntity<String>get(){
        return new ResponseEntity<>("hello world!", HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ItemDto>post(@Valid @RequestBody ItemDto itemDto,
                                       HttpServletRequest request)throws ParseException {
        String ownerEmail = (String) request.getAttribute("ownerEmail");
        ItemDto itemDtoResult = itemService.addItem(itemDto, ownerEmail);

        return new ResponseEntity<>(itemDtoResult, HttpStatus.OK);
    }

}
