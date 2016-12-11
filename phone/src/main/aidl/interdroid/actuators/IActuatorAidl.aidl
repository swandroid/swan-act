// IActuatorAidl.aidl
package interdroid.actuators;

// Declare any non-default types here with import statements

interface IActuatorAidl {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

     int getPid();
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void register(String deviceId,int anInt, String actuator, String options, String describeActuator,String packageName,String serviceName);

     void registerExpression(String expression, String trigger, String actuator, int option, int actuatorId);
}
