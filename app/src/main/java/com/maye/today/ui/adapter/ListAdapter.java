package com.maye.today.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.maye.today.domain.Group;
import com.maye.today.group.GroupPresenter;
import com.maye.today.today.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private boolean onBind;
    private List<Group> list;
    private Context context;

    public ListAdapter(Context context, List<Group> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        onBind = true;
        holder.cb_done.setChecked(list.get(position).isDone());
        holder.tv_description.setText(list.get(position).getDescription());
        onBind = false;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
        CheckBox cb_done;
        TextView tv_description;

        public ListViewHolder(View itemView) {
            super(itemView);
            cb_done = (CheckBox) itemView.findViewById(R.id.cb_done);
            cb_done.setOnCheckedChangeListener(this);

            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_description.setOnClickListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!onBind) {
                list.get(getAdapterPosition()).setDone(isChecked);
                notifyItemChanged(getAdapterPosition());
            }
        }

        @Override
        public void onClick(View v) {
            new MaterialDialog.Builder(context).title("input").
                    inputRangeRes(2, 20, R.color.md_edittext_error).
                    input(null, null, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            if (TextUtils.isEmpty(input.toString())) {
                                list.get(getAdapterPosition()).setDate(input.toString());
                                notifyItemChanged(getAdapterPosition());
                            }
                        }
                    }).show();
        }
    }
}
