package org.zadatak.zadatak;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

public class ZadatakService extends Service {
	int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used

    
    final Handler handler = new Handler();
    
    Runnable runable = new Runnable() { 
        public void run() { 
            try{
                //do your code here
                //also call the same runnable 
                //handler.postDelayed(this, 60000);
                //Toast.makeText(context, "Checking Time", Toast.LENGTH_SHORT).show();
            	
            	ZadatakApp app = (ZadatakApp) getApplicationContext();
            	
            	Calendar calendar = Calendar.getInstance(); 
            	Integer currentMinute = calendar.get(Calendar.MINUTE) + 60*calendar.get(Calendar.HOUR);
            	
                Log.v("AsyncText","On Minute Checking Time");
                
                // check to see if a task starts at this minute
                Set<TaskBlock> tasks = app.dbman.getTasks(app.today());
                Map<Integer,String> taskNames = new HashMap<Integer,String>();
                
                for (TaskBlock taskBlock : tasks) {
        			taskNames.put(taskBlock.startTime, taskBlock.task.get(Task.Attributes.Name));
        		}
                
                TaskBlock thisTime = new TaskBlock(currentMinute);
                
                if (taskNames.containsKey(thisTime)) {
                	Intent dialogIntent = new Intent(getBaseContext(), Activity_AlertTask.class);
                	dialogIntent.putExtra("taskname", taskNames.get(thisTime));
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(dialogIntent);
                }
                
                
            }
            catch (Exception e) {
                // TODO: handle exception
            	handler.postDelayed(this, 60000);
            }
            finally{
                //also call the same runnable 
                handler.postDelayed(this, 60000); 
            }
        } 
    };
    
    @Override
    public void onCreate() {
    	Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
        // The service is being created
    	
    	final Context context = this;
        
        
        Calendar rightnow = Calendar.getInstance();
        long minuteOffset = rightnow.getTimeInMillis() % 60000;
        
        
        handler.postDelayed(runable, 60000-minuteOffset); 
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show(); 
        // The service is starting, due to a call to startService()
        return mStartMode;
    }
    @Override
    public IBinder onBind(Intent intent) {
    	Toast.makeText(this, "Service Bound", Toast.LENGTH_SHORT).show(); 
        // A client is binding to the service with bindService()
        return mBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
    	Toast.makeText(this, "Service Unbound", Toast.LENGTH_SHORT).show(); 
        // All clients have unbound with unbindService()
        return mAllowRebind;
    }
    @Override
    public void onRebind(Intent intent) {
    	Toast.makeText(this, "Service Rebound", Toast.LENGTH_SHORT).show(); 
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }
    @Override
    public void onDestroy() {
    	Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show(); 
        // The service is no longer used and is being destroyed
    	handler.removeCallbacks(runable);
    }
}