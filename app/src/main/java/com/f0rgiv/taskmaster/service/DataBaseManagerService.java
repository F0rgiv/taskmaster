package com.f0rgiv.taskmaster.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DataBaseManagerService extends Service {
    public DataBaseManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
