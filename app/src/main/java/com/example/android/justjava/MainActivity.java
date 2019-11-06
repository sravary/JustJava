
package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, getText(R.string.more_than_100_coffee), Toast.LENGTH_SHORT).show();
            // Exit the method early when nothing else to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, getText(R.string.less_than_1_coffee), Toast.LENGTH_SHORT).show();
            return;
        }
            quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // Types of coffee:
        RadioButton cafeLatteRadioButton = findViewById(R.id.cafe_latte_radio);
        boolean hasCafeLatte = cafeLatteRadioButton.isChecked();

        RadioButton cappuccinoRadioButton = findViewById(R.id.cappuccino_radio);
        boolean hasCappuccino = cappuccinoRadioButton.isChecked();

        RadioButton regularJoeRadioButton = findViewById(R.id.regular_joe_radio);
        boolean hasRegularJoe = regularJoeRadioButton.isChecked();

        // Coffee cup sizes:
        RadioButton smallCupRadioButton = findViewById(R.id.small_radio);
        boolean hasSmallCup = smallCupRadioButton.isChecked();

        RadioButton mediumCupRadioButton = findViewById(R.id.medium_radio);
        boolean hasMediumCup = mediumCupRadioButton.isChecked();

        RadioButton largeCupRadioButton = findViewById(R.id.large_radio);
        boolean hasLargeCup = largeCupRadioButton.isChecked();

        // Choice of toppings:
        CheckBox whippedCreamCheckbox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        CheckBox cinnamonCheckbox = findViewById(R.id.cinnamon_checkbox);
        boolean hasCinnamon = cinnamonCheckbox.isChecked();

        CheckBox nutmegCheckbox = findViewById(R.id.nutmeg_checkbox);
        boolean hasNutmeg = nutmegCheckbox.isChecked();


        int price = calculatePrice
                (hasCafeLatte, hasCappuccino, hasRegularJoe,
                        hasSmallCup, hasMediumCup, hasLargeCup,
                        hasWhippedCream, hasChocolate, hasCinnamon, hasNutmeg);
        String priceMessage = createOrderSummary
                (name, price, hasCafeLatte, hasCappuccino, hasRegularJoe,
                        hasSmallCup, hasMediumCup, hasLargeCup,
                        hasWhippedCream, hasChocolate, hasCinnamon, hasNutmeg);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getText(R.string.order_summary_email_subject) + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     * @param addSmallCup is whether or not the user wants small cup
     * @param addMediumCup is whether or not the user wants medium cup
     * @param addLargeCup is whether or not the user wants large cup
     *
     * @param addCafeLatte is whether or not the user wants cafe latte
     * @param addCappuccino is whether or not the user wants cappuccino
     * @param addRegularJoe is whether or not the user wants regular coffee
     *
     * @param addWhippedCream is whether or not the user wants whipped cream
     * @param addChocolate is whether or not the user wants chocolate
     * @param addCinnamon is whether or not the user wants cinnamon
     * @param addNutmeg is whether or not the user wants nutmeg
     * @return total price
     *
     */
    private int calculatePrice
    (boolean addCafeLatte, boolean addCappuccino, boolean addRegularJoe,
     boolean addSmallCup, boolean addMediumCup, boolean addLargeCup,
     boolean addWhippedCream, boolean addChocolate, boolean addCinnamon, boolean addNutmeg) {

        int basePrice = 4;

        if (addCafeLatte) {
            basePrice += 5;
        }

        if (addCappuccino) {
            basePrice += 4;
        }

        if (addSmallCup) {
            basePrice += 3;
        }

        if (addMediumCup) {
            basePrice += 4;
        }

        if (addLargeCup) {
            basePrice += 5;
        }

        if (addWhippedCream) {
            basePrice += 1;
        }
        if (addChocolate) {
            basePrice += 2;
        }

        return quantity * basePrice;
    }

    /**
     * @param name is to display the user's name
     * @param price of the order
     *
     * @param addCafeLatte is whether or not the user wants cafe latte
     * @param addCappuccino is whether or not the user wants cappuccino
     * @param addRegularJoe is whether or not the user wants regular coffee
     *
     * @param addSmallCup is whether or not the user wants small cup
     * @param addMediumCup is whether or not the user wants medium cup
     * @param addLargeCup is whether or not the user wants large cup
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param addCinnamon is whether or not the user wants cinnamon
     * @param addNutmeg is whether or not the user wants nutmeg
     * @return text summary
     */
    private String createOrderSummary
    (String name, int price, boolean addCafeLatte, boolean addCappuccino, boolean addRegularJoe,
     boolean addSmallCup, boolean addMediumCup, boolean addLargeCup,
     boolean addWhippedCream, boolean addChocolate, boolean addCinnamon, boolean addNutmeg) {

        String priceMessage = getString(R.string.order_summary_name, name);

        // Types of coffee:
        priceMessage += "\n\n" + getString(R.string.order_summary_cafe_latte);
        if (addCafeLatte) {
            priceMessage += getString(R.string.toppings_yes);
        } else priceMessage += getString(R.string.toppings_no);

        priceMessage += "\n" + getString(R.string.order_summary_cappuccino);
        if (addCappuccino) {
            priceMessage += getString(R.string.toppings_yes);
        } else priceMessage += getString(R.string.toppings_no);

        priceMessage += "\n" + getString(R.string.order_summary_regular_joe);
        if (addRegularJoe) {
            priceMessage += getString(R.string.toppings_yes);
        } else priceMessage += getString(R.string.toppings_no);

        // Size of coffee cups:
        priceMessage += "\n\n" + getString(R.string.order_summary_small_cup);
        if (addSmallCup) {
            priceMessage += getString(R.string.toppings_yes);
        } else priceMessage += getString(R.string.toppings_no);

        priceMessage += "\n" + getString(R.string.order_summary_medium_cup);
        if (addMediumCup) {
            priceMessage += getString(R.string.toppings_yes);
        } else priceMessage += getString(R.string.toppings_no);

        priceMessage += "\n" +getString(R.string.order_summary_large_cup);
        if (addLargeCup) {
            priceMessage += getString(R.string.toppings_yes);
        } else priceMessage += getString(R.string.toppings_no);

        // Topping choices:
        priceMessage += "\n\n" + getString(R.string.order_summary_whipped_cream);
        if (addWhippedCream) {
            priceMessage += getString(R.string.toppings_yes);
        } else priceMessage += getString(R.string.toppings_no);

        priceMessage += "\n" + getString(R.string.order_summary_chocolate);
        if (addChocolate) {
            priceMessage += getString(R.string.toppings_yes);
        } else priceMessage += getString(R.string.toppings_no);

        priceMessage += "\n" + getString(R.string.order_summary_cinnamon);
        if (addCinnamon) {
            priceMessage += getString(R.string.toppings_yes);
        } else priceMessage += getString(R.string.toppings_no);

        priceMessage += "\n" + getString(R.string.order_summary_nutmeg);
        if (addNutmeg) {
            priceMessage += getString(R.string.toppings_yes);
        } else priceMessage += getString(R.string.toppings_no);

        priceMessage += "\n\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
