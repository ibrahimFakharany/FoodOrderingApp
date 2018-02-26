package exampls.com.foodorderingapp.Utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by 450 G1 on 23/02/2018.
 */

public class NetworkCalls {
    MyListener myListener;

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    public interface MyListener{
        void onFinish(DataSnapshot dataSnapshot);

    }
    String TAG = "networkcalls";
    public NetworkCalls(){}
    public void findRestaurantsImgAndName() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference allReviewRateRef = database.getReference();
        allReviewRateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               myListener.onFinish(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
