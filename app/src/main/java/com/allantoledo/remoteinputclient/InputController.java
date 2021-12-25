package com.allantoledo.remoteinputclient;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class InputController {
    private int lastTouchX;
    private int lastTouchY;
    private int actualX;
    private int actualY;
    private long firstTouch;
    private boolean leftClick;
    private boolean rightClick;
    private byte keyPressed = 0;
    private int scrollY;
    private int lastScrollY;

    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    public void setAddress(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public void resetMove(int x, int y) {
        actualX = x;
        actualY = y;
        lastTouchX = actualX;
        lastTouchY = actualY;
    }

    public void setFirstTouch() {
        firstTouch = System.currentTimeMillis();
    }

    public void setLastTouch(int x, int y) {
        long lastTouch = System.currentTimeMillis();

        if ((lastTouch - firstTouch) < 150) {
            leftClick = true;
            sendData();
            leftClick = false;
        }
    }

    public void setKeyPressed(byte key) {
        keyPressed = key;
        sendData();
        keyPressed = 0;
    }

    public void setLeftClick() {
        leftClick = true;
        sendData();
        leftClick = false;
    }

    public void setRightClick() {
        rightClick = true;
        sendData();
        rightClick = false;
    }

    public void moveTouch(int x, int y) {
        lastTouchX = actualX;
        lastTouchY = actualY;
        actualX = x;
        actualY = y;
        if (lastTouchX != actualX || lastTouchY != actualY)
            sendData();
    }

    public byte[] getData() {

        byte[] data = new byte[]{
                (byte) (actualX - lastTouchX),
                (byte) (actualY - lastTouchY),
                (byte) (leftClick ? 1 : 0),
                (byte) (rightClick ? 1 : 0),
                keyPressed,
                (byte) (scrollY - lastScrollY)
        };

        return data;
    }

    public void setFirstScrollTouch(int y) {
        scrollY = y;
        lastScrollY = y;
    }

    public void moveScroll(int y) {
        lastScrollY = scrollY;
        scrollY = y;
        sendData();
    }

    public void setLastScrollTouch() {
        lastScrollY = scrollY = 0;
    }

    public void sendData() {
        if (address == null)
            return;

        byte[] buf = getData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new DatagramSocket();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
                    socket.send(packet);
                    socket.close();
                } catch (IOException e) {
                    log(e.getMessage());
                }
            }
        }).start();
    }

    private void log(String msg) {
        Log.v("INPUTSENDER", msg);
    }

}
