package com.example.yiliu.forecast;

import android.app.Activity;
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
 * {@link CurrentWeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CurrentWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentWeatherFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "queryData";
    private static final String ARG_PARAM2 = "degreeType";

    // TODO: Rename and change types of parameters
    private String mQueryData;
    private String mDegreeType;
    private JSONObject mData;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentWeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentWeatherFragment newInstance(String queryData, String degreeType) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, queryData);
        args.putString(ARG_PARAM2, degreeType);
        fragment.setArguments(args);
        return fragment;
    }

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQueryData = getArguments().getString(ARG_PARAM1);
            mDegreeType = getArguments().getString(ARG_PARAM2);
            try {
                mData = new JSONObject(mQueryData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View currentWeatherView = inflater.inflate(R.layout.fragment_current_weather, container, false);
        CurrentWeather cw = new CurrentWeather(currentWeatherView, mData, mDegreeType);

        return currentWeatherView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
