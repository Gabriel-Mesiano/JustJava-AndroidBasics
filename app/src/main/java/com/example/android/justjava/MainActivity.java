package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        int price = calculatePrice();
        displayMessage(createOrderSummary(price));
        composeEmail("bielmesiano@gmail.com");
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private void displayMessage(String string) {
        TextView priceMessageTextView = (TextView) findViewById(R.id.total);
        priceMessageTextView.setText(string);
    }

    public void increment(View view) {
        quantity++;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity--;
            display(quantity);
        }
    }

    private int calculatePrice(){
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        int totalPrice = quantity*5;
        if(chocolate.isChecked()){
            totalPrice += 2 * quantity;
        }
        if (whippedCream.isChecked()){
            totalPrice += 1 * quantity;
        }
        return totalPrice;
    }

    private String createOrderSummary(int price){
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        EditText name = (EditText) findViewById(R.id.name_input_edit_text);
        String message = getString(R.string.name_output) + name.getText().toString();
        message += "\n"+ getString(R.string.add_whipped_cream) + whippedCream.isChecked();
        message += "\n" + getString(R.string.add_chocolate) + chocolate.isChecked();
        message += "\n" + getString(R.string.quantity) + ": ";
        message += price/5;
        message += "\n" + getString(R.string.total) + price;
        message += "\n" + getString(R.string.thank_you);
        return message;
    }

    public void composeEmail(String addresses) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        EditText name = (EditText) findViewById(R.id.name_input_edit_text);
        int totalPrice = calculatePrice();
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order from" + name.getText());
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(totalPrice));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}