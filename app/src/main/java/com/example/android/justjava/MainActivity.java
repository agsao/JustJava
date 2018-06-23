package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity=2;
    public void submitOrders(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.name_edittext);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        String name=nameEditText.getText().toString();
        boolean addWhippedCream=whippedCreamCheckBox.isChecked();
        boolean addChocolate=chocolateCheckBox.isChecked();
        composeEmail(getString(R.string.orderSummaryMailSubject,name),getPriceSummary(calculatePrice(addWhippedCream,addChocolate), name,addWhippedCream,addChocolate));
        //displayPrice(quantity*5);
    }
    public void increment(View view) {
        if (quantity==100){
            Toast.makeText(this,"You Cannot have more than 100 coffee", Toast.LENGTH_SHORT ).show();
            return;}
        quantity+=1;
        display(quantity);
    }
    public void decrement(View view) {
        if (quantity==1){
            Toast.makeText(this,"You Cannot have less than 1 coffee", Toast.LENGTH_SHORT ).show();
            return;}
        quantity-=1;
        display(quantity);
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int cupPrice=5;
        if (addWhippedCream)
            cupPrice+=1;
        if (addChocolate)
            cupPrice+=2;
        return quantity * cupPrice;
    }

    public String getPriceSummary(int price,String name, boolean addWhippedCream, boolean addChocolate)
    {
        String priceSummary= getString(R.string.orderSummaryName,name);
        if (addWhippedCream) priceSummary += "\n"+getResources().getString(R.string.addWhippedCream);
        if (addChocolate)priceSummary += "\n"+getResources().getString(R.string.addChocolate);
        priceSummary += "\n"+ getString(R.string.orderSummaryQuantity ,quantity);
        priceSummary += "\n"+getResources().getString(R.string.orderSummaryPrice,price );
        priceSummary +="\n"+ getString(R.string.thankYou);
        return priceSummary;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void composeEmail(String subject,String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}