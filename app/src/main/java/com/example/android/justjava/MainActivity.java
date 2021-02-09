/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int numberOfCoffees = 0;
    int priceOfCoffee = 5;
    String orderSummary = "";
    String usersName = "Customer";
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQuantity(numberOfCoffees);
    }

    public void submitOrder(View view) {
        displayMessage(numberOfCoffees);
        sendEmail();
    }

    public void increment(View view) {
        if(numberOfCoffees <=100) {
            numberOfCoffees += 1;
        }
        displayQuantity(numberOfCoffees);
    }

    public void decrement(View view) {
        if (numberOfCoffees > 1) {
            numberOfCoffees -= 1;
        }
        displayQuantity(numberOfCoffees);
    }

    public void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    private void displayMessage(int quantity) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText nameEditText = (EditText) findViewById(R.id.input_name);

        hasWhippedCream = whippedCreamCheckBox.isChecked();
        hasChocolate = chocolateCheckBox.isChecked();
        Log.i("MainActivityLog", String.valueOf(nameEditText.getText()));
        if (usersName != "") {
            usersName = String.valueOf(nameEditText.getText());
        }

        priceTextView.setText(createOrderSummary(quantity));
    }

    private int calculatePrice(int quantity) {
        int price = quantity * priceOfCoffee;
        if (hasWhippedCream) {
            price += 1;
        }
        if (hasChocolate) {
            price += 2;
        }

        return price;
    }

    private String createOrderSummary(int quantity) {
        orderSummary = "Name: " + usersName
                + "\nQuantity: " + quantity
                + "\nAdd Whipped Cream: " + hasWhippedCream
                + "\nAdd Chocolate: " + hasChocolate
                + "\nTotal: $" + calculatePrice(quantity)
                + "\nThank You!";
        return orderSummary;
    }

    private void sendEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));//only email should handle this
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, usersName + "'s Coffee Order");
        emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if(emailIntent.resolveActivity(getPackageManager()) != null){
            startActivity(emailIntent);
        }
        else{
            Log.i("MainActivity", "Error opening intents");
        }
    }
}