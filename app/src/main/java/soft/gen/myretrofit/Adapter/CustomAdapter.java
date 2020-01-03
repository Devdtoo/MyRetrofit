package soft.gen.myretrofit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import soft.gen.myretrofit.ItemActivity;
import soft.gen.myretrofit.MainActivity;
import soft.gen.myretrofit.R;
import soft.gen.myretrofit.Utility.Commonhelper;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private JSONArray array;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomAdapter(JSONArray array, Context context ) {
        this.array = array;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);
        ViewHolder holder = new ViewHolder(view); // pass the view to View Holder
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            holder.name.setText(array.getJSONObject(position).getString("name"));
            holder.description.setText(array.getJSONObject(position).getString("description"));
            holder.price.setText(array.getJSONObject(position).getString("price"));
            holder.chef.setText(array.getJSONObject(position).getString("chef"));
            holder.timestamp.setText(array.getJSONObject(position).getString("timestamp"));
            String url = array.getJSONObject(position).getString("thumbnail");
            Glide.with(context).load(url).into(holder.thumbnail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ItemActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price,chef,timestamp;// init the item view's
        ImageView thumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
           this.name =  itemView.findViewById(R.id.name);
           this.description =  itemView.findViewById(R.id.description);
           this.price =  itemView.findViewById(R.id.price);
           this.chef =  itemView.findViewById(R.id.chef);
           this.timestamp =  itemView.findViewById(R.id.timestamp);
           this.thumbnail =  itemView.findViewById(R.id.thumbnail);

        }
    }


}
