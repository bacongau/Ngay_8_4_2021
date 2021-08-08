package com.example.ngay_8_4_2021.dangnhap;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.ngay_8_4_2021.MainActivity;
import com.example.ngay_8_4_2021.R;
import com.example.ngay_8_4_2021.custom.LoadingDialog;
import com.example.ngay_8_4_2021.databinding.FragmentDangNhapBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DangNhapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DangNhapFragment extends Fragment {

    MainActivity mMainActivity;
    FragmentManager fragmentManager;
    LoadingDialog loadingDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DangNhapFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangNhapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DangNhapFragment newInstance(String param1, String param2) {
        DangNhapFragment fragment = new DangNhapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainActivity = (MainActivity) getActivity();
        fragmentManager = mMainActivity.getSupportFragmentManager();

        FragmentDangNhapBinding dangNhapBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_dang_nhap, container, false);
        View view = dangNhapBinding.getRoot();
        DangNhapViewModel dangNhapViewModel = new DangNhapViewModel(mMainActivity);
        dangNhapBinding.setDangNhapViewModel(dangNhapViewModel);

        loadingDialog = new LoadingDialog(mMainActivity);

        // response
        dangNhapViewModel.getResponseBodyMutableLiveData()
                .observe(mMainActivity, responseBody -> {
                    Toast.makeText(mMainActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .replace(R.id.fragment_container, mMainActivity.homeFragment)
                            .addToBackStack(null)
                            .commit();
                });

        // throwable
        dangNhapViewModel.getThrowableMutableLiveData()
                .observe(mMainActivity, throwable -> {
                    Toast.makeText(mMainActivity, "Thông tin không chính xác", Toast.LENGTH_SHORT).show();
                });

        // chuyen sang trang dang ky
        dangNhapViewModel.getChuyenSangDangKy()
                .observe(mMainActivity, integer -> {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .replace(R.id.fragment_container, mMainActivity.dangKyFragment)
                            .addToBackStack(null)
                            .commit();
                });

        // check input nguoi dung
        dangNhapViewModel.getCheckInput()
                .observe(mMainActivity, integer -> {
                    Toast.makeText(mMainActivity, "Không được để trống thông tin" + "\n" + "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                });

        // an hien loading
        dangNhapViewModel.getLoading()
                .observe(mMainActivity, isLoading -> {
                    if(isLoading != null){
                        if(isLoading){
                            loadingDialog.show();
                        }else {
                            loadingDialog.hide();
                        }
                    }
                });

        return view;
    }
}