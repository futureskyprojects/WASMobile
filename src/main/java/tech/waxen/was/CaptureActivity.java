package tech.waxen.was;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CaptureActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

        mScannerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CaptureActivity.this);
                builder.setTitle("QUAY LẠI")
                        .setMessage("Bỏ qua phần quét mã này?")
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mScannerView.stopCamera();
                                Intent returnIntent = new Intent();
                                setResult(Activity.RESULT_CANCELED, returnIntent);
                                finish();
                            }
                        })
                        .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        if (rawResult.getBarcodeFormat().toString().equals("QR_CODE")) {
            // If you would like to resume scanning, call this method below:
            mScannerView.resumeCameraPreview(this);
            Intent returnIntent = new Intent();
            returnIntent.putExtra(AttendanceStudent.DATA_STUDENT_CODE, rawResult.getText());
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}
