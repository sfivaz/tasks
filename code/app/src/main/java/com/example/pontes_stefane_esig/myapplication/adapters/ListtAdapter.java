package com.example.pontes_stefane_esig.myapplication.adapters;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.pontes_stefane_esig.myapplication.R;
import com.example.pontes_stefane_esig.myapplication.activities.CardFormActivity;
import com.example.pontes_stefane_esig.myapplication.activities.ProjectActivity;
import com.example.pontes_stefane_esig.myapplication.daos.CardDAO;
import com.example.pontes_stefane_esig.myapplication.models.Card;
import com.example.pontes_stefane_esig.myapplication.models.Listt;

public class ListtAdapter extends RecyclerView.Adapter<ListtAdapter.MyViewHolder>
        implements View.OnLongClickListener {

    private final ProjectAdapter projectAdapter;
    private final int listtPosition;
    private final ProjectActivity activity;
    private Listt listt;

    ListtAdapter(Listt listt, ProjectAdapter projectAdapter, int listtPosition, ProjectActivity activity) {
        this.listt = listt;
        this.projectAdapter = projectAdapter;
        this.listtPosition = listtPosition;
        this.activity = activity;
    }

    public Listt getListt() {
        return listt;
    }

    void updateBurnDownChart() {
        activity.checkCurrentState();
    }

    //ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener {

        TextView tvName;
        TextView tvPoints;
        FrameLayout flCard;
        private Card card;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_card_name);
            tvPoints = view.findViewById(R.id.tv_card_points);
            flCard = view.findViewById(R.id.fl_card);

            view.setOnCreateContextMenuListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view,
                                        final ContextMenu.ContextMenuInfo menuInfo) {
            //TODO check if these view.getId and getAdapterPosition are really useful
            MenuItem edit = menu.add(0, view.getId(), getAdapterPosition(), R.string.card_edit);
            MenuItem delete = menu.add(0, view.getId(), getAdapterPosition(), R.string.card_delete);

            edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (card != null) {
                        Intent intent = new Intent(activity, CardFormActivity.class);
                        intent.putExtra("card_id", card.getId());
                        activity.startActivity(intent);
                    }
                    return false;
                }
            });

            delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    //TODO remove this check later
                    if (card != null) {
                        //TODO maybe I should just use the position to find the Card,
                        //TODO instead of getting the card from a setCard
                        CardDAO dao = new CardDAO(activity);
                        dao.delete(card);
                        listt.getCards().remove(card);
                        ListtAdapter.this.notifyItemRemoved(((Integer) flCard.getTag()));
                        updateTotal();
                    }
                    return false;
                }
            });
        }

        @Override
        public void onClick(View view) {
            view.showContextMenu();
        }

        public void setCard(Card card) {
            this.card = card;
        }
    }

    private void updateTotal() {
        projectAdapter.notifyDataSetChanged();
        activity.invalidateOptionsMenu();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Card card = listt.getCards().get(position);
        holder.setCard(card);
        //TODO perform these methods inside MyViewHolder class
        holder.tvName.setText(card.getName());
        holder.tvPoints.setText(String.valueOf(card.getPoints()));
        holder.flCard.setTag(position);
        holder.flCard.setOnLongClickListener(this);
        holder.flCard.setOnDragListener(new DragListener(projectAdapter, listtPosition));
    }

    @Override
    public int getItemCount() {
        return listt.getCards().size();
    }

    //Drag and Drop
    @Override
    public boolean onLongClick(View view) {
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.startDragAndDrop(data, shadowBuilder, view, 0);
        } else {
            view.startDrag(data, shadowBuilder, view, 0);
        }
        return true;
    }
}
