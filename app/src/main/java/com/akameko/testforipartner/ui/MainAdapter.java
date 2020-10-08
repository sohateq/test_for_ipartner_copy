package com.akameko.testforipartner.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.akameko.testforipartner.R;
import com.akameko.testforipartner.repository.pojos.getentries.Note;
import com.akameko.testforipartner.utils.DateUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Main adapter for main recyclerView in [MainFragment]
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Note> noteList;

    private OnItemClickListener itemClickListener;


    public interface OnItemClickListener {
        void onItemClick(Note noteToShow);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layoutItemMain;

        public TextView textViewItemCreated;
        public TextView textViewItemChanged;
        public TextView textViewItemBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemCreated = itemView.findViewById(R.id.text_view_note_created);
            textViewItemChanged = itemView.findViewById(R.id.text_view_note_changed);
            textViewItemBody = itemView.findViewById(R.id.text_view_note_body);

            layoutItemMain = itemView.findViewById(R.id.main_item_layout);
        }
    }

    public MainAdapter(@NotNull List<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String dateCreated = DateUtils.convertDigitsToDate(noteList.get(position).getDa());
        String dateChanged = DateUtils.convertDigitsToDate(noteList.get(position).getDm());

        holder.textViewItemCreated.setText(dateCreated);
        holder.textViewItemChanged.setText(dateChanged);

        if (!dateCreated.equals(dateChanged)) {
            holder.textViewItemChanged.setVisibility(View.VISIBLE);
        }

        holder.textViewItemBody.setText(noteList.get(position).getBody());

        holder.layoutItemMain.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(noteList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}