package com.bookcross;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel {

    private MutableLiveData<List<Message>> messages = new MutableLiveData<>();
    private MutableLiveData<User> otherUser = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSent = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference referenceUsers = firebaseDatabase.getReferenceFromUrl("https://bookcross-377713-default-rtdb.firebaseio.com/Users");
    private DatabaseReference referenceMessages = firebaseDatabase.getReferenceFromUrl("https://bookcross-377713-default-rtdb.firebaseio.com/Messages");

    private String otherUserId;
    private String currentUserId;

    public ChatViewModel(String otherUserId, String currentUserId) {
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;
        referenceUsers.child(otherUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                otherUser.setValue(user);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {

            }
        });
        referenceMessages
                .child(currentUserId)
                .child(otherUserId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot snapshot) {
                        List<Message> messagesFromDb = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Message message = dataSnapshot.getValue(Message.class);
                            messagesFromDb.add(message);
                        }
                        messages.setValue(messagesFromDb);
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError error) {

                    }
                });
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public LiveData<User> getOtherUser() {
        return otherUser;
    }

    public LiveData<Boolean> getIsSent() {
        return isSent;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void setOnlineStatus(boolean isOnline){
        referenceUsers.child(currentUserId).child("online").setValue(isOnline);
    }

    public void sendMessage(Message message) {
        referenceMessages
                .child(message.getSenderId())
                .child(message.getReceiverId())
                .push()
                .setValue(message)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        referenceMessages
                                .child(message.getReceiverId())
                                .child(message.getSenderId())
                                .push()
                                .setValue(message)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        isSent.setValue(true);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NotNull Exception e) {
                                        error.setValue(e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NotNull Exception e) {
                        error.setValue(e.getMessage());
                    }
                });
    }
}