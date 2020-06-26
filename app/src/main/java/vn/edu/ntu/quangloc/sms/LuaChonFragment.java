package vn.edu.ntu.quangloc.sms;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.time.Year;
import java.util.Calendar;

public class LuaChonFragment extends Fragment implements View.OnClickListener {

    public static final String KEY_TU_NGAY = "tu-ngay";
    public static final String KEY_DEN_NGAY = "den-ngay";
    EditText edtTuNgay, edtDenNgay;
    ImageView imvTuNgay, imvDenNgay;
    ImageButton imBtnHienThi;
    NavController navController;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lua_chon, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtTuNgay = view.findViewById(R.id.edtTuNgay);
        edtDenNgay = view.findViewById(R.id.edtDenNgay);
        imvTuNgay = view.findViewById(R.id.imvTuNgay);
        imvDenNgay = view.findViewById(R.id.imvDenNgay);
        imBtnHienThi = view.findViewById(R.id.imBtnHienThi);

        imvTuNgay.setOnClickListener(this);
        imvDenNgay.setOnClickListener(this);
        imBtnHienThi.setOnClickListener(this);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity)getActivity()).navController = this.navController;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case (R.id.imvTuNgay): thietLapNgay(0); break;
            case (R.id.imvDenNgay): thietLapNgay(1); break;
            case (R.id.imBtnHienThi): hienThiSMS();
        }
    }

    private void thietLapNgay(final int luaChon)
    {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                StringBuilder builder = new StringBuilder();
                builder.append(year)
                        .append("-")
                        .append(++month)
                        .append("-")
                        .append(dayOfMonth);
                if (luaChon == 0)
                    edtTuNgay.setText(builder.toString());
                else
                    edtDenNgay.setText(builder.toString());
            }

        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                listener, calendar.YEAR, calendar.MONTH, calendar.DAY_OF_MONTH);
        datePickerDialog.show();
    }

    private void hienThiSMS()
    {
        if (edtTuNgay.getText().toString().length()>0 &&
            edtDenNgay.getText().toString().length()>0)
        {
            Bundle data = new Bundle();
            data.putCharSequence(KEY_TU_NGAY, edtTuNgay.getText().toString());
            data.putCharSequence(KEY_DEN_NGAY, edtDenNgay.getText().toString());
            navController.navigate(R.id.action_luaChonFragment_to_SMSFragment, data);
        }
    }
}