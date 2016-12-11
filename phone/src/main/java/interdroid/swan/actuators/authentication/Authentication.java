package interdroid.swan.actuators.authentication;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;



import java.io.IOException;

import static rx.Notification.Kind.OnError;

/**
 * Created by rm on 18/10/2016.
 */

public class Authentication {

    /*public void authenticate(Context context) {

        AccountManager am = AccountManager.get(context);
        Bundle options = new Bundle();

        am.getAuthToken(
                myAccount_,                     // Account retrieved using getAccountsByType()
                "Manage your tasks",            // Auth scope
                options,                        // Authenticator-specific options
                this,                           // Your activity
                new OnTokenAcquired(),          // Callback called when a token is successfully acquired
                new Handler(new OnError()));    // Callback called if an error occur

    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            // Get the result of the operation from the AccountManagerFuture.
            try {
                Bundle bundle = result.getResult();
            }
            catch (IOException io){
            }
            catch (OperationCanceledException oe){

            }
            catch (AuthenticatorException ae){

            }
            // The token is a named value in the bundle. The name of the value
            // is stored in the constant AccountManager.KEY_AUTHTOKEN.
            token = bundle.getString(AccountManager.KEY_AUTHTOKEN);

            Intent launch = (Intent) result.getResult().get(AccountManager.KEY_INTENT);
            if (launch != null) {
                startActivityForResult(launch, 0);
                return;
            }

        }
    }*/

}
