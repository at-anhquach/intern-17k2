package vn.asiantech.internship.friend;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.friend.model.Friend;

/**
 * Created by BACKDOOR on 07-Feb-17.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private List<Friend> mNotes;

    public FriendAdapter(List<Friend> data) {
        mNotes = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvFriendName.setText(mNotes.get(position).getFriendName());
        switch (new Random().nextInt(4)) {
            case 0:
                holder.mImgFriend.setImageResource(R.drawable.img_summer);
                break;
            case 1:
                holder.mImgFriend.setImageResource(R.drawable.img_autumn);
                break;
            case 2:
                holder.mImgFriend.setImageResource(R.drawable.img_spring);
                break;
            default:
                holder.mImgFriend.setImageResource(R.drawable.img_winter);
        }
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    /**
     * create RecyclerViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvFriendName;
        private ImageView mImgFriend;
        private Button mBtnAddFriend;

        ViewHolder(View itemView) {
            super(itemView);
            mTvFriendName = (TextView) itemView.findViewById(R.id.tvFriendName);
            mImgFriend = (ImageView) itemView.findViewById(R.id.imgFriend);
            mBtnAddFriend = (Button) itemView.findViewById(R.id.btnAddFriend);

            mBtnAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Friend noteObject = mNotes.get(getAdapterPosition());
                    if (!noteObject.isFriend()) {
                        mBtnAddFriend.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_friend_check_button, 0, 0, 0);
                        mBtnAddFriend.setText(R.string.friendFragment_button_friend_state);
                        noteObject.setFriend(true);
                    } else {
                        mBtnAddFriend.setText(R.string.friendFragment_button_friend_add);
                        mBtnAddFriend.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_friend_add_button, 0, 0, 0);
                        noteObject.setFriend(false);
                    }
                }
            });
        }
    }
}
