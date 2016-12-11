// IActuationResult.aidl
package interdroid.actuators;

// Declare any non-default types here with import statements

interface IActuationResult {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void onData(int option, String result);

}
