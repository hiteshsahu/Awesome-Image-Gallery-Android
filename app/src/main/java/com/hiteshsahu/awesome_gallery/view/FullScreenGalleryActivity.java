package com.hiteshsahu.awesome_gallery.view;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hiteshsahu.awesome_gallery.BuildConfig;
import com.hiteshsahu.awesome_gallery.R;
import com.hiteshsahu.awesome_gallery.modal.CenterRepository;
import com.hiteshsahu.awesome_gallery.view.adapter.TouchImageAdapter;
import com.hiteshsahu.awesome_gallery.view.widget.ExtendedViewPager;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FullScreenGalleryActivity extends AppCompatActivity {

    public static final String IMAGE_POSITION = "PagerPosition";

    @BindView(R.id.view_pager)
    ExtendedViewPager mViewPager;
    private TouchImageAdapter galleryAdaqpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_gallery);
        ButterKnife.bind(this);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setEnterTransition(new Explode());
//            getWindow().setExitTransition(new Explode());
//            getWindow().setSharedElementEnterTransition(new ChangeImageTransform());
//            getWindow().setSharedElementExitTransition(new ChangeImageTransform());
//            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this)
//                    .inflateTransition(R.transition.curve));
//        }

        int currentPagePosition = getIntent().getIntExtra(IMAGE_POSITION, 0);
        galleryAdaqpter = new TouchImageAdapter();
        mViewPager.setAdapter(galleryAdaqpter);
        mViewPager.setCurrentItem(currentPagePosition);

//
//            rootView.findViewById(R.id.info).setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
////                    // 1. Instantiate an AlertDialog.Builder with its
////                    // constructor
////                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
////
////                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int id) {
////
////                        }
////                    });
////                    builder.setNegativeButton("Done", new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int id) {
////                            // User cancelled the dialog
////                        }
////                    });
////
////                    StringBuilder str = new StringBuilder();
////
////                    // 2. Chain together various setter methods to set the
////                    // dialog characteristics
////
////                    // 3. Get the AlertDialog from create()
////
////                    // There are multiple ways to get a Metadata object for
////                    // a file
////
////                    //
////                    // SCENARIO 1: UNKNOWN FILE TYPE
////                    //
////                    // This is the most generic approach. It will
////                    // transparently determine the file type and invoke the
////                    // appropriate
////                    // readers. In most cases, this is the most appropriate
////                    // usage. This will handle JPEG, TIFF, GIF, BMP and RAW
////                    // (CRW/CR2/NEF/RW2/ORF) files and extract whatever
////                    // metadata is available and understood.
////
////                    //
////
////                    File file = getImageFile();
////
////                    try {
////                        Metadata metadata = ImageMetadataReader.readMetadata(file);
////
////                        for (Directory directory : metadata.getDirectories()) {
////
////                            //
////                            // Each Directory stores values in Tag objects
////                            //
////                            for (Tag tag : directory.getTags()) {
////                                System.out.println(tag);
////
////                                str.append(tag.toString());
////                                str.append("\n");
////
////                            }
////
////                            //
////                            // Each Directory may also contain error
////                            // messages
////                            //
////                            if (directory.hasErrors()) {
////                                for (String error : directory.getErrors()) {
////                                    System.err.println("ERROR: " + error);
////                                }
////                            }
////
////                        }
////
////                    } catch (ImageProcessingException e) {
////                        // handle exception
////                    } catch (IOException e) {
////                        // handle exception
////                    }
////
////                    builder.setMessage(str.toString()).setTitle("Image Info");
////
////                    AlertDialog dialog = builder.create();
////
////                    dialog.show();
//
//                }
//            });
//


    }

    @OnClick(R.id.canvas)
    public void editThisItem() {
        Intent editIntent = new Intent(Intent.ACTION_EDIT);
        editIntent.setDataAndType(Uri.fromFile(
                new File(CenterRepository.getInstance().getImageCollection().
                        getImageAt((mViewPager.getCurrentItem())).getImagePath())), "image/*");
        editIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(editIntent, null));

    }

    @OnClick(R.id.share)
    public void shareThisItem() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(FullScreenGalleryActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        new File(CenterRepository.getInstance().getImageCollection().
                                getImageAt((mViewPager.getCurrentItem())).getImagePath())));
        startActivity(Intent.createChooser(share, "Share Image"));
    }


    @OnClick(R.id.delete)
    public void deleteThisFile() {

        // Set up the projection (we only need the ID)
        String[] projection = {MediaStore.Images.Media._ID};

        // Match on the file path
        String selection = MediaStore.Images.Media.DATA + " = ?";
        String[] selectionArgs = new String[]{CenterRepository.getInstance().getImageCollection().
                getImageAt((mViewPager.getCurrentItem())).getImagePath()
        };

        // Query for the ID of the media matching the file path
        Cursor imageCursor = getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        selection,
                        selectionArgs,
                        null);

        if (imageCursor != null) {

            if (imageCursor.moveToFirst()) {
                // We found the ID. Deleting the item via the content
                // provider will also remove the file
                long imageID =
                        imageCursor.getLong(imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                Uri deleteUri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                imageID);
                getContentResolver().delete(deleteUri, null, null);

                CenterRepository.getInstance().getImageCollection().
                        getListOfImages().remove(mViewPager.getCurrentItem());

                galleryAdaqpter.notifyDataSetChanged();

                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(new File(CenterRepository.getInstance().getImageCollection().
                                getImageAt((mViewPager.getCurrentItem())).getImagePath()))));

                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();

            }

            imageCursor.close();
        }
    }
}

