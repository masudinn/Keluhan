package com.kp.keluhanpegawai;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kp.keluhanpegawai.databinding.ActivityRegisterBinding;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = binding.username.getText().toString().trim();
        String email = binding.email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();
        String jabatan = binding.jabatan.getText().toString().trim();
        String alamat = binding.alamat.getText().toString().trim();
        String noHp = binding.nohp.getText().toString().trim();


        if (binding.username.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        } else if (binding.email.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        } else if (binding.password.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        } else if (binding.jabatan.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Jabatan tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        } else if (binding.alamat.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        } else if (binding.nohp.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "No Whatsapp tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        binding.progressbar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = FirebaseAuth
                                .getInstance()
                                .getCurrentUser()
                                .getUid();

                        Map<String, Object> register = new HashMap<>();
                        register.put("username", username);
                        register.put("email", email);
                        register.put("password", password);
                        register.put("jabatan", jabatan);
                        register.put("alamat", alamat);
                        register.put("nohp", noHp);
                        register.put("uid", uid);

                        FirebaseFirestore
                                .getInstance()
                                .collection("users")
                                .document(uid)
                                .set(register)
                                .addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()) {
                                        binding.progressbar.setVisibility(View.GONE);
                                        showSuccessDialog();
                                    } else {
                                        binding.progressbar.setVisibility(View.GONE);
                                        showFailureDialog();
                                    }
                                });
                    } else {
                        binding.progressbar.setVisibility(View.GONE);
                        showFailureDialog();
                    }
                });

    }

    /// munculkan dialog ketika gagal registrasi
    private void showFailureDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Gagal melakukan registrasi")
                .setMessage("Silahkan mendaftar kembali dengan informasi yang benar")
                .setIcon(R.drawable.ic_baseline_clear_24)
                .setPositiveButton("OKE", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    onBackPressed();
                })
                .show();
    }

    /// munculkan dialog ketika sukses registrasi
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Berhasil melakukan registrasi")
                .setMessage("Silahkan login")
                .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
                .setPositiveButton("OKE", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    onBackPressed();
                })
                .show();
    }

    /// HAPUSKAN ACTIVITY KETIKA SUDAH TIDAK DIGUNAKAN, AGAR MENGURANGI RISIKO MEMORY LEAKS
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

