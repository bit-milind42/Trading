package com.milind.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milind.modal.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    
    Watchlist findByUserId(Long UserId);
}
