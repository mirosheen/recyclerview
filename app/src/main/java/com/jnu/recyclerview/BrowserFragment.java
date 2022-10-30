package com.jnu.recyclerview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowserFragment extends Fragment {



    public BrowserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BrowserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrowserFragment newInstance() {
        BrowserFragment fragment = new BrowserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_browser, container, false);
        //设置一个浏览器
        WebView webView=rootView.findViewById(R.id.webView_browser);
        WebSettings webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://news.sina.com.cn");
        webView.setWebViewClient(new WebViewClient(){
        //这里设置不要新唤起一个浏览器，而是内置浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        return rootView;
    }
}