package com.imooc.imooc_voice.view.friend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imooc.imooc_voice.R;
import com.imooc.imooc_voice.api.RequestCenter;
import com.imooc.imooc_voice.model.friend.BaseFriendModel;
import com.imooc.imooc_voice.model.friend.FriendBodyValue;
import com.imooc.imooc_voice.view.friend.adapter.FriendRecyclerAdapter;
import com.imooc.lib_common_ui.recyclerview.wrapper.LoadMoreWrapper;
import com.imooc.lib_network.listener.DisposeDataListener;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoadMoreWrapper.OnLoadMoreListener {

    private Context mContext;
    /*
     * UI
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FriendRecyclerAdapter mAdapter;
    private LoadMoreWrapper mLoadMoreWrapper;
    /*
     * data
     */
    private BaseFriendModel mRecommandData;
    private List<FriendBodyValue> mDatas = new ArrayList<>();

    public static Fragment newInstance() {
        return new FriendFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friend_layout, null);
        mSwipeRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_red_light));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //发请求更新UI
        requestData();
    }

    /**
     * 下拉刷新接口
     */
    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }

    private void loadMore() {
        RequestCenter.requestFriendData(new DisposeDataListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(Object responseObj) {
                BaseFriendModel moreData = (BaseFriendModel) responseObj;
                mDatas.addAll(moreData.data.list);
                mLoadMoreWrapper.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }

    private void requestData() {
        RequestCenter.requestFriendData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mRecommandData = (BaseFriendModel) responseObj;
                updateUI();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }

    /**
     * 更新UI
     */
    private void updateUI() {
        mSwipeRefreshLayout.setRefreshing(false);
        mDatas = mRecommandData.data.list;
        mAdapter = new FriendRecyclerAdapter(mContext, mDatas);
        mRecyclerView.setAdapter(mAdapter);

        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(this);
    }
}
