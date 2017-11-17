package com.hiteshsahu.awesome_gallery.modal;

public class ImageModel {

    private String imageName;
    private String imagePath;

    public String getImageName() {
        return imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ImageModel(String imageName, String imagePath) {
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

}
