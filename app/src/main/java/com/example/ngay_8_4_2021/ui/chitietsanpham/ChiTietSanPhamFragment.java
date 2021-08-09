package com.example.ngay_8_4_2021.ui.chitietsanpham;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.ngay_8_4_2021.MainActivity;
import com.example.ngay_8_4_2021.R;
import com.example.ngay_8_4_2021.databinding.FragmentChiTietSanPhamBinding;


public class ChiTietSanPhamFragment extends Fragment {
    MainActivity mainActivity;
    FragmentManager fragmentManager;

    String id;

    public ChiTietSanPhamFragment () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // khoi tao
        mainActivity = (MainActivity) getActivity();
        fragmentManager = mainActivity.getSupportFragmentManager();

        // set DataBinding
        FragmentChiTietSanPhamBinding chiTietSanPhamBinding = DataBindingUtil.inflate(
                inflater,R.layout.fragment_chi_tiet_san_pham,container,false);
        View view = chiTietSanPhamBinding.getRoot();
        ChiTietSanPhamViewModel chiTietSanPhamViewModel = new ChiTietSanPhamViewModel();
        chiTietSanPhamBinding.setChiTietSanPhamViewModel(chiTietSanPhamViewModel);

        // call api chi tiet san pham theo id
        chiTietSanPhamViewModel.getClothesById(id);

        // lay ra response sau khi call api
        chiTietSanPhamViewModel.getResponseChiTietSanPhamMutableLiveData()
                .observe(mainActivity,responseChiTietSanPham -> {
                    Glide
                            .with(mainActivity)
                            .load(responseChiTietSanPham.getData().getLogoUrl())
                            .centerCrop()
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(chiTietSanPhamBinding.logoUrl);
                });

        // lay ra throwable
        chiTietSanPhamViewModel.getThrowableMutableLiveData()
                .observe(mainActivity,throwable -> {
                    Log.d("zzzzzz", "throwable:  " + throwable.getLocalizedMessage());
                });

        // back ve man hinh danh sach san pham
        chiTietSanPhamBinding.backToListProduct.setOnClickListener(v -> {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragment_container, mainActivity.homeFragment)
                    .addToBackStack(null)
                    .commit();
        });


        return view;
    }

    public void getIdClothesFromHomeFragment(String idClothes){
        id = idClothes;
    }
}