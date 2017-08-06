package com.example.mateus.jera.list_books;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mateus.jera.helpers.Book;
import com.example.mateus.jera.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mateus.jera.R.string.pages;

class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private ArrayList<Book> mDataSet;
    private Context mContext;

    BookListAdapter(ArrayList<Book> dataSet, Context context) {
        mDataSet = dataSet;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        parent = (ViewGroup) layoutInflater.inflate(R.layout.book_list_custom_content, parent, false);
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = mDataSet.get(position);
        holder.textViewMainBookTitle.setText(book.getTitle());
        holder.textViewMainBookPages.setText(getStringRes(pages, book.getPages()));
    }

    private String getStringRes(int stringRes, Object... formatArgs) {
        return mContext.getResources().getString(stringRes, formatArgs);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.main_book_title)
        TextView textViewMainBookTitle;
        @BindView(R.id.main_book_pages)
        TextView textViewMainBookPages;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}