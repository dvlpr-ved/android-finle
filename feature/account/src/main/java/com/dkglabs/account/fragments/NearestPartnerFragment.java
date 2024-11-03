package com.dkglabs.account.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dkglabs.account.R;
import com.dkglabs.account.adapters.PartnerAdapter;
import com.dkglabs.account.databinding.FragmentNearestPartnerBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.model.response.PartnerResponse;

import java.util.ArrayList;
import java.util.List;

public class NearestPartnerFragment extends BaseFragment implements MenuProvider {
    private List<PartnerResponse> partnerList;
    private PartnerAdapter adapter;

    private FragmentNearestPartnerBinding binding = null;

    public NearestPartnerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNearestPartnerBinding.inflate(getLayoutInflater(), container, false);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().addMenuProvider(this, getViewLifecycleOwner());
    }

    private void initializeView() {
        setUpBackToolbar(getString(R.string.text_nearest_partner));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        binding.recyclerView.setLayoutManager(layoutManager);
        partnerList = getData();
        adapter = new PartnerAdapter(partnerList, context);
        binding.recyclerView.setAdapter(adapter);

    }

    private List<PartnerResponse> getData() {
        List<PartnerResponse> list = new ArrayList<>();
        list.add(new PartnerResponse("E Savari Rentals", "4th Floor, 18, Gulab Bagh, Devas Naka,\nNear Metro Mall, Indore, Madhya Pradesh, 452010", "https://static.wixstatic.com/media/336130_97177954bedc4f5dab3bdbe24309fd16~mv2.jpg/v1/fill/w_655,h_666,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/336130_97177954bedc4f5dab3bdbe24309fd16~mv2.jpg", "9988776656", 72.6546, 28.5369));
        list.add(new PartnerResponse("ZoomCar Rentals", "123 ABC Street, Mumbai, Maharashtra, 400001", "https://scontent.fdel1-6.fna.fbcdn.net/v/t1.18169-9/24909899_1153612334775675_2439491219867055262_n.jpg?_nc_cat=1&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=LVIfNz5oJlIAX8LA4Wi&_nc_ht=scontent.fdel1-6.fna&oh=00_AfDlf_kIM2HRa8C9IUenq9vRkifWs7Rx57jLt3y50Dqkww&oe=64BCD325", "9876543210", 19.0760, 72.8777));
        list.add(new PartnerResponse("DriveEasy Car Rentals", "6th Floor, Tower Plaza, MG Road, Bangalore, Karnataka, 560001", "https://scontent.fdel1-7.fna.fbcdn.net/v/t39.30808-6/301410314_401118128813944_1574572894909482359_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=7IYEIogIkv8AX_r7vEw&_nc_ht=scontent.fdel1-7.fna&oh=00_AfARDpCcAXi64xpMjctzLNARNilPILdWl5Iygoksn4T4yw&oe=649978F1", "9876543210", 12.9716, 77.5946));
        list.add(new PartnerResponse("E Savari Rentals", "4th Floor, 18, Gulab Bagh, Devas Naka,\nNear Metro Mall, Indore, Madhya Pradesh, 452010", "https://static.wixstatic.com/media/336130_97177954bedc4f5dab3bdbe24309fd16~mv2.jpg/v1/fill/w_655,h_666,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/336130_97177954bedc4f5dab3bdbe24309fd16~mv2.jpg", "9988776656", 72.6546, 28.5369));
        list.add(new PartnerResponse("ZoomCar Rentals", "123 ABC Street, Mumbai, Maharashtra, 400001", "https://scontent.fdel1-6.fna.fbcdn.net/v/t1.18169-9/24909899_1153612334775675_2439491219867055262_n.jpg?_nc_cat=1&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=LVIfNz5oJlIAX8LA4Wi&_nc_ht=scontent.fdel1-6.fna&oh=00_AfDlf_kIM2HRa8C9IUenq9vRkifWs7Rx57jLt3y50Dqkww&oe=64BCD325", "9876543210", 19.0760, 72.8777));
        list.add(new PartnerResponse("DriveEasy Car Rentals", "6th Floor, Tower Plaza, MG Road, Bangalore, Karnataka, 560001", "https://scontent.fdel1-7.fna.fbcdn.net/v/t39.30808-6/301410314_401118128813944_1574572894909482359_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=7IYEIogIkv8AX_r7vEw&_nc_ht=scontent.fdel1-7.fna&oh=00_AfARDpCcAXi64xpMjctzLNARNilPILdWl5Iygoksn4T4yw&oe=649978F1", "9876543210", 12.9716, 77.5946));
        return list;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_search, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_search) {
            SearchView searchView = (SearchView) menuItem.getActionView();

            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
            return true;
        }
        return false;
    }


}