package capstone.walkreen.controller;

import capstone.walkreen.dto.*;
import capstone.walkreen.service.DailyService;
import capstone.walkreen.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/getItem")
    public ResponseEntity<ItemResponse> getItem(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(itemService.getItem(httpServletRequest));
    }

    @PostMapping("/buyItem")
    public ResponseEntity<ItemResponse> buyItem(@RequestBody ItemRequest itemRequest, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(itemService.buyItem(itemRequest, httpServletRequest));
    }
}
