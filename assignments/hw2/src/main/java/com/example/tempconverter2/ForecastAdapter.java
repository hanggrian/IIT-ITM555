package com.example.tempconverter2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter for the forecast recycler view in the stub view.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private String[] temperatures;
    private String[] dates;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        temperatures = parent.getContext().getResources().getStringArray(R.array.wkTemps);
        dates = parent.getContext().getResources().getStringArray(R.array.wkDates);
        return new ViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.stubview_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(temperatures[position]);
        holder.subtitle.setText(dates[position]);

        switch (position) {
            case 0:
                holder.image.setImageResource(R.drawable.weather_cloudy);
                break;
            case 1:
            case 2:
                holder.image.setImageResource(R.drawable.weather_rainy);
                break;
            case 3:
            default:
                holder.image.setImageResource(R.drawable.weather_sunny);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView subtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
        }
    }
}
