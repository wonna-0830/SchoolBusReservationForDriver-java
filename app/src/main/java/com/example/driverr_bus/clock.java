package com.example.driverr_bus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class clock extends AppCompatActivity {
    private static final String TAG = "ClockActivity";

    private TextView regBoolTextView;
    private DatabaseReference reservationsRef;
    private ValueEventListener valueEventListener;
    private boolean isListening = false;
    private TextView digitalClock;
    private TextView currentDate;
    private boolean isShape1Visible = false;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        Intent i = getIntent();
        String req_route = i.getStringExtra("route");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // 패치 -> 데이터베이스에 저장되는 이름이 달라서 패치가 필요함
        // 1) A2->안심역->사월역     --->    학교->안심역->사월역
        if (req_route.equals("A2->안심역->사월역"))
            req_route = "학교->안심역->사월역";

        // 2) 안심역->교내순환     --->    안심역 출발
        if (req_route.equals("안심역->교내순환"))
            req_route = "안심역 출발";

        // 3) 하양역->교내순환     --->    하양역 출발
        if (req_route.equals("하양역->교내순환"))
            req_route = "하양역 출발";

        // 4) 사월역->교내순환     --->    사월역 출발
        if (req_route.equals("사월역->교내순환"))
            req_route = "사월역 출발";


        final String req_route_patch    = req_route;
        final String req_time           = i.getStringExtra("time");

        // Initialize Firebase Realtime Database reference
        reservationsRef = FirebaseDatabase.getInstance().getReference().child("reservations");

        //------------------------------------------------------------------------------------------------------------- 교내 순환
        db.collection("Reservation")
                .whereEqualTo("route", req_route_patch)
                .whereEqualTo("time", req_time).
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<String> places = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("place") != null) {
                                places.add(doc.getString("place"));
                            }
                        }

                        regBoolTextView = findViewById(R.id.reg_bool);

                        String output_text = "";

                        if (req_route_patch.equals("교내 순환")) {
                            output_text = "• 정문            " + Collections.frequency(places, "정문") + " 명" + "\n\n" +
                                    "• B1               " + Collections.frequency(places, "B1") + " 명" + "\n\n" +
                                    "• C7               " + Collections.frequency(places, "C7") + " 명" + "\n\n" +
                                    "• C13             " + Collections.frequency(places, "C13") + " 명" + "\n\n" +
                                    "• D6               " + Collections.frequency(places, "D6") + " 명" + "\n\n" +
                                    "• A2               " + Collections.frequency(places, "A2(건너편)") + " 명" + "\n";
                        }

                        if (req_route_patch.equals("하양역 출발")) {
                            output_text = "• 하양역            " + Collections.frequency(places, "하양역 출발") + " 명" + "\n\n" +
                                    "• 정문               " + Collections.frequency(places, "정문") + " 명" + "\n\n" +
                                    "• B1                 " + Collections.frequency(places, "B1") + " 명" + "\n\n" +
                                    "• C7                 " + Collections.frequency(places, "C7") + " 명" + "\n\n" +
                                    "• C13               " + Collections.frequency(places, "C13") + " 명" + "\n\n" +
                                    "• D6                 " + Collections.frequency(places, "D6") + " 명" + "\n\n" +
                                    "• A2                 " + Collections.frequency(places, "A2(건너편)") + " 명" + "\n";
                        }

                        if (req_route_patch.equals("안심역 출발")) {
                            output_text =    "• 안심역            " + Collections.frequency(places, "안심역(3번출구)") + " 명" + "\n\n" +
                                    "• 정문              " + Collections.frequency(places, "정문") + " 명" + "\n\n" +
                                    "• B1                 " + Collections.frequency(places, "B1") + " 명" + "\n\n" +
                                    "• C7                 " + Collections.frequency(places, "C7") + " 명" + "\n\n" +
                                    "• C13               " + Collections.frequency(places, "C13") + " 명" + "\n\n" +
                                    "• D6                 " + Collections.frequency(places, "D6") + " 명" + "\n\n" +
                                    "• A2                 " + Collections.frequency(places, "A2(건너편)") + " 명" + "\n";
                        }

                        if (req_route_patch.equals("사월역 출발")) {

                            output_text = "• 사월역            " + Collections.frequency(places, "사월역(3번출구)") + " 명" + "\n\n" +
                                    "• 정문              " + Collections.frequency(places, "정문") + " 명" + "\n\n" +
                                    "• B1                 " + Collections.frequency(places, "B1") + " 명" + "\n\n" +
                                    "• C7                 " + Collections.frequency(places, "C7") + " 명" + "\n\n" +
                                    "• C13               " + Collections.frequency(places, "C13") + " 명" + "\n\n" +
                                    "• D6                 " + Collections.frequency(places, "D6") + " 명" + "\n\n" +
                                    "• A2                 " + Collections.frequency(places, "A2(건너편)") + " 명" + "\n";
                        }


                        if (req_route_patch.equals("학교->안심역->사월역")) {
                            output_text = "• A2                      " + Collections.frequency(places, "A2(건너편)") + " 명" + "\n\n\n" +
                                    "• 안심역(하차)       " + Collections.frequency(places, "안심역") + " 명" + "\n\n\n" +
                                    "• 사월역(하차)       " + Collections.frequency(places, "사월역") + " 명" + "\n";
                        }

                        regBoolTextView.setText(output_text);
                    }
                });

        // reg_bool 텍스트뷰
        regBoolTextView = findViewById(R.id.reg_bool);

        Button toggleButton = findViewById(R.id.start_operation);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleListening();
            }
        });

        //시계 정의
        digitalClock = findViewById(R.id.digitalClock);

        //날짜 정의
        currentDate = findViewById(R.id.currentDate);

        // 매 초마다 시간, 날짜 업데이트
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateClockAndDate();
                handler.postDelayed(this, 1000); // 1초마다 업데이트
            }
        });

        // Back 버튼 추가
        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(clock.this, route_time.class);
                startActivity(intent);
                finish(); // 현재 액티비티 종료
            }
        });

        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth myAuth = FirebaseAuth.getInstance();
                if (myAuth != null){
                    myAuth.signOut(); //로그아웃 구현
                    startActivity(new Intent(clock.this, login.class));
                    finish();
                    return;
                }
            }
        });
    }

    private void updateClockAndDate() {
        // 현재 시간 업데이트
        Calendar currentTime = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String formattedTime = timeFormat.format(currentTime.getTime());
        digitalClock.setText(formattedTime);

        // 현재 날짜 업데이트
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(currentTime.getTime());
        currentDate.setText(formattedDate);
    }

    private void toggleListening() {
        Button start_operation = findViewById(R.id.start_operation);
        if (!isListening) {
            regBoolTextView.setVisibility(View.VISIBLE); // 글자 보이기
            startDataListener();
            start_operation.setText("운행종료");
        } else {
            regBoolTextView.setVisibility(View.INVISIBLE); // 글자 숨기기
            stopDataListener();
            start_operation.setText("운행시작");

            Intent intent = new Intent(clock.this, route_time.class);
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        }
    }

    private void startDataListener() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String reservedRoute = dataSnapshot.child("route").getValue(String.class);
                    TextView subwayTextView = findViewById(R.id.subway);
                    subwayTextView.setText(reservedRoute);

                    regBoolTextView.setText("예약한 사람이 있습니다!");
                    isShape1Visible = true;
                } else {
                    regBoolTextView.setText("");
                    isShape1Visible = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(clock.this, "데이터를 읽어올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        };

        reservationsRef.addValueEventListener(valueEventListener);
        isListening = true;
    }

    private void stopDataListener() {
        if (valueEventListener != null) {
            reservationsRef.removeEventListener(valueEventListener);
            valueEventListener = null;
            isListening = false;
        }
    }
}
