package com.hiteshsahu.awesome_gallery.modal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hitesh on 23-07-2016.
 */
public class ImageCollection {

    private ArrayList<String> folderNames = new ArrayList<>();
    private ArrayList<ImageModel> listOfImages = new ArrayList<ImageModel>();
    private Map<String, ArrayList<ImageModel>> imagesInFolderMap = new HashMap<>();

    public Map<String, ArrayList<ImageModel>> getImagesInFolderMap() {
        return imagesInFolderMap;
    }

    public boolean isFolderAdded(String folderName) {
        return imagesInFolderMap.containsKey(folderName);
    }

    public ArrayList<ImageModel> getListOfImages() {
        return listOfImages;
    }

    public ArrayList<String> getFolderNames() {
        return folderNames;
    }

    public void setFolderNames(ArrayList folderNames) {
        this.folderNames = folderNames;
    }

    public ImageModel getImageAt(int position) {
        return listOfImages.get(position);
    }
}
