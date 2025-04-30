package com.milind.service;

import java.util.List;

import com.milind.modal.Asset;
import com.milind.modal.Coin;
import com.milind.modal.User;

public interface AssetService {
    
    Asset createAsset (User user, Coin coin, double quantity); 
    Asset getAssetById(Long assetId); 
     
    Asset getAssetByUserIdAndId (Long userId, Long assetId); 
    List<Asset> getUsersAssets(Long userId); 
     
    Asset updateAsset (Long assetId, double quantity);  
    Asset findAssetByUserIdAndCoinId (Long userId, String coinId); 
    void deleteAsset (Long assetId); 

}
