package com.smallwei.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.List;

/**
 * Created by smallwei on 2016/1/5.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {
    List<Tree<?>> list;
    Context context;
    RecyclerView recyclerView;
    private MyItemAnimator animator;

    public MyAdapter(Context context, List<Tree<?>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        Log.w("111", viewType + "");
        switch (viewType){
            case 0:
            case 1:
                view=LayoutInflater.from(context).inflate(R.layout.group_layout,parent,false);
                break;
            case 2:
                view=LayoutInflater.from(context).inflate(R.layout.child_layout,parent,false);
                break;

        }
        view.setOnClickListener(this);
        MyViewHolder holder = new MyViewHolder(view);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Tree<?> tree = list.get(position);
        String data= (String) tree.getData();
        switch (tree.getLevel()){
            case 0:
            case 1:
                holder.groupText.setText(data);
                holder.groupCheck.setChecked(tree.isExpand());
                break;
            case 2:
                holder.childText.setText(data);
                break;
        }
        holder.position=position;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        animator = new MyItemAnimator();
        this.recyclerView = recyclerView;
        recyclerView.setItemAnimator(animator);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getLevel();
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        Tree<?> tree = list.get(position);

        if (tree.isExpandable()) {
            if (tree.isExpand()) {
                if (tree.getLevel()==1) {
                    removeAll(position + 1, tree.getChildren());
                }else{
                    List<Tree<?>> trees=tree.getChildren();
                    for (int i = 0; i <trees.size(); i++) {
                        if(trees.get(i).isExpand()){
                            removeAll(position+i+2,trees.get(i).getChildren());
                        }
                    }
                    removeAll(position+1,tree.getChildren());
                }
            } else {
                if (tree.getLevel()==1) {
                    addAll(position + 1, tree.getChildren());
                }else{
                    addAll(position+1,tree.getChildren());

                    List<Tree<?>> treeList = tree.getChildren();
                    for(int i=0;i<treeList.size();i++){
                        Tree<?> tree1 = treeList.get(i);
                        if (tree1.getLevel()==1) {
                            View view = recyclerView.getChildAt(position + i+1);
                            if(view!=null) {
                                Log.w("111", (view == null) + "");
                                CheckBox check = (CheckBox) view.findViewById(R.id.group_check);
                                if (check.isChecked()) {
                                    addAll(position + i + 2, tree1.getChildren());
                                }
                            }
                            /*if (view.getTag()!=null) {
                                MyViewHolder holder= (MyViewHolder) view.getTag();
                                if (holder.groupCheck.isChecked()){
                                    addAll(position+i+2,tree1.getChildren());
                                }
                            }*/

                        }
                    }
                }

            }
            tree.setExpand(!tree.isExpand());
            notifyItemChanged(position);
        } else {
            Toast.makeText(v.getContext(), (String) tree.getData(), Toast.LENGTH_SHORT).show();
        }
    }

    public void addAll(int position, Collection<? extends Tree<?>> collection){

        list.addAll(position, collection);
        notifyItemRangeInserted(position, collection.size());
    }

    public void removeAll(int position, Collection<? extends Tree<?>> collection){
       /* for (Tree tree:collection) {
            if (tree.getLevel()==2) {
                list.removeAll(collection);
                notifyItemRangeRemoved(position, collection.size());
            }else{
                removeAll(position + 1, tree.getChildren());
            }
        }*/
        list.removeAll(collection);
        notifyItemRangeRemoved(position, collection.size());
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox groupCheck;
        TextView groupText,childText;
        int position;
        public int getMyPosition(){
            return position;
        }
        public MyViewHolder(View itemView) {
            super(itemView);
            groupCheck= (CheckBox) itemView.findViewById(R.id.group_check);
            groupText= (TextView) itemView.findViewById(R.id.group_text);
            childText= (TextView) itemView.findViewById(R.id.child_text);
        }
    }
}
