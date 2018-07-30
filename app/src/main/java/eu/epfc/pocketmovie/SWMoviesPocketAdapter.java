package eu.epfc.pocketmovie;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import eu.epfc.pocketmovie.model.Movie;


public class SWMoviesPocketAdapter extends RecyclerView.Adapter<SWMoviesPocketAdapter.MovieViewHolder>{

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

//    private List<Movie> Movies;
    private List<Movie> movieDataPocket;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        // get a layoutInflater from the context attached to the parent view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // inflate the layout item_movie in a view
        View movieView = layoutInflater.inflate(R.layout.item_layout,parent,false);

        // create a new ViewHolder with the view movieView
        return new MovieViewHolder(movieView);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie Movie = movieDataPocket.get(position);

        // get the movie name TextView of the item
        ViewGroup itemViewGroup = (ViewGroup)holder.itemView;

        if(movieDataPocket.size()==0)
        {
            TextView titleTextView = itemViewGroup.findViewById(R.id.text_item_title);
            titleTextView.setText("You don't have a movie in the pocket");
        }
        TextView titleTextView = itemViewGroup.findViewById(R.id.text_item_title);
        titleTextView.setText(Movie.getTitle());

        TextView abstractTextView = itemViewGroup.findViewById(R.id.text_item_abstract);
        abstractTextView.setText(Movie.getVote_average());

        if (!Movie.getPoster_path().isEmpty()) {
            ImageView thumbnailImageView = itemViewGroup.findViewById(R.id.image_item);
            Picasso.get().load(Movie.getPoster_path()).into(thumbnailImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (movieDataPocket != null) {
            return movieDataPocket.size();
        }
        else {
            return 0;
        }
    }

    public void setMovies(List<Movie> Movies) {
        this.movieDataPocket = Movies;
        this.notifyDataSetChanged();
    }

    final private ListItemClickListener listItemClickListener;

    public SWMoviesPocketAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public void setMovieDatas(List<Movie> movieDataPocket) {

        this.movieDataPocket = movieDataPocket;

        //notify the adapter that the data have changed
        this.notifyDataSetChanged();
    }
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private MovieViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
            SWMoviesPocketAdapter.this.listItemClickListener.onListItemClick(clickedPosition);
        }

    }








}
