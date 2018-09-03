package vn.baonq.mvvmproject.ui.main;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.sinch.android.rtc.SinchError;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import es.dmoral.toasty.Toasty;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.ActivityMainBinding;
import vn.baonq.mvvmproject.service.NotificationListener;
import vn.baonq.mvvmproject.service.SignalRService;
import vn.baonq.mvvmproject.ui.addpost.AddPostActivity;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.main.delivery.DeliveryFragment;
import vn.baonq.mvvmproject.ui.main.home_main.HomeMainFragment;
import vn.baonq.mvvmproject.ui.main.message.dialogs.DialogFragment;
import vn.baonq.mvvmproject.ui.main.message.video_call.SinchService;
import vn.baonq.mvvmproject.ui.main.more.MoreFragment;
import vn.baonq.mvvmproject.ui.main.order.OrderFragment;
import vn.baonq.mvvmproject.utils.ProcessBar;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements MainNavigator, HasSupportFragmentInjector, NotificationListener,
        ServiceConnection,
        SinchService.StartFailedListener
{

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    public static final String TAG = MainActivity.class.getSimpleName();

//    @Inject
//    ViewModelProvider.Factory vmFactory;

    @Inject
    MainViewModel mainViewModel;

    ActivityMainBinding activityMainBinding;
    private boolean bound = false;

    private AHBottomNavigationAdapter navigationAdapter;
    private int[] tabColors;

    public static SignalRService signalRService;
    // UI
    private AHBottomNavigationViewPager viewPager;
    private AHBottomNavigation bottomNavigation;
    private FloatingActionButton floatingActionButton;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    boolean dialogReload = false;
    boolean offerSuccess = false;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        //mainViewModel = ViewModelProviders.of(this,vmFactory).get(MainViewModel.class);
        return mainViewModel;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = getViewDataBinding();
        mainViewModel.setNavigator(this);
        //registerSignalR();
        initUI();
        ReceiveIntent();
        if (dialogReload) {
            bottomNavigation.setCurrentItem(3);
            //  pushFragment(DialogFragment.newInstance(), DialogFragment.class.getSimpleName());
        } else if (offerSuccess) {
            Toasty.success(this, "Đã gửi đề nghị", Toast.LENGTH_SHORT, true).show();
        } else if (currentMenu.equals("1")) {
            // Toasty.success(this, "Xác nhận thành công", Toast.LENGTH_SHORT, true).show();
            bottomNavigation.setCurrentItem(1);
            showFloatingPoint(true);

        }

        if (android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                || android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE},5268);
       return;
        }}
        getApplicationContext().bindService(new Intent(this, SinchService.class), this,
                BIND_AUTO_CREATE);
       /* if(!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient(mainViewModel.getDataManager().getCurrentUser().getUid());
        }*/
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode==5268) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                            || android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            || android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                            || android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
                                    Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE},5268);
                            return;
                        }}
                    getApplicationContext().bindService(new Intent(this, SinchService.class), this,
                            BIND_AUTO_CREATE);

                }
        }
    }
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            try {
                SignalRService.LocalBinder binder = (SignalRService.LocalBinder) service;
                signalRService = binder.getService();
                bound = true;
                signalRService.setCallbacks(MainActivity.this);// register
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.d(TAG, "Service disconnected");
            bound = false;
        }


    };

    @Override
    protected void onStart() {
        super.onStart();
        registerSignalR();
    }

    private void registerSignalR() {
        try {
            Intent intent = new Intent(this, SignalRService.class);
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
            bound = true;
            //startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroy " + bound);
        if (bound) {
            signalRService.setCallbacks(null);
            unbindService(serviceConnection);
            bound = false;
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stop");
        if (bound) {
//            signalRService.setCallbacks(null);
//            unbindService(serviceConnection);
            bound = false;
        }
        super.onStop();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void oncClickFloatButton() {
        startActivity(new Intent(AddPostActivity.newIntent(this)));

    }

    private void initUI() {
        setupBottomNav();
        pushFragment(HomeMainFragment.newInstance(), HomeMainFragment.class.getSimpleName());
    }

    private void setupBottomNav() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }

        bottomNavigation = activityMainBinding.bottomNavigation2;
        floatingActionButton = activityMainBinding.floatingActionButton2;
        tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu_5);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
        bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(Color.parseColor("#ff009688"));
        showFloatingPoint(true);
        // set bot nav listener
        bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            switch (position) {
                case 0:
                    pushFragment(HomeMainFragment.newInstance(), HomeMainFragment.class.getSimpleName());
                    break;
                case 1:
                    pushFragment(OrderFragment.newInstance(), HomeMainFragment.class.getSimpleName());
                    break;
                case 2:
                    pushFragment(DeliveryFragment.newInstance(), HomeMainFragment.class.getSimpleName());
                    break;
                case 3:
                    pushFragment(DialogFragment.newInstance(), DialogFragment.class.getSimpleName());
                    break;

                case 4:
                    pushFragment(MoreFragment.newInstance(), MoreFragment.class.getSimpleName());
                    break;
                default:
                    pushFragment(HomeMainFragment.newInstance(), HomeMainFragment.class.getSimpleName());
                    break;
            }
            if (position == 1) {
                floatingActionButton.setImageResource(R.drawable.round_add);
                showFloatingPoint(true);
            } else {
                showFloatingPoint(false);
            }
            return true;
        });
        showFloatingPoint(false);

    }

    private void showFloatingPoint(boolean isShow) {
        if (isShow) {
            bottomNavigation.setNotification("", 1);
            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.setAlpha(0f);
            floatingActionButton.setScaleX(0f);
            floatingActionButton.setScaleY(0f);
            floatingActionButton.animate()
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1)
                    .setDuration(300)
                    .setInterpolator(new OvershootInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            floatingActionButton.animate()
                                    .setInterpolator(new LinearOutSlowInInterpolator())
                                    .start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .start();
        } else {
            if (floatingActionButton.getVisibility() == View.VISIBLE) {
                floatingActionButton.animate()
                        .alpha(0)
                        .scaleX(0)
                        .scaleY(0)
                        .setDuration(300)
                        .setInterpolator(new LinearOutSlowInInterpolator())
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                floatingActionButton.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                floatingActionButton.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .start();
            }
        }

    }

    public void pushFragment(Fragment fragment, String fragmentBack) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (ft != null) {
            ft.replace(R.id.nav_contentframe, fragment).addToBackStack(fragmentBack);
            ft.commit();
        }
    }

    String currentMenu = "";

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    public void ReceiveIntent() {
        try {
            dialogReload = (Boolean) getIntent().getSerializableExtra("reloadDialog");
            offerSuccess = (Boolean) getIntent().getSerializableExtra("offerSuccess");
        } catch (Exception e) {
        }
        currentMenu = getIntent().getStringExtra("currentMenu") == null ? "" : getIntent().getStringExtra("currentMenu");
    }

    @Override
    public void setCount(int count) {

    }
    private SinchService.SinchServiceInterface mSinchServiceInterface;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (SinchService.class.getName().equals(componentName.getClassName())) {
            mSinchServiceInterface = (SinchService.SinchServiceInterface) iBinder;
            if(!getSinchServiceInterface().isStarted()) {
                getSinchServiceInterface().startClient(mainViewModel.getDataManager().getCurrentUser().getUid());}
            onServiceConnected();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    protected SinchService.SinchServiceInterface getSinchServiceInterface() {
        return mSinchServiceInterface;
    }
    @Override
    public void onStartFailed(SinchError error) {

    }

    @Override
    public void onStarted() {

    }
    protected void onServiceConnected() {
        // for subclasses
        getSinchServiceInterface().setStartListener(this);
    }

    protected void onServiceDisconnected() {
        // for subclasses
    }
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
       if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Nhấp đúp để thoát", Toast.LENGTH_SHORT).show();
           bottomNavigation.restoreBottomNavigation(true);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            //startAppAd.onBackPressed();
            super.onBackPressed();
            finish();
            return;
        }

    }
}
