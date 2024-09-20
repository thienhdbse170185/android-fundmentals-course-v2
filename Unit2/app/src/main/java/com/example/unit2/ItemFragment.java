package com.example.unit2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.unit2.placeholder.PlaceholderContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private MyItemRecyclerViewAdapter adapter;
    private List<PlaceholderContent.PlaceholderItem> itemList;  // List to hold the items

    public ItemFragment() {
    }

    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        // Initialize item list with placeholder items
        itemList = new ArrayList<>(PlaceholderContent.ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter for RecyclerView
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        // Initialize the adapter with item list
        adapter = new MyItemRecyclerViewAdapter(itemList);
        recyclerView.setAdapter(adapter);

        // Set up FloatingActionButton to add new item
        FloatingActionButton fab = view.findViewById(R.id.fab_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog();
            }
        });

        return view;
    }

    // Function to show a dialog where the user can enter the item name
    private void showAddItemDialog() {
        // Create an EditText view to get user input
        final EditText input = new EditText(requireContext());
        input.setHint("Enter product name");

        // Create and show the AlertDialog
        new AlertDialog.Builder(requireContext())
                .setTitle("Add New Product")
                .setMessage("Enter the name for the new product:")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String itemName = input.getText().toString();
                    if (!itemName.isEmpty()) {
                        addItemToList(itemName);
                    } else {
                        Toast.makeText(getContext(), "Item name cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
    }

    // Function to add a new item to the list and notify adapter
    private void addItemToList(String itemName) {
        int newItemId = itemList.size() + 1;
        PlaceholderContent.PlaceholderItem newItem = new PlaceholderContent.PlaceholderItem(
                String.valueOf(newItemId), itemName, "Details about " + itemName);

        // Add new item to the list
        itemList.add(newItem);

        // Notify adapter about the new item
        adapter.notifyItemInserted(itemList.size() - 1);
    }
}
