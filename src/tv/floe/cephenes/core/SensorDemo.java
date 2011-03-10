/**
    Copyright [2011] [Josh Patterson]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
 */
package tv.floe.cephenes.core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.hardware.SensorListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 
 * Inspired from article at:
 * 
 * http://www.ibm.com/developerworks/opensource/library/os-android-sensor/index.html
 * 
 * @author Josh
 *
 */
public class SensorDemo extends Activity implements SensorListener {
	
	final String tag = "SensorDemo";
	private Button btnclickme;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnclickme = (Button) findViewById( R.id.Button01 );
    	
        btnclickme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	String str = "Sending Packets!";
            	Toast.makeText( getBaseContext(), str, Toast.LENGTH_SHORT ).show();
           	
            	//ServerThread.SendPacketBurst();
            	sendPacketMsg("1, 2, 3, 4");
            	
            	str = "Packets Away!";
            	Toast.makeText( getBaseContext(), str, Toast.LENGTH_SHORT ).show();

            }
        });                
    
    }

	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {

		Log.d(tag,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
		
	}

	private void sendPacketMsg( String strMsg ) {
		
		try {
			
			Log.i("UDP-SYS", "Sending Message...");
			//System.out.print( "sending udp message out" );
			
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("10.0.2.2");
			
			Log.i("UDP-SYS", "Acquired Socket");
			
			byte[] sendData = new byte[1024];
			
			String sentence = strMsg;
			sendData = sentence.getBytes();			
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9800);
			
			Log.i("UDP-SYS", "Packet created, attempting to send packet");
			
		    clientSocket.send(sendPacket);	
		    
		    //System.out.print( "message sent" );
		    Log.i("UDP-SYS", "Message SENT");
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			//System.out.print( e );
			Log.e("UDP-SYS-ERROR", e.toString() );
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//System.out.print( e );
			Log.e("UDP-SYS-ERROR", e.toString() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.out.print( e );
			Log.e("UDP-SYS-ERROR", e.toString() );
		}		
		
	}
	
	@Override
	public void onSensorChanged(int sensor, float[] values) {


		synchronized (this) {
			Log.d(tag, "onSensorChanged: " + sensor + ", x: " +	values[0] + ", y: " + values[1] + ", z: " + values[2]);
		}	
		

		
		
	}
}