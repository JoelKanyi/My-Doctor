package com.example.mydoctor.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mydoctor.model.Profile;
import com.example.mydoctor.others.SignOut;
import com.example.mydoctor.ui.activities.SignInActivity;
import com.example.navigationview.R;
import com.example.navigationview.databinding.FragmentMedicalBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MedicalFragment extends Fragment {

    FragmentMedicalBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMedicalBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);

        binding.progressBar2.setVisibility(View.VISIBLE);
        binding.mpBack.setVisibility(View.INVISIBLE);

        databaseReference = FirebaseDatabase.getInstance().getReference("medical_profile");
        firebaseAuth = FirebaseAuth.getInstance();

        binding.mpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_medicalFragment_to_homeFragment);
            }
        });

        fetchMedicalData();

        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.discovery_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.signOut){
            Toast.makeText(requireContext(), "Sign out", Toast.LENGTH_SHORT).show();
            SignOut.signOut();
            Intent intent = new Intent(getActivity(), SignInActivity.class);
            startActivity(intent);
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchMedicalData(){
        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Profile profile = snapshot.getValue(Profile.class);

                    binding.nextOfKin.setText("Next of Kin: "+profile.getNext_of_kin());
                    binding.gfname.setText("Guarding First Name: "+profile.getGuardian_firstName());
                    binding.glname.setText("Guardian Last Name: "+profile.getGuardian_lastName());
                    binding.gId.setText("Guarding ID No: "+profile.getGuardian_id());
                    binding.guardianPhoneNo.setText("Guarding Phone No: "+profile.getGuardian_phoneNum());
                    binding.gLocality.setText("Guardian Area of Locality: "+profile.getGuardian_locality());
                    binding.bloodGroup.setText("Blood Group: "+profile.getBloodGroup());
                    binding.bloodpressure.setText("Blood Pressure: "+profile.getBloodPressure());
                    binding.height.setText("Estimate Height in inches: "+profile.getHeight());
                    binding.weight.setText("Weight: "+profile.getWeight());
                    binding.existingCondition.setText("Existing Medical Condition: "+profile.getExistingMedCondition());
                    binding.hivAidStatus.setText("HIV/AIDs Status: "+profile.getHivStatus());

                    binding.progressBar2.setVisibility(View.INVISIBLE);
                    binding.mpBack.setVisibility(View.VISIBLE);

                }else{
                    Toast.makeText(requireContext(), "Data does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}