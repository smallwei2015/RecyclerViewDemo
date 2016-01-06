package com.smallwei.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<Tree<?>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(new MyAdapter(this,list));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    private void initial() {
        list=new ArrayList<>();

        for(int i=0;i<10;i++){
            Tree<String> tree1= new Tree<String>(String.format("first%02d",i));
            for (int j = 0; j < 10; j++) {
                Tree<String> trees2=new Tree<>(String.format("second%02d",j));
                tree1.addChild(trees2);
                for (int k = 0; k < 10; k++) {
                    trees2.addChild(new Tree<>(String.format("third%02d", k)));
                }

            }
            list.add(tree1);
        }
    }
}
