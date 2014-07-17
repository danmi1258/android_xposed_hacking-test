package com.github.xposed.crackmodule;

import android.content.pm.PackageManager;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class XposedMain implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals("com.github.crackme"))
			return;

		crackProApp(lpparam);
	}

	private void crackProApp(LoadPackageParam lpparam) {
		findAndHookMethod("android.app.ApplicationPackageManager",
				lpparam.classLoader, "getPackageInfo", String.class, int.class,
				new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						String packageName = (String) param.args[0];

						// pretend the app to exist
						if (packageName.equals("com.github.crackme.pro"))
							param.args[0] = "com.github.crackme";
					}
				});

		findAndHookMethod("android.app.ApplicationPackageManager",
				lpparam.classLoader, "checkSignatures", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						String pkg1 = (String) param.args[0];
						String pkg2 = (String) param.args[1];

						// pretend matching signatures
						if (pkg2.equals(pkg1+".pro"))
							param.setResult(PackageManager.SIGNATURE_MATCH);
					}
				});
	}

}