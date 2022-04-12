package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import com.braintreepayments.cardform.view.CardForm;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.databinding.ActivityPaymentBinding;

public class PaymentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPaymentBinding binding=ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(PaymentActivity.this);
        binding.cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        binding.pay.setOnClickListener(view -> {

            if (binding.cardForm.isValid()) {
                showDialog(binding.cardForm);
            }else {
                TastyToast.makeText(getApplicationContext(), "Please complete the form", TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
            }
        });
    }

    void showDialog(CardForm cardForm){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(PaymentActivity.this);
        alertBuilder.setTitle("Confirm before purchase");
        alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                "Card CVV: " + cardForm.getCvv() + "\n" +
                "Postal code: " + cardForm.getPostalCode() + "\n" +
                "Phone number: " + cardForm.getMobileNumber());
        alertBuilder.setPositiveButton("Confirm", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            TastyToast.makeText(getApplicationContext(), "Thank you for purchase", TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        });
        alertBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}