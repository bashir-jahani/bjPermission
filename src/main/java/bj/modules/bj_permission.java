package bj.modules;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.StringRes;

import bj.modules.bj_messageBox_objcets.messageBox;
import bj.modules.bj_permission_objects.permissionCreateDialog;
import bj.modules.bj_permisson.R;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static bj.modules.bj_messageBox.BJMessagesButtonKind.*;
import static bj.modules.bj_messageBox.MessageBox;

public class bj_permission {
	public interface OnGetPermissionListener {
		void onPermissionProcesComplated(String PermissionNeeded, Boolean HavePermission);
	}
	public static boolean HavePermission(Context context, String PermissionNeeded){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
				&& PermissionNeeded != null) {
			if (  checkSelfPermission(context, PermissionNeeded) == PackageManager.PERMISSION_GRANTED) {
				return true;
			}else {
				return false;
			}
		}else {
			return true;
		}
	}

	public static boolean CheckPermision(Context context, String PermissionNeeded, String   PermissionNeedDescription, OnGetPermissionListener getPermissionListener){
		return permissionCreateDialog.openMe(context,PermissionNeeded,PermissionNeedDescription,getPermissionListener);
	}
	public static boolean CheckPermision(Context context, String PermissionNeeded, @StringRes int PermissionNeedDescriptionResourseID, OnGetPermissionListener getPermissionListener){
		return permissionCreateDialog.openMe(context,PermissionNeeded,context.getResources().getString(PermissionNeedDescriptionResourseID),getPermissionListener);
	}

	public static void GetPermissions(Context context, final Activity activety, final String PermissionNeeded, @StringRes int PermissionNeedDescriptionResourseID, final Boolean WaitUntil, OnGetPermissionListener getPermissionListener)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
				&& PermissionNeeded != null) {
			//Toast.makeText(G.MyContext, (ActivityCompat.checkSelfPermission(G.MyContext, PermissionNeeded) != PackageManager.PERMISSION_GRANTED)+"\n"+ActivityCompat.checkSelfPermission(G.MyContext, PermissionNeeded) + "==" + PackageManager.PERMISSION_GRANTED, Toast.LENGTH_SHORT).show();

			if (checkSelfPermission(context, PermissionNeeded) != PackageManager.PERMISSION_GRANTED) {

				if (WaitUntil) {
					Log.e("GGN","Cant wait for permission");
					try{

						MessageBox(R.string.title_Permission,PermissionNeedDescriptionResourseID,Yes_No,context,new messageBox.OnDialogResultListener(){
							@Override
							public boolean OnResult(Boolean dialogResult) {
								if (dialogResult) {
									try{
										requestPermissions(activety, new String[]{PermissionNeeded}, 1);
									}catch (Exception e1){
										Log.e("GGN","Permision Error 11:"+ e1.getMessage());
									}
									return true;
								}else {
									return false;
								}

							}
						});
					}catch (Exception e){
						Log.e("GGN","Permision Error 1:"+ e.getMessage());
					}

				}else {
					try{
						MessageBox(R.string.title_Permission,PermissionNeedDescriptionResourseID, Yes_No,context,new  messageBox.OnDialogResultListener(){
							@Override
							public boolean OnResult(Boolean dialogResult) {
								if (dialogResult) {
									try{
										requestPermissions(activety, new String[]{PermissionNeeded}, 1);
									}catch (Exception e1){
										Log.e("GGN","Permision Error 22:"+ e1.getMessage());
									}

									return true;
								}else {
									return false;
								}

							}
						});
					}catch (Exception e){
						Log.e("GGN","Permision Error 2:"+ e.getMessage());
					}
				}


			}
		}
	}
	public static void GetPermissions(final Context context, final Activity activety, final String PermissionNeeded, String PermissionNeedDescription, final Boolean WaitUntil)
	{

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
				&& PermissionNeeded != null) {
			if (checkSelfPermission(context, PermissionNeeded) != PackageManager.PERMISSION_GRANTED) {
				if (WaitUntil) {
					Log.e("GGN","Cant wait for permission");
					try{
						MessageBox(context.getResources().getString(R.string.title_Permission),PermissionNeedDescription, Yes_No,context,new messageBox.OnDialogResultListener(){
							@Override
							public boolean OnResult(Boolean dialogResult) {
								if (dialogResult) {
									requestPermissions(activety, new String[]{PermissionNeeded}, 1);
									return true;
								}else {
									return false;
								}

							}
						});
					}catch (Exception e){
						Log.e("GetPermissions 1","GetPermissions Error: "+e.getMessage());
					}

				}else {
					try{
						MessageBox(context.getResources().getString(R.string.title_Permission),PermissionNeedDescription, Yes_No,context,new messageBox.OnDialogResultListener(){
							@Override
							public boolean OnResult(Boolean dialogResult) {

								if (dialogResult) {
									requestPermissions( activety, new String[]{PermissionNeeded}, 1);

									return true;
								}else {
									return false;
								}

							}
						});
					}catch (Exception e){
						Log.e("GetPermissions 11","GetPermissions Error: "+e.getMessage());
					}
				}


			}
		}
	}
}
