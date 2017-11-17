package com.hiteshsahu.awesome_gallery.domain;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;

import com.hiteshsahu.awesome_gallery.modal.CenterRepository;
import com.hiteshsahu.awesome_gallery.modal.ImageModel;

import java.util.ArrayList;

public class ImageMediaProvider {

    private static ImageMediaProvider mediaController = new ImageMediaProvider();

    public static ImageMediaProvider getInstance() {
        return mediaController;
    }

    private ImageMediaProvider() {
    }

    public void getAllImages(Context appContext) {

        CenterRepository.getInstance().getImageCollection().getListOfImages().clear();

        String[] projection = {MediaColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME};

        //Fetch all External SD card Images
        fetchImagesInCursor(appContext.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                        null,
                        null,
                        null));

        // Get all Internal SD Card images
        fetchImagesInCursor(appContext.getContentResolver()
                .query(MediaStore.Images.Media.INTERNAL_CONTENT_URI, projection,
                        null,
                        null,
                        null));

    }

    public void getImageFolderMap(Context appContext) {

        CenterRepository.getInstance().getImageCollection().getImagesInFolderMap().clear();

        String[] projection = {MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        //Get all External Image Files
        fetchImagesInFolderCursor(appContext.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        null,
                        null,
                        null));

        // Get all Internal images
        fetchImagesInFolderCursor(appContext.getContentResolver()
                .query(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                        projection,
                        null,
                        null,
                        null));

        //Get all folders Name from Map
        CenterRepository.getInstance()
                .getImageCollection()
                .setFolderNames(new ArrayList<String>(CenterRepository
                        .getInstance()
                        .getImageCollection()
                        .getImagesInFolderMap()
                        .keySet()));
    }

    private void fetchImagesInFolderCursor(Cursor imageFolderCursor) {
        int columnIndexData = imageFolderCursor.getColumnIndexOrThrow(MediaColumns.DATA);
        int columnIndexFolderName = imageFolderCursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while (imageFolderCursor.moveToNext()) {

            String absolutePathOfImage = imageFolderCursor.getString(columnIndexData);
            String folderName = imageFolderCursor.getString(columnIndexFolderName);

            if (CenterRepository.getInstance().getImageCollection().isFolderAdded(folderName)) {

                //If folder is added then insert new Image Data into that folder
                CenterRepository.getInstance()
                        .getImageCollection()
                        .getImagesInFolderMap()
                        .get(folderName)
                        .add(new ImageModel(folderName, absolutePathOfImage));

            } else {

                //if folder not added then create a new list
                ArrayList<ImageModel> listOfImagesInFolder = new ArrayList<ImageModel>();
                //insert image data in that list
                listOfImagesInFolder
                        .add(new ImageModel(folderName, absolutePathOfImage));
                //add list into that folder
                CenterRepository.getInstance()
                        .getImageCollection()
                        .getImagesInFolderMap()
                        .put(folderName, listOfImagesInFolder);
            }
        }

        imageFolderCursor.close();
    }

    private void fetchImagesInCursor(Cursor imageCursor) {
        int columnIndexData = imageCursor.getColumnIndexOrThrow(MediaColumns.DATA);
        int columnIndexName = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

        while (imageCursor.moveToNext()) {

            CenterRepository.getInstance()
                    .getImageCollection()
                    .getListOfImages()
                    .add(new ImageModel(imageCursor.getString(columnIndexName), imageCursor.getString(columnIndexData)));
        }
        imageCursor.close();
    }


}
