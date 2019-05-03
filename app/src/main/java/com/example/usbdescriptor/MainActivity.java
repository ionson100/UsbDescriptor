package com.example.usbdescriptor;

import android.content.Context;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView  textView=findViewById(R.id.text_data);

        findViewById(R.id.bt_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder=new StringBuilder();

                textView.setText("");

                UsbManager mUsbManager = (UsbManager) MainActivity.this.getSystemService(Context.USB_SERVICE);

                HashMap<String,UsbDevice> map=mUsbManager.getDeviceList();
                builder.append("Найдено - ").append(map.size()).append(" устройства").append(System.lineSeparator());

                for (UsbDevice ud : map.values()) {
                    builder.append("**********************************").append(System.lineSeparator());
                    builder.append(""+ud.getDeviceName()).append(System.lineSeparator());
                    builder.append("vendor: "+ud.getVendorId()).append(System.lineSeparator());
                    builder.append("Interface: "+ud.getInterfaceCount()).append(System.lineSeparator());
                    for (int i = 0; i < ud.getInterfaceCount(); i++) {
                        for (int i1 = 0; i1 < ud.getInterface(i).getEndpointCount(); i1++) {
                            UsbEndpoint ep = ud.getInterface(i).getEndpoint(i1);
                            if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK){
                                if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
                                    builder.append("endpoint Bulk Out: endpoint - ").append(i1).append(System.lineSeparator());
                                } else {
                                    builder.append("endpoint Bulk Int: endpoint - ").append(i1).append(System.lineSeparator());
                                }
                            }
                            if(ep.getType() == UsbConstants.USB_ENDPOINT_XFER_CONTROL){
                                builder.append("endpoint control: endpoint - ").append(i1).append(System.lineSeparator());
                            }

                        }
                    }
                }
                textView.setText(builder.toString());
            }
        });

    }
}
