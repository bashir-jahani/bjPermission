package bj.modules.bj_permission_objects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;



import java.util.Random;

import bj.modules.bj_permission.*;
import bj.modules.bj_permisson.R;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static bj.modules.bj_permission.*;

public class permissionCreateDialog extends AppCompatActivity {
	private static String TAG="permissionCreateDialog";
	TextView TXVPermission,TXVPermissionDSC;
	Button BTNYes,BTNNo;
	private static String permissionNeeded,  permissionNeedDescription;
	private static Context context;
	private TextView TXVTitle;
	public static OnGetPermissionListener _getPermissionListener;
	public static String TitleSet;
	private static int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
	private static final  boolean showInfo=false;
	public static boolean openMe(Context context, String PermissionNeeded, String PermissionNeedDescription, OnGetPermissionListener getPermissionListener){
		TitleSet=context.getResources().getString(R.string.title_Permission);
		if (showInfo)  Log.i(TAG, "openMe: PermissionNeeded: "+PermissionNeeded+" have: "+HavePermission(context,PermissionNeeded));
			if (!HavePermission(context,PermissionNeeded)) {
				permissionCreateDialog.	permissionNeeded = PermissionNeeded;
				permissionCreateDialog.permissionNeedDescription = PermissionNeedDescription;
				permissionCreateDialog.context = context;
				permissionCreateDialog._getPermissionListener=getPermissionListener;
				Random r = new Random();
				permissionCreateDialog.MY_PERMISSIONS_REQUEST_READ_CONTACTS= r.nextInt(100) + 10;

				Intent intent = new Intent(context, permissionCreateDialog.class);
				intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);

				return false;
			}else {
				if (getPermissionListener!=null){
					getPermissionListener.onPermissionProcesComplated(PermissionNeeded,true);
				}
				return true;
			}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_permission_create_dialog);

		TXVPermission=findViewById(R.id.APCDTXVPermission);
		TXVPermissionDSC=findViewById(R.id.APCDTXVPermissionDescription);
		TXVTitle=findViewById(R.id.APCDTXVTitle);
		BTNNo=findViewById(R.id.APCDBTNNo);
		BTNYes=findViewById(R.id.APCDBTNYes);

		TXVPermissionDSC.setText(permissionNeedDescription);
		TXVPermission.setText(permissionNeeded);
		TXVTitle.setText(TitleSet);
		BTNNo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				finish();
				if (showInfo)  Log.i("*************************","_getPermissionListener is nul: "+(_getPermissionListener==null));
				Toast.makeText(permissionCreateDialog.this, "The permission was rejected: "+permissionNeeded, Toast.LENGTH_SHORT).show();
				if (_getPermissionListener!=null){
					_getPermissionListener.onPermissionProcesComplated(permissionNeeded, HavePermission(context,permissionNeeded));
				}
			}
		});
		BTNYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(permissionCreateDialog.this, "Get permission action was taken: "+permissionNeeded, Toast.LENGTH_SHORT).show();
				ActivityCompat.requestPermissions(permissionCreateDialog.this,
						new String[]{permissionNeeded},
						MY_PERMISSIONS_REQUEST_READ_CONTACTS);
			}
		});

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode==MY_PERMISSIONS_REQUEST_READ_CONTACTS){
			finish();
			if (_getPermissionListener != null) {
				_getPermissionListener.onPermissionProcesComplated(permissionNeeded, HavePermission(context, permissionNeeded));
			}

		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}

