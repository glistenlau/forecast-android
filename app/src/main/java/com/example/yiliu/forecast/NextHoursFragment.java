package com.example.yiliu.forecast;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NextHoursFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NextHoursFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "rawData";
    private static final String ARG_PARAM2 = "degreeType";

    // TODO: Rename and change types of parameters
    private String mRawData;
    private String mDegreeType;

    private View tableView;


    public NextHoursFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NextHoursFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NextHoursFragment newInstance(String param1, String param2) {
        NextHoursFragment fragment = new NextHoursFragment();
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
            mRawData = getArguments().getString(ARG_PARAM1);
            mDegreeType = getArguments().getString(ARG_PARAM2);
        }

        try {
            JSONObject data = new JSONObject(mRawData);
            tableView = new TwoDimentionalScrollTable(getContext(), data.getJSONObject("hourly"), mDegreeType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tableView.setId(R.id.hoursTableView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return tableView;
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
