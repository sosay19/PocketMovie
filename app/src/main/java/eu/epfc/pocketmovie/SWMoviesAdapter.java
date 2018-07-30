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





public class SWMoviesAdapter extends RecyclerView.Adapter<SWMoviesAdapter.MovieViewHolder>{

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    private List<Movie> movieDatas;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        // get a layoutInflater from the context attached to the parent view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // inflate the layout item_movie in a view
//        View movieView = layoutInflater.inflate(R.layout.item_layout,parent,false);

        View itemView;

        if(viewType==R.layout.item_text_previous) {
            itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.item_text_previous, parent, false);
        }
        else if(viewType==R.layout.item_layout){
            itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        }
        else{
            itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.item_text_next, parent, false);
        }

        // create a new ViewHolder with the view movieView
        return new MovieViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        // get the movie name TextView of the item
        ViewGroup itemViewGroup = (ViewGroup)holder.itemView;
        TextView titleTextView1 = itemViewGroup.findViewById(R.id.text_next_film);
        TextView titleTextView2 = itemViewGroup.findViewById(R.id.text_previous_film);
//        if(movieDatas.size()==0)
//        {
//
//            titleTextView1.setText("There aren't movie, check your internet");
//
//            titleTextView2.setText("");
//
//        }

        if(position == 20) {

        }
        else if(position == 21)
        {

        }
        else {
            Movie Movie = movieDatas.get(position);



            TextView titleTextView = itemViewGroup.findViewById(R.id.text_item_title);
            titleTextView.setText(Movie.getTitle());


            TextView abstractTextView = itemViewGroup.findViewById(R.id.text_item_abstract);
            abstractTextView.setText("Rating : " +Movie.getVote_average());

            if (!Movie.getPoster_path().isEmpty()) {
                ImageView thumbnailImageView = itemViewGroup.findViewById(R.id.image_item);
                Picasso.get().load(Movie.getPoster_path()).into(thumbnailImageView);
            }
        }


    }

    @Override
    public int getItemCount() {
        if (movieDatas != null) {
            return movieDatas.size()+ 2;
        }
        else {
            return 0;
        }
    }

    public void setMovies(List<Movie> Movies) {
        this.movieDatas = Movies;
        this.notifyDataSetChanged();
    }

    final private ListItemClickListener listItemClickListener;

    public SWMoviesAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public void setMovieDatas(List<Movie> movieDatas) {
        this.movieDatas = movieDatas;
        //notify the adapter that the data have changed
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if(position==21)
        {
            return R.layout.item_text_previous;
        }
        else if(position==20)
        {
            return R.layout.item_text_next;
        }
        else
        {
            return  R.layout.item_layout;
        }



    }





    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public View text;

        private MovieViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//            text = (TextView) itemView.findViewById(R.id.text_next_film);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            SWMoviesAdapter.this.listItemClickListener.onListItemClick(clickedPosition);
        }

    }

}




