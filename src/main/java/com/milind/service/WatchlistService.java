package com.milind.service;

import com.milind.modal.Watchlist;

public interface WatchlistService {
    
    Watchlist findUWatchlist(Long userId);
    Watchlist createWatchlist(User user);
    Watchlist findById(Long id);

    Coin addItemToWatchlist(Coin coin, User user);
}
