package com.example.ngay_8_4_2021.dangky;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.ngay_8_4_2021.MainActivity;
import com.example.ngay_8_4_2021.R;
import com.example.ngay_8_4_2021.databinding.FragmentDangKyBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DangKyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DangKyFragment extends Fragment {

    MainActivity mMainActivity;
    FragmentManager fragmentManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DangKyFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangKyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DangKyFragment newInstance(String param1, String param2) {
        DangKyFragment fragment = new DangKyFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainActivity = (MainActivity) getActivity();
        fragmentManager = mMainActivity.getSupportFragmentManager();

        FragmentDangKyBinding dangKyBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_dang_ky, container, false);
        View view = dangKyBinding.getRoot();
        DangKyViewModel dangKyViewModel = new DangKyViewModel();
        dangKyBinding.setDangKyViewModel(dangKyViewModel);

        // reponse
        dangKyViewModel.getResponseBodyMutableLiveData()
                .observe(mMainActivity, responseBody -> {
                    Toast.makeText(mMainActivity, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .replace(R.id.fragment_container, mMainActivity.dangNhapFragment)
                            .addToBackStack(null)
                            .commit();
                });

        // throwable
        dangKyViewModel.getThrowableMutableLiveData()
                .observe(mMainActivity, throwable -> {
                    String error = throwable.getLocalizedMessage().toLowerCase().trim();
                    if (error.equals("HTTP 409".toLowerCase())) {
                        Toast.makeText(mMainActivity, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mMainActivity, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                });

        // chuyen sang trang dang nhap
        dangKyViewModel.getChuyenSangDangNhap()
                .observe(mMainActivity, integer -> {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .replace(R.id.fragment_container, mMainActivity.dangNhapFragment)
                            .addToBackStack(null)
                            .commit();
                });

        // check input nguoi dung
        dangKyViewModel.getCheckInput()
                .observe(mMainActivity, integer -> {
                    Toast.makeText(mMainActivity, "Không được để trống thông tin" + "\n" + "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                });

        return view;
    }
}