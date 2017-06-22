package vn.asiantech.internship.day11.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day11.database.NoteModify;
import vn.asiantech.internship.day11.model.Note;
import vn.asiantech.internship.day11.ui.activity.NoteActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by at-hoavo on 19/06/2017.
 */
public class InformationEditFragment extends Fragment {
    private static final int TYPE_GALLERY = 0;

    private EditText mEdtTitle;
    private EditText mEdtDescription;
    private ImageView mImgNote;
    private ImageView mImgSave;
    private ImageView mImgPhoto;
    //    private Uri mUri;
    Uri saveUriImage;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_information, container, false);
        initView(v);
        mImgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, TYPE_GALLERY);
            }
        });
        mImgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEdtTitle.getText().toString())) {
                    Toast.makeText(getContext(), "input title", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(mEdtDescription.getText().toString())) {
                    Toast.makeText(getContext(), "input description", Toast.LENGTH_LONG).show();
                } else if (saveUriImage == null) {
                    Toast.makeText(getContext(), "input picture", Toast.LENGTH_LONG).show();
                } else {
                    NoteModify noteModify = new NoteModify(getContext());
                    Date date = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd hh:mm:ss ", Locale.getDefault());
                    Note note = new Note(0, mEdtTitle.getText().toString(), mEdtDescription.getText().toString(), saveUriImage.toString(), ft.format(date));
                    noteModify.insert(note);
                    if (getActivity() instanceof NoteActivity) {
                        ((NoteActivity) getActivity()).changeFragment(new NoteFragment());
                    }
                }
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && requestCode == TYPE_GALLERY) {
            if (data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(
                        selectedImage, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                }

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                convertBitmapToFile(bitmap);
                mImgNote.setVisibility(View.VISIBLE);
                mImgNote.setImageBitmap(bitmap);
            }
        }
    }

    public void initView(View v) {
        mEdtTitle = (EditText) v.findViewById(R.id.edtTitle);
        mEdtDescription = (EditText) v.findViewById(R.id.edtDescription);
        mImgNote = (ImageView) v.findViewById(R.id.imgEditInformation);
        mImgSave = (ImageView) v.findViewById(R.id.imgSave);
        mImgPhoto = (ImageView) v.findViewById(R.id.imgPhoto);
    }

    public void convertBitmapToFile(Bitmap bitmap) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//check if device mount with externalStorage
            String path = Environment.getExternalStorageDirectory().getPath();
            File file = new File(path, "ImageNote"); //create folder with absolute dir and filename
            if (!file.exists()) {
                file.mkdir();
            } else {
                Log.d("Error", "  Folder isn't create ");
            }
            File f = new File(file, "Image" + setFileName() + ".png");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                OutputStream os = new FileOutputStream(f);  //create outputstream to write file
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os); // compress bitmap to PNG
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveUriImage = Uri.parse(f.getAbsolutePath());
            Toast.makeText(getContext(), "uri:  " + saveUriImage, Toast.LENGTH_LONG).show();
        }
    }

    private String setFileName() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss ", Locale.getDefault());
        String name = simpleDateFormat.format(date).trim();
        return name.replace(":", "").replace(" ", "").replace(".", "");
    }
}
