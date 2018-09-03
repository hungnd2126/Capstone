package vn.baonq.mvvmproject.ui.main.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.ActivityHomeBinding;
import vn.baonq.mvvmproject.databinding.FragmentHomeBinding;
import vn.baonq.mvvmproject.ui.addtrip.AddTripActivity;
import vn.baonq.mvvmproject.ui.base.BaseFragment;
import vn.baonq.mvvmproject.ui.main.MainActivity;
import vn.baonq.mvvmproject.ui.main.home.Post.PostAdapter;
import vn.baonq.mvvmproject.ui.main.home.Post.PostViewModel;
import vn.baonq.mvvmproject.ui.main.order.Transit.TransitAdapter;
import vn.baonq.mvvmproject.utils.ProcessBar;


public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator  {
    public static final String TAG = HomeFragment.class.getSimpleName();

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    HomeViewModel mHomeViewModel;
    FragmentHomeBinding fragmentHomeBinding;

    @Inject
    HomeAdapter homeAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProvider.Factory vmFactory;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        mHomeViewModel = ViewModelProviders.of(this, vmFactory).get(HomeViewModel.class);
        return mHomeViewModel;

    }

    private PostViewModel postViewModel;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mHomeViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentHomeBinding = getViewDataBinding();
        ProcessBar.runProgress(getContext());
        setUp();
        setLiveData();
    /*    recyclerView = activityHomeBinding.fragmentHomeRecyclerView;
        postViewModel = ViewModelProviders.of(getActivity()).get(PostViewModel.class);
        postViewModel.getArrayListMutableLiveData().observe(this, new Observer<ArrayList<PostViewModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<PostViewModel> postViewModels) {
                postAdapter = new PostAdapter(getActivity(), postViewModels);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(postAdapter);

            }
        });
      /*  DetailBinding binding = DataBindingUtil.setContentView(getActivity(), R.layout.layout_item);
        PostPresenter postPresenter= new PostPresenter() {
            @Override
            public void onClick(View view) {

            }
        };
        binding. setCallback(this);*/
        //;
        // live data update
       /* PostViewModel post= new PostViewModel();
        post.setName("asdasdas");
        ArrayList<PostViewModel> posts=new ArrayList<>();
        posts.add(post);
        postViewModel.arrayListMutableLiveData.postValue(posts);*/
        mHomeViewModel.setContext(getContext());
        this.hideKeyboard();
        ProcessBar.endProgress();

//
    }



    private void setUp(){
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentHomeBinding.fragmentHomeRecyclerView.setLayoutManager(mLayoutManager);
        fragmentHomeBinding.fragmentHomeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentHomeBinding.fragmentHomeRecyclerView.setAdapter(homeAdapter);

    }

    private void setLiveData() {
        mHomeViewModel.getPostLiveData().observe(this, requested -> mHomeViewModel.addPosts(requested));
    }

    @Override
    public void viewDetail() {

    }

    @Override
    public void onCreateTrip() {
        Intent intent = AddTripActivity.newIntent(getContext());
        startActivity(intent);
    }
}
