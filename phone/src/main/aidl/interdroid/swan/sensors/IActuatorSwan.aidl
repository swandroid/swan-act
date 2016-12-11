// IActuatorSwan.aidl
package interdroid.swan.sensors;

// Declare any non-default types here with import statements

interface IActuatorSwan {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void getActuationParameters(String expression, String type,String parameters);
}
