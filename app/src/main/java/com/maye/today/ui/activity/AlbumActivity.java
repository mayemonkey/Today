package com.maye.today.ui.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import android.util.Log;
import android.widget.GridView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.maye.today.domain.ImageItem;
import com.maye.today.today.R;
import com.maye.today.ui.adapter.AlbumAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AlbumActivity extends Activity implements MaterialSpinner.OnItemSelectedListener {

    private MaterialSpinner ms_album;

    //图片路径集合
    private List<String> list_dir = new ArrayList<>();

    //当前GridView中显示内容List集合
    private List<ImageItem> list = new ArrayList<>();

    //设备中所有图片的文件夹及文件夹内Image
    private Map<String, List<ImageItem>> map = new HashMap<>();

    private AlbumAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getDirList();
            if (list_dir.size() > 0) {
                ms_album.setItems(list_dir);
                ms_album.setSelectedIndex(0);
                list.clear();
                String dir = list_dir.get(0);
                List<ImageItem> imageItems = map.get(dir);
                list.addAll(imageItems);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getImageDir();
                list_dir.clear();
                handler.sendEmptyMessage(0);
            }
        }).start();

        initComponent();
    }

    private void initComponent() {
        ms_album = (MaterialSpinner) findViewById(R.id.ms_album);
        ms_album.setOnItemSelectedListener(this);

        GridView gv_album = (GridView) findViewById(R.id.gv_album);

        adapter = new AlbumAdapter(this, list);
        gv_album.setAdapter(adapter);
    }

    /**
     * 获取所有图片路径
     */
    private void getImageDir() {
        String columns[] = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.PICASA_ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        ContentResolver cr = getContentResolver();

        Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, null);

        if (cur == null) {
            return;
        }

        //获取设备中所有保存的图片信息（由系统数据库收集）
        while (cur.moveToNext()) {
            String _id = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
            String path = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            String bucketName = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));

            ImageItem imageItem = new ImageItem();
            imageItem.setImageId(_id);
            imageItem.setImagePaht(path);

            //添加数据至Map集合中，将文件夹名作为key存储
            //获取新的文件夹
            if (!map.containsKey(bucketName)) {
                List<ImageItem> list = new ArrayList<>();
                list.add(imageItem);
                map.put(bucketName, list);
            } else {
                List<ImageItem> list = map.get(bucketName);
                list.add(imageItem);
                map.put(bucketName, list);
            }
        }
        cur.close();
    }

    private void getDirList() {
        Set<String> set = map.keySet();
        for (String dir : set) {
            list_dir.add(dir);
        }
    }

    public void backResult(String path) {
        Intent intent = new Intent();
        intent.putExtra("path", path);
        setResult(0, intent);
        finish();
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        list.clear();
        String dir = list_dir.get(position);
        List<ImageItem> imageItems = map.get(dir);
        list.addAll(imageItems);
        adapter.notifyDataSetChanged();
    }

}
