package com.example.adminmobile;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class tambahjadwal extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Button btnsimpan;

    String IDMobil;

    private TextView editTanggalkembali, editTanggalpinjam;
    private EditText editPenjemputan, editTujuan, editNamamobil, editBahanbakar;
    private Spinner spinerr;
    private ImageButton balek;
    private ArrayList<String> arrayMobil;
    private  ArrayAdapter<String> adapter;
    private QuerySnapshot mobiles;
    private ProgressDialog progressDialog;
    private long totalHari, hri;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_pesanan);
        mAuth = FirebaseAuth.getInstance();

        spinerr = findViewById(R.id.spinnerlist);
        editPenjemputan = findViewById(R.id.txtPenjemputan);
        editTujuan = findViewById(R.id.txtTujuan);
        editTanggalpinjam = findViewById(R.id.txtTglPinjam);
        editTanggalkembali = findViewById(R.id.txtTglKembali);


        btnsimpan = findViewById(R.id.buttontambahjadwal);
        balek = findViewById(R.id.back);

        db = FirebaseFirestore.getInstance();

        arrayMobil = new ArrayList<>();

        progressDialog = new ProgressDialog(tambahjadwal.this);
        progressDialog.setTitle("Proses");
        progressDialog.setMessage("Sabar Bolooo.....");

        getDataSpin();




        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayMobil);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerr.setAdapter(adapter);








        balek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(tambahjadwal.this, MainActivity.class);
                startActivity(inten);
            }
        });
        editTanggalkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDatePicker2();

            }
        });

        editTanggalpinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDatePicker1();

            }
        });



        spinerr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "" +adapter.getItem(i), Toast.LENGTH_SHORT).show();
                IDMobil = mobiles.getDocuments().get(i).getId();
                Log.e("ID Nama_Mobil", mobiles.getDocuments().get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        getDataSpin();

    }

    private void openDatePicker1(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                //Showing the picked value in the textView
                editTanggalpinjam.setText(String.valueOf(year)+ "."+String.valueOf(month +1)+ "."+String.valueOf(day));

            }
        }, 2023, 12, 1);

        datePickerDialog.show();
    }

    private void openDatePicker2(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                //Showing the picked value in the textView
                editTanggalkembali.setText(String.valueOf(year)+ "."+String.valueOf(month +1)+ "."+String.valueOf(day));



            }
        }, 2023, 12, 2);

        datePickerDialog.show();

    }



    private static String generateAutoId() {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();

        // Convert UUID to a string and remove hyphens
        String autoGeneratedId = uuid.toString().replace("-", "");

        return generateAutoId();
    }

//    private long calculateTotalDays() {
//        String pickUpDateawl = editTanggalpinjam.getText().toString();
//        String returnDateakhr = editTanggalkembali.getText().toString();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
//        try {
//            Date pickUpDate1 = sdf.parse(pickUpDateawl);
//            Date returnDate2 = sdf.parse(returnDateakhr);
//
//            long diffInMilliseconds = returnDate2.getTime() - pickUpDate1.getTime();
//            hri = diffInMilliseconds / (24 * 60 * 60 * 1000); // Convert milidetik ke hari
//            totalHari = hri + 1;
//
//         //   TextView totalDaysTextView = findViewById(R.id.total_days);
//           // totalDaysTextView.setText(String.valueOf(totalHari));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return totalHari;
//    }


    private void getDataSpin(){
        progressDialog.show();
        db.collection("Data_Mobil").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressDialog.show();
                mobiles = queryDocumentSnapshots;
                if (queryDocumentSnapshots.size()>0){
                    arrayMobil.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots){
                        arrayMobil.add(doc.getString("Nama"));

                    }
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(), "data idak ada", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();

            }
        });




        btnsimpan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String penjemputan = editPenjemputan.getText().toString().trim();
                String tujuan = editTujuan.getText().toString().trim();
                String tglpinjam = editTanggalpinjam.getText().toString().trim();
                String tglkembali= editTanggalkembali.getText().toString().trim();
                String mobil = spinerr.getSelectedItem().toString();
                String bbm = "iya";
//                String mobilhr = spinerr.getSelectedItem().toString();

                Log.d("onClick", "onClick: idMobil" + IDMobil);
                if(penjemputan.isEmpty()){
                    editPenjemputan.setError("penjemputan tidak boleh kosong");
                } else if (tujuan.isEmpty()) {
                    editTujuan.setError("tujuan tidak boleh kosong");
                } else if (tglpinjam.isEmpty()) {
                    editTanggalpinjam.setError("pilih tanggal pinjam");
                } else if (tglkembali.isEmpty()) {
                    editTanggalkembali.setError("pilih tanggal pinjam");
                } else if (mobil.isEmpty()) {
                    editNamamobil.setError("pilih mobil yang diinginkan");
                } else if (bbm.isEmpty()) {
                    editBahanbakar.setError("pilih bbm");
                }else{
                    db.collection("Data_Mobil").whereEqualTo("Nama",spinerr.getSelectedItem().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", "Document Id: " + document.getId());
                            }
                        }
                    });
                    Map<String, Object> user = new HashMap<>();
                   // long hari = calculateTotalDays();
                    user.put("Penjemputan", penjemputan);
                    user.put("Tujuan", tujuan);
                    user.put("TanggalPinjam", tglpinjam);
                    user.put("TanggalKembali", tglkembali);
                    user.put("IDMobil", IDMobil);
                    user.put("BahanBakar", bbm);
                //    user.put("JumlahHari", hari);
                    user.put("UID", mAuth.getCurrentUser().getUid());


                    CollectionReference dbReff = db.collection("Booking");



                    dbReff.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Intent intent = new Intent(tambahjadwal.this, rincianPesanan.class);
                            intent.putExtra("Tujuan",tujuan);
                            intent.putExtra("TanggalPinjam", tglpinjam);
                            intent.putExtra("TanggalKembali", tglkembali);
                            intent.putExtra("IDMobil", IDMobil);
                            intent.putExtra("BahanBakar", bbm);
                        //    intent.putExtra("JumlahHari", hari);
                            intent.putExtra("UID", mAuth.getCurrentUser().getUid());
                            intent.putExtra("DocumentID",documentReference.getId());



                            startActivity(intent);
                        }
                    });
                }
            }
        });


    }
}
