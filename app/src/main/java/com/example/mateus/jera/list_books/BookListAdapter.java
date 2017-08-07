package com.example.mateus.jera.list_books;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mateus.jera.R;
import com.example.mateus.jera.helpers.Book;
import com.example.mateus.jera.list_books.BookListContract.Presenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mateus.jera.R.string.pages;

class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private ArrayList<Book> mDataSet;
    private Context mContext;
    private Presenter mPresenter;

    BookListAdapter(Context context, ArrayList<Book> dataSet, Presenter presenter) {
        mDataSet = dataSet;
        mContext = context;
        mPresenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        parent = (ViewGroup) layoutInflater.inflate(R.layout.book_list_custom, parent, false);
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Book book = mDataSet.get(position);
        final int index = position;
        holder.mBookTitle.setText(book.getTitle());
        holder.mBookPages.setText(getStringRes(pages, book.getPages()));
        holder.mBookDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.confirmDelete(getBookClicked(index));
            }
        });
        holder.mBookReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setNewReminder(getBookClicked(index));
            }
        });
    }

    private String getStringRes(int stringRes, Object... formatArgs) {
        return mContext.getResources().getString(stringRes, formatArgs);
    }

    private Book getBookClicked(int position) {
        return mDataSet.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.book_image)
        ImageView mBookImage;
        @BindView(R.id.book_title)
        TextView mBookTitle;
        @BindView(R.id.book_pages)
        TextView mBookPages;
        @BindView(R.id.book_delete)
        ImageButton mBookDelete;
        @BindView(R.id.book_reminder)
        ImageButton mBookReminder;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}