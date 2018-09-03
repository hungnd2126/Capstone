package vn.baonq.mvvmproject.ui.main.message.messages;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.remote.ApiEndPoint;
import vn.baonq.mvvmproject.databinding.ActivityCustomHolderMessagesBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.main.MainActivity;
import vn.baonq.mvvmproject.ui.main.message.model.Dialog;
import vn.baonq.mvvmproject.ui.main.message.model.Message;
import vn.baonq.mvvmproject.ui.main.message.model.MessageFrieBase;
import vn.baonq.mvvmproject.ui.main.message.model.User;
import vn.baonq.mvvmproject.ui.main.message.video_call.CallScreenActivity;
import vn.baonq.mvvmproject.ui.main.message.video_call.PlaceCallActivity;
import vn.baonq.mvvmproject.ui.main.message.video_call.SinchService;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class CustomHolderMessagesActivity extends BaseActivity<ActivityCustomHolderMessagesBinding, HolderMessageViewModel>
        implements MessagesListAdapter.OnMessageLongClickListener<Message>,
            MessageInput.InputListener,
                    MessageInput.AttachmentsListener ,
                    MessagesListAdapter.SelectionListener,
                    MessagesListAdapter.OnLoadMoreListener,
             ServiceConnection,
        SinchService.StartFailedListener
        {

                private static final int TOTAL_MESSAGES_COUNT = 100;
                public static final int PICK_IMAGE = 1001;

                private  String  senderId;
                private  String  senderAvartar;
                private  String  senderNickName;

                private  String reciverId;
                private String receiverAvatar="";
                private String receiverName="";
                protected ImageLoader imageLoader;
                protected MessagesListAdapter<Message> messagesAdapter;

                private Menu menu;
                private int selectionCount;
                private Date lastLoadedDate;
                private List<MessageFrieBase> messageFrieBaseList;
                DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
                public static void open(Context context) {
                    context.startActivity(new Intent(context, CustomHolderMessagesActivity.class));
                }
                @Inject
                HolderMessageViewModel holderMessageViewModel;
                private MessagesList messagesList;

                @Override
                public int getBindingVariable() {
                    return BR.viewModel;
                }

                @Override
                public int getLayoutId() {
                    return R.layout.activity_custom_holder_messages;
                }

                @Override
                public HolderMessageViewModel getViewModel() {
                    return holderMessageViewModel;
                }
                @Override
                public boolean onOptionsItemSelected(MenuItem item) {
                    if(item.getTitle()!=null){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE},100);

                        }
                        if(!getSinchServiceInterface().isStarted()) {
                            getSinchServiceInterface().startClient(holderMessageViewModel.getDataManager().getCurrentUser().getUid());
                        }else reCall();
                                                return true;

                                        }
                        if(getIntent().getStringExtra("callFrom")!=null){
                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("reloadDialog",true);
                    startActivity(intent);
                    finish();                    }
                    else{
                        onBackPressed();
                        finish();
                    }

                    return true;
                }

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    // setContentView(R.layout.activity_custom_holder_messages);
                    getSupportActionBar().show();
                    imageLoader = new ImageLoader() {
                        @Override
                        public void loadImage(ImageView imageView, String url) {
                            Picasso.get().load(ApiEndPoint.BASE_URL+ url).into(imageView);
                        }
                    };
                    getApplicationContext().bindService(new Intent(this, SinchService.class), this,
                            BIND_AUTO_CREATE);
                    messagesList = (MessagesList) findViewById(R.id.messagesList);
                    ReceiveIntent();
                    initAdapter();
                    MessageInput input = (MessageInput) findViewById(R.id.input);
                    input.setInputListener(this);
                    input.setAttachmentsListener(this);
                    ProcessBar.runProgress(this);
                    CheckDialog();
                    loadMessages();

                }
                @Override
                protected void onStart() {
                    super.onStart();
                    //    messagesAdapter.addToStart(MessagesFixtures.getTextMessage(), true);
                }
    @Override
    public boolean onSubmit(CharSequence input) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd HHmmss").format(Calendar.getInstance().getTime());
                    MessageFrieBase messageFrieBase= new MessageFrieBase(input.toString(),senderId,senderAvartar,
                            senderNickName, timeStamp,0);
      if(senderId.compareTo(reciverId)<1) {
                        myRef.child("Message").child("Message_" + senderId+"_"+reciverId).child("ListMessage").push().setValue(messageFrieBase);
                        myRef.child("Message").child("Message_" + senderId+"_"+reciverId).child("LastMessage").setValue(input.toString());
                        myRef.child("Message").child("Message_" + senderId+"_"+reciverId).child("Create_Date").setValue(timeStamp);

                    }
                    else
                    {
                        myRef.child("Message").child("Message_" + reciverId +"_"+senderId).child("ListMessage").push().setValue(messageFrieBase);
                        myRef.child("Message").child("Message_" + reciverId +"_"+senderId).child("LastMessage").setValue(input.toString());
                        myRef.child("Message").child("Message_" + reciverId +"_"+senderId).child("Create_Date").setValue(timeStamp);

                    }
        return true;
    }
    private static final int RESULT_LOAD_IMAGE = 516;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            // upload image
           holderMessageViewModel.getCompositeDisposable().add(holderMessageViewModel.getDataManager()
                   .uploadImage(encodedImage)
                    .subscribeOn(holderMessageViewModel.getSchedulerProvider().io())
                    .observeOn(holderMessageViewModel.getSchedulerProvider().ui())
                    .subscribe(
                            baseResponse -> {
                                if (baseResponse != null) {
                                    baseResponse= baseResponse.substring(1,baseResponse.length()-1);
                                    String timeStamp = new SimpleDateFormat("yyyyMMdd HHmmss").format(Calendar.getInstance().getTime());
                                    MessageFrieBase messageFrieBase= new MessageFrieBase(baseResponse,senderId,senderAvartar,
                                            senderNickName, timeStamp,1);
                                    if(senderId.compareTo(reciverId)<1) {
                                        myRef.child("Message").child("Message_" + senderId+"_"+reciverId).child("ListMessage").push().setValue(messageFrieBase);
                                        myRef.child("Message").child("Message_" + senderId+"_"+reciverId).child("LastMessage").setValue("Đã gửi 1 ảnh");
                                        myRef.child("Message").child("Message_" + senderId+"_"+reciverId).child("Create_Date").setValue(timeStamp);
                                    }
                                    else
                                    {
                                        myRef.child("Message").child("Message_" + reciverId +"_"+senderId).child("ListMessage").push().setValue(messageFrieBase);
                                        myRef.child("Message").child("Message_" + reciverId +"_"+senderId).child("LastMessage").setValue("Đã gửi 1 ảnh");
                                        myRef.child("Message").child("Message_" + reciverId +"_"+senderId).child("Create_Date").setValue(timeStamp);

                                    }
                                    User user = new User(senderId, senderAvartar, senderNickName, true);
                                    Date date =Calendar.getInstance().getTime();

                                }
                            }, throwable -> {
                                Log.d("Create Post Error: ", throwable.toString());
                            }
                    ));
            /*Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            User user = new User(senderId, senderAvartar, senderNickName, true);
             Date date =Calendar.getInstance().getTime();
            Message message = new Message("0", user, null,date);
            message.setImage(new Message.Image("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/27867558_1238387526294132_395751054214790552_n.jpg?_nc_cat=0&oh=74bf0c44fcc11a2651b3ab2022c38d1b&oe=5BBBCC14"));
            messagesAdapter.addToStart(message, true);
*/

        }

}

    @Override
    public void onAddAttachments() {
        if (!hasPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1002);
        }
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onMessageLongClick(Message message) {
        Toast.makeText(getApplicationContext(), "meessage long click",Toast.LENGTH_LONG).show();

    }

    private void initAdapter() {
        MessageHolders holdersConfig = new MessageHolders()
                .setIncomingTextConfig(
                        CustomIncomingTextMessageViewHolder.class,
                        R.layout.item_custom_incoming_text_message)
                .setOutcomingTextConfig(
                        CustomOutcomingTextMessageViewHolder.class,
                        R.layout.item_custom_outcoming_text_message)
                .setIncomingImageConfig(
                        CustomIncomingImageMessageViewHolder.class,
                        R.layout.item_custom_incoming_image_message)
                .setOutcomingImageConfig(
                        CustomOutcomingImageMessageViewHolder.class,
                        R.layout.item_custom_outcoming_image_message);
        messagesAdapter = new MessagesListAdapter<>(senderId+"", holdersConfig, imageLoader);
        messagesAdapter.setOnMessageLongClickListener(this);
        messagesAdapter.setLoadMoreListener(this);


        messagesList.setAdapter(messagesAdapter);
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
            //loadMessages();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.chat_actions_menu, menu);
        onSelectionChanged(1);
        return true;
    }

    @Override
    public void onSelectionChanged(int count) {
        this.selectionCount = count;
        menu.findItem(R.id.action_delete).setVisible(count > 0);
    }
    protected void loadMessages() {
        String node="";
        if(senderId.compareTo(reciverId)<1) {
            node ="Message_" + senderId+"_"+reciverId;
        }
        else
        {
            node ="Message_" +reciverId +"_"+ senderId;
        }
        myRef.child("Message").child(node).child("ListMessage").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(messageFrieBaseList==null){
                    messageFrieBaseList= new ArrayList<>();
                }
                if(dataSnapshot!=null){
                    MessageFrieBase messageFrieBase = new MessageFrieBase();
                    messageFrieBase=dataSnapshot.getValue(MessageFrieBase.class);
                    messageFrieBaseList.add(messageFrieBase);

                    User user = new User(messageFrieBase.getSenderId(), messageFrieBase.getAvartar_URL(), messageFrieBase.getAvartar_URL(), true);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
                    Date date = new Date();
                    try {
                        date =formatter.parse(messageFrieBase.getCreate_Date());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Message message=null;
                    if(messageFrieBase.getType()==0){
                     message = new Message("0", user, messageFrieBase.getMessage(),date);}
                     else{
                        message = new Message("0", user, null,date);
                        message.setImage(new Message.Image(messageFrieBase.getMessage()));

                    }
                    messagesAdapter.addToStart(message, true);
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if(getIntent().getStringExtra("callFrom")!=null){
       Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("reloadDialog",true);
        startActivity(intent);
        finish();}else{
        super.onBackPressed();}

    }
    public void CheckDialog(){
        Dialog dialog= new Dialog();
        dialog.setSender_Name(senderNickName);
        dialog.setSenderAvartar(senderAvartar);
        dialog.setSenderId(senderId);
        dialog.setReceiverAvartar(receiverAvatar);
        dialog.setReceiverName(receiverName);
        dialog.setReceiverId(reciverId);

        if(senderId.compareTo(reciverId)<1) {
            DatabaseReference dialogRef= myRef.child("Message");
            dialogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (!snapshot.child("Message_" + senderId+"_"+ reciverId).exists()) {
                        myRef.child("Message").child("Message_" + senderId+"_"+ reciverId)
                                .setValue(dialog);
                    }
                    ProcessBar.endProgress();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            myRef.child("Message").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (!snapshot.child("Message_" + reciverId +"_"+senderId).exists()) {

                        myRef.child("Message").child("Message_" +  reciverId +"_"+senderId).setValue(dialog);
                    }
                    ProcessBar.endProgress();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    public void ReceiveIntent() {
        reciverId = getIntent().getStringExtra("receiverId");
        receiverName = getIntent().getStringExtra("receiverName");
        receiverAvatar = getIntent().getStringExtra("receiverAvatar");

        senderId = getIntent().getStringExtra("SenderId");
        senderNickName = getIntent().getStringExtra("NickName");
        senderAvartar = getIntent().getStringExtra("Avatar");
    }
    private SinchService.SinchServiceInterface mSinchServiceInterface;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (SinchService.class.getName().equals(componentName.getClassName())) {
            mSinchServiceInterface = (SinchService.SinchServiceInterface) iBinder;
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
    protected void onServiceConnected() {
        // for subclasses
        getSinchServiceInterface().setStartListener(this);
    }

    protected void onServiceDisconnected() {
        // for subclasses
    }

    protected SinchService.SinchServiceInterface getSinchServiceInterface() {
        return mSinchServiceInterface;
    }

            @Override
            public void onStartFailed(SinchError error) {

            }

            @Override
            public void onStarted() {
                reCall();
            }

    private void reCall() {
        if(android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                && android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            && android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
        && android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
        {
        Call call = getSinchServiceInterface().callUserVideo(reciverId);
        String callId = call.getCallId();
        Intent callScreen = new Intent(this, CallScreenActivity.class);
        callScreen.putExtra(SinchService.CALL_ID, callId);
        callScreen.putExtra("receiverName",receiverName);
        callScreen.putExtra("receiverAvatar",receiverAvatar);
        startActivity(callScreen);
        finish();

    }else{
            reCall();
        }
    }
}
