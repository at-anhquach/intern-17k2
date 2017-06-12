package vn.asiantech.internship.Day5;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Use to create a RecyclerView
 *
 * @author at-HoaVo
 * @version 1.0
 * @since 2017-6-12
 */
class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.RecyclerViewHolder> {
    private List<String> mFriends;

    AdapterRecycler(List<String> friends) {
        this.mFriends = friends;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RecyclerViewHolder(inflater.inflate(R.layout.item_list_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.mTvName.setText(mFriends.get(position));
        if (position <= 1) {
            holder.mBtnAdd.setVisibility(View.INVISIBLE);
            holder.mBtnFriends.setVisibility(View.VISIBLE);
        } else {
            holder.mBtnAdd.setVisibility(View.VISIBLE);
            holder.mBtnFriends.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    /**
     * Use to create a RecyclerViewHoder
     */
    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private Button mBtnAdd;
        Button mBtnFriends;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mBtnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            mBtnFriends = (Button) itemView.findViewById(R.id.btnFriend);
            mBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBtnAdd.setVisibility(View.INVISIBLE);
                    mBtnFriends.setVisibility(View.VISIBLE);
                }
            });
            mBtnFriends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBtnAdd.setVisibility(View.VISIBLE);
                    mBtnFriends.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
