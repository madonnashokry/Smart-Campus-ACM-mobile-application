package com.campus.acm;

import static android.content.ContentValues.TAG;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.os.Vibrator;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import androidx.annotation.Nullable;

public class HostCardEmulatorService extends HostApduService {

    //constants

    // the response sent from the phone if it does not understand an APDU
    private static final byte[] UNKNOWN_COMMAND_RESPONSE = {(byte) 0xff};

    private static final byte[] SELECT_AID_COMMAND = {
            (byte) 0x00, // Class
            (byte) 0xA4, // Instruction
            (byte) 0x04, // Parameter 1
            (byte) 0x00, // Parameter 2
            (byte) 0x06, // length
            (byte) 0xF0,
            (byte) 0xAB,
            (byte) 0xCD,
            (byte) 0xEF,
            (byte) 0x00,
            (byte) 0x00
    };

    // OK status sent in response to SELECT AID command (0x9000)
    private static final byte[] SELECT_RESPONSE_OK = {(byte) 0x90, (byte) 0x00};

    // Custom protocol commands issued by terminal
    private static final byte READ_TOKEN_COMMAND = (byte) 0x01;
    private static final byte DATA_COMMAND = (byte) 0x02;

    // Custom protocol responses by phone
    private static final byte READ_TOKEN_RESPONSE = (byte) 0x00;
    private static final byte DATA_RESPONSE_OK = (byte) 0x00;
    private static final byte DATA_RESPONSE_NOK = (byte) 0x01;

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {

        if(Arrays.equals(SELECT_AID_COMMAND,commandApdu)){
            Log.i(TAG,"link to reader established");
            notifyLinkEstablished();
            return SELECT_RESPONSE_OK;
        } else if (commandApdu[0] == READ_TOKEN_COMMAND) {
            byte [] token =  getTokenBytes();
            byte[] responseApdu = new byte[token.length +1];
            responseApdu[0] = READ_TOKEN_RESPONSE;

            System.arraycopy(token,0,responseApdu,1,token.length);
            return responseApdu;
        }
        else {
            Log.e(TAG, "Terminal sent unknown command: ");
            return UNKNOWN_COMMAND_RESPONSE;
        }



    }

    @Override
    public void onDeactivated(int reason) {
        Log.d(TAG, "Link deactivated: " + reason);

    }

    private byte[] getTokenBytes(){
        try {
            return SecurePreferencesHelper.getToken(getApplicationContext()).getBytes("ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e); // never happens
        }
    }
    private void notifyLinkEstablished() {
        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(200);
    }

}
