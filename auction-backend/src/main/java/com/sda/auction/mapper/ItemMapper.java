package com.sda.auction.mapper;

import com.sda.auction.dto.ItemDto;
import com.sda.auction.model.Item;
import com.sda.auction.util.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class ItemMapper {

    @Autowired
    private DateConverter dateConverter;

    public Item convert(ItemDto itemDto) throws ParseException {

        Item item = new Item();
        item.setName(itemDto.getName());
        item.setCategory(itemDto.getCategory());
        item.setDescription(itemDto.getDescription());
        item.setStartingPrice(itemDto.getStartingPrice());

        Date startDate = dateConverter.parse(itemDto.getStartDate());
        item.setStartDate(startDate);

        Date endDate = dateConverter.parse(itemDto.getEndDate());
        item.setEndDate(endDate);

        return item;
    }


    public ItemDto convert(Item item) throws ParseException {

        ItemDto itemDto = new ItemDto();
        itemDto.setName(item.getName());
        itemDto.setCategory(item.getCategory());
        itemDto.setDescription(item.getDescription());
        itemDto.setStartingPrice(item.getStartingPrice());

        String startDate = dateConverter.format(item.getStartDate());
        itemDto.setStartDate(startDate);

        String endDate = dateConverter.format(item.getEndDate());
        itemDto.setEndDate(endDate);

        itemDto.setId(item.getId());

        return itemDto;
    }
}
