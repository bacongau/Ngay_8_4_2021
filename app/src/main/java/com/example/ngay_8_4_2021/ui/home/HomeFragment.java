package com.example.ngay_8_4_2021.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngay_8_4_2021.MainActivity;
import com.example.ngay_8_4_2021.R;
import com.example.ngay_8_4_2021.adapter.ClothesAdapter;
import com.example.ngay_8_4_2021.custom.LoadingDialog;
import com.example.ngay_8_4_2021.databinding.FragmentHomeBinding;
import com.example.ngay_8_4_2021.model.Clothes;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    MainActivity mainActivity;
    FragmentManager fragmentManager;

    ClothesAdapter clothesAdapter;
    ArrayList<Clothes> clothesArrayList;
    LoadingDialog loadingDialog;

    int pageIndex = 0;

    private ISendIdClothesListener iSendIdClothesListener;

    public interface ISendIdClothesListener {
        void sendId(String idClothes);
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iSendIdClothesListener = (ISendIdClothesListener) getActivity();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // khoi tao
        mainActivity = (MainActivity) getActivity();
        fragmentManager = mainActivity.getSupportFragmentManager();
        clothesArrayList = new ArrayList<>();
        clothesAdapter = new ClothesAdapter(clothesArrayList, mainActivity);
        loadingDialog = new LoadingDialog(mainActivity);

        // khi quay lại màn hình này thì reset pageindex về 0
        pageIndex = 0;

        // set DataBinding
        FragmentHomeBinding homeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        View view = homeBinding.getRoot();
        HomeFragmentViewModel homeFragmentViewModel = new HomeFragmentViewModel();
        homeBinding.setHomeFragmentViewModel(homeFragmentViewModel);

        // thuc hien call api va nhan ve response
        homeFragmentViewModel.getDanhSachClothes(pageIndex);

        // nhan response va xu ly: set list cho adapter
        homeFragmentViewModel.getResponseDanhSachClothesMutableLiveData()
                .observe(mainActivity, responseDanhSachClothes -> {
//                    clothesAdapter.setArrayList(responseDanhSachClothes.getData().getResults());
                    for (Clothes clothes:responseDanhSachClothes.getData().getResults()){
                        clothesArrayList.add(clothes);
                    }
                    clothesAdapter.notifyDataSetChanged();
                });

        // throwable
        homeFragmentViewModel.getThrowableMutableLiveData()
                .observe(mainActivity, throwable -> {
                    Log.d("zzzzzz", "throwable:  " + throwable.getLocalizedMessage());
                });

        // loading
        homeFragmentViewModel.getLoading()
                .observe(mainActivity, isLoading -> {
                    if (isLoading != null) {
                        if (isLoading) {
                            loadingDialog.show();
                        } else {
                            loadingDialog.hide();
                        }
                    }
                });

        // setup recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false);
        homeBinding.rvDanhsachSanphamHome.setAdapter(clothesAdapter);
        homeBinding.rvDanhsachSanphamHome.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mainActivity,
                linearLayoutManager.getOrientation());
        homeBinding.rvDanhsachSanphamHome.addItemDecoration(dividerItemDecoration);

        // item recyclerview onclick: chuyen man hinh chi tiet san pham
        clothesAdapter.setOnItemClickListener(position -> {
            String id = clothesArrayList.get(position).getId().trim();
            iSendIdClothesListener.sendId(id);

            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragment_container, mainActivity.chiTietSanPhamFragment)
                    .addToBackStack(null)
                    .commit();
        });


        // khi scroll đến cuối danh sách thì sẽ load các sản phẩm trong pageindex tiếp theo
        homeBinding.rvDanhsachSanphamHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && pageIndex <= 10) {
                    pageIndex++;
                    homeFragmentViewModel.getDanhSachClothes(pageIndex);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        return view;
    }
}