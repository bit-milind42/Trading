package com.milind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PatchExchange;

import com.milind.modal.Coin;
import com.milind.modal.User;
import com.milind.modal.Watchlist;
import com.milind.service.CoinService;
import com.milind.service.UserService;
import com.milind.service.WatchlistService;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    
    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<Watchlist> getUserWatchlist(
        @RequestHeader("Authorization") String jwt) {
            User user = userService.getUserProfileByJwt(jwt);
            Watchlist watchlist = watchlistService.findUserWatchlist(user.getId());
        return ResponseEntity.ok(watchlist);
    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<Watchlist> getWatchlistById(
        @PathVariable Long watchlistId) {
            Watchlist watchlist = watchlistService.findById(watchlistId);
        return ResponseEntity.ok(watchlist);
    }

    @PatchExchange("/add/coin/{coinId}")
    public ResponseEntity<Coin> addCoinToWatchlist(
        @PathVariable String coinId,
        @RequestHeader("Authorization") String jwt) {
            User user = userService.getUserProfileByJwt(jwt);
            Coin coin = coinService.findById(coinId);
            Coin addedCoin = watchlistService.addItemToWatchlist(coin, user);
        return ResponseEntity.ok(addedCoin);
    }
}
