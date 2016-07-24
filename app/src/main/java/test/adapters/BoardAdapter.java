package test.adapters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import test.models.Pin;
import test.utils.ImageCache;
import test.utils.UniversalDownloader;

/**
 * Created by Babar on 23-Jul-16.
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder>
{
    private int layoutResId;

    private List<Pin> pins;

    private UniversalDownloader downloader;

    private int reqWidth, reqHeight;

    public BoardAdapter(int layoutResId, List<Pin> pins)
    {
        this.layoutResId = layoutResId;

        this.pins = pins;

        downloader = new UniversalDownloader();

        reqWidth = Resources.getSystem().getDisplayMetrics().widthPixels / 2;
        reqHeight = Resources.getSystem().getDisplayMetrics().heightPixels / 2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View pin = LayoutInflater.from(parent.getContext())
                                 .inflate(layoutResId, parent, false);

        return new ViewHolder(pin);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
        Pin pin = getItem(position);

        Bitmap bitmap = ImageCache.getInstance().getBitmapFromCache(pin.getUrl());

        if(bitmap != null)
        {
            viewHolder.imageView.setImageBitmap(bitmap);
        }
        else
        {
            viewHolder.imageView.setImageBitmap(null);

            downloader.downloadAndSetImage(pin.getUrl(), reqWidth,
                                           reqHeight, viewHolder.imageView,
                                           viewHolder.imageView + pin.getUrl());
        }

    }

    private Pin getItem(int position)
    {
        return pins.get(position);
    }

    @Override
    public int getItemCount()
    {
        return pins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView;

        public ViewHolder(View pin)
        {
            super(pin);

            imageView = (ImageView) pin;
        }
    }
}