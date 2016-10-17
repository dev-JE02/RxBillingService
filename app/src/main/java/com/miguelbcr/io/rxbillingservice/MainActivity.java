package com.miguelbcr.io.rxbillingservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.miguelbcr.io.rx_billing_service.RxBillingService;
import com.miguelbcr.io.rx_billing_service.entities.ProductType;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
  private TextView textView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textView = (TextView) findViewById(R.id.textView);

    isBillingSupported(ProductType.IN_APP);
    isBillingSupported(ProductType.SUBS);
  }

  private void isBillingSupported(ProductType productType) {
    RxBillingService.getInstance(this)
        .isBillingSupported(productType)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(supported -> {
          String text = textView.getText().toString();
          text += "Billing supported (" + productType.getName() + ") = " + supported + "\n";
          textView.setText(text);
        }, throwable -> {
          throwable.printStackTrace();
          String text = textView.getText().toString();
          text += "error = " + throwable.getMessage() + "\n";
          textView.setText(text);
        });
  }
}