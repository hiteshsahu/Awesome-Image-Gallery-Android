package com.hiteshsahu.awesome_gallery.modal;

/**
 * Created by Hitesh on 23-07-2016.
 */
public class CenterRepository {

    private ImageCollection imageCollection = new ImageCollection();

    private static CenterRepository centerRepositoryInstance;

    private CenterRepository() {
    }

    public static CenterRepository getInstance() {

        if (null == centerRepositoryInstance) {
            centerRepositoryInstance = new CenterRepository();
        }
        return centerRepositoryInstance;
    }

    public ImageCollection getImageCollection() {
        return imageCollection;
    }


    public void clearImageCollection() {
        imageCollection.getListOfImages().clear();
    }

}
