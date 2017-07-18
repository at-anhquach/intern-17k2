package vn.asiantech.internship.notesqlite;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vn.asiantech.internship.R;

import static android.app.Activity.RESULT_OK;

/**
 * Used to enter data for item of recyclerView.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-20
 */
public class AddNoteFragment extends Fragment {
    private static final int REQUEST_CODE = 3;
    private ImageView mImgNote;
    private EditText mEdtTitle;
    private EditText mEdtContent;
    private NoteSqlite mDatabase;
    private Uri mUriImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = new NoteSqlite(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        mImgNote = (ImageView) view.findViewById(R.id.imgNoteAdd);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitleAdd);
        mEdtContent = (EditText) view.findViewById(R.id.edtContentAdd);
        ImageView imgChooseImage = (ImageView) view.findViewById(R.id.imgAddImage);
        ImageView imgAdd = (ImageView) view.findViewById(R.id.imgAdd);
        imgChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
                SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
                if (TextUtils.equals(mEdtTitle.getText().toString(), "") || TextUtils.equals(mEdtContent.getText().toString(), "")) {
                    Toast.makeText(getActivity(), "Inquire enough data entry!", Toast.LENGTH_LONG).show();
                } else {
                    mDatabase.open();
                    mDatabase.createNote(new Note(dayOfWeekFormat.format(date), String.valueOf(DateFormat.format("dd", date)), monthFormat.format(date), String.valueOf(DateFormat.format("hh:mm:ss", date)), mEdtTitle.getText().toString(), mEdtContent.getText().toString(), getRealPathFromUri(mUriImage)));
                    mDatabase.close();
                    ((NoteActivity) (getActivity())).replaceNoteFragment();
                }
            }
        });
        return view;
    }

    private String getRealPathFromUri(Uri tempUri) {
        Cursor cursor;
        try {
            String[] images = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(tempUri, images, null, null, null);
            int column_index;
            String path;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);
                cursor.close();
            } else {
                path = null;
            }
            return path;
        } catch (NullPointerException e) {
            Log.e("Pointer is null", e.toString());
        }
        return "";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE) {
                mUriImage = data.getData();
                mImgNote.setVisibility(View.VISIBLE);
                mImgNote.setImageBitmap(decreaseImageSize(mUriImage));
                Date date = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss", Locale.ENGLISH);
                saveImage(mImgNote, ft.format(date));
            }
        }
    }

    private void saveImage(ImageView imageView, String name) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File image = new File(sdCardDirectory, name + ".png");
        boolean success = false;
        try {
            FileOutputStream outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            success = true;
        } catch (IOException e) {
            Log.e("SaveImage: ", e.toString());
        }
        if (success) {
            Toast.makeText(getActivity(), "Image saved with success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Error during image saving", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap decreaseImageSize(Uri imageFileUri) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int dw = size.x;
        int dh = size.y;
        try {
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) dh);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) dw);
            if (heightRatio > 1 && widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    bmpFactoryOptions.inSampleSize = heightRatio;
                } else {
                    bmpFactoryOptions.inSampleSize = widthRatio;
                }
            }
            bmpFactoryOptions.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);

        } catch (FileNotFoundException e) {
            Log.v("ERROR", e.toString());
        }
        return null;
    }
}
