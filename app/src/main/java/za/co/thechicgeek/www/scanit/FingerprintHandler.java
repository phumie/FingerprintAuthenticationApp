package za.co.thechicgeek.www.scanit;

        import android.annotation.TargetApi;
        import android.content.Context;
        import android.content.pm.PackageManager;
        import android.hardware.fingerprint.FingerprintManager;
        import android.Manifest;
        import android.os.Build;
        import android.os.CancellationSignal;
        import android.support.v4.app.ActivityCompat;
        import android.widget.ImageView;
        import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context context;
    ImageView imageView;

    public FingerprintHandler(Context mContext, ImageView image) {
        context = mContext;
        imageView = image;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {

        Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show();
        imageView.setImageResource(R.drawable.fingerprint_red);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

        Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
        imageView.setImageResource(R.drawable.fingerprint_green);
    }

}
